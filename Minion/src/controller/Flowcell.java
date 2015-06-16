package controller;

import java.util.ArrayList;
import java.util.Scanner;

import error.ErrorCodes;
import error.MyException;

/**
 * 
 *@author Friederike Hanssen
 *@functionality A flowcell holds and generates a number of pores in an Arraylist. 5 for now. Pores can be added. Right now it is blocked that pores can be subtracted. Maybe reasonable for turning off pores though??!
 *@input number of pores
 *@output none
 */
public class Flowcell{

	private ArrayList<Pore> poreList = new ArrayList<Pore>();
	/**
	 * Empty flowcell
	 * @throws MyException
	 */
	public Flowcell() throws MyException{
		try{
			checkFlowcellState();
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
		}
	}
	
	public Flowcell(int numberOfPores) throws MyException{
		try{
			addPores(numberOfPores);
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
		}
	}
	
//	public Flowcell(int numberOfPores, FastA genome) throws MyException{
//		try{
//			addPores(numberOfPores);
//			checkFlowcellState();
//		}catch(MyException e){
//			System.err.println(e.getErrorMessage());
//		}
//	}
	/**
	 * NUmber of pore is added to the flowcell, if a number > 0 is given, else an error is thrown. So far we haven't set an upper boundary(512?) so theoretically we could add more pores.
	 * @param numberOfPores
	 * @throws MyException
	 */
	public void addPores(int numberOfPores) throws MyException{
	
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
		// for quicker testing for adding different numbers of pores
		Scanner sc = new Scanner(System.in);
//		//Flowcell f = new Flowcell();
		Flowcell g = new Flowcell();
//		boolean r = true;
//		while(true){
//			int j = sc.nextInt();
//			Flowcell f = new Flowcell(j);			
//		}
		while(true){
			int i = sc.nextInt();
			g.addPores(i);
		}
		
//		Flowcell f = new Flowcell(); //Flowcell is empty err expected
//		Flowcell g = new Flowcell(1); //1 pore added, Flowcell contains 1 pore expected
//		g.addPores(3); //3 pores added, flowcell contains 4 pores expected
//		g.addPores(0); //0 pores added, flowcell contains 4 pores expected
//		g.getPoreAt(3);//"test2" expected
		
		
	}
}
