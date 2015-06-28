package controller;


import error.*;
import reader.*;

import java.util.Iterator;
import java.util.Random;

import Basecalling.SimulationError;
import LengthDistribution.LengthDistribution;

/**
 * @author Albert Langensiepen und Daniel Dehncke
 * @functionality the class simulates a minION Pore via the simulate function
 * @input a DNA sequence, an error model chosen by the user, a random sequence length from the Length Distribution and a basecalling code
 * @output a Sequence object
 */
public class Pore {
	
	String fasta ="";
	Sequence seq = new Sequence("> DNA sequence generated by Pore",fasta);
	
	String state = "Bored";
	int age = 0;
	int numbersOfTimeAsked = 0;
	int sequenceLength = 0; //for checking if more ticks are are done then sequence length
	
	
	/**
	 * @author Albert Langensiepen
	 * @functionality empty constructor for Pore
	 * @output a pore object
	 */
	public Pore()
	{
		
	}

	/**
	 * @author Albert Langensiepen und Daniel Dehncke
	 * @throws Exception 
	 * @functionality simulation of a single DNA sequence running through a minION Pore
	 * 				  first a random starting point in the sequence is generated and a length is given
	 * 				  if the starting point is so far in the sequence, that with the given length
	 * 				  the subsequence exceeds the length of the sequence a more fitting starting point is generated
	 * 				  then the subsequence is generated and given to the error model class which edits it and then returns it
	 * 				  in the end the edited sequence is put into a Sequence object and the object is being returned 
	 * @input a DNA sequence, an error model chosen by the user, a random sequence length from the Length Distribution and a basecalling code
	 * @output a Sequence object
	 */
	public Sequence simulate(String sequence, SimulationError errorModel, LengthDistribution l, int basecalling) throws Exception
	{
		Random rand = new Random();

		int length =(int) l.getRandLength();
		
		//checks if the random lengths are feasible
		for(int i=0;i<10;i++)
		{
			if(length>=sequence.length())
			{
				length =(int) l.getRandLength();
			}
			else break;
			if(i==9)
			{
				System.out.println("Die Sequenz ist zu kurz!");
			}
		}
		//random number between 0 and sequenceLength-lenght is created
		int start = rand.nextInt(sequence.length()-length);
		
		
		/*
		 * SequenceLengthThreshold is for the 
		 */
		sequenceLength = length;
	

		String subseq = sequence.substring(start, start+length);

		fasta = errorModel.applyErrorBasecalling(subseq, basecalling,"setting/default.setting");
		
		if(fasta.isEmpty()) System.out.println("Sequence is empty");

		seq = new Sequence("> DNA sequence generated by Pore",fasta);
	
		
		
		return seq;

	}
	
	public String checkStatus()
	{
		if(this.state.equals("Dead"))
			return "Dead";
		
		if(this.state.equals("Running"))
		{
			this.age++;
			this.numbersOfTimeAsked++;
			return "Running";
		}
		
		if(this.state.equals("Bored"))
			return "Bored";
		
		if(this.numbersOfTimeAsked > this.sequenceLength)
		{
			setState("Finished");
			return "Finished";
		}else
		{
			//TODO Albert
			int ageDead=tryToDie(age);
			
			if(age>ageDead)
			{
				setState("Dead");
				return "Dead";
			}
			
			else if(this.state.equals("Finished"))
			{
				setState("Bored");
				return "Bored";
			}
			else return "Running";
		}
		
	}
	
	private void setState(String state)
	{
		this.state = state;
	}
	
	/**
	 * @author: Albert Langensiepen
	 * @input: age of a Pore
	 * @output: the age at which the Pore shall die
	 * @functionality: depending on the age of a pore the probability that it dies increases
	 * 					a random double between 0 and 1 is generated and depending on the age the interval for dying grows
	 * 					if the number is in this interval the pore dies
	 */
	private int tryToDie(int age)
	{
		
		int ageDead=1000000000;
		
		Random rand = new Random();
		double d = rand.nextDouble(); 
		
		// for now identical distribution
		//double sup= age/49194; //longest length thus biggest age?
		double sup= age/100;
		if(d<sup)
		{
			ageDead=age;
		}
		
		return ageDead;
		
	}


}
