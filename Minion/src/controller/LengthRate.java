package controller;

/**
 * 
 * @author Friederike und Daneil Dehncke
 * saves the Sequenz Lengths from the analyzed Data and their possibilities.
 */
public class LengthRate {

	
	private double[][] possibilitiesLenght;

	//analysed lengths. Window of 3125
	public LengthRate(){
		this.possibilitiesLenght = new double[][]
				{{0.4132566348542293,0.6420447117270232,0.8364492090469342,
					0.9405151000130736,0.9748986795659563,0.9874493397829781,
					0.992417309452216,0.9960779186821807,0.997516015165381,
					0.9980389593410902,0.9986926395607267,0.9989541116485814,
					0.9993463197803634,0.999607791868218,0.9997385279121453,1.0},
				{3125.0,6250.0,9375.0,12500.0,15625.0,18750.0,21875.0,25000.0,
					28125.0,31250.0,34375.0,37500.0,40625.0,43750.0,46875.0,50000.0,}};
		
	}
	
	public double getLength(int index){
		return possibilitiesLenght[index][1];
		
	}
	
	public double getProb(int index){
		return possibilitiesLenght[index][0];
	}
/**
 * Test	
 */
//	public static void main(String args[]){
//		LengthRate r = new LengthRate();
//		for(int i = 0; i < 4; i++){
//				System.out.print(r.getProb(i) +" ");
//		}
//		System.out.println();
//		for(int i = 0; i < 4; i++){
//			System.out.print(r.getLength(i)+" ");
//		}
//	}
}
