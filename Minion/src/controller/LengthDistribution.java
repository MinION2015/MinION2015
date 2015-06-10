package controller;

import java.util.Random;

import error.Chance;
/**
 * 
 * @author Albert Langensiepen und Daniel Dehncke
 * LengthDistribution object will return a rand length with a prob found in the lengthrate class.
 * Same as with errorbasecalling, should prob not be a class, also length rate and length distribution can be merged, thus the commented lines of code, but i'm aunable to test it right now.
 */
public class LengthDistribution {

	
	
	public LengthDistribution(){
		
	}
	/**
	 * @author Friederike Hanssen und Albert Langensiepen und Daniel Dehncke
	 * generates a Random Length from the analyzed Data in LengthRate and returns it
	 */
	public double getRandLength(){
		
		LengthRate l = new LengthRate();
		
		
		Random r = new Random(); 
		double d = r.nextDouble(); 
		
		int i = 0;
		while(d > l.getProb(i))
		{
			i++;
		}
		
		return (int)l.getLength(i);
	}

	
/**
 * Test	
 */
//	public static void main(String args[]){
//		LengthDistribution l = new LengthDistribution();
//		double rand = l.getRandLength();
//		System.out.println(rand);
//	}
}
