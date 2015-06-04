package controller;

import java.util.Random;

import error.ErrorBasecalling;

public class Pore {
	
	public Pore()
	{};

	public String simulate(String sequence, ErrorBasecalling errorModel, LengthDistribution l )
	{
		Random rand = new Random();
		//random number between 0 and sequenceLength-1 is created
		int start = rand.nextInt(sequence.length());
		
		int length =(int) l.getRandLength();
		/*
		 *  if the given length is smaller than the difference
		 *  between sequenceLength and start this length can be used
		 *  otherwise it would cause an outOfBounds-Error 
		 */
		while(length > sequence.length()-start)
		{
			length= (int)l.getRandLength();
			
		}
		System.out.println(length);
		String subseq = sequence.substring(start, start+length);
		
		String fasta = errorModel.apply(subseq, 1);


		return fasta;
		
		
	}
	


}
