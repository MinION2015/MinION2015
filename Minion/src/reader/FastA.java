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
/**
 * 
 * @author Friederike Hanssen
 * A FastA object can parse a file or add existing sequences to itself.
 * It doesn't have an input. FastA contains two array lists of the same length. One stores the entry in the fastA file the other some kind of error information.
 * It outputs the data in an output file
 */
public class FastA implements FiletypeContainingSequences {

	private ArrayList<Sequence> seqList;
	private ArrayList<MyException> errList;

	/**
	 * FastA object contains two array lists, one storing correct sequences, one storing occurring errors.
	 */
	public FastA() {
		this.seqList = new ArrayList<Sequence>();
		this.errList = new ArrayList<MyException>();
	}

	
	/**
	 * Gets a filename and parses each sequence.
	 * @param inputFilename gets the inputFilename
	 */
	public void parse(String inputFilename) throws IOException,MyException{
	
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

	/**
	 * Each read is checked for errors and accordingly stored. Error containing sequences are not stored but a sequence object containing null instead. 
	 * The error in sequence list is built as the counterpart, anytime there is no error, a sequence object is stored in the sequence list, while in the error list there is an object containing null.
	 * 
	 * @param header
	 * @param sequence
	 * @throws MyException
	 */
	private void processRead(String header, String sequence) throws MyException {

		try {
			checkForReadingError(header, sequence);
			Sequence seq = new Sequence(header, sequence);
			MyException err = new MyException(ErrorCodes.NO_ERROR);
			seqList.add(seq);
			errList.add(err);

		} catch (MyException e){
			
			Sequence seq = new Sequence(null,null);
			seqList.add(seq);
			errList.add(e);
		}

	}
/**
 * Each read is checked for errors, if an exception is thrown.
 * @param identity
 * @param sequence
 * @return
 * @throws MyException
 */
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

	/**
	 * Writes results in the given filename. Each sequence in the order: Entry\\header\\sequence\\Errors:\\(error messages||null)
	 */

	public void writeInFile(String outputFilename) {
		File write_file = new File(outputFilename);
		try
		{
			FileWriter fw = new FileWriter(write_file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < seqList.size();i++) {
				bw.append("Entry:");
				bw.newLine();
				bw.append(seqList.get(i).getHeader());
				bw.newLine();
				bw.append(seqList.get(i).getSequence());
				bw.newLine();
				bw.append("Errors found:");
				bw.newLine();
				bw.append(errList.get(i).getErrorMessage());
				bw.newLine();
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


	
	public ArrayList<Sequence> getSequence() {
		return seqList;
	}


	public ArrayList<MyException> getErrorInSequence() {
		return errList;
	}
	
	/**
	 * Adds a single sequence to an existing fasta file and processes it accordingly
	 */
	public void addSeq(Sequence seq) throws MyException{
		
		processRead(seq.getHeader(),seq.getSequence());
	}

	/**
	 * Test
	 */

//	public static void main(String[] args) throws IOException, MyException{
//		if(args.length != 1){
//			System.err.println("You should specify a FastA file as input!");
//			System.exit(1);
//		}else{
//			
//			FastA pfastA = new FastA();
//			pfastA.parse(args[0]);
//			pfastA.writeInFile("C:/Users/Friederike/Desktop/MinionTestParser/Test2.txt");
//			
//		}
//
//	}

}
