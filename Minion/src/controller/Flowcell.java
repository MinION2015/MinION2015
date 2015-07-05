package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import reader.FastA;
import reader.Sequence;
import Basecalling.BasecallingErrorRate;
import LengthDistribution.LengthDistribution;
import error.Chance;
import error.ErrorCodes;
import error.MyException;

/**
 * 
 *@author Friederike Hanssen
 *@functionality A flowcell holds and generates a number of pores in an Arraylist. 
 *The flowcell is supposed to determine if there are any alive pores left at a tick.
 *@input number of pores
 *@output none
 */
public class Flowcell{
	
	private ArrayList<Pore> poreList = new ArrayList<Pore>();
	private FastA fastA;
	private int maxAgeOfPores;
	
	public Flowcell(int numberOfPores,int maxAgeOfPores) throws MyException{
		try{
			addPores(numberOfPores);
		}catch(MyException e){
			System.err.println(e.getErrorMessage());
		}
		this.maxAgeOfPores = maxAgeOfPores;
		fastA = new FastA();
	}
	
	
	
	/** Since Length Distribution and Simulation are supposedly static I removed them as parameters
	  * @param seq
	  * @param basecalling
	  */	
	public void startFlowcell(Sequence seq) throws MyException{
		for(Pore p : poreList){
			try{
				p.simulate(seq);
				System.out.println("Pore is simulated");
				checkFlowcellState();
				System.out.println("flowcellstatecheck is executed");
			}catch(MyException e){
				if(e.getErrorCode() == 4001){
					continue;
				}else{
					System.err.println(e.getErrorMessage());
				}
			}
			
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
				//TODO change constructor as soon as pore is fixed
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
			System.err.println("Flowcell is empty");
			throw new MyException(ErrorCodes.FLOWCELL_EMPTY);
		}
		else{
				//for testing purposes
				for(Pore p :poreList){
					System.out.println("An alive pore is still in flowcell");
				}
				throw new MyException(ErrorCodes.FLOWCELL_CONTAINS_PORES);
		}
	}
	
	
	/**
	 * If there are any dead pores they will be removed from the arraylist as they won't be able to perform any further actions
	 */
	private void removeDeadPores() {
		
		List<Pore> posOfDeadPores = new ArrayList<Pore>(); 
		for(Pore p : poreList){
			String statusOfPore = p.checkStatus();//"Dead";//"alive";
			if(statusOfPore.equals("Dead")){
					posOfDeadPores.add(p);
					//System.out.println("This pore is dead");
			}
			
		}
		for(Pore i : posOfDeadPores){
			//System.out.println("pore #"+posOfDeadPores.indexOf(i)+" got removed removed");
			poreList.remove(i);
		}
	}

	/**
	 * In each tick all pores are checked and either given work , if their are bored, else they are left alone. If one is finished the output is added to the FastA object, so later it can be printed to a file
	 */
	public void tick(Sequence seq){
		fastA =new FastA();
		for(Pore p : poreList){
			//TODO apparently never changes it state, thus still only hard coded version works
			//also prob the reason why the output doesn't change as no pore gets resimulated
			/******************************/
			String statusOfPore = "Finished";//p.checkStatus();//"Running"//"Bored"//"Finished"
			/*******************************/
			//just for playing around with the status;
//			double rand = Chance.getRand();
//			if(rand<0.3){
//				statusOfPore = "Running";
//			}else if(rand < 0.6){
//				statusOfPore= "Bored";
//			}else{
//				statusOfPore = "Finished";
//			}
			
			if(statusOfPore.equals("Running") || statusOfPore.equals("Dead")){
				System.out.println("Busy with running or being dead");
				continue;
			}else if(statusOfPore.equals("Bored")){
				System.out.println("I am bored");
				try{
					p.simulate(seq);
				}catch(MyException e){
					System.err.println(e.getErrorMessage());
				}
			}else if(statusOfPore.equals("Finished")){
				//collecting output
				System.out.println("I am done.");
				
				seq = p.getSequenceFromPore();
				try{
					//TODO each pore gives me the excact same output x-times
					fastA.addSeq(p.getSequenceFromPore());
				}catch(Exception e){
					System.err.println(e.getMessage());
				}
			}
		}	
	}
	
	public FastA getFlowcellOutput(){
		return fastA;
	}
	public int getNumberOfPores(){
		return poreList.size();
	}
	
//	//All test suffice
//	/*
//	 * tests
//	 */
//	public static void main(String[] args) throws MyException,IOException{
//		
//	Flowcell g = new Flowcell(1);
//	try{
//		BasecallingErrorRate err = new BasecallingErrorRate(1, "default");
//		LengthDistribution lenght = new LengthDistribution(10);
//	}catch(Exception e){
//		System.err.println(e.getMessage());
//	}
//	Sequence seq = new Sequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
//	g.startFlowcell(seq);
//	g.tick(seq);
//	FastA f = g.getFlowcellOutput();
//	f.writeInFile("TestFlowcell.txt");
//	//Length Distribution throws nullpointers, thus I can't retest the startFlowcellmethod right now
//	
//	
//	//Flowcell f = new Flowcell(0);
//		//Flowcell t = new Flowcell(-10);
//		//Flowcell d = new Flowcell(10);
//	
//	
//	}
}
