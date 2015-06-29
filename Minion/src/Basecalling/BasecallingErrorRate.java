package Basecalling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import error.ErrorCodes;
import error.MyException;

/**
 * 
 * @author Kevin Lindner & Friederike Hanssen
 *@functionailty For a certain base and a basecalling type the transformation rate can becalled here
 * @Input: basecalling type
 * @Output: getters for the value, base and row
 *Hardcoded error rates for basecalling, calculated from the data. So far only dummy values are written. 
 *To generate the values for each position each letter gets a window. For example if a A switches to a G the value wirtten down is 1, but the prob is 0.1(->1-0.9).
 */
public class BasecallingErrorRate {


	private static double[][] transProb;
	private static char[] base = {'A','T','G','C','-'};
	
	public BasecallingErrorRate(int basecalling,String settingFilename) throws Exception{
		
		
		this.transProb = new double[4][5];
		generate(basecalling, settingFilename);
		
	}
	
	private static void generate(int basecalling,String settingFilename) throws Exception{
		BufferedReader Input = new BufferedReader(new FileReader(settingFilename));
			if(basecalling == 1){
				Input.readLine();
				Input.readLine();
			}else if(basecalling == 2){
				for(int i=0;i<27;i++)
					Input.readLine();
			}
				transProb[0][0] = Integer.parseInt(Input.readLine());
				transProb[0][1] = transProb[0][0]+Integer.parseInt(Input.readLine());
				transProb[0][2] = transProb[0][1]+Integer.parseInt(Input.readLine());
				transProb[0][3] = transProb[0][2]+Integer.parseInt(Input.readLine());
				transProb[0][4] = transProb[0][3]+Integer.parseInt(Input.readLine());
				Input.readLine();
				transProb[1][0] = Integer.parseInt(Input.readLine());
				transProb[1][1] = transProb[1][0]+Integer.parseInt(Input.readLine());
				transProb[1][2] = transProb[1][1]+Integer.parseInt(Input.readLine());
				transProb[1][3] = transProb[1][2]+Integer.parseInt(Input.readLine());
				transProb[1][4] = transProb[1][3]+Integer.parseInt(Input.readLine());
				Input.readLine();
				transProb[2][0] = Integer.parseInt(Input.readLine());
				transProb[2][1] = transProb[2][0]+Integer.parseInt(Input.readLine());
				transProb[2][2] = transProb[2][1]+Integer.parseInt(Input.readLine());
				transProb[2][3] = transProb[2][2]+Integer.parseInt(Input.readLine());
				transProb[2][4] = transProb[2][3]+Integer.parseInt(Input.readLine());
				Input.readLine();
				transProb[3][0] = Integer.parseInt(Input.readLine());
				transProb[3][1] = transProb[3][0] +Integer.parseInt(Input.readLine());
				transProb[3][2] = transProb[3][1] +Integer.parseInt(Input.readLine());
				transProb[3][3] = transProb[3][2] +Integer.parseInt(Input.readLine());
				transProb[3][4] = transProb[3][3]+Integer.parseInt(Input.readLine());
	}
	
	public static double getValue(int i, int j){
		return transProb[i][j];
	}
	
	public static String getBase(int i){
		return Character.toString(base[i]);
	}
	
	public static  int getRow(char a){
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
