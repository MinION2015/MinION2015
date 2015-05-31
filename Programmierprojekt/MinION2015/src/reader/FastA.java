package reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import error.ErrorCodes;
import error.MyException;

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
		bufferReader.close();
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
			
			Sequence seq = new Sequence(null,null);
			if(!isCritical){
				seq = new Sequence(header, sequence);
			}
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
	public void writeInFile(String filename) {
		// TODO Auto-generated method stub
		File write_file = new File(filename);
		try
		{
			FileWriter fw = new FileWriter(write_file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < seqList.size();i++) {
				bw.append(seqList.get(i).getHeader());
				bw.newLine();
				bw.append(seqList.get(i).getSequence());
				bw.newLine();
				bw.append("Errors found:");
				bw.newLine();
				bw.append(errList.get(i).getErrorMessage());
				bw.newLine();
			}

			
			bw.close();
			fw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}


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
	
//	private void print() {
//	for (Sequence entry : seqList) {
//		System.out.println(entry.getHeader() + "\n" + entry.getSequence()
//				+ "\n");
//	}
//
//	for (ErrorInSequence entry : errList) {
//		System.out.println("We found the following errors \n"
//				+ entry.getErrorMessage() + "\n" + entry.getErrorCode()
//				+ "\n" + entry.isCritical() + "\n");
//	}
//
//}
	
	public static void main(String[] args) throws IOException, MyException{
		if(args.length != 1){
			System.err.println("You should specify a FastA file as input!");
			System.exit(1);
		}else{
			
			FastA pfastA = new FastA();
			pfastA.parse(args[0]);
			pfastA.writeInFile("C:/Users/Friederike/Desktop/MinionTestParser/MinionTestParser.txt");
			
		}

	}

}
