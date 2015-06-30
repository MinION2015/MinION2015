package LengthDistribution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Daniel Dehncke
 * saves the Sequenz Lengths from the analyzed Data and their possibilities.
 */
public class LengthRate {

	
	private static double[][] possibilitiesLength;
	
/*
 * could add come options here, like default window sizes that a dividers of the Array which 
 * contains the Lengths
 */
	
	public LengthRate(int window) throws IOException{
		
		possibilitiesLength = 	GetDefaultLengths(window);

	}
	
	public LengthRate(String filename, int window) throws IOException{
		possibilitiesLength = 	GetSelectedLengths(filename, window);
	}
	
	
	
	public double getLength(int index){
		return possibilitiesLength[1][index];
	}
	
	public  static double getProb(int index){
		return possibilitiesLength[0][index];
	}
	
	/**
	 * 
	 * @param filename		user selected Filename
	 * @param window		selected window
	 * @return	double[][] Array with the computed possibilities
	 * gets called by the constructor which gets a filename from the user. returns a double[][] Array with the possibilities. works through the Lines and 
	 * adds a Length to the LengthsArraytemp everytime it reaches a Sequence identifier. After this it uses the generatePossibilities to check get an double[][]
	 * Array and return it to the constructor
	 * @throws IOException
	 */
	public static double[][] GetSelectedLengths(String filename,int window) throws IOException {
		// TODO Auto-generated method stub
		int approximationlongestSequence = 50000;		//just the longest Sequence i found, dont know if it is enough
		int sumOfSequences = 0;
		
		int t = 0;
		
		int[] LengthsArraytemp = new int[approximationlongestSequence];
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String currLine = "";
		
		boolean firstLine = true;
		String fastA = "";
		while((currLine = br.readLine()) != null)
		{
			
			
		String tmp = currLine.substring(0, 1);

		
		if(tmp.equals(";"))									//skips comments
		{
			
			continue;
		}
		
		if(tmp.equals(">"))									//skips the line if it starts with an ">" and adds the length to LengthsArraytemp
		{
			if(!firstLine)									//skips the firstLine
			{
				LengthsArraytemp[fastA.length()]++;	
				sumOfSequences++;
				fastA = "";
			}
			firstLine = false;
			continue;
		}
			
		
		for (int j = 1; j < currLine.length()-1;j++)
		{
			tmp = currLine.substring(j-1, j);

			fastA = fastA + tmp;
		}
		
		}
		LengthsArraytemp[fastA.length()]++;					//adds the last FastA Length
		sumOfSequences++;
		
		//window is the Size we look at
		br.close();
		return generatePossibilities(LengthsArraytemp, window, sumOfSequences);

	}
	
	
	
	
	/**
	 * @author Daniel Dehncke
	 * @param window
	 * @return	double Array with the possibilities
	 * @throws IOException
	 * this is the Default Lengths generator. Takes Lengths from LengthList.txt
	 */
	public static double[][] GetDefaultLengths(int window) throws IOException {
		// TODO Auto-generated method stub
		int approximationlongestSequence = 49196;
		
		String filename = "LengthList.txt";
		int sumOfSequences = 0;
		int[] savenumberofLengths = new int[5364];
		
		int[] saveLengths = new int[5364];
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String currLine = "";
		
		int currentArrayindex = 0;
		int currentArrayindex2 = 0;
		
		boolean writeinnumberOfLengths = false;
		while((currLine = br.readLine()) != null)
		{
		String Zahl = "";
		
		//works through the .txt file. when the first { is detected the Array savenumberofLengths is chosen. When the second { is found 
		//this means we are getting the Lengths out of the .txt file and we save them into Array saveLengths. After this we merge 
		//Both Arrays together to a new Array which contains at the Length of the Sequences as index and their quantity
		
		for (int j = 1; j < currLine.length();j++)
		{	
			String tmp = currLine.substring(j-1, j);
			
			if(tmp.equals("{"))									//array of Number of Lengths is detected
			{
				writeinnumberOfLengths = !writeinnumberOfLengths;
				continue;
				
			}
			if(tmp.equals("}"))
			{
				continue;
			}
			
			if(writeinnumberOfLengths)							//if true we write into the array which saves the Lengths
			{													//gets the first array from .txt file
				
			if(tmp.equals(",") && !Zahl.equals(""))
			{				
				int tempZahl = Integer.parseInt(Zahl.replaceAll(",",""));
				savenumberofLengths[currentArrayindex] = tempZahl;
				sumOfSequences += tempZahl;
				Zahl = "";
				currentArrayindex++;
				
				
			}
			else{
				Zahl = Zahl + tmp;
			}
			
			}else
			{													//if false we save the number of Lengths
				
				if(tmp.equals(",") && !Zahl.equals(""))
				{				
					saveLengths[currentArrayindex2] = Integer.parseInt(Zahl.replaceAll(",",""));
//					System.out.println(currentArrayindex+" "+Integer.parseInt(Zahl.replaceAll(",","")));
					currentArrayindex2++;
					Zahl = "";


				}
				else{
					Zahl = Zahl + tmp;
				}
				
			}
			
			
			
			
			
			

		}
		}
//		System.out.println(sumOfSequences);
		
		
		int[] mergedArray = new int[approximationlongestSequence];
		//Merge the two Arrays
		for(int i = 0; i < savenumberofLengths.length;i++)
		{
//			System.out.println(saveLengths[i] +" "+ savenumberofLengths[i]);
			mergedArray[saveLengths[i]] = savenumberofLengths[i];
			
		}

		//window is the Size we look at
		br.close();
		double[][] returnpossibilities = generatePossibilities(mergedArray, window, sumOfSequences);
		return returnpossibilities;
		
	}

	
	
	/**
	 * @author Daniel Dehncke
	 * @input int[] ArrayLengths,int Window, int sumOfSequences
	 * @output double[][] Array with the resulting possibilities
	 * Generates a double Array with the given Lengths and the chosen window Size. When window isn't a divider of 
	 * the Length of the Array some Sequences can get lost.
	 * 
	 */
	private static double[][] generatePossibilities(int[] Lengths,int window,int sumOfSequences)
	{
		
		int[] SolutionArray = new int[Lengths.length/window];
				
		for(int h = 0; h < Lengths.length;h++)
		{	
			if(Lengths[h] != 0)
			{
				
			int i = 0;
			
			while(h > ((i+1) * window) && (i < SolutionArray.length-1))				//runs until the specified window is found or the solutionArray ends
			{																		//when windw isn´t a divider of approximationlongestSequence some Sequences maybe erased
				i++;
			}
			
			SolutionArray[i]+= Lengths[h];
			
			}
			
		}
		
		double[][] possibilities = new double[2][SolutionArray.length];
		double temp = 0;
		for(int i = 0; i < SolutionArray.length;i++)			//different lengths?
		{
			
			temp += (double)SolutionArray[i]/(double)sumOfSequences;
			possibilities[0][i] = temp;

			
			possibilities[1][i] = (i+1)*window;
			
		}
		
		

		//here´s the problem. possibilities[][] contains data but after returning it to the constructor the data is gone.
		return possibilities;
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
