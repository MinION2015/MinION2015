package controller;

import error.Chance;
/**
 * 
 * @author Friederike Hanssen
 *
 */
public class LengthDistribution {

	public LengthDistribution(){
		
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
	
//	public static void main(String args[]){
//		LengthDistribution l = new LengthDistribution();
//		double rand = l.getRandLength();
//		System.out.println(rand);
//	}
}
