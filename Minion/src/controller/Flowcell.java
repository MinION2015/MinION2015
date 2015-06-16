package controller;

import java.util.ArrayList;
import java.util.Scanner;

import reader.Sequence;
import LengthDistribution.LengthDistribution;
import error.ErrorCodes;
import error.MyException;
import error.SimulationError;

/**
 * 
 *@author Friederike Hanssen
 *@functionality A flowcell holds and generates a number of pores in an Arraylist. 5 for now. Pores can be added. Right now it is blocked that pores can be subtracted. Maybe reasonable for turning off pores though??!
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
			
			p.simulate(seq.getSequence(), err, length, basecalling);
		}
	}
	/**
	 * NUmber of pore is added to the flowcell, if a number > 0 is given, else an error is thrown. So far we haven't set an upper boundary(512?) so theoretically we could add more pores.
	 * @param numberOfPores
	 * @throws MyException
	 */
	private void addPores(int numberOfPores) throws MyException{
	
		try{
			checkNumberOfAddingPores(numberOfPores);
			for(int i = 0; i < numberOfPores; i++){
				Pore p = new Pore();
				p.fasta = "test"+i;
				poreList.add(p);
			}
			System.out.println("You are adding "+numberOfPores+" pores to your flowcell.");
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
	 * @throws MyException
	 */
	private void checkFlowcellState()throws MyException{
		if(poreList.isEmpty()){
			throw new MyException(ErrorCodes.FLOWCELL_EMPTY);
		}else{
			throw new MyException(ErrorCodes.FLOWCELL_CONTAINS_PORES);
		}
	}
	
	/**
	 * Negative number of pores added not allowed.
	 * Maybe reasonable though for 'turning off pores'???
	 * @param numberOfPores
	 * @throws MyException
	 */
	private void checkNumberOfAddingPores(int numberOfPores) throws MyException{
		if(numberOfPores < 0){
			throw new MyException(ErrorCodes.FLOWCELL_NEGATIVE_AMOUNT_PORES_ADDED);
		}
	}
	private int getNumberOfPores(){
		return poreList.size();
	}
	public Pore getPoreAt(int index){
		//Test
		System.out.println(poreList.get(index).fasta);
		return poreList.get(index);
	}
	
	
	
	/*
	 * tests
	 */
	public static void main(String[] args) throws MyException{
		
		Flowcell g = new Flowcell(5);
	
		g.getPoreAt(3);//"test2" expected
		
		
	}
}
