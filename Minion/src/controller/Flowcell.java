package controller;

import java.util.ArrayList;

import error.ErrorCodes;
import error.MyException;

/**
 * 
 *@author Friederike Hanssen
 *@functionality A flowcell holds and generates a number of pores in an Arraylist. 5 for now.
 *@input number of pores
 *@output none
 */
public class Flowcell{

	private ArrayList<Pore> poreList = new ArrayList<Pore>();
	
	public Flowcell(int numberOfPores) throws MyException{
		try{
			addPores(numberOfPores);
		}catch(MyException e){
			System.out.println(e.getErrorMessage());
		}
	}
	
	/**
	 * NUmber of pore is added to the flowcell, if a number > 0 is given, else an error is thrown
	 * @param numberOfPores
	 * @throws MyException
	 */
	public void addPores(int numberOfPores) throws MyException{
		
		try{
			checkAmountOfPore(numberOfPores);
			for(int i = 0; i < numberOfPores; i++){
				Pore p = new Pore();
				p.fasta = "test";
				poreList.add(p);
			}
		}catch(MyException e){
			System.out.println(e.getErrorMessage());
		}
		
	}
	
	private void checkAmountOfPore(int numberOfPores) throws MyException{
		if(numberOfPores == 0){
			throw new MyException(ErrorCodes.FLOWCELL_EMPTY);
		}
	}
	public Pore getPoreAt(int index){
		//Test
		//System.out.println(poreList.get(index).fasta);
		
		return poreList.get(index);
	}
	
	
	
	/*
	 * tests
	 */
//	public static void main(String[] args) throws MyException{
//		Flowcell f = new Flowcell(5);
//		f.getPoreAt(3);
//		f.addPores(0);
//		f.addPores(2);
//		for(Pore e: f.poreList){
//			System.out.println(e.fasta);
//			
//		}
//	}
}
