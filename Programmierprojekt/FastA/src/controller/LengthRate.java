package controller;

/**
 * 
 * @author Friederike
 *
 */
public class LengthRate {

	
	private double[][] length;

	
	public LengthRate(){
		this.length = new double[4][2];
		generate();
	}
	
	private void generate(){
		
		length[0][0] = 0.2;
		length[1][0] = 0.6;
		length[2][0] = 0.8;
		length[3][0] = 1;
		
		length[0][1] = 10;
		length[1][1] = 20;
		length[2][1] = 5;
		length[3][1] = 1;
	}
	
	public double getLength(int index){
		return length[index][1];
		
	}
	
	public double getProb(int index){
		return length[index][0];
	}
	
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
