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
 * @Input as inout u need the sourcepath of the blast file, the name of the created setting file and the dimension of the blast file
 * @Ouput the function itself doesnt give a value back, but generates a .setting file
 */
public class createSetting {
	
	
	double percentageMatrix[][] = new double[5][5];
	
	public createSetting(String sourcepath, String outputName, int dimension) throws IOException{
		this.percentageMatrix=calculate(sourcepath);
		print(percentageMatrix,outputName,dimension);
	}
	
	private double[][] calculate(String sourcepath) throws IOException{

		BufferedReader Input = new BufferedReader(new FileReader(sourcepath));
		
		char cacheChar;
		int end=0;
		String query= "";
		String sbjct= "";
		//A T G C -
		double[][] matrix = new double[5][5];
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++)
				matrix[i][j]=0;
		
		int countA = 0;
		int countT = 0;
		int countG = 0;
		int countC = 0;
		int countminus = 0;

		while(true){
			while(true){
			Input.readLine();
			Input.mark(1000);
			cacheChar=(char) Input.read();
			Input.reset();
			if(cacheChar=='Q')
				break;
			}
			while(true){
			query = Input.readLine();
			Input.readLine();
			sbjct = Input.readLine();
			Input.readLine();

			for(int i=0;i<sbjct.length()-4;i++){
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
				if(query.charAt(i)=='-'&&sbjct.charAt(i)=='A'){
					matrix[0][4]++;
				countA++;}
				if(query.charAt(i)=='-'&&sbjct.charAt(i)=='T'){
					matrix[1][4]++;
				countT++;}
				if(query.charAt(i)=='-'&&sbjct.charAt(i)=='G'){
					matrix[2][4]++;
				countG++;}
				if(query.charAt(i)=='-'&&sbjct.charAt(i)=='C'){
					matrix[3][4]++;
				countC++;}
				if(query.charAt(i)=='A'&&sbjct.charAt(i)=='-'){
					matrix[4][0]++;
				countminus++;}
				if(query.charAt(i)=='T'&&sbjct.charAt(i)=='-'){
					matrix[4][1]++;
				countminus++;}
				if(query.charAt(i)=='G'&&sbjct.charAt(i)=='-'){
					matrix[4][2]++;
				countminus++;}
				if(query.charAt(i)=='C'&&sbjct.charAt(i)=='-'){
					matrix[4][3]++;
				countminus++;}
			}	
			Input.mark(1000);
			cacheChar=(char) Input.read();
			Input.reset();
			if(cacheChar!='Q')
				break;
			}
			
			Input.mark(100000);
			for(int i=0;i<100;i++){
			Input.readLine();
			}
			end= Input.read();
			Input.reset();
			if(end==-1)
				break;		
		}
		
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
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
				case 4:
					matrix[i][j]=matrix[i][j]/countminus;
					break;
				}
			}
		}
		return matrix;
	}
	
	
	private void print(double matrix[][], String outputName, int dimension) throws IOException{
		
		BufferedWriter Output = new BufferedWriter(new FileWriter("src/settingFiles/"+outputName+".setting"));
		Output.write("1D");
		if(dimension==1){
			for(int i=0;i<4;i++){
				switch(i){
				case 0:
					Output.newLine();
					Output.write("A");
					break;
				case 1:
					Output.newLine();
					Output.write("T");
					break;
				case 2:
					Output.newLine();
					Output.write("G");
					break;
				case 3:
					Output.newLine();
					Output.write("C");
					break;
				}
				for(int j=0;j<5;j++){
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
				}
			}	
			for(int i=0;i<25;i++){
				Output.newLine();
				Output.write("0");
			}
		}else if(dimension==2){
			Output.write("0");
			for(int i=0;i<24;i++){
				Output.newLine();
				Output.write("0");
			}
			for(int i=0;i<5;i++){
				switch(i){
				case 0:
					Output.newLine();
					Output.write("A");
					break;
				case 1:
					Output.newLine();
					Output.write("T");
					break;
				case 2:
					Output.newLine();
					Output.write("G");
					break;
				case 3:
					Output.newLine();
					Output.write("C");
					break;
				}
				for(int j=0;j<5;j++){
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
				}
			}	
		}else{
			for(int k=0;k<2;k++){
				switch(k){
				case 0:
					Output.write("1D");
					break;
				case 1:
					Output.write("2D");
					break;
				}
			for(int i=0;i<5;i++){
				switch(i){
				case 0:
					Output.newLine();
					Output.write("A");
					break;
				case 1:
					Output.newLine();
					Output.write("T");
					break;
				case 2:
					Output.newLine();
					Output.write("G");
					break;
				case 3:
					Output.newLine();
					Output.write("C");
					break;
				}
				for(int j=0;j<5;j++){
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
					Output.newLine();
					Output.write(Double.toString(matrix[i][j]));
				}
			}	
			}
			
		}
		Output.close();
			
	}
	/* works
	public static void main(String args[]) throws IOException{

	createSetting create = new createSetting("run09_4_-5_5_5.txt","settingname",1);
	}
	*/
	
}
