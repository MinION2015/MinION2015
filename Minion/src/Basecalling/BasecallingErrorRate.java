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
 * @functionailty For a certain base the transformation rate can becalled here
 * @Input: settingFilename
 * @Output: getters for the value, base and row
 * default values of the lambda_phage are in the default.setting
 *To generate the values for each position each letter gets a window. For example if a A switches to a G the value wirtten down is 1, but the prob is 0.1(->1-0.9).
 */
public class BasecallingErrorRate {


	private static double[][] transProbMatrix = new double[4][4];
	private static char[] base = {'A','T','G','C'};
	private static double insertionProb = 0;
	private static double insertionExtProb = 0;
	private static double deletionProb = 0;
	private static double deletionExtProb = 0;
	
	public BasecallingErrorRate(String settingFilePath) throws Exception{
		generate(settingFilePath);
	}
	
	/**
	 * reads in the setting file and loads the probabilities
	 * @param settingFilePath
	 * @throws Exception
	 */
	private static void generate(String settingFilePath) throws Exception{
		char cacheChar = ' ';
		String Value;
		BufferedReader Input = new BufferedReader(new FileReader(settingFilePath));
			Input.readLine();
			for(int i=0;i<4;i++){
				cacheChar=' ';
				while(cacheChar!='#')
					cacheChar=(char) Input.read();
				for(int j=0;j<4;j++){
					cacheChar=' ';
					Value = "";
					while(cacheChar!='#'){
						cacheChar=(char) Input.read();
						if(cacheChar!='#')
							Value = Value+cacheChar;
					}
					
						transProbMatrix[i][j] = Double.parseDouble(Value);
				}
			}
			cacheChar= ' ';
			while(cacheChar!='#')
				cacheChar=(char) Input.read();
			Value = Input.readLine();
			insertionProb = Double.parseDouble(Value);
			cacheChar= ' ';
			while(cacheChar!='#')
				cacheChar=(char) Input.read();
			Value = Input.readLine();
			insertionExtProb = Double.parseDouble(Value);
			cacheChar= ' ';
			while(cacheChar!='#')
				cacheChar=(char) Input.read();
			Value = Input.readLine();
			deletionProb = Double.parseDouble(Value);
			cacheChar= ' ';
			while(cacheChar!='#')
				cacheChar=(char) Input.read();
			Value = Input.readLine();
			deletionExtProb = Double.parseDouble(Value);
			Input.close();
	}
	
	/**
	 * make the variables worse to simulate the aging of pores
	 */
	public static void age(int secondsPerTick){
		double[][] cache = new double[4][4];
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				cache[i][j]=transProbMatrix[i][j];
		for(int i=3;i>0;i--)
			for(int j=1;j<4;j++)
				cache[j][i]=cache[j][i]-cache[j-1][i];
		double changeValue = Math.pow(Math.log(259200)-4.414,0.1);
		changeValue=Math.pow(changeValue, secondsPerTick);
		insertionProb=insertionProb*(2-changeValue);
		deletionProb=deletionProb*(2-changeValue);
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(i==j)
					cache[i][j]=changeValue*cache[i][j];
				else
					cache[i][j]=(2-changeValue)*cache[i][j];
			}
		}
		for(int i=0;i<4;i++)
			for(int j=1;j<4;j++)
				cache[i][j]=cache[i][j]+cache[i][j-1];
		transProbMatrix=cache;
	}
	
	

	public static double getValue(int i, int j){
		return transProbMatrix[i][j];
	}
	
	public static String getBase(int i){
		return Character.toString(base[i]);
	}
	
	public static double getInsertionProb(){
		return insertionProb;
	}
	
	public static double getInsertionExtProb(){
		return insertionExtProb;
	}
	
	public static double getDeletionProb(){
		return deletionProb;
	}
	
	public static double getDeletionExtProb(){
		return deletionExtProb;
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
/*test works
	public static void main(String args[]){
		BasecallingErrorRate err;
		try {
			err = new BasecallingErrorRate(2,"/Users/kevinlindner/Documents/null.setting");
		} catch (Exception e) {
		}
		for(int i = 0; i < 4;i++){
			for(int j = 0; j < 4;j++){
				System.out.print(BasecallingErrorRate.getValue(i,j) + " ");
		}
		System.out.println();
		}
		System.out.println(BasecallingErrorRate.getInsertionProb());
		System.out.println(BasecallingErrorRate.getInsertionExtProb());
		System.out.println(BasecallingErrorRate.getDeletionProb());
		System.out.println(BasecallingErrorRate.getDeletionExtProb());
		
		
//		System.out.println(err.getBase(2)); //T
		
	//	System.out.println(err.getRow('T')); //2
	}*/
	
	
	
}
