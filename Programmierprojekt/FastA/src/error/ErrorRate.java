package error;

/**
 * 
 * @author Friederike Hanssen
 *
 */
public class ErrorRate {


	private double[][] transProb;
	private char[] base = {'A','C','T','G'};
	public ErrorRate(int basecalling){
		
		
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
				transProb[2][1] = 0.8;
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
	
	
	
}
