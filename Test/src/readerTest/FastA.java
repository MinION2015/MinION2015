package readerTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import error.*;

public class FastA implements TestFormat {

	private ArrayList<Sequence> seqList;
	private ArrayList<ErrorInSequence> errList;

	public FastA() {
		this.seqList = new ArrayList<Sequence>();
		this.errList = new ArrayList<ErrorInSequence>();
	}

	@Override
	public void parse(String inputFilename) throws IOException, MyException {
		File file = new File(inputFilename);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferReader = new BufferedReader(fileReader);

		String temp = "";
		String sequence = "";
		String identity = "";
		int counter = 0;

		while ((temp = bufferReader.readLine()) != null) {

			if (counter == 0 && temp.startsWith(">")) {
				identity = temp;
				counter++;
				continue;
			}
			if (counter == 1) {

				if (!temp.startsWith(">")) {
					sequence += temp;
				} else {
					// System.out.println(identity);
					// System.out.println(sequence);
					processRead(identity, sequence);
					identity = temp;
					sequence = "";
				}
			}
		}
		// System.out.println(identity);
		// System.out.println(sequence);
		processRead(identity, sequence);
	}

	private void processRead(String header, String sequence) throws MyException {

		try {
			checkForReadingError(header, sequence);
			Sequence seq = new Sequence(header, sequence);
			ErrorInSequence err = new ErrorInSequence(-1, null, false);
			seqList.add(seq);
			errList.add(err);

		} catch (MyException e) {
			// creating new incorrect fasta entry object and stores it
			int errorCode = e.getErrorCode();
			String errorMessage = e.getErrorMessage();
			boolean isCritical = e.isCriticalError();

			Sequence seq = new Sequence(header, sequence);
			ErrorInSequence err = new ErrorInSequence(errorCode, errorMessage,
					isCritical);

			seqList.add(seq);
			errList.add(err);
		}

	}

	private int checkForReadingError(String identity, String sequence)
			throws MyException {

		char[] id = identity.toCharArray();
		boolean emptyName = true;
		for (int i = 1; i < id.length; i++) {
			if (id[i] != ' ') {
				emptyName = false;
				break;
			}
		}

		if (emptyName) {
			throw new MyException(ErrorCodes.NO_SEQUENCE_NAME);
		}

		if (sequence.isEmpty()) {
			throw new MyException(ErrorCodes.NO_SEQUENCE);
		}

		if (sequence.contains("-")) {
			throw new MyException(ErrorCodes.GAPPED_SEQUENCE);
		}

		for (int i = 0; i < sequence.length(); i++) {
			if ((!sequence.contains("A") && !sequence.contains("C")
					&& !sequence.contains("T") && !sequence.contains("G"))
					&& (!sequence.contains("a") && !sequence.contains("c")
							&& !sequence.contains("t") && !sequence
								.contains("g"))) {
				throw new MyException(ErrorCodes.CORRUPTED_SEQUENCE);
			}
		}

		if (sequence != sequence.toUpperCase()) {
			throw new MyException(ErrorCodes.LOWERCASE_SEQUENCE);
		}

		return 0;
	}

	@Override
	public void writeInFile() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printOutFile() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Sequence> getSequence() {
		// TODO Auto-generated method stub
		return seqList;
	}

	@Override
	public ArrayList<ErrorInSequence> getErrorInSequence() {
		// TODO Auto-generated method stub
		return errList;
	}

}
