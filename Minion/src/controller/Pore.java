package controller;


import error.*;
import reader.*;

import java.io.IOException;
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
	private Sequence seq;

	private String state = "Bored";
	private int age = 0;
	private int numbersOfTimeAsked = 0;
	private int sequenceLength = 0; //for checking if more ticks are are done then sequence length s
	private static int  ageLimit=0;
	private static int [] deathProbs = new int[ageLimit];// the probabilities if the pore dies depending on ageLimit 
	private static int [] sleepProbs = new int[500];// the probabilities if the pore goes to sleep depending on the time it last slept
	private static int [] wakeProbs = new int[100];
	private boolean beenAsleepOnce=false;
	private int timeBetweenLastSlumber=0;
	private int sleepTime=0; //time it has been sleeping
	private boolean wokeUp=false;
	
	/**
	 * @author Albert Langensiepen
	 * @functionality constructor for Pore
	 * @output a pore object
	 */
	public Pore(){
		
	}
	public Pore(int ageLimit)
	{
		this.ageLimit=ageLimit;
		this.deathProbs =new int[ageLimit];
		this.deathProbs =setDeathProbs(ageLimit);
		this.sleepProbs=setSleepProbs(500); // for now after 500 ticks the Pore will definitely go to sleep
		this.wakeProbs=setWakeProbs(100);
	}
	
	//Exceptions fŸr zu hohes age -> arrayoutofBounds schreiben
	public static void main(String[] args) throws MyException, IOException
	{
		
		Pore p = new Pore(700);
		
		p.sleepTime=10;
		p.beenAsleepOnce=true;
		p.timeBetweenLastSlumber=400;
		p.age=599;
		p.numbersOfTimeAsked=5;
		p.sequenceLength=10;
		p.setStatus("Running");
		System.out.println(p.checkStatus());
		System.out.println("Pore State: "+p.state);
		System.out.println("Age: "+p.age);
		System.out.println("Numbers of Times Asked: "+p.numbersOfTimeAsked);
		
		//Sequence seq = new Sequence(">","ATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCG");
		//System.out.println(p.simulate(seq).getSequence()); 
		

		
		
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
	public Sequence simulate(Sequence sequence) throws Exception
	{
		Random rand = new Random();

		int length = (int) LengthDistribution.getRandLength();
		
		//checks if the random lengths are feasible
		for(int i=0;i<10;i++)
		{
			if(length>=sequence.lengthOfSequence())
			{
				length =(int) LengthDistribution.getRandLength();
			}
			else break;
			if(i==9)
			{
				throw new MyException(ErrorCodes.Pore_NOCAPABLESEQUENCELENGTH);
				
			}
		}
		
		//random number between 0 and sequenceLength-lenght is created
		int start = rand.nextInt(sequence.lengthOfSequence()-length);
		
		System.out.println(start);
		
		
		/*
		 * SequenceLengthThreshold is for the checkStatus-method
		 */
		sequenceLength = length;
	

		String subseq = sequence.getSequence().substring(start, start+length);
		
		System.out.println(subseq);


		String fasta = errorModel.applyErrorBasecalling(subseq, basecalling,"setting/default.setting");
		try{
			fasta = SimulationError.applyErrorBasecalling(subseq);
		}catch(Exception e){
			System.err.println("Error occurs in pore class");
		}
		
		if(fasta.isEmpty()) System.out.println("Sequence is empty");

		//Sequence seq = new Sequence(sequence.,fasta);
		seq = new Sequence(sequence.getHeader(),fasta);
		this.state = "Running";
		
		
		return seq;

	}
	
	public Sequence getSequenceFromPore(){
		return seq;
	}
	/**
	 * @author Daniel Dehncke und Albert Langensiepen
	 * @return String state
	 * checks ths state of the pore and returns the fitting state. Depending on the state some values are changed
	 */
	public String checkStatus()
	{
		if(wokeUp)
		{
			timeBetweenLastSlumber++;
		}
		
		if(state.equals("Dead"))
			return "Dead";
		
		if(state.equals("Finished"))
			return "Bored";
		
		if(state.equals("Running"))
		{	
			// nur wŠhrend running? ? noch unklar!
			if(numbersOfTimeAsked > sequenceLength) 
			{
				setStatus("Finished");
				return "Finished";
			}
			else
			{
				

				boolean dead=tryToDie(age);
				
				if(dead)
				{
					setStatus("Dead");
					return "Dead";
				}
//				else if(state.equals("Finished"))
//				{
//					setStatus("Bored");
//					return "Bored";
//				}
				else{
					age++;
					numbersOfTimeAsked++;
					return "Running";
				}
			}
			
			
//			age++;
//			numbersOfTimeAsked++;
//			return "Running";
		}
		
		if(state.equals("Sleeping"))
		{
			boolean wake= tryToWake(sleepTime);
			if(wake)
			{
				timeBetweenLastSlumber++;
				wokeUp=true;
				setStatus("Bored");
				return "Bored";
			}
			else{
				sleepTime++;
				return "Sleeping";
			
			}
			
		}
		
		if(state.equals("Bored"))
		{
			if(!beenAsleepOnce)
			{	
			boolean asleep=tryToSleep(age);
			
			if(asleep)
			{
				setStatus("Sleeping");
				timeBetweenLastSlumber=0;
				beenAsleepOnce=true;
				wokeUp=false;
				return "Sleeping";
			}
			else return "Bored";
			}
			
			else
			{	
			boolean asleep=tryToSleep2(timeBetweenLastSlumber);
			
			if(asleep)
			{
				setStatus("Sleeping");
				timeBetweenLastSlumber=0;
				return "Sleeping";
			}
			else return "Bored";
			}
		}
		//not sure about this
		else return "Bored";
		
	}
	
	public void setStatus(String state)
	{
		this.state = state;
	}
	
	/**
	 * @author: Albert Langensiepen
	 * @input: age of a Pore 
	 * @output: boolean if the pore dies
	 * @functionality:  a random integer between 1 and 100 is generated and depending on the age of the Pore the possibility
	 * 					at this index in the array is taken and if the number is less or equal to this number the pore dies 
	 * 					
	 */
	private static boolean tryToDie(int age)
	{
		boolean dead=false;
		Random rand = new Random();
		int r = rand.nextInt(100)+1; 
		System.out.println("zufallszahl: "+r);
		System.out.println("Intervallsobergrenze: "+deathProbs[age]);
		
		if(r<=deathProbs[age]) return dead=true;
		else return dead=false;
	}
	
	/**
	 * @author: Albert Langensiepen
	 * @input: age of a Pore 
	 * @output: boolean if the pore goes to sleep
	 * @functionality:  a random integer between 1 and 100 is generated and depending on the age of the Pore the possibility
	 * 					at this index in the array is taken and if the number is less or equal to this number the pore goes to sleep  					
	 */
	private static boolean tryToSleep(int age)
	{
		boolean sleep=false;
		Random rand = new Random();
		int r = rand.nextInt(100)+1; 
		System.out.println("zufallszahl: "+r);
		System.out.println("Intervallsobergrenze: "+sleepProbs[age]);
		
		if(r<=sleepProbs[age]) return sleep=true;
		else return sleep=false;
	}
	
	/**
	 * @author: Albert Langensiepen
	 * @input: time between last time the Pore was asleep  
	 * @output: boolean if the pore goes to sleep
	 * @functionality:  a random integer between 1 and 100 is generated and depending on the last time the Pore was asleep, the possibility
	 * 					at this index in the array is taken and if the number is less or equal to this number the pore goes to sleep  					
	 */
	private static boolean tryToSleep2(int timeBetweenLastSlumber)
	{
		
		boolean sleep=false;
		Random rand = new Random();
		int r = rand.nextInt(100)+1; 
		
		System.out.println("zufallszahl: "+r);
		System.out.println("Intervallsobergrenze: "+sleepProbs[timeBetweenLastSlumber]);
		if(r<=sleepProbs[timeBetweenLastSlumber]) return sleep=true;
		else return sleep=false;
	}
	
	private static boolean tryToWake(int sleepTime)
	{
		
		boolean wake=false;
		Random rand = new Random();
		int r = rand.nextInt(100)+1;
		//System.out.println("zufallszahl: "+r);
		//System.out.println("Intervallsobergrenze: "+wakeProbs[sleepTime]);
		if(r<=wakeProbs[sleepTime]) return wake=true;
		else return wake=false;
	}
	
	
	
	/**
	 * @author: Albert Langensiepen
	 * @input:  age Limit of a Pore 
	 * @output: array of death probabilities depending on age Limit or sleep probs depending on last time the Pore slept
	 * @functionality: depending on the age of a pore the probability that it dies increases
	 * 					an array is generated where with 5-10%-intervals of AgeLimit the Probability to die increases
	**/
	private static int[] setDeathProbs(int ageLimit)
	{
		for(int i=0; i<ageLimit;i++)
		{
			if(i<ageLimit*0.1) deathProbs[i]=0;
			if(ageLimit*0.1<=i && i <ageLimit*0.2) deathProbs[i]=1;
			if(ageLimit*0.2<=i && i<ageLimit*0.3) deathProbs[i]=3;
			if(ageLimit*0.3<=i &&i<ageLimit*0.4) deathProbs[i]=4;
			if(ageLimit*0.4<=i &&i<ageLimit*0.5) deathProbs[i]=5;
			if(ageLimit*0.5<=i &&i<ageLimit*0.6) deathProbs[i]=10;
			if(ageLimit*0.6<=i &&i<ageLimit*0.7) deathProbs[i]=20;
			if(ageLimit*0.7<=i &&i<ageLimit*0.8) deathProbs[i]=40;
			if(ageLimit*0.8<=i &&i<ageLimit*0.9) deathProbs[i]=60;
			if(ageLimit*0.9<=i &&i<ageLimit*0.95) deathProbs[i]=90;
			if(ageLimit*0.95<=i &&i<ageLimit) deathProbs[i]=100;
			
		}
		return deathProbs;
	}
	
	private static int[] setSleepProbs(int age)
	{
		for(int i=0; i<age;i++)
		{
			if(i<age*0.1) sleepProbs[i]=0;
			if(age*0.1<=i && i <age*0.2) sleepProbs[i]=1;
			if(age*0.2<=i && i<age*0.3) sleepProbs[i]=3;
			if(age*0.3<=i &&i<age*0.4) sleepProbs[i]=4;
			if(age*0.4<=i &&i<age*0.5) sleepProbs[i]=5;
			if(age*0.5<=i &&i<age*0.6) sleepProbs[i]=10;
			if(age*0.6<=i &&i<age*0.7) sleepProbs[i]=20;
			if(age*0.7<=i &&i<age*0.8) sleepProbs[i]=40;
			if(age*0.8<=i &&i<age*0.9) sleepProbs[i]=60;
			if(age*0.9<=i &&i<age*0.95) sleepProbs[i]=90;
			if(age*0.95<=i &&i<age) sleepProbs[i]=100;
			
		}
		return sleepProbs;
	}
	
	private static int[] setWakeProbs(int age)
	{
		for(int i=0; i<age;i++)
		{
			if(i<age*0.1) wakeProbs[i]=0;
			if(age*0.1<=i && i <age*0.2) wakeProbs[i]=1;
			if(age*0.2<=i && i<age*0.3) wakeProbs[i]=3;
			if(age*0.3<=i &&i<age*0.4) wakeProbs[i]=4;
			if(age*0.4<=i &&i<age*0.5) wakeProbs[i]=5;
			if(age*0.5<=i &&i<age*0.6) wakeProbs[i]=10;
			if(age*0.6<=i &&i<age*0.7) wakeProbs[i]=20;
			if(age*0.7<=i &&i<age*0.8) wakeProbs[i]=40;
			if(age*0.8<=i &&i<age*0.9) wakeProbs[i]=60;
			if(age*0.9<=i &&i<age*0.95) wakeProbs[i]=90;
			if(age*0.95<=i &&i<age) wakeProbs[i]=100;
			
		}
		return wakeProbs;
	}
	
	
//	public static void main(String[] args){
//		Pore p = new Pore(10);
//		System.out.println(p.checkStatus());
//	}



}
