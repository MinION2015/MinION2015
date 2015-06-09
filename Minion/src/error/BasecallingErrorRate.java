package error;

/**
 * 
 * @author Friederike Hanssen
 * For a certain base and a basecalling type the transformation rate can becalled here
 * Input: basecalling type
 * Output: getters for the value, base and row
 *Hardcoded error rates for basecalling, calculated from the data. So far only dummy values are written. 
 *To generate the values for each position each letter gets a window. For example if a A switches to a G the value wirtten down is 1, but the prob is 0.1(->1-0.9).
 */
public class BasecallingErrorRate {


	private double[][] transProb;
	private char[] base = {'A','C','T','G'};
	public BasecallingErrorRate(int basecalling){
		
		
		this.transProb = new double[4][4];
		generate(basecalling);
		
	}
	
	private void generate(int basecalling){
		
			if(basecalling == 1){

				transProb[0][0] = 0.5;
				transProb[0][1] = 0.8;
				transProb[0][2] = 0.9;
				transProb[0][3] = 1;
				
				
				transProb[1][0] = 0.1;
				transProb[1][1] = 0.6;
				transProb[1][2] = 0.8;
				transProb[1][3] = 1;
				
				
				transProb[2][0] = 0.1;
				transProb[2][1] = 0.2;
				transProb[2][2] = 0.8;
				transProb[2][3] = 1;
				
				
				transProb[3][0] = 0.3;
				transProb[3][1] = 0.4;
				transProb[3][2] = 0.5;
				transProb[3][3] = 1;
				
				
			}else if(basecalling == 2){
				transProb[0][0] = 0.5;
				transProb[0][1] = 0.8;
				transProb[0][2] = 0.9;
				transProb[0][3] = 1;
				
				
				transProb[1][0] = 0.1;
				transProb[1][1] = 0.6;
				transProb[1][2] = 0.8;
				transProb[1][3] = 1;
				
				
				transProb[2][0] = 0.1;
				transProb[2][1] = 0.2;
				transProb[2][1] = 0.8;
				transProb[2][3] = 1;
				
				
				transProb[3][0] = 0.3;
				transProb[3][1] = 0.4;
				transProb[3][2] = 0.5;
				transProb[3][3] = 1;
			}
	}
	
	public double getValue(int i, int j){
		return transProb[i][j];
	}
	
	public String getBase(int i){
		return Character.toString(base[i]);
	}
	
	public int getRow(char a){
		for(int i = 0; i < base.length;i++){
			if(a == base[i])
				return i;
		}
		return -1;
	}
	
	/**
	 * Test
	 */

//	public static void main(String args[]){
//		BasecallingErrorRate err = new BasecallingErrorRate(2);
//		for(int i = 0; i < 4;i++){
//			for(int j = 0; j < 4;j++){
//				System.out.print(err.getValue(i,j) + " ");
//			}
//		System.out.println();
//		}
//		
//		System.out.println(err.getBase(2)); //T
//		
//		System.out.println(err.getRow('T')); //2
//	}
//	
	
	
}
