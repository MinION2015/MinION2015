package controller;

import error.Chance;
import error.ErrorCodes;
import error.MyException;
import gui.GUIOptions;

import java.io.IOException;
import java.util.ArrayList;

import reader.*;
import Basecalling.BasecallingErrorRate;
import LengthDistribution.LengthDistribution;


/**
 * 
 * @author Friederike Hanssen
 *@Functionality Controllers runs the program. First, the file ending is checked, if it is a fasta ending, a new FastA object is created, if fastQ a new fastQ object. The user can additionally decide if he wants fastA or fastA as output.
 *The program only runs if file either has a fasta ending or a fastq ending . 
 *It sets up the Error model and the length distribution so they can be used everywhere in the program, initliazes the flowcell and applies some kind of time 
 *between the controller and the flowcell still need to figure out loggin of results.
 *@input GUIOptions object containing all necessary information
 *@output file with results
 */
/**
 * TODO : comments, class that will create a FastQ read(Kevin?), changing errormodel accroding to time, maybe some kind of tryToChangeModelFunction that gets called and changes accroding to numbers of times aslked(a bit like Pore)
 * Write more testcases, think about if start flowcell is actually needed,  change tests using absolute path, how does pore know what to write
 * add contracts
 * @author Friederike
 *
 */
public class Controller {
	
	private GUIOptions options;
	private FiletypeContainingSequences inputFile;
	private FiletypeContainingSequences outputFile;
	private Flowcell flowcell;
	private String status; //"Running","Paused","Stopped"
	private int currentNumberOfTicks;
	private boolean hasBeenStopped =false;
	
//	public Controller(){
//		
//	} //used for testing
/**	
 * @param options
 */
	public Controller(GUIOptions options){
		System.out.println("New Controller is created.");
		this.options = options;
		//first its determined if a new fasta or fastq file was the input
		//then which output the user wishes
		//all options are inititalized
		try{
			createInputFormat(options.getInputFilename());
			createOutputFormat(options.getOutputFormat());
			initialize(options);
		}catch(MyException e){
			System.err.println("Error in controller constructor: "+e.getErrorMessage());
		}catch(Exception e){
			System.err.println("Error in controller constructor: "+e.getMessage());
		}
		
	}


	public void run() throws MyException{
		System.out.println("Run is called");
		try{	
			//Sequence seq = new FastASequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
	
			while((currentNumberOfTicks < options.getTotalNumberOfTicks()) && !checkIfStoppedYet()  && flowcell.getNumberOfAlivePores() > 0){
				
				int pos = Chance.getRandInt(0, inputFile.getSequence().size()-1);
				flowcell.tick(inputFile.getSequence().get(pos));
				if(options.getWriteInFileOption().equals("Real-Time")){
					try{
						flowcell.getFlowcellOutput().writeInFile(options.getOutputFilename());
						Thread.sleep(options.getDurationOfTick());
					}catch(Exception e){
						System.err.println(e.getMessage());
					}
				}else if(options.getWriteInFileOption().equals("Write all")){
					try{
						for(Sequence s : flowcell.getFlowcellOutput().getSequence()){
							outputFile.addSeq(s);
						}
						Thread.sleep(options.getDurationOfTick());
					}catch(Exception e){
						System.err.println(e.getMessage());
					}
				}
				currentNumberOfTicks++;
				
			}
			if(options.getWriteInFileOption().equals("Write all")){
				outputFile.writeInFile(options.getOutputFilename());
			}
			System.out.println("Run was executed without throwing errors");
		}catch(MyException e){
			System.err.println("Run method in Controller: "+e.getErrorMessage());
			throw new MyException(ErrorCodes.CONTROLLER_NOT_RUNNING);
		}catch(Exception e){
			System.err.println("Run method in Controller2: "+e.getMessage());
			throw new MyException(ErrorCodes.CONTROLLER_NOT_RUNNING);
		}	
	}

	public void resume() throws MyException{
		
		try{
				checkIfStoppedYet();
				if(status.equals("Paused")){
					status = "Running";
					System.out.println("Program resumed");
					System.out.println("currentNum Ticks when resuming(should be equal to when pausing): "+currentNumberOfTicks);
					run();

				}
		}catch(MyException e){
			throw new MyException(ErrorCodes.CONTROLLER_NOT_RESUMING);
		}
	}
	
	public void pause() throws MyException{
		//int counter=0; //used for testing
		try{
			checkIfStoppedYet();
			status = "Paused";
			System.out.println("Program paused");
			System.out.println("Current number of ticks when pausing: "+currentNumberOfTicks);
			while(status.equals("Paused")){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					System.err.println("Thread.sleep in pause throws following error: "+e.getMessage());
				}
				//for testing purposes: after 300 ticks something should happen and controller not be paused anymore
//				counter++;
//				if(counter == 3000){
//					resume();
//				}

			}
		}catch(MyException e){
			throw new MyException(ErrorCodes.CONTROLLER_NOT_PAUSING);
		}
	}
	
	public void stop() throws MyException{
		
		status = "Stopped";
		System.out.println("Program stopped");
		currentNumberOfTicks = options.getTotalNumberOfTicks();	
	}
	
	private boolean checkIfStoppedYet() throws MyException{
		if(status.equals("Stopped")){
			hasBeenStopped = true;
			System.out.println("Program has been stopped: "+hasBeenStopped);
			throw new MyException(ErrorCodes.CONTROLLER_NOT_RUNNING);
		}
		
		return hasBeenStopped;
	}

	
	public ArrayList<MyException> getInputFileErrors() {
		return inputFile.getErrorInSequence();
	}
	public ArrayList<MyException> getOutputFileErrors(){
		return outputFile.getErrorInSequence();
	}
	public Flowcell getFlowcell()
	{
		return flowcell;
	}

	//throws error: For input string: "0.1#"
	private static void setupModel(int basecalling, String settingfile, int windowSize) throws Exception{
		
			LengthDistribution lengthDistribution = new LengthDistribution(windowSize);
			System.out.println("SetupModel method in Controller created new length distribution.");
		
			//TODO error occurs when trying to set up basecalling method
			BasecallingErrorRate basecallingError = new BasecallingErrorRate(basecalling,settingfile);
			System.out.println("SetupModel method in Controller created new basecalling error rate.");
		
	}
	
	/**
	 * Either a new fasta or fastq inputFile is created depending on gui input
	 * @param filename
	 * @throws MyException
	 */
	private void createInputFormat(String filename) throws MyException{
		if(filename.endsWith(".fasta")){
			inputFile = new FastA();
			System.out.println("new FastA file created");
		}else if(filename.endsWith(".fastq")){
			inputFile = new FastQ();
			System.out.println("new FastQ file created");
		}else{
			throw new MyException(ErrorCodes.BAD_FILETYPE);
		}
	}

	/**
	 * According to user input a new output format filetype is created (is still stored as txt, but the user can choose in the controller if he wants fasta or fastq as output and accordingly the sequencing should happen
	 * @param format fasta or fastq as String
	 * @throws MyException
	 */
	private void createOutputFormat(String format) throws MyException{
		
		if(format == "fasta"){
			outputFile = new FastA();
			System.out.println("fastA output created");
		}else if(format == "fastq"){
			outputFile = new FastQ();
			System.out.println("fastq output created");
		}else{
			throw new MyException(ErrorCodes.BAD_FILETYPE);
		}
	}
	
	/**
	 * Flowcell, current number of Ticks and status of Controller is set. Model is created
	 * @param options
	 * @throws MyException
	 */
	private void initialize(GUIOptions options) throws MyException{

		currentNumberOfTicks = 0;
		status = "Running";
		this.flowcell = new Flowcell(options.getNumberOfPores(),options.getMaxAgeOfPores(),options.getOutputFormat());
		
		System.out.println("New flowcell object is created in controller");
		try{
			inputFile.parse(options.getInputFilename());
		}catch(IOException e){
			System.out.println("Sth went wrong when parsing inputFile in controller: " + e.getMessage());
		}
		//doens't work: error message: For input string: "0.1#" looks like sth isn't formatted correctly for cretae setting, maybe kevin knows better, can't find the error in the file
		//setting up new length distribuiton works
		try{
			setupModel(options.getBasecalling(),options.getBasecallingSetup(),options.getWindowSizeForLengthDistribution());
			/**********************/
		}catch(Exception e){
			System.err.println("Error in setupModel method" + e.getMessage());
		}

	}

///**
// * Tests
// * @param args
// */
//	public static void main(String[] args){
//		
//		GUIOptions op = new GUIOptions("C:/Users/Friederike/University/Fourth Semester/Programmierprojekt/git/MinION2015/Minion/src/example4.fasta","TestController.txt","Real-Time","fasta","C:/Users/Friederike/University/Fourth Semester/Programmierprojekt/git/MinION2015/Minion/default.setting",1,1,100,10,100,10);
//		Controller cd = new Controller();
//		
//		//expected output
//		try {
//			cd.createInputFormat("example.fastq"); //expected: new FastQ is created
//			cd.createInputFormat("example.fasta"); //expected: new FastA is created
//			cd.createInputFormat("example.txt"); // catch block is expected
//		} catch (MyException e) {
//			System.err.println("create inputformat method in controller throws following error: "+ e.getErrorMessage());
//		}
//		
//		//expected output
//		try{
//			cd.createOutputFormat("fasta"); //expected: new FastA output created
//			cd.createOutputFormat("fastq"); //expected: new fastQ output created
//			cd.createOutputFormat("txt"); //catch block expected
//		}catch(MyException e){
//			System.err.println("create outputformat method in controller throws following error: "+ e.getErrorMessage());
//		}
//		
//		//TODO unexpected error
//		try {
//			setupModel(op.getBasecalling(), op.getBasecallingSetup(), 10);
//		}  catch (Exception e) {
//			System.err.println("model excep: "+ e.getMessage());
//		}
//		
//		//expected output
//		try{
//			cd.initialize(op); //expected: flowcell is created
//			System.out.println("current# Ticks: " + cd.currentNumberOfTicks); //expected:0
//			System.out.println("status of controller: "+ cd.status);//expected: Running
//		}catch(MyException e){
//			System.err.println("Initialize method in controller throws following error: "+ e.getErrorMessage()); //expected: setupModel doesnt work
//		}
//		
//		Controller cd = new Controller(op);
//		try{
//			cd.run();
//		}catch (MyException e){
//			System.err.println("Running thrwos: "+ e.getErrorMessage());
//		}
//		
//		try{
//			cd.pause();
//		}catch(MyException e){
//			System.err.println("Pause thrwos: "+ e.getErrorMessage());
//		}
//		
//		try{
//			cd.resume();
//		}catch(MyException e){
//			System.err.println("Pause thrwos: "+ e.getErrorMessage());
//		}
//		
//		try{
//			cd.stop();
//		}catch(MyException e){
//			System.err.println("Stop throws: "+ e.getErrorMessage());
//		}
//		//this should now not be able to be executed anymore:
//		System.err.println("There shouldn't be any output after this line.");
//		System.out.println(cd.status);
//		System.out.println("cur#Ticks vs. maxNumberOfTicks : "+ cd.currentNumberOfTicks + " "+ op.getTotalNumberOfTicks());
//		try{
//			cd.run();
//		}catch (MyException e){
//			System.err.println("Catch in main for run() throws: "+ e.getErrorMessage());
//		}
//		
//		try{
//			cd.pause();
//		}catch(MyException e){
//			System.err.println("Pause thrwos: "+ e.getErrorMessage());
//		}
//		
//		try{
//			cd.resume();
//		}catch(MyException e){
//			System.err.println("Resume thrwos: "+ e.getErrorMessage());
//		}
//		
//	}

}

