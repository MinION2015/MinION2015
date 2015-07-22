package controller;


import java.util.ArrayList;

import reader.FastA;
import reader.FastASequence;
import reader.FastQ;
import reader.FiletypeContainingSequences;
import reader.Sequence;
import error.Chance;
import error.ErrorCodes;
import error.MyException;
//TODO LEngthDistribution is returning nullpointers!

/**
 * 
 *@author Friederike Hanssen
 *@functionality A flowcell holds and generates a number of pores in an Arraylist. 
 *The flowcell is supposed to determine if there are any alive pores left at a tick.
 *@input number of pores
 *@output none
 */
public class Flowcell{
	int counter =0;
	private ArrayList<Pore> poreList = new ArrayList<Pore>();
	private FiletypeContainingSequences outputSequence;
	private int maxAgeOfPores;
	private int currentSumOfReads; //needed for already recorded Reads
	private String outputFormat;
	private String status;
	
//	public Flowcell(){
//		//for testing reasons
//	}
	public Flowcell(int numberOfPores,int maxAgeOfPores,String outputFormat) throws MyException{
		System.out.println("A new flowcell is created");
		currentSumOfReads = 0;
		status = "Running";
		this.outputFormat = outputFormat;
		this.maxAgeOfPores = maxAgeOfPores;
		try{
			addPores(numberOfPores);
			setFlowcellOutputFormat(outputFormat);
		}catch(MyException e){
			System.err.println("Flowcell constructor throws: "+e.getErrorMessage());
		}
		
	}
	
	/**
	 * Sets the outputformat to what was specified by the user: fasta or fastq
	 * @throws MyException
	 */
	private void setFlowcellOutputFormat(String outputFormat) throws MyException{
		if(outputFormat.endsWith("fasta")){
			outputSequence = new FastA();
		}else if(outputFormat.endsWith("fastq")){
			outputSequence = new FastQ();
		}else{
			throw new MyException(ErrorCodes.FLOWCELL_OUTPUT_FORMAT_COULD_NOT_BE_CREATED);
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
				Pore p = new Pore(maxAgeOfPores,outputFormat);
				poreList.add(p);
			}
			checkFlowcellState();
		
		}catch(MyException e){
				System.err.println("Add pores throws: "+e.getErrorMessage());	
		}
		
	}
	
	/**
	 * Checks if flowcell contains any alive pores momentarily.
	 * The pores carry flags about being working, dead, bored or finished. If pores are flagged as dead they will be removed from the flowcell. If then the flowcell is empty
	 * (meaning all pores are dead) the controller needs to somehow recieve a message about this and inform the user.
	 * @throws MyException
	 */
	private void checkFlowcellState()throws MyException{
		boolean alivePoresLeft = false;
		if(poreList.isEmpty()){
			throw new MyException(ErrorCodes.FLOWCELL_EMPTY);
		}
		else{

			for(Pore p :poreList){
				//String s = "Dead";//p.checkstatus() //works in case all pores are dead
				if(!p.checkStatus().equals("Dead")){
					alivePoresLeft = true;
					break;
				}
			}
			if(!alivePoresLeft){
				throw new MyException(ErrorCodes.FLOWCELL_ONLY_CONTAINS_DEAD_PORES);
			}
		}
	}
	

	/**
	 * In each tick all pores are checked and either given work , if their are bored, else they are left alone. If one is finished the output is added to the FastA object, so later it can be printed to a file
	 */
	public void tick(Sequence seq){
		if(status.equals("Running")){
			try{
				setFlowcellOutputFormat(outputFormat);
				checkFlowcellState();
				for(Pore p : poreList){
					
					//TODO remove fake setting the pore to finish for testing runner and controller
					if(Chance.getRand() < 0.2){
						p.setStatus("Dead");
					}else if (Chance.getRand() < 0.5){
						p.setStatus("Sleeping");
					}else{
						p.setStatus("Bored");
					}
					String statusOfPore = p.checkStatus();//"Finished";//"Finished"//"Dead"//"sleeping"
//					System.out.println("This sequences are in the pores right now but can't be returned as the pore isn't done yet: "+p.getSequenceFromPore().getSequence());
					if(Chance.getRand() < 0.2){
						p.setStatus("Finished");
						statusOfPore = "Finished";
					}
					

					if(statusOfPore.equals("Running") || statusOfPore.equals("Dead") || statusOfPore.equals("Sleeping")){
						System.out.println("This pore is running, dead or sleeping");
						continue;
					}else if(statusOfPore.equals("Bored")){
						System.out.println("This pore is bored, thus should be simulated");
						try{
							p.simulate(seq);
							System.out.println("Pore was simulated in flowcell");
						}catch(MyException e){
							System.err.println("Pore could not be simulated because: " +e.getErrorMessage());
						}
					}else if(statusOfPore.equals("Finished")){
						//collecting output
						System.out.println("This pore is finished.");
						try{
							outputSequence.addSeq(p.getSequenceFromPore());
						}catch(Exception e){
							System.err.println("Error in tick-collecting output: "+e.getMessage());
						}
					}
				}
				checkFlowcellState();
			}catch(MyException e){
				System.err.println("This error occurs in the flowcell tick method: "+ e.getErrorMessage());

			}
		}
		
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public FiletypeContainingSequences getFlowcellOutput(){
		return outputSequence;
	}
	
	public int getNumberOfAllPores(){
		return poreList.size();
	}
	
	public int getNumberOfAlivePores(){
		int numAlivePores = 0;
		for(Pore p : poreList){
			if(!p.checkStatus().equals("Dead")){
				numAlivePores++;
			}
		}
		return numAlivePores;
	}
	
	public double[] getStates() throws MyException
	{
		//TODO return lengths of finished sequences?
		double[] states = new double[5];		//[0] Running, [1]Bored, [2] Dead,[3] Finished, [4] Sleeping,[4] sum of Pores
		for(Pore p : poreList)
		{
			switch(p.getState()){
			case "Running": 
				states[0]++; 
				break;
			case "Bored": 
				states[1]++; 
				break;
			case "Dead": 
				states[2]++; 
				break;
			case "Finished": 
				states[3]++; 
				break;
			case "Sleeping": 
				states[4]++; 
				break;
			default: throw new MyException(ErrorCodes.FLOWCELL_Invalid_Pore_Status);
			}
		}
		
		currentSumOfReads += (int)states[0] * 10;//currentReads are Recorded every Tick and added. 10 is just a example, not sure where the reads per tick are stored
												//better move this into methode tick
		return states;
	}
	public int getcurrentSumOfReads()
	{
		return currentSumOfReads;
	}
	
	//All test suffice
	/*
	 * tests
	 */
//	public static void main(String[] args) throws MyException{
//		
//	Flowcell g = new Flowcell();
//	g.currentSumOfReads = 0;
//	g.maxAgeOfPores = 100;
//	
//	//expected output
//	try{
//		g.checkFlowcellState();
//	}catch(MyException e){
//		System.err.println("Checkflowcell should return 'empty flowcell': "+ e.getErrorMessage());
//	}
//	
//	//expected output
//	try{
//		g.addPores(0);
//	}catch(MyException e){
//		System.err.println(e.getErrorMessage());
//	}
//	
//	//expected output
//	try{
//		g.addPores(5);
//	}catch(MyException e){
//		System.err.println(e.getErrorMessage());
//	}
//	//expected output
////	g.outputFormat = "txt";
//	g.outputFormat = "fasta";
////	g.outputFormat = "fastq";
//	try{
//		g.setFlowcellOutputFormat("fasta");
//	}catch(MyException e){
//		System.err.println(e.getErrorMessage());
//	}
//	
//	//expected
//	System.out.println("# all pores: " + g.getNumberOfAllPores()); //5
//	System.out.println("# alive pores: " + g.getNumberOfAlivePores()); //5
//	
//	
//	
//	try{
//		LengthDistribution length = new LengthDistribution(10);
//		BasecallingErrorRate err = new BasecallingErrorRate(1, "default.setting");
//	}catch(Exception e){
//		System.err.println("CReating Basecaling and Lengthdistribution causes errors in flowcell tests: " +e.getMessage());
//	}
//	Sequence seq = new FastASequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
//	g.tick(seq);
//	
//	Flowcell f = new Flowcell(5,100,"fasta");
//	f.tick(seq);
//	f.getNumberOfAlivePores();
//	f.getNumberOfAllPores();
//	f.getcurrentSumOfReads();
//	FiletypeContainingSequences blub = f.getFlowcellOutput();
//	blub.writeInFile("TestFlowcell.txt");
//	}
}
