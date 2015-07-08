package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import error.MyException;

public class FastQ implements FiletypeContainingSequences{

	private ArrayList<FastQSequence> seqList;
	private ArrayList<MyException> errList;
	
	public FastQ(){
		this.seqList = new ArrayList<FastQSequence>;
		this.errList = new ArrayList<MyException>();
	}
	public void parse(String inputFilename) throws IOException, MyException {
		File file = new File(inputFilename);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		
		String temp = "";
		String sequence = "";
		String identity = "";
		String addInfo = "";
		String score = "";
		int counter = 0;

		// assumption: fastq contains excactly 4 lines as it is most widely used
		// according to wikipedia
		while ((temp = bufferReader.readLine()) != null) {
			if (counter == 0 && temp.startsWith("@")) {
				identity = temp;
				counter++;
			} else if (counter == 1 && temp == temp.toUpperCase()) {
				sequence = temp;
				counter++;
			} else if (counter == 1) {
				identity = "";
				sequence = "";
				addInfo = "";
				score = "";
				counter = 0;
			} else if (counter == 2 && temp.startsWith("+")) {
				addInfo = temp;
				counter++;
			} else if (counter == 2) {
				identity = "";
				sequence = "";
				addInfo = "";
				score = "";
				counter = 0;
			} else if (counter == 3) {
				score = temp;
				processRead(identity, sequence,addInfo,score);
				identity = temp;
				sequence = "";
				addInfo = "";
				score = "";
				counter = 0;
			}
		}
		processRead(identity, sequence,addInfo,score);
		bufferReader.close();
	}
		
		
	}

	public void writeInFile(String filename) {
		// TODO Auto-generated method stub
		
	}

	public void addSeq(Sequence seq) throws MyException {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Sequence> getSequence() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MyException> getErrorInSequence() {
		// TODO Auto-generated method stub
		return null;
	}
	

}


//public class FastQParser {
//
//	private BufferedReader bufferReader;
//	private ArrayList<FastQEntry> fastQList;
//
//	/**
//	 * Gets a file name and creates a new FastQPArser object just for this file. The file is then processed. 
//	 * @param inputFile
//	 * @throws IOException
//	 */
//	public FastQParser(String inputFile) throws IOException {
//		// so it's not necessary to type in the file ending, just the name
//		inputFile += ".fastq";
//		File file = new File(inputFile);
//		FileReader fileReader = new FileReader(file);
//		this.bufferReader = new BufferedReader(fileReader);
//		this.fastQList = new ArrayList<FastQEntry>();
//
//		run();
//
//	}
//
//	private void run() throws IOException {
//		processFile();
//		printFile();
//	}
//
//	private void printFile() {
//		System.out.println("We found the following sequences: ");
//		for (FastQEntry entry : fastQList) {
//			System.out.println(entry.getIdentity() + "\n" + entry.getSequence() + "\n"
//					+ entry.getAddInfo() + "\n" + entry.getScore());
//
//		}
//	}
//
//	/**
//	 * Each line is processed. Lines staring with an '@', as long as they can't be a scoring line are stored in identity, same for sequences(only upper case letters, additional Information('+') and the score.
//	 * If a line has '@' as it's first letter and the score is not read everything gets reset(since it can't be a correctly displayed sequence) and it is retried to store the following potential sequence.
//	 * In case the scoring line starts with an '@' but is in fact the next sequence header, so far we can only check if the lengths of the sequence and the header are differing from each other.
//	 * Worst case scenario would be that they share the same length although the header is not the scoring line. This would cause the next sequence if it is displayed correctly to be thrown out.
//	 * This seems fairly unlikely but it should be caught regardless. One possibility would be to check specifically for Illuminasince they have further requirements for the header.
//	 * @throws IOException
//	 */
//
//	private void storeSequence(String sequence, String identity,
//			String addInfo, String score) {
//		FastQEntry fQEntry = new FastQEntry(identity, sequence, addInfo, score);
//		fastQList.add(fQEntry);
//
//	}
//
//	private boolean entryCorrect(String sequence, String identity,
//			String addInfo, String score) {
//		if (!(sequence.isEmpty() || identity.isEmpty() || addInfo.isEmpty() || score
//				.isEmpty()) && sequence.length() == score.length()) {
//			return true;
//		}
//		return false;
//	}
//
//	public ArrayList<FastQEntry> getFastQList() {
//		return fastQList;
//	}
//
//	public void setFastQList(ArrayList<FastQEntry> fastQList) {
//		this.fastQList = fastQList;
	}
