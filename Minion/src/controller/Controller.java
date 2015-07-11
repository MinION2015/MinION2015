package controller;

import error.Chance;
import error.ErrorCodes;
import error.MyException;
import gui.GUIOptions;

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
 * Write more testcases, think about if start flowcell is actually needed, test start/stop/pause using gui, change tests using absolute path, how does pore know what to write
 * add contracts
 * @author Friederike
 *
 */
public class Controller {
	
	private GUIOptions options;
	private FiletypeContainingSequences inputFile;
	private FiletypeContainingSequences outputFile;
	private Flowcell flowcell;
	private String status; //"Ready","Running","Paused","Stopped"
	private int currentNumberOfTicks;
	
	
	
	public Controller(GUIOptions options){
		this.options = options;
		//first its determined if a new fasta or fasta file was the input
		try{
			
			checkFileEnding(options.getInputFilename());
			createOutputFormat();
			
			
			//initializing run
			setupModel(options.getBasecalling(),"default",options.getWindowSizeForLengthDistribution());
			currentNumberOfTicks = 0;
			this.flowcell = new Flowcell(options.getNumberOfPores(),options.getMaxAgeOfPores(),options.getOutputFormat());
			status = "Running";
			//TODO options.getSetting or sth like that
			//TODO create SettingFile
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	public void run(){
		try{	
		
			//Sequence seq = new Sequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
			int pos = Chance.getRandInt(0, inputFile.getSequence().size()-1);
			//when p.simulat is commented out in pore method than it works, why? -> p.simulate seems to give nullpointer
			flowcell.startFlowcell(inputFile.getSequence().get(pos));

			while(currentNumberOfTicks < options.getTotalNumberOfTicks() && !status.equals("Stopped")  &&flowcell.getNumberOfPores() > 0){

				pos = Chance.getRandInt(0, inputFile.getSequence().size()-1);
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
//				if(currentNumberOfTicks == 50){
//					System.out.println("currentNum Ticks: "+currentNumberOfTicks);
//					pause();
//				}



			}
			if(options.getWriteInFileOption().equals("Write all")){
				outputFile.writeInFile(options.getOutputFilename());
			}

			

		}catch(MyException e){
			System.err.println("Controller "+e.getErrorMessage());
		}catch(Exception e){
			//for some reason it catches this
			System.err.println("Controller "+e.getMessage());
		}	
	}
	//TODO maybe change start button to resume after pause is pressed or pause button to resume or sth
	public void resume(){
		
		if(status.equals("Paused")){
			status = "Running";
			System.out.println("Resumed");
			run();
			//System.out.println("currentNum Ticks: "+currentNumberOfTicks);
		}
	}
	public void pause(){
		//int counter=0;
		status = "Paused";
		System.out.println("Paused");
		while(status.equals("Paused")){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
			}
//			counter++;
//			if(counter == 3000){
//				System.out.println("resumed");
//				resume();
//			}
			
		}
	}
	
	public void stop(){
		status = "Simulation is Stopped";
		System.out.println("Stopped");
		currentNumberOfTicks = options.getTotalNumberOfTicks();
		
		
	}
	
	
	public static void setupModel(int basecalling, String settingfile, int windowSize) throws Exception{
		BasecallingErrorRate basecallingError = new BasecallingErrorRate(basecalling,settingfile);
		LengthDistribution lengthDistribution = new LengthDistribution(windowSize);	
	}
	
	
	/**
	 * Either a new fasta or fastq inputFile is created depending on gui input
	 * @param filename
	 * @throws MyException
	 */
	private void checkFileEnding(String filename) throws MyException{
		if(filename.endsWith(".fasta")){
			inputFile = new FastA();
		}else if(filename.endsWith(".fastq")){
			inputFile = new FastQ();
		}else{
			throw new MyException(ErrorCodes.BAD_FILETYPE);
		}
	}
	
	private void createOutputFormat() throws MyException{
		
		if(options.getOutputFormat() == "fasta"){
			outputFile = new FastA();
		}else if(options.getOutputFormat() == "fastq"){
			outputFile = new FastQ();
		}else{
			throw new MyException(ErrorCodes.BAD_FILETYPE);
		}
	}
	
	public ArrayList<MyException> getInputFileErrors() {
		return inputFile.getErrorInSequence();
	}
	
	public ArrayList<MyException> getOutputFileErrors(){
		return outputFile.getErrorInSequence();
	}

/*	public static void main(String[] args){
		
		GUIOptions op = new GUIOptions("C:/Users/Friederike/University/Fourth Semester/Programmierprojekt/git/MinION2015/Minion/src/example4.fasta","TestController.txt","Real-Time",1,1,100,10,100,10);
		Controller cd = new Controller(op);
		cd.run();
	
	}*/

	public Flowcell getFlowcell()
	{
		return flowcell;
	}

}

