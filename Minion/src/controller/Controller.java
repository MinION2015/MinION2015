package controller;

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
 *@Functionality Controllers runs the program. First, the file ending is checked, if it is approved, the program should be run//TODO implement stop. 
 *It sets up the Error model and the length distribution so they can be used everywhere in the program, initliazes the flowcell and applies some kind of time //TODO email
 *between the controller and the flowcell still need to figure out loggin of results.
 *@input GUIOptions object containing all necessary information
 *@output file with results
 */
public class Controller {
	
	private GUIOptions options;
	private FiletypeContainingSequences fastA;
	private FiletypeContainingSequences outputFastA;
	
	public Controller(GUIOptions options){
		this.options = options;
		System.out.println("New Controller");
	}
	
	public void run(){
		
		//wrong filetype works
		try{
			checkFileEnding(options.getInputFilename());
			this.fastA = new FastA();
			this.outputFastA = new FastA();
			fastA.parse(options.getInputFilename());
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
			//TODO tell gui
			//TODO don't run programm; stop
		}catch(Exception e){
			
		}
		
		
		//TODO some kind of pause stop method
		
		
		try{
			//?
			//setupModel(options.getBasecalling(),options.getWindowSizeForLengthDistribution());
			
			Flowcell flowcell = new Flowcell(options.getNumberOfPores());
			int currentNumberOfTicks = 0;
			//TODO like this?, not sure if this works, not sure how i am able to test thread.sleep
			
			while(currentNumberOfTicks < options.getTotalNumberOfTicks()){
				
				if(flowcell.getNumberOfPores() > 0){
					//TODO impelemt how to get all sequences from the arrylist and maybe flag uf they already have been simulated
					flowcell.tick(fastA.getSequence().get(0));
					Thread.sleep(options.getDurationOfTick());
					outputFastA = flowcell.getFlowcellOutput();
					outputFastA.writeInFile(options.getOutputFilename());
				}else{
					//TODO stop run, inform user
				}
				currentNumberOfTicks++;
			}
			
			
		}catch(MyException e){
			
		}catch(Exception e){
			
		}
		
		
		
				
	}
	
	//not sure how to test, kevin needs to provide more information to me first
	private static void setupModel(int basecalling,int windowSize) throws Exception{
		BasecallingErrorRate basecallingError = new BasecallingErrorRate(basecalling,"blub");
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
	
//	public static void main(String[] args){
//		
//		GUIOptions op = new GUIOptions("example4.txt","TestController.txt",1,1,1,10,2);
//		Controller cd = new Controller(op);
//		cd.run();
//	}

}
