package controller;

import error.Chance;
/**
 * 
 * @author Friederike Hanssen
 * LengthDistribution object will return a rand length with a prob found in the lengthrate class.
 * Same as with errorbasecalling, should prob not be a class, also length rate and length distribution can be merged, thus the commented lines of code, but i'm aunable to test it right now.
 */
public class LengthDistribution {

	//private double[][] length;
	
	public LengthDistribution(){
//		this.length = new double[4][2];
//		generate();
	}
	
	public double getRandLength(){
		double prob = Chance.getRand();
		LengthRate l = new LengthRate();
		
		if(prob <= l.getProb(0)){
			return l.getLength(0);
		}else if (prob > l.getProb(0) && prob <= l.getProb(1)){
			return l.getLength(1);
		}else if (prob > l.getProb(1) && prob <= l.getProb(2)){
			return l.getLength(2);
		}else if (prob > l.getProb(2) && prob <= l.getProb(3)){
			return l.getLength(3);
		}
		
		return -1;
	}

//	private void generate(){
//
//		length[0][0] = 0.2;
//		length[1][0] = 0.6;
//		length[2][0] = 0.8;
//		length[3][0] = 1;
//
//		length[0][1] = 10;
//		length[1][1] = 20;
//		length[2][1] = 5;
//		length[3][1] = 1;
//	}
//	
//	private double getLength(int index){
//		return length[index][1];
//		
//	}
//	
//	private double getProb(int index){
//		return length[index][0];
//	}
	
	
//	public static void main(String args[]){
//		LengthDistribution l = new LengthDistribution();
//		double rand = l.getRandLength();
//		System.out.println(rand);
//	}
}
