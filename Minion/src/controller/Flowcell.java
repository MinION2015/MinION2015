package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import reader.*;
import Basecalling.SimulationError;
import error.*;

/**
 * 
 *@author Friederike Hanssen
 *@functionality A flowcell holds and generates a number of pores in an Arraylist. 5 for now.
 *The flowcell is supposed to determine if there are any alive pores left at a tick.
 *@input number of pores
 *@output none
 */
public class Flowcell{
	//TODO notes say flowcell isn't suppoesed to be static, but shouldn't it be?
	
	private ArrayList<Pore> poreList = new ArrayList<Pore>();
	
	public Flowcell(int numberOfPores) throws MyException{
		try{
			addPores(numberOfPores);
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
		}
	}
	 /**
	  * Since Length Distribution and Simulation are supposedly static I removed them as parameters
	  * @param seq
	  * @param basecalling
	  */
	public void startFlowcell(Sequence seq) throws MyException{
		for(Pore p : poreList){
			//since LEngth distribution and simualtion error are supposed to be static they do not need to be passed over anymore
			//TODO check with albert if he has changed his method parameters
			//p.simulate();
			System.out.println("Pore is simulated");
		}
		try{
			checkFlowcellState();
		}catch(MyException e){
			
		}
	}
	/**
	 * The integer gives the number of pores that should be added. They are stored in a Arraylist, thus they are just added as a new object to the flowcell array list 
	 * Since the flowcell won't get any more pores after being initiated I made the method private
	 * 
	 */
	private void addPores(int numberOfPores) throws MyException{
	
		try{
			for(int i = 0; i < numberOfPores; i++){
				Pore p = new Pore();
				poreList.add(p);
			}
			checkFlowcellState();
		
		}catch(MyException e){
			//If error code says flowcell is not empty/containing alive pores, print out the number of alove pores 
			if(e.getErrorCode() == 4001){
				System.out.println(e.getErrorMessage() + " "+getNumberOfPores());
			}else{
				//else print out any other error message
				System.err.println(e.getErrorMessage());	
			}
		}
		
	}
	
	/**
	 * Checks how many alive pores the flowcell contains momentarily.
	 * The pores carry flags about being working, dead, bored or finished. If pores are flagged as dead they will be removed from the flowcell. If then the flowcell is empty
	 * (meaning all pores are dead) the controller needs to somehow recieve a message about this and inform the user.
	 * @throws MyException
	 */
	private void checkFlowcellState()throws MyException{
		//Remove all dead pores from the Flowcell
		removeDeadPores();
		
		if(poreList.isEmpty()){
			throw new MyException(ErrorCodes.FLOWCELL_EMPTY);
		}
		else{
				//for testing purposes
//				for(Pore p :poreList){
//					System.out.println("I'm alive");
//				}
				throw new MyException(ErrorCodes.FLOWCELL_CONTAINS_PORES);
		}
	}
	
	
	/**
	 * If there are any dead pores they will be removed from the arraylist as they won't be able to perform any further actions
	 */
	private void removeDeadPores() {
		
		List<Pore> posOfDeadPores = new ArrayList<Pore>(); 
		for(Pore p : poreList){
			//TODO get method name from albert/daniel
			String statusOfPore = "alive"; //p.checkStatus();//"Dead";//"alive";
			if(statusOfPore.equals("Dead")){
					posOfDeadPores.add(p);
					System.out.println("I am dead");
			}
			
		}
		for(Pore i : posOfDeadPores){
			System.out.println("I am removed");
			poreList.remove(i);
		}
	}

	/**
	 * In each tick all pores are cheked and either given work , if their are bored, else they are left alone. If on is finished the output is added the FastA object, so later it can be printed to a file
	 */
	private void tick(){
		for(Pore p : poreList){
			String statusOfPore = p.checkStatus();
			
			if(statusOfPore.equals("Running") || statusOfPore.equals("Dead")){
				continue;
			}else if(statusOfPore.equals("Bored")){
				//TODO which sequence or is sequence going to be static as well?
				p.simulate();
			}else if(statusOfPore.equals("Finished")){
				//method is missing in Pore
				Sequence seq = p.getSequence();
				//fasta will be static, thus the sequence will be added, later the controller will write the content of fasta to a file
				FastA.addSeq(seq);
			}
		}	
	}
	
	private int getNumberOfPores(){
		return poreList.size();
	}
	
	
	/*
	 * tests
	 */
	public static void main(String[] args) throws MyException,IOException{
		
	Flowcell g = new Flowcell(5);
	//Flowcell f = new Flowcell(0);
	//Flowcell t = new Flowcell(-10);
	//Flowcell d = new Flowcell(10);
	Sequence seq = new Sequence("me","ACTGTGA");
	SimulationError err = new SimulationError();
	//Length Distribution throws nullpointers, thus I can't retest the startFlowcellmethod right now
	
	
	}
}
