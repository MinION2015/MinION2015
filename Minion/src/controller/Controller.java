package controller;

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
	
	public Controller(GUIOptions options){
		this.options = options;
		System.out.println("New Controller");
		//wrong filetype works
		try{
			checkFileEnding(options.getInputFilename());
			this.fastA = new FastA();
			this.outputFastA = new FastA();
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
			//TODO tell gui
			//TODO don't run programm; stop
		}catch(Exception e){
			
		}
	}

	
	public void run(){
		
		//didn't put the right file path for testing, works now
		try{
			fastA.parse(options.getInputFilename());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		//TODO some kind of pause stop method
		
		
		try{
		
			//setupModel(options.getBasecalling(),options.getSettingfile(),options.getWindowSizeForLengthDistribution());
			
			Flowcell flowcell = new Flowcell(options.getNumberOfPores());
			int currentNumberOfTicks = 0;
			//TODO like this?, not sure if this works, not sure how i am able to test thread.sleep
			
			while(currentNumberOfTicks < options.getTotalNumberOfTicks()){
				System.out.println("while reached "+flowcell.getNumberOfPores());
				if(flowcell.getNumberOfPores() > 0){
					//TODO impelemt how to get all sequences from the arrylist and maybe flag uf they already have been simulated
					//reached
					flowcell.tick(fastA.getSequence().get(0));
					//not reached
					outputFastA = flowcell.getFlowcellOutput();
					outputFastA.writeInFile(options.getOutputFilename());
					System.out.println("blub");
					Thread.sleep(options.getDurationOfTick());
					
				}else{
					//TODO stop run, inform user
				}
				//this is never reached
				System.out.println("blub");
				currentNumberOfTicks++;
				outputFastA = flowcell.getFlowcellOutput();
				System.out.println(options.getOutputFilename());
				outputFastA.writeInFile(options.getOutputFilename());
			}
			
			
		}catch(MyException e){
			
		}catch(Exception e){
			
		}
		
		
		
				
	}
	
	public void createSettingfile(String fastafile, String settingname, int dimension){
		try {
			createSetting newSetting = new createSetting(fastafile, settingname, dimension);
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
		System.out.println(fastA.getErrorInSequence().get(0).getErrorMessage());
		return fastA.getErrorInSequence();
	}
	
	public static void main(String[] args){
		
		GUIOptions op = new GUIOptions("C:/Users/Friederike/University/Fourth Semester/Programmierprojekt/git/MinION2015/Minion/src/example4.fasta","TestController.txt",1,5,1,10,2);
		Controller cd = new Controller(op);
		cd.run();
		
		cd.getFastAErrors();
	}

}
