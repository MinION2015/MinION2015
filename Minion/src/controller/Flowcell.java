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
			
		}
	}
	
	private void addPores(int numberOfPores) throws MyException{
		if(numberOfPores == 0){
			throw new MyException(ErrorCodes.FLOWCELL_EMPTY);
		}
		for(int i = 0; i < numberOfPores; i++){
			Pore p = new Pore();
			poreList.add(p);
		}
	}
	
	public Pore getPoreAt(int index){
		return poreList.get(index);
	}
	
	public void setPore(Pore p){
		poreList.add(p);
	}
}
