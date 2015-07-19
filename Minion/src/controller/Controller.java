package controller;

import error.Chance;
import error.ErrorCodes;
import error.MyException;
import gui.GUIOptions;
import guiStatistics.guiStatistics;

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
 * TODO : comments, class that will create a FastQ score(Kevin?), changing errormodel accroding to time, maybe some kind of tryToChangeModelFunction that gets called and changes accroding to numbers of times aslked(a bit like Pore)
 * Write more testcases, 
 * add contracts
 * @author Friederike Hanssen
 *
 */
public class Controller {
	
	private GUIOptions options;
	private FiletypeContainingSequences inputFile;
	private FiletypeContainingSequences outputFile;
	private Flowcell flowcell;
	
	private boolean hasBeenStopped =false;
	private boolean isPaused = false;
	private Runner runningThread;
	
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
		}catch(MyException e){
			System.err.println("Error in controller constructor input: "+e.getErrorMessage());
		}catch(Exception e){
			System.err.println("Error in controller constructor input: "+e.getMessage());
		}
		try{	
			createOutputFormat(options.getOutputFormat());
		}catch(MyException e){
			System.err.println("Error in controller constructor output: "+e.getErrorMessage());
		}catch(Exception e){
			System.err.println("Error in controller constructor output: "+e.getMessage());
		}
		try{
			initialize(options);
		}catch(MyException e){
			System.err.println("Error in controller constructor initialize: "+e.getErrorMessage());
		}catch(Exception e){
			System.err.println("Error in controller constructor intilaize: "+e.getMessage());
		}
		runningThread = new Runner(this);
		
	}


	public void startController() throws MyException{
		try{
			checkIfPaused();
			runningThread.start();
			System.out.println("Run is started");
		}catch(MyException e){
			System.err.println(e.getErrorMessage() +". Please press stop before starting a new run.");
		}

		//TODO
		//createguiStatistics(double[][] porestates, double[] reads, int tick);
		//updateData(double[][] porestates, double[] reads, int tick)

	}



	public void resume() throws MyException{
		try{
			checkIfStoppedYet();
			runningThread.resume();
			System.out.println("Program resumed");
		}catch(MyException e){
			System.err.println("Run is not resuming. It was stopped already. A new one has to be initialized first");
		}
	}
	
	public void pause() throws MyException{
		try{
			checkIfStoppedYet();
			runningThread.suspend();
			System.out.println("Program paused");
		}catch(MyException e){
			throw new MyException(ErrorCodes.CONTROLLER_NOT_PAUSING);
		}
	}
	
	public void stop() throws MyException{
		runningThread.suspend();
		hasBeenStopped = true;
		System.out.println("Program stopped");

	}
	
	private void checkIfStoppedYet() throws MyException{
		if(hasBeenStopped){
			System.out.println("Program has been stopped: "+hasBeenStopped);
			throw new MyException(ErrorCodes.CONTROLLER_NOT_RUNNING);
		}
	}

	private void checkIfPaused() throws MyException {
		if(isPaused){
			throw new MyException(ErrorCodes.CONTROLLER_IS_PAUSING);
		}
		
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
	public GUIOptions getOptions(){
		return options;
	}

	public FiletypeContainingSequences getOutputFile() {
		return outputFile;
	}


	public void setOutputFile(FiletypeContainingSequences outputFile) {
		this.outputFile = outputFile;
	}


	public FiletypeContainingSequences getInputFile() {
		return inputFile;
	}


	public boolean isHasBeenStopped() {
		return hasBeenStopped;
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
		if(format.endsWith("fasta")){
			outputFile = new FastA();
			System.out.println("fastA output created");
		}else if(format.endsWith("fastq")){
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
//		GUIOptions op = new GUIOptions("src/example4.fasta","TestController.txt","Real-Time","fasta","C:/Users/Friederike/University/Fourth Semester/Programmierprojekt/git/MinION2015/Minion/default.setting",1,1,100,10,100,10);
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
//		
//		
//	}

}

