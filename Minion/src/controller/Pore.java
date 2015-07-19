package controller;


import java.util.Random;

import reader.*;
import Basecalling.BasecallingErrorRate;
import Basecalling.SimulationError;
import LengthDistribution.LengthDistribution;
import error.Chance;
import error.MyException;

/**
 * @author Albert Langensiepen und Daniel Dehncke
 * @functionality the class simulates a minION Pore via the simulate function
 * @input a DNA sequence, an error model chosen by the user, a random sequence length from the Length Distribution and a basecalling code
 * @output a Sequence object
 */
public class Pore {
	

	private Sequence seqInPore;
	private String state;
	private int age;
	private int numbersOfTimeAsked;
	private int sequenceLength; //for checking if more ticks are are done then sequence length sf
	
	private static int timeBetweenLastSlumber;
	private int sleepTime; //time it has been sleeping
	private static boolean wokeUp;

	private static int  ageLimit;
	private static int [] deathProbs;// = new int[ageLimit];// the probabilities if the pore dies depending on ageLimit 
	private static int [] sleepProbs;// = new int[500];// the probabilities if the pore goes to sleep depending on the time it last slept
	private static int [] wakeProbs;// = new int[100];
	
	
	/**
	 * @author Albert Langensiepen
	 * @functionality constructor for Pore
	 * @output a pore object
	 */
//	public Pore(){
//		
//	}
	public Pore(int ageLimit)
	{
		this.state = "Bored";
		this.age = 0;
		this.numbersOfTimeAsked = 0;
		this.sequenceLength = 0;
		
		this.timeBetweenLastSlumber = 0;
		this.sleepTime = 0;
		this.wokeUp = false;
		
		int sleepForSure =(int)Math.round(ageLimit*0.5) ; //number of ticks at which the Pore will sleep with 100% prob
		int wakeForSure =(int)Math.round(ageLimit*0.25) ;
			
		Pore.ageLimit=ageLimit;
		Pore.deathProbs =new int[ageLimit];
		Pore.sleepProbs = new int[sleepForSure];
		Pore.wakeProbs = new int [wakeForSure];
		Pore.deathProbs =setProbs(ageLimit,deathProbs);
		Pore.sleepProbs=setProbs(sleepForSure,sleepProbs); // for now after 500 ticks the Pore will definitely go to sleep
		Pore.wakeProbs=setProbs(wakeForSure,wakeProbs);
	}
	

	/**
	 * @author Albert Langensiepen und Daniel Dehncke und Friederike Hanssen(reorganized somethings, added that the same sequence type the pore got will be returned, so that the pore doesn't have to bother with fasta/fastq format)
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
	public Sequence simulate(Sequence sequence, String seqType) throws MyException
	{
		this.state = "Running";
		sequenceLength = 0;
		seqInPore = sequence;
		boolean lengthFound = false;
		for(int i = 0; i < 10; i++){
			if(!lengthFound){
				sequenceLength = (int) LengthDistribution.getRandLength();
				try{
					if (sequenceLength < seqInPore.lengthOfSequence()){
						lengthFound = true;
					}
				}catch(Exception e){
					//TODO LEngthDistribution throws NUllpointers right now
					sequenceLength = 4;
					lengthFound = true;
					System.err.println("Lenght distribution class caused following error: "+ e.getMessage());
				}
			}
		}



		//random number between 0 and sequenceLength-lenght is created
		int start = Chance.getRandInt(0,seqInPore.lengthOfSequence()-sequenceLength);		

		//	System.out.println(sequence.getSequence().substring(start, start+sequenceLength));
		String[] mutation;
		String seqMutated = "";
		String score ="";
		try{
			mutation = SimulationError.applyErrorBasecalling(seqType,(seqInPore.getSequence().substring(start, start+sequenceLength)));
			seqMutated = mutation[0];
			score = mutation[1];
		}catch(Exception e){//Should be mYException, but basecalling throws index out of bounds thus left it like this to keep the program running
			seqMutated = "ACTG";
			System.err.println("Following error occurrs in pore class when simulate tries to apply basecalling error rate :" + e.getMessage());
		}

		if(seqMutated.isEmpty()){
			System.out.println("Pore had trouble sequencing. Could not produce any output.");
		}

		seqInPore.setSequence(seqMutated);
		seqInPore.setScore(score);
		return sequence;
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
		
		switch(state)
		{
		case "Dead": return "Dead";
		case "Finished": setStatus("Bored");
							return "Bored";
							//when the Pore has been asked more often than the length of the sequence its processed it is finished
							//when its still processing it can always die, which depends on its age
		case "Running": 	if(numbersOfTimeAsked >= sequenceLength) 
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
									wokeUp=false;
									return "Dead";
								}
					
								else{
									age++;
									numbersOfTimeAsked++;
									return "Running";
								}
							}
		//the Pore can wake if its sleeping or continue to sleep					
		case "Sleeping":  	age++;
							boolean wake= tryToWake(sleepTime);
							if(wake)
							{
								//timeBetweenLastSlumber++;
								wokeUp=true;
								setStatus("Bored");
								return "Bored";
							}
							else{
								sleepTime++;
								return "Sleeping";
							}
							
							//when the Pore is bored it can go to sleep or it will simulate again and process another read
							// question is if its return
		case "Bored":		if(state.equals("Bored"))
							{
								
								boolean asleep=tryToSleep(timeBetweenLastSlumber);
								
								if(asleep)
								{
									setStatus("Sleeping");
									timeBetweenLastSlumber=0;
									return "Sleeping";
								}
								else
								{
									timeBetweenLastSlumber++;
									return "Running";
									// or return "Bored" ??
								}
							}
		default: return "undefined";
		
		}
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
		if(r<=deathProbs[age]) return dead=true;
		else return dead=false;
	}
	
	/**
	 * @author: Albert Langensiepen
	 * @input: age of a Pore or time between the last time the Pore slept
	 * @output: boolean if the pore goes to sleep
	 * @functionality:  a random integer between 1 and 100 is generated and depending on the age of the Pore the possibility
	 * 					at this index in the array is taken and if the number is less or equal to this number the pore goes to sleep  					
	 */
	private static boolean tryToSleep(int age)
	{
		boolean sleep=false;
		Random rand = new Random();
		int r = rand.nextInt(100)+1; 
	

			if(r<=sleepProbs[timeBetweenLastSlumber])
			{
				wokeUp=false;
				return sleep=true;
			}
			else return sleep=false;
			
	}
	
	private static boolean tryToWake(int sleepTime)
	{
		
		boolean wake=false;
		Random rand = new Random();
		int r = rand.nextInt(100)+1;
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
	private static int[] setProbs(int ageLimit, int [] probs)
	{
		for(int i=0; i<ageLimit;i++)
		{
			if(i<ageLimit*0.1) probs[i]=0;
			if(ageLimit*0.1<=i && i <ageLimit*0.2) probs[i]=1;
			if(ageLimit*0.2<=i && i<ageLimit*0.3) probs[i]=3;
			if(ageLimit*0.3<=i &&i<ageLimit*0.4) probs[i]=4;
			if(ageLimit*0.4<=i &&i<ageLimit*0.5) probs[i]=5;
			if(ageLimit*0.5<=i &&i<ageLimit*0.6) probs[i]=10;
			if(ageLimit*0.6<=i &&i<ageLimit*0.7) probs[i]=20;
			if(ageLimit*0.7<=i &&i<ageLimit*0.8) probs[i]=40;
			if(ageLimit*0.8<=i &&i<ageLimit*0.9) probs[i]=60;
			if(ageLimit*0.9<=i &&i<ageLimit*0.95) probs[i]=90;
			if(ageLimit*0.95<=i &&i<ageLimit) probs[i]=100;
			
		}
		return probs;
	}

	public void setStatus(String state)
	{
		this.state = state;
	}

	public String getState()
	{
		return state;
	}
	public int getnumbersOfTimeAsked()
	{
		return numbersOfTimeAsked;
	}
	public Sequence getSequenceFromPore(){
		return seqInPore;
	}
	
	/**
	 * Test Friederike
	 * @param args
	 */
//	public static void main(String[] args){
//		Pore p = new Pore(300);
//		
//		p.age=280;
//		p.state="Running";
//		p.numbersOfTimeAsked=10;
//		p.sequenceLength=20;
//		System.out.println(p.checkStatus());
//		
//		
//	
//	}




}
