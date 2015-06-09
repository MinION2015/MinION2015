package controller;


import error.*;
import reader.*;
import java.util.Random;

/**
 * @author Albert Langensiepen
 * @functionality the class simulates a minION Pore via the simulate function
 * @input a DNA sequence, an error model chosen by the user, a random sequence length from the Length Distribution and a basecalling code
 * @output a Sequence object
 */
public class Pore {
	
	String fasta ="";
	Sequence seq= new Sequence("> DNA sequence generated by Pore",fasta);
	
	/**
	 * @author Albert Langensiepen
	 * @functionality empty constructor for Pore
	 * @output a pore object
	 */
	public Pore()
	{
		
	}

	/**
	 * @author Albert Langensiepen
	 * @functionality simulation of a single DNA sequence running through a minION Pore
	 * 				  first a random starting point in the sequence is generated and a length is given
	 * 				  if the starting point is so far in the sequence, that with the given length
	 * 				  the subsequence exceeds the length of the sequence a more fitting starting point is generated
	 * 				  then the subsequence is generated and given to the error model class which edits it and then returns it
	 * 				  in the end the edited sequence is put into a Sequence object and the object is being returned 
	 * @input a DNA sequence, an error model chosen by the user, a random sequence length from the Length Distribution and a basecalling code
	 * @output a Sequence object
	 */
	public Sequence simulate(String sequence, SimulationError errorModel, LengthDistribution l, int basecalling) throws MyException
	{
		Random rand = new Random();
		
		//random number between 0 and sequenceLength-1 is created
		int start = rand.nextInt(sequence.length());
		
		int length =(int) l.getRandLength();
		/*
		 *  if the starting point is so far in the sequence, that with the given length
	     *  the subsequence exceeds the length of the sequence a more fitting starting point is generated
		 */
		while(start > sequence.length()-length)
		{
			start = rand.nextInt(sequence.length());
			
		}

		String subseq = sequence.substring(start, start+length);

		
		
		try{
			fasta = errorModel.applyErrorBasecalling(subseq, basecalling);
			throw new MyException(ErrorCodes.PORE_INITIATED);
			
		}catch(MyException e){
			
		}
		
		
		try{
			seq = new Sequence("> DNA sequence generated by Pore",fasta);
			throw new MyException(ErrorCodes.PORE_FINISHED);
			
			
		}catch(MyException e){
			
			
		}
		
		
		return seq;

	}
	
	


}