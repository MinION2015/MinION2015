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

	
	private double[][] possibilitiesLenght;
	
/*
 * could add come options here, like default window sizes that a dividers of the Array which 
 * contains the Lengths
 */
	public LengthRate(int window) throws IOException{
		this.possibilitiesLenght = 	GetDefaultLengths(window);
	}
	
	public LengthRate(String filename, int window) throws IOException{
		this.possibilitiesLenght = 	GetSelectedLengths(filename, window);
	}
	
	
	
	public double getLength(int index){
		return possibilitiesLenght[1][index];
	}
	
	public double getProb(int index){
		return possibilitiesLenght[0][index];
	}
	
	/**
	 * 
	 * @param filename		user selected Filename
	 * @param window		selected window
	 * @return	double[][] Array with the computed possibilities
	 * gets called by the constructor which gets a filename from the user. returns a double[][] Array with the possibilities
	 * @throws IOException
	 */
	public double[][] GetSelectedLengths(String filename,int window) throws IOException {
		// TODO Auto-generated method stub
		int approximationlongestSequence = 50000;		//just the longest Sequence i found, dont know if it is enough
		int sumOfSequences = 0;
		
		
		
		int[] LengthsArraytemp = new int[approximationlongestSequence];
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String currLine = "";
		
		boolean firstLine = true;

		while((currLine = br.readLine()) != null)
		{
		String fastA = "";
		String tmp = currLine.substring(0, 1);
		
		if(tmp.equals(">"))									//skips the line if it starts with an ">" and adds the length to LengthsArraytemp
		{
			if(!firstLine)									//skips the firstLine
			{
				LengthsArraytemp[fastA.length()]++;	
				sumOfSequences++;
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
	public double[][] GetDefaultLengths(int window) throws IOException {
		// TODO Auto-generated method stub
		int approximationlongestSequence = 49196;
		String filename = "LengthList.txt";
		int sumOfSequences = 0;
		
		int[] LengthsArraytemp = new int[approximationlongestSequence];
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String currLine = "";

		while((currLine = br.readLine()) != null)
		{
		String Zahl = "";
		for (int j = 1; j < currLine.length()-1;j++)
		{
			String tmp = currLine.substring(j-1, j);
			
			if(tmp.equals(" ") && !Zahl.equals(""))
			{				
				LengthsArraytemp[Integer.parseInt(Zahl.replaceAll(" ",""))]++;
				Zahl = "";
				sumOfSequences++;
			}
			else{
				Zahl = Zahl + tmp;
			}
		}
		}
		//window is the Size we look at
		br.close();
		return generatePossibilities(LengthsArraytemp, window, sumOfSequences);

	}
	/**
	 * @author Daniel Dehncke
	 * @input int[] ArrayLengths,int Window, int sumOfSequences
	 * @output double[][] Array with the resulting possibilities
	 * Generates a double Array with the given Lengths and the chosen window Size. When window isn't a divider of 
	 * the Length of the Array some Sequences can get lost.
	 * 
	 */
	private double[][] generatePossibilities(int[] Lengths,int window,int sumOfSequences)
	{
		int[] SolutionArray = new int[Lengths.length/window];
		
		for(int h = 0; h < Lengths.length;h++)
		{	
			if(Lengths[h] != 0)
			{
				
			int i = 0;
			
			while(h > ((i+1) * window) && (i < SolutionArray.length-1))		//runs until the specified window is found or the solutionArray ends
			{																//when window isn't a divider of approximationlongestSequence some Sequences maybe erased
				i++;
			}
			
			SolutionArray[i]+= Lengths[h];
			
			}
			
		}
		double[][] possibilities = new double[2][SolutionArray.length];
		double temp = 0;
		for(int i = 0; i < SolutionArray.length;i++)
		{
			temp += (double)SolutionArray[i]/(double)sumOfSequences;
			possibilities[0][i] = temp;
			possibilities[1][i] = (i+1)*window;
		}
		
		
		
		
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
