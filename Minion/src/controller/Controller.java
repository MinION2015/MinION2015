package controller;

import error.Chance;
import error.ErrorCodes;
import error.MyException;
import gui.GUIOptions;

import java.io.IOException;
import java.util.ArrayList;


import reader.FastA;
import reader.Sequence;
import Basecalling.BasecallingErrorRate;
import Basecalling.createSetting;
import LengthDistribution.LengthDistribution;


/**
 * 
 * @author Friederike Hanssen
 *@Functionality Controllers runs the program. First, the file ending is checked, if it is approved, the program should be run//TODO implement stop. 
 *It sets up the Error model and the length distribution so they can be used everywhere in the program, initliazes the flowcell and applies some kind of time 
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
	private int currentNumberOfTicks;
	
	
	public Controller(GUIOptions options){
		this.options = options;
		//wrong filetype works
		try{
			checkFileEnding(options.getInputFilename());
			this.fastA = new FastA();
			this.outputFastA = new FastA();
			fastA.parse(options.getInputFilename());
			this.flowcell = new Flowcell(options.getNumberOfPores(),options.getMaxAgeOfPores());
			status = "Running";
			//TODO options.getSetting or sth like that
			setupModel(options.getBasecalling(),"default",options.getWindowSizeForLengthDistribution());
			currentNumberOfTicks = 0;
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	public void run(){
		try{	
		
			//Sequence seq = new Sequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
			int pos = Chance.getRandInt(0, fastA.getSequence().size()-1);
			//when p.simulat is commented out in pore method than it works, why? -> p.simulate seems to give nullpointer
			flowcell.startFlowcell(fastA.getSequence().get(pos));

			while(currentNumberOfTicks < options.getTotalNumberOfTicks() && !status.equals("Stopped")  &&flowcell.getNumberOfPores() > 0){


				if(options.getWriteInFileOption().equals("Real-Time")){
					try{
						pos = Chance.getRandInt(0, fastA.getSequence().size()-1);
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
				currentNumberOfTicks++;
				if(currentNumberOfTicks == 50){
					System.out.println("currentNum Ticks: "+currentNumberOfTicks);
					pause();
				}



			}
			if(options.getWriteInFileOption().equals("Write all")){
				outputFastA.writeInFile(options.getOutputFilename());
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
		int counter=0;
		status = "Paused";
		System.out.println("Paused");
		while(status.equals("Paused")){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
			}
			counter++;
			if(counter == 3000){
				System.out.println("resumed");
				resume();
			}
			
		}
	}
	
	public void stop(){
		status = "Simulation is Stopped";
		System.out.println("Stopped");
		currentNumberOfTicks = options.getTotalNumberOfTicks();
		//should not do anything, doesn't :)
		resume();
		
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
	
	public ArrayList<MyException> getFastAErrors() {
		return fastA.getErrorInSequence();
	}
//
//	public static void main(String[] args){
//		
//		GUIOptions op = new GUIOptions("C:/Users/Friederike/University/Fourth Semester/Programmierprojekt/git/MinION2015/Minion/src/example4.fasta","TestController.txt","Real-Time",1,1,100,10,100,10);
//		Controller cd = new Controller(op);
//		cd.run();
//	
//	}

}

