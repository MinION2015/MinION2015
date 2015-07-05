package controller;

import error.Chance;
import error.ErrorCodes;
import error.MyException;
import gui.GUIOptions;

import java.io.IOException;
import java.util.ArrayList;

import reader.*;
import Basecalling.BasecallingErrorRate;
import Basecalling.createSetting;
import LengthDistribution.LengthDistribution;
/**
 * 
 * @author Friederike Hanssen
 *@Functionality Controllers runs the program. First, the file ending is checked, if it is approved, the program should be run//TODO implement stop. 
 *It sets up the Error model and the length distribution so they can be used everywhere in the program, initliazes the flowcell and applies some kind of time //TODO email
 *between the controller and the flowcell still need to figure out loggin of results.
 *@input GUIOptions object containing all necessary information
 *@output file with results
 */
public class Controller {
	
	private GUIOptions options;
	private FastA fastA;
	private FastA outputFastA;
	private Flowcell flowcell;
	private String status; //"Ready","Running","Paused","Stopped","NotAbleToPerform"."NoAlivePoresLeft"
	
	
	public Controller(GUIOptions options){
		this.options = options;
		//wrong filetype works
		try{
			checkFileEnding(options.getInputFilename());
			this.fastA = new FastA();
			this.outputFastA = new FastA();
			fastA.parse(options.getInputFilename());
			this.flowcell = new Flowcell(options.getNumberOfPores(),options.getMaxAgeOfPores());
			//TODO options.getSetting or sth like that
			setupModel(options.getBasecalling(),"default",options.getWindowSizeForLengthDistribution());
			status = "Ready";
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
			status = "NotAbleToPerform";
		}catch(Exception e){
			System.out.println(e.getMessage());
			status = "NotAbleToPerform";
		}
	}

	
	public String getStatus() {
		return status;
	}


	public void run(){
		
//		try{
//			fastA.parse(options.getInputFilename());
//		}catch(Exception e){
//			System.err.println(e.getMessage());
//		}
		
		
		status = "Running";
		try{	
			
			int currentNumberOfTicks = 0;
			//Sequence seq = new Sequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
			int pos = Chance.getRandInt(0, fastA.getSequence().size()-1);
			flowcell.startFlowcell(fastA.getSequence().get(pos));


			while(currentNumberOfTicks < options.getTotalNumberOfTicks()){
				if(status.equals("Running")){
					if(flowcell.getNumberOfPores() > 0){

						if(options.getWriteInFileOption().equals("Real-Time")){
							try{
								pos = Chance.getRandInt(0, fastA.getSequence().size()-1);
								//System.out.println(fastA.getSequence().get(pos).getSequence());
								flowcell.tick(fastA.getSequence().get(pos));
								flowcell.getFlowcellOutput().writeInFile(options.getOutputFilename());
								Thread.sleep(options.getDurationOfTick());
							}catch(Exception e){
								System.err.println(e.getMessage());
							}
						}else if(options.getWriteInFileOption().equals("Write all")){
							try{

								pos = Chance.getRandInt(0, fastA.getSequence().size()-1);
								flowcell.tick(fastA.getSequence().get(pos));
								for(Sequence s : flowcell.getFlowcellOutput().getSequence()){
									outputFastA.addSeq(s);
								}

								Thread.sleep(options.getDurationOfTick());
							}catch(Exception e){
								System.err.println(e.getMessage());
							}
						}

					}else{
						status= "NoAlivePoresLeft";
					}
					currentNumberOfTicks++;
				}
			}
			System.out.println("while ends");
			if(options.getWriteInFileOption().equals("Write all")){
				System.out.println("Write all");
				outputFastA.writeInFile(options.getOutputFilename());
			}
			
			
			
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}	
	}
	
	public void pause(){
		status = "Paused";
		
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void stop(){
		status = "Stopped";
	}
	
	/**
	 * pls dont delete the function its necessary for the create new Setting file  button
	 * @param blastfilePath
	 * @param settingname
	 * @param dimension
	 */
	public void createSettingfile(String blastfilePath, String settingname, int dimension){
		try {
			createSetting newSetting = new createSetting(blastfilePath, settingname, dimension);
		} catch (IOException e) {
			// TODO catch needs to be done
			e.printStackTrace();
		}
	}
	
	
	private static void setupModel(int basecalling, String settingfile, int windowSize) throws Exception{
		BasecallingErrorRate basecallingError = new BasecallingErrorRate(basecalling,settingfile);
		LengthDistribution lengthDistribution = new LengthDistribution(windowSize);	
	}
	//works
	private void checkFileEnding(String filename) throws MyException{
		if(!filename.endsWith(".fasta")){
			throw new MyException(ErrorCodes.BAD_FILETYPE);
		}
	}
	
	//NUllpointer not sure why
	public ArrayList<MyException> getFastAErrors() {
		return fastA.getErrorInSequence();
	}

	public static void main(String[] args){
		
		GUIOptions op = new GUIOptions("C:/Users/Friederike/University/Fourth Semester/Programmierprojekt/git/MinION2015/Minion/src/example4.fasta","TestController.txt","Real-Time",1,3,10,1,10,10);
		Controller cd = new Controller(op);
		cd.run();
	
	}

}

