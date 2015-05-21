package reader;
//TODO FileNot FOund exception
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import error.*;
//works for most tested files but not for some.
/**
* @author Friederike Hanssen
* Contract: This class gets a filename and construct an object containing two array list, 
* one containing all correct reads, one containing all incorrect ones including all the information about the occured error.
* We will count all correct and incorrect sequences to keep track of where errors occur.
 */
	public class ParseFastA{
	
	private BufferedReader bufferReader;
	private ArrayList<FastAEntry> fastAList;
	private ArrayList<IncorrectFastAEntry> fastAErrorList;
	
	/**
	 * First of all it is checked if the file name is correct.(This should better not happen in the constructor, but i couldn't find a better solution yet)
	 * If it is a valid file name we will parse it, else the incorrectFastAEntry list will only contain this error and the nothing will be parsed
	 * @param inputFile 
	 * @throws IOException
	 */
	public ParseFastA (String inputFile) throws IOException, MyException{
		
		try{

			isFileName(inputFile);
			
			//Needed to process the file any further
			File file = new File(inputFile); 
			FileReader fileReader = new FileReader(file); 
			bufferReader = new BufferedReader(fileReader);
			
			fastAList = new ArrayList<FastAEntry>();
			fastAErrorList = new ArrayList<IncorrectFastAEntry>();
			
			//the file will be parsed further if it's valid
			parseFile();
			print();
			
		}catch(MyException e){
			//If the sequence is incorrect a IncorrectFastAEntry object is created. 
			//It always consists of all the information we so far gather about an error: 
			//positionParsed(-1 -> none parsed), Code from the database, message and critical to determine later if it deserves a popup
			int positionParsed = -1;
			int errorCode = e.getErrorCode();
			String errorMessage = e.getErrorMessage();
			boolean isCritical = e.isCriticalError();
			
			fastAList = new ArrayList<FastAEntry>();
			fastAErrorList = new ArrayList<IncorrectFastAEntry>();
			
			//fastAList can't contain anything, since there are none sequences read
			FastAEntry fastAEntry = new FastAEntry(null, null);
			fastAList.add(fastAEntry);
			
			IncorrectFastAEntry incorretFastAEntry = new IncorrectFastAEntry(positionParsed,errorCode,errorMessage, isCritical);
			fastAErrorList.add(incorretFastAEntry);
			
			print();
		}catch(IOException x){
			
		}	
		
	}

	/**
	 * checks for .fasta ending, thus if the input is a correct file type. Anything else can't be parsed
	 */
	private void isFileName(String inputFile) throws MyException {
		
		if(!inputFile.endsWith(".fasta")){
			throw new MyException(ErrorCodes.BAD_FILETYPE);
		}
	}

	
	/**
	 * 
	 * @throws IOException
	 * 
	 * @param temp: saves the read line temporarly
	 * @param sequence: stores the sequence
	 * @param identity: stores the identity
	 * 
	 * Reads each line. If the first sign was a >, the whole line is saved in identity. The following lines are stored in sequence until the next > is found.
	 *If we hit a new > everything is checked for errors. The newline starting with > is stored and the rest is read.
	 */
	private void parseFile() throws IOException{
		
		String temp = "";
		String sequence = "";
		String identity ="";
		int counter = 0;
		int amountParsed = 0;
		
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
//					System.out.println(identity);
//					System.out.println(sequence);
					amountParsed++;
					processRead(identity,sequence,amountParsed);
					identity = temp;
					sequence ="";
				}
			}
		}
//		System.out.println(identity);
//		System.out.println(sequence);
		amountParsed++;
		processRead(identity, sequence,amountParsed);
		
		
	}

	
	/**
	 * Each entry is checked for correctness before stored in the corresponding list
	 */
	private void processRead(String identity,String sequence, int amountParsed) {	
	
		try{
			checkForReadingError(identity, sequence);
			
			//If there is no reading error, we have a new sequence parsed and store it.
			FastAEntry fastAEntry = new FastAEntry(identity, sequence);
			fastAList.add(fastAEntry);
			
		}catch(MyException e){
			//creating new incorrect fasta entry object and stores it
			int errorCode = e.getErrorCode();
			String errorMessage = e.getErrorMessage();
			boolean isCritical = e.isCriticalError();		
			
			IncorrectFastAEntry incorretFastAEntry = new IncorrectFastAEntry (amountParsed, errorCode,errorMessage,isCritical);
			fastAErrorList.add(incorretFastAEntry);
		}

	}
	
	/**
	 * This class works together with the error package and figures out the correct error.
	 * Possible errors are:
	 * no name,no sequence, corrupted sequence(gaps, non nucleotids...) -> critical
	 * lowercase of ACTG -> non critical
	 * The if's are sorted in a way that critical errors are found first.
	 * @param identity
	 * @param sequence
	 * @return
	 * @throws MyException
	 */
	private int checkForReadingError(String identity, String sequence) throws MyException{
		
		
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
		
		
		if(sequence.isEmpty()){
			throw new MyException(ErrorCodes.NO_SEQUENCE);
		}
		
		if(sequence.contains("-")){
			throw new MyException(ErrorCodes.GAPPED_SEQUENCE);
		}
		
		
		for(int i = 0; i < sequence.length(); i++){
			if((!sequence.contains("A") || !sequence.contains("C")||!sequence.contains("T")||!sequence.contains("G"))
					&& (!sequence.contains("a") || !sequence.contains("c")||!sequence.contains("t")||!sequence.contains("g")) )
				throw new MyException(ErrorCodes.CORRUPTED_SEQUENCE);
		}
		
		if(sequence != sequence.toUpperCase()){
			throw new MyException(ErrorCodes.LOWERCASE_SEQUENCE);
		}
		
		return 0;
	}
	
	public ArrayList<IncorrectFastAEntry> getFastAErrorList() {
		return fastAErrorList;
	}

	public ArrayList<FastAEntry> getFastAList() {
		return fastAList;
	}
	
	
	
	// TEST
	private void print() {
		for (FastAEntry entry : fastAList) {
			System.out.println(entry.getIdentity() + "\n" + entry.getSequence()
					+ "\n");
		}

		for (IncorrectFastAEntry entry : fastAErrorList) {
			System.out.println("We found the following errors \n"
					+ entry.getPositionParsed() + "\n"
					+ entry.getErrorMessage() + "\n" + entry.getErrorCode()
					+ "\n" + entry.isCritical() + "\n");
		}

	}

	public static void main(String[] args) throws IOException, MyException{
		if(args.length != 1){
			System.err.println("You should specify a FastA file as input!");
			System.exit(1);
		}else{
			ParseFastA pfastA = new ParseFastA(args[0]);
	
		}

	}
	
	
	
}