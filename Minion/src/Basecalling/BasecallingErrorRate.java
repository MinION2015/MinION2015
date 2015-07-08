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
 * started  to make class static, not sure how to test, as the whole class is not functioning for me with the settingFilename etc.(Friederike)
 * above is fixed
 *@functionailty For a certain base and a basecalling type the transformation rate can becalled here
 * @Input: basecalling type and the settingFilename
 * @Output: getters for the value, base and row
 * default values of the lambda_phage are in the default.setting
 *
 *To generate the values for each position each letter gets a window. For example if a A switches to a G the value wirtten down is 1, but the prob is 0.1(->1-0.9).
 */
public class BasecallingErrorRate {


	private static double[][] transProbMatrix;
	private static char[] base = {'A','T','G','C'};
	private static double insertionProb = 0;
	private static double deletionProb = 0;
	
	public BasecallingErrorRate(int dimension,String settingFilePath) throws Exception{
		BasecallingErrorRate.transProbMatrix = new double[4][4];
		generate(dimension, settingFilePath);
		
	}
	
	
	private static void generate(int dimension,String settingFilePath) throws Exception{
		char cacheChar = ' ';
		String Value = "";
		BufferedReader Input = new BufferedReader(new FileReader(settingFilePath));
			if(dimension == 1){
				Input.readLine();
				Input.readLine();
			}else if(dimension == 2)
				for(int i=0;i<10;i++)
					Input.readLine();
			
			for(int i=0;i<4;i++){
				cacheChar=' ';
				Value = "";
				while(cacheChar!='#')
					cacheChar=(char) Input.read();
				for(int j=0;j<4;j++){
					cacheChar=' ';
					while(cacheChar!='#'&&cacheChar!='I'){
						cacheChar=(char) Input.read();
						Value = Value+cacheChar;
						transProbMatrix[i][j] = Double.parseDouble(Value);
					}
				}
			}
			while(cacheChar!='#')
				cacheChar=(char) Input.read();
			Value = "";
			cacheChar = ' ';
			while(cacheChar!='#'&&cacheChar!='O'){
				cacheChar=(char) Input.read();
				Value = Value+cacheChar;
			}
			insertionProb = Double.parseDouble(Value);
			Value = "";
			cacheChar = ' ';
			while(cacheChar!='#')
				cacheChar=(char) Input.read();
			while(cacheChar!='#'){
				cacheChar=(char) Input.read();
				Value = Value+cacheChar;
			}
			deletionProb = Double.parseDouble(Value);
	}
	/*
	public static void age(){
		insertionProb=insertionProb*1.1;
		deletionProb=deletionProb*1.1;
		for(int i=0;i<4;i++){
			transProbMatrix[i][]
		}
		*/	
				
				
	
	
	public static double getValue(int i, int j){
		return transProbMatrix[i][j];
	}
	
	public static String getBase(int i){
		return Character.toString(base[i]);
	}
	
	public static double getInsertionProb(){
		return insertionProb;
	}
	
	public static double getDeletionProb(){
		return deletionProb;
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
/* test works
	public static void main(String args[]){
		BasecallingErrorRate err;
		try {
			err = new BasecallingErrorRate(2,"default");
		} catch (Exception e) {
		}
		for(int i = 0; i < 4;i++){
			for(int j = 0; j < 5;j++){
				System.out.print(BasecallingErrorRate.getValue(i,j) + " ");
		}
		System.out.println();
		}
		
//		System.out.println(err.getBase(2)); //T
		
	//	System.out.println(err.getRow('T')); //2
	}*/
	
	
	
}
