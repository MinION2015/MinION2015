package Basecalling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * 
 * @author kevinlindner
 * @Functionality the function creates a new settings file with a given blast file 
 * to see how the basecalling configuration works look at the notes below
 * @Input as inout u need the sourcepath of the blast file, the name of the created setting file and the dimension of the blast file
 * @Ouput the function itself doesnt give a value back, but generates a .setting file
 */
public class createSetting {
	
	
	static double percentageMatrix[][] = new double[4][4];
	static double insertionProb;
	static double insertionExtProb;
	static double deletionProb;
	static double deletionExtProb;
	
	public static void createSettingFile(String sourcepath, String outputPath, int dimension) throws IOException{
		calculate(sourcepath);
		print(outputPath,dimension);
	}
	
	/**
	 * reads the blast file and calculates the prob.
	 * @param sourcepath blast file
	 * @throws IOException
	 */
	private static void calculate(String sourcepath) throws IOException{

		@SuppressWarnings("resource")
		BufferedReader Input = new BufferedReader(new FileReader(sourcepath));
		String cacheString = " ";
		int endInt = 0;
		int end=0;
		String query= " ";
		String sbjct= " ";
		//A T G C -
		double[][] matrix = new double[4][4];
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				matrix[i][j]=0;
		
		int countA = 0;
		int countT = 0;
		int countG = 0;
		int countC = 0;
		int countAll = 0;
		int countInsertion = 0;
		int countInsertionExt = 0;
		int countDeletion = 0;
		int countDeletionExt = 0;

		do{
			while(!(cacheString.contains("Query") && !(cacheString.contains("Query=")))){
				Input.mark(1000);
				cacheString = Input.readLine();
			}
			Input.reset();
			do {
			query = Input.readLine();
			if(!query.contains("Query"))
				break;
			Input.readLine();
			sbjct = Input.readLine();
			Input.readLine();

			for(int i=1;i<sbjct.length()-17;i++){
				if(query.charAt(i)=='A'&&sbjct.charAt(i)=='A'){
					matrix[0][0]++;
				countA++;}
				if(query.charAt(i)=='A'&&sbjct.charAt(i)=='T'){
					matrix[1][0]++;
				countT++;}
				if(query.charAt(i)=='A'&&sbjct.charAt(i)=='G'){
					matrix[2][0]++;
				countG++;}
				if(query.charAt(i)=='A'&&sbjct.charAt(i)=='C'){
					matrix[3][0]++;
				countC++;}
				if(query.charAt(i)=='T'&&sbjct.charAt(i)=='A'){
					matrix[0][1]++;
				countA++;}
				if(query.charAt(i)=='T'&&sbjct.charAt(i)=='T'){
					matrix[1][1]++;
				countT++;}
				if(query.charAt(i)=='T'&&sbjct.charAt(i)=='G'){
					matrix[2][1]++;
				countG++;}
				if(query.charAt(i)=='T'&&sbjct.charAt(i)=='C'){
					matrix[3][1]++;
				countC++;}
				if(query.charAt(i)=='G'&&sbjct.charAt(i)=='A'){
					matrix[0][2]++;
				countA++;}
				if(query.charAt(i)=='G'&&sbjct.charAt(i)=='T'){
					matrix[1][2]++;
				countT++;}
				if(query.charAt(i)=='G'&&sbjct.charAt(i)=='G'){
					matrix[2][2]++;
				countG++;}
				if(query.charAt(i)=='G'&&sbjct.charAt(i)=='C'){
					matrix[3][2]++;
				countC++;}
				if(query.charAt(i)=='C'&&sbjct.charAt(i)=='A'){
					matrix[0][3]++;
				countA++;}
				if(query.charAt(i)=='C'&&sbjct.charAt(i)=='T'){
					matrix[1][3]++;
				countT++;}
				if(query.charAt(i)=='C'&&sbjct.charAt(i)=='G'){
					matrix[2][3]++;
				countG++;}
				if(query.charAt(i)=='C'&&sbjct.charAt(i)=='C'){
					matrix[3][3]++;
				countC++;}
				if(sbjct.charAt(i)=='-' && sbjct.charAt(i-1)=='-')
					countInsertionExt++;
				else if(sbjct.charAt(i)=='-')
					countInsertion++;
				if(query.charAt(i)=='-' && query.charAt(i-1)=='-')
					countDeletionExt++;
				else if(query.charAt(i)=='-')
					countDeletion++;
				}	
			} while (query.contains("Query"));
			
			Input.mark(50000);
			for(int i=0;i<5000;i++){
			endInt = Input.read();
			}
			Input.reset();	
		}while(endInt != -1);
		
		countAll = countA+countT+countC+countG+countDeletion+countInsertion;
		
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				switch(i){
				case 0:
					matrix[i][j]=matrix[i][j]/countA;
					break;
				case 1:
					matrix[i][j]=matrix[i][j]/countT;
					break;
				case 2:
					matrix[i][j]=matrix[i][j]/countG;
					break;
				case 3:
					matrix[i][j]=matrix[i][j]/countC;
					break;
				}
			}
		}
		double cache2=0;
		double cache3=0;
		insertionProb=countInsertion/(double)countAll;
		cache2=(countInsertionExt/(double)countInsertion);
		cache3=(countInsertionExt/(double)countInsertion)+1;
		insertionExtProb=cache2/(double)cache3;
		deletionProb=countDeletion/(double)countAll;
		cache2=(countDeletionExt/(double)countDeletion);
		cache3=(countDeletionExt/(double)countDeletion)+1;
		deletionExtProb=cache2/(double)cache3;
		percentageMatrix=matrix;
	}
	
	/**
	 * writes the variables in a setting file
	 * @param outputPath
	 * @param dimension
	 * @throws IOException
	 */
	private static void print(String outputPath, int dimension) throws IOException{
		BufferedWriter Output = new BufferedWriter(new FileWriter(outputPath));
		switch(dimension){
		case 1:
			Output.write("1D");
			break;
		case 2:
			Output.write("2D");
			break;
		}
		for(int i=0;i<4;i++){
			switch(i){
			case 0:
				Output.newLine();
				Output.write("A#");
				break;
			case 1:
				Output.newLine();
				Output.write("T#");
				break;
			case 2:
				Output.newLine();
				Output.write("G#");
				break;
			case 3:
				Output.newLine();
				Output.write("C#");
				break;
			}
			
			Output.write(Double.toString(percentageMatrix[i][0])+"#");
			Output.write(Double.toString(percentageMatrix[i][0]+percentageMatrix[i][1])+"#");
			Output.write(Double.toString(percentageMatrix[i][0]+percentageMatrix[i][1]+percentageMatrix[i][2])+"#");
			Output.write(Double.toString(percentageMatrix[i][0]+percentageMatrix[i][1]+percentageMatrix[i][2]+percentageMatrix[i][3])+"#");
	
		}
		Output.newLine();
		Output.write("IN#"+insertionProb);
		Output.newLine();
		Output.write("IN_ext#"+insertionExtProb);
		Output.newLine();
		Output.write("OUT#"+deletionProb);
		Output.newLine();
		Output.write("OUT_ext#"+deletionExtProb);
		Output.close();
	}
	/*
public static void main(String args[]){
	try {
		createSetting test = new createSetting("/Users/kevinlindner/Documents/workspace/lambda_analyse/run08_4_-5_5_5.txt","/Users/kevinlindner/git/MinION2015/Minion/setting files/default.setting",1);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}*/
	
	
	/* CONFIGURATION OF BASECALLING SETTING FILES
	 * 1D or 2D
	 * A#0.2 for A to A#% for A to T#% for A to G#% for A to C
	 * T#% for T to A#% for T to T#% for T to G#% for T to C
	 * G#% for G to A#% for G to T#% for G to G#% for G to C
	 * C#% for C to A#% for C to T#% for C to G#% for C to C
	 * IN#% for an - Insertion
	 * IN_ext#%
	 * OUT#% to delete a Base
	 * OUT_ext#%
	 */
	
	
	
	
	
	
}
