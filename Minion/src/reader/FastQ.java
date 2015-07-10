package reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import error.ErrorCodes;
import error.MyException;

/**
 * 
 * @author Friederike Hanssen
 * @functionality A FastQ object can parse a file or add existing sequences to itself. It implements the FiletypeContaing sequences interface.
 * A fastq object is assumed to have conventional format without line breaks. According to wikipedia it consists of 4 lines: header, sequence, metadata,score, with sequence and score being of the same length
 * 
 * @input It doesn't have an input. FastQ contains two array lists of the same length. One stores the entry in the fastq file the other some kind of error information.
 * @output outputs the data in an output file
 */
public class FastQ implements FiletypeContainingSequences{

	private ArrayList<Sequence> seqList;
	private ArrayList<MyException> errList;
	
	public FastQ(){
		this.seqList = new ArrayList<Sequence>();
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
			} else if (counter == 1) {
				sequence = temp;
				counter++;
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
				identity = "";
				sequence = "";
				addInfo = "";
				score = "";
				counter = 0;
			}
		}
		processRead(identity, sequence,addInfo,score);
		bufferReader.close();
	}
		
	
	private void processRead(String header,String sequence, String addInfo, String score){
		
		try{
			checkForReadingError(header,sequence,addInfo,score);
			Sequence seq = new FastQSequence(header, sequence,addInfo,score);
			MyException err = new MyException(ErrorCodes.NO_ERROR);
			seqList.add(seq);
			errList.add(err);
		}catch(MyException e){
			if(e.getErrorCode() == 2003 || e.getErrorCode() == 2010){//gapped seq||no sequnce name
				Sequence seq = new FastQSequence(header, sequence,addInfo,score);
				seqList.add(seq);
				errList.add(e);
			}else{
			Sequence seq = new FastQSequence(null,null,null,null);
			seqList.add(seq);
			errList.add(e);
			}
		}
	}
	
	private void checkForReadingError(String identity, String sequence, String addInfo, String score) throws MyException{
		
		char[] id = identity.toCharArray();
		boolean emptyName = true;
		for (int i = 1; i < id.length; i++) {
			if (id[i] != ' ') {
				emptyName = false;
				break;
			}
		}
		if(emptyName){
			throw new MyException(ErrorCodes.NO_SEQUENCE_NAME);
		}
		
		if(sequence.isEmpty()){
			throw new MyException(ErrorCodes.NO_SEQUENCE);
		}
		
		if(sequence.contains("-")){
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
		
		if(!(score.length() == sequence.length())){
			throw new MyException(ErrorCodes.INCORRECT_SEQUENCE_SCORE);
		}
		
		
	}

	public void writeInFile(String outputFilename) {
		File writeInFile = new File(outputFilename);
		try {
			new FileOutputStream(writeInFile,true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			try
			{
				FileWriter fw = new FileWriter("./" + writeInFile,true);
				BufferedWriter bw = new BufferedWriter(fw);
				for (int i = 0; i < seqList.size();i++) {
					bw.append(seqList.get(i).getHeader());
					bw.newLine();
					bw.append(seqList.get(i).getSequence());
					bw.newLine();
					bw.append(((FastQSequence) seqList.get(i)).getAddInfo());
					bw.newLine();
					bw.append(seqList.get(i).getScore());
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

//			//TODO check if user should be informed about the fact that the filename already exists, so far new content gets appended to old one

	}

	public void addSeq(Sequence seq) throws MyException {
		// TODO Auto-generated method stub
		processRead(seq.getHeader(),seq.getSequence(),((FastQSequence) seq).getAddInfo(), seq.getScore());
	}

	public ArrayList<Sequence> getSequence() {
		// TODO Auto-generated method stub
		return seqList;
	}

	public ArrayList<MyException> getErrorInSequence() {
		// TODO Auto-generated method stub
		return errList;
	}
	
//public static void main(String[] args) throws IOException, MyException{
//	FiletypeContainingSequences fastQ = new FastQ();
//	Sequence seq = new FastQSequence("me","ACTG","+ a FastQ test", "$%^&");
//	fastQ.addSeq(seq);
//	fastQ.parse("C:/Users/Friederike/University/Fourth Semester/Programmierprojekt/git/MinION2015/Minion/src/FastQTestFile.fastq");
//	fastQ.writeInFile("TestFastQWirteInfile.txt");
//}
}


	
