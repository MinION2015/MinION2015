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
 *It sets up the Error model and the length distribution so they can be used everywhere in the program, initilaizes the flowcell and applies some kind of time //TODO email
 *between the controller and the flowcell still need to figure out loggin of results.
 *@input GUIOptions object containing all necessary information
 *@output file with results
 */
public class Controller {
	
	private GUIOptions options;
	private FiletypeContainingSequences fastA;
	
	public Controller(GUIOptions options){
		this.options = options;
	}
	
	public void run(){
		
		try{
			checkFileEnding(options.getInputFilename());
			this.fastA = new FastA();
			fastA.parse(options.getInputFilename());
		}catch(MyException e){
			System.out.println(e.getErrorMessage());
			//TODO tell gui
			//TODO don't run programm; stop
		}catch(Exception e){
			
		}
		//TODO some kind of pause stop method
		try{
			setupModel(options.getBasecalling(),options.getWindowSizeForLengthDistribution());
			Flowcell flowcell = new Flowcell(options.getNumberOfPores());
			int overallNumberOfTicks = options.getTicksPerSecond()*options.getRunningTime();
			//TODO still trying to understand how to implement time
			for(int i =0; i < overallNumberOfTicks;i++){
				
				flowcell.tick();
				wait(1);
			
			}
		}catch(MyException e){
			
		}catch(Exception e){
			
		}
				
	}
	
	private static void setupModel(int basecalling,int windowSize) throws Exception{
		//TODO initilaize Basecalling Error setupFile, get more from kevin about how
		BasecallingErrorRate basecallingError = new BasecallingErrorRate(basecalling,"blub");
		LengthDistribution lengthDistribution = new LengthDistribution(windowSize);
		
	}
	private void checkFileEnding(String filename) throws MyException{
		if(!filename.endsWith(".fasta")){
			throw new MyException(ErrorCodes.BAD_FILETYPE);
		}
	}
	
	public ArrayList<MyException> getFastAErrors() {
		return fastA.getErrorInSequence();
	}

}
