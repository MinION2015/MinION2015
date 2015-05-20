package reader;

//TODO counter for sequences parsed 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import error.*;
/**
 * 
 * @author Friederike Hanssen

 *
 */
	public class ParseFastA{
	
	private BufferedReader bufferReader;
	private ArrayList<FastAEntry> fastAList;
	private ArrayList<IncorrectFastAEntry> fastAErrorList;
	
	/**
	 * 
	 * @param inputFile gets the via the main written in String
	 * @throws IOException
	 */
	public ParseFastA (String inputFile) throws IOException{
		
		try{
			isFileName(inputFile);
			
			File file = new File(inputFile); 
			FileReader fileReader = new FileReader(file); 
			bufferReader = new BufferedReader(fileReader);
			
			fastAList = new ArrayList<FastAEntry>();
			fastAErrorList = new ArrayList<IncorrectFastAEntry>();
			
			run();
		}
		catch(MyException e){
			
			int errorCode = e.getErrorCode();
			String errorMessage = e.getMessage();
			boolean isCritical = e.isCriticalError();
			
			fastAList = new ArrayList<FastAEntry>();
			fastAErrorList = new ArrayList<IncorrectFastAEntry>();
			
			FastAEntry fastAEntry = new FastAEntry(null, null);
			fastAList.add(fastAEntry);
			IncorrectFastAEntry incorretFastAEntry = new IncorrectFastAEntry (-1,errorMessage, errorCode,isCritical);
			fastAErrorList.add(incorretFastAEntry);
		}
		
		
		
		
	}



	private void isFileName(String inputFile) throws MyException {
	
		if(!inputFile.endsWith(".fasta")){
			throw new MyException(ErrorCodes.BAD_FILETYPE);
		}
		
	}



	private void run() throws IOException {
		processFile();
	}



//	private void print() {
//		for(FastAEntry entry : fastAList){
//			System.out.println("We found the following sequence \n" + entry.getIdentity() + "\n" + entry.getSequence()+"\n");
//		}
//	}
	
	
	
	/**
	 * 
	 * @throws IOException
	 * 
	 * @param temp: saves the read line temporarly
	 * @param sequence: stores the sequence
	 * @param identity: stores the identity
	 * 
	 * Reads each line. If the first sign was a >, the whole line is saved in identity. The following lines are stored in sequence until the next > is found
	 * Identity and sequence are then stored as a FastAEntry object in an ArrayList
	 * If nothing is written in the sequence it is thrown out and not stored
	 */
	private void processFile() throws IOException{
		
		String temp = "";
		String sequence = "";
		String identity ="";
		boolean checkEntry = false;
		int counter = 0;
		
		while((temp = bufferReader.readLine()) != null){
			
			if(counter == 0 && temp.startsWith(">")){
				identity = temp;
				counter++;
				continue;
			}
			if(counter == 1){

				if(!temp.startsWith(">")){
					sequence += temp;
				}else{
					processRead(identity,sequence);
					identity = temp;
					sequence ="";
				}
			}
		}
		processRead(sequence, identity);
		
		
	}



	private void processRead(String sequence, String identity) {
		int amountParsed =0;
		try{
			readerError(identity, sequence);
			FastAEntry fastAEntry = new FastAEntry(identity, sequence);
			fastAList.add(fastAEntry);
			amountParsed++;
		}catch(MyException e){
			int errorCode = e.getErrorCode();
			String errorMessage = e.getMessage();
			boolean isCritical = e.isCriticalError();
			amountParsed++;
			IncorrectFastAEntry incorretFastAEntry = new IncorrectFastAEntry (amountParsed,errorMessage, errorCode,isCritical);
			fastAErrorList.add(incorretFastAEntry);
		}
	}
	
	private int readerError(String identity, String sequence) throws MyException{
		//non gapped, correctsequence, that has a name
		if(sequence==""){
			throw new MyException(ErrorCodes.NO_SEQUENCE);
		}
		if(sequence != sequence.toUpperCase()){
			throw new MyException(ErrorCodes.LOWERCASE_SEQUENCE);
		}
		char[] seq = sequence.toCharArray();
		for(int i = 0; i < seq.length; i++){
			if(seq[i] != 'A' || seq[i] != 'C'||seq[i] != 'T'||seq[i] != 'G')
				throw new MyException(ErrorCodes.CORRUPTED_SEQUENCE);
		}
		
		
		char[] id = identity.toCharArray();
		boolean emptyName = true;
		for(int i = 1; i< id.length; i++){
			if(id[i] != ' '){
				emptyName = false;
				break;
			}
		}
		if(emptyName){
			throw new MyException(ErrorCodes.NO_SEQUENCE_NAME);
		}
		
		
		return 0;
	}
	
	private void setFastAErrorList(ArrayList<IncorrectFastAEntry> fastAErrorList) {
		this.fastAErrorList = fastAErrorList;
	}



	public ArrayList<IncorrectFastAEntry> getFastAErrorList() {
		return fastAErrorList;
	}



	public ArrayList<FastAEntry> getFastAList() {
		return fastAList;
	}

	private void setFastAList(ArrayList<FastAEntry> fastAList) {
		this.fastAList = fastAList;
	}

//	public static void main(String[] args) throws IOException{
//		 	
//		if(args.length != 1){
//			System.err.println("You should specify a FastA file as input!");
//			System.exit(1);
//		}else{
//			ParseFastA pfastA = new ParseFastA(args[0]);
//			
//		}
//
//	}
	
}