package controller;

import java.io.IOException;
import java.util.ArrayList;

import reader.Sequence;
import LengthDistribution.LengthDistribution;
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

	private ArrayList<Pore> poreList = new ArrayList<Pore>();
	
	public Flowcell(int numberOfPores) throws MyException{
		try{
			addPores(numberOfPores);
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
		}
	}
	 
	public void startFlowcell(Sequence seq,SimulationError err, int basecalling, LengthDistribution length) throws MyException{
		for(Pore p : poreList){
			//commented since sth with the length distribution is not working
			p.simulate(seq.getSequence(), err, length, basecalling);
			System.out.println("pore is simulated");
		}
		try{
			checkFlowcellState();
		}catch(MyException e){
			
		}
	}
	/**
	 * Since the flowcell won't get any more pores after being initiated I made the method private
	 */
	private void addPores(int numberOfPores) throws MyException{
	
		try{
			for(int i = 0; i < numberOfPores; i++){
				Pore p = new Pore();
				poreList.add(p);
			}
			checkFlowcellState();
		
		}catch(MyException e){
			if(e.getErrorCode() != 4001){
				System.err.println(e.getErrorMessage());
			}else{
				System.out.println(e.getErrorMessage() + " "+getNumberOfPores());
				
			}
		}
		
	}
	
	/**
	 * Checks how many pores the flowcell contains momentarily.
	 * We weren't quite sure, if the pores get a boolean flagging them as being alive or if the flowcell somehow carries flags for all of them. 
	 * If the pores get a boolean alive and a getter for it, then we could check here 
	 * if there are any alive pores left and either return some kind of message if all of them are not functioning anymore at a tick or keep sequencing.
	 * @throws MyException
	 */
	private void checkFlowcellState()throws MyException{
		if(poreList.isEmpty()){
			throw new MyException(ErrorCodes.FLOWCELL_EMPTY);
		}else{
			//suggestion/idea of how to incoperate  dying pores
//			boolean hasAlivePores = false;
//			for(Pore p : poreList){
//				if(p.isAlive()){
//					hasAlivePores = true;
//				}
//			}
			//if(hasAlivePores){
			throw new MyException(ErrorCodes.FLOWCELL_CONTAINS_PORES);
//			}else{
//				throw new MyException(ErrorCodes.FLOWCELL_ALL_PORES_DEAD);
//			}
		}
	}

	private int getNumberOfPores(){
		return poreList.size();
	}
	
	
	/*
	 * tests
	 */
//	public static void main(String[] args) throws MyException,IOException{
//		
//	Flowcell g = new Flowcell(5);
//	Flowcell f = new Flowcell(0);
//	Flowcell t = new Flowcell(-10);
//	Flowcell d = new Flowcell(10);
//	Sequence seq = new Sequence("me","ACTGTGA");
//	SimulationError err = new SimulationError();
//	
//	System.out.print("Sum of sequences:" );
//	LengthDistribution length = new LengthDistribution(1);
//	
//	g.startFlowcell(seq, err, 1, length);
//	}
}
