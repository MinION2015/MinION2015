package LengthDistribution;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	
	
	public static double getLength(int index){
		//System.out.println("getLEngth: "+possibilitiesLength[1][0]);
		return possibilitiesLength[1][index];
	}
	
	public  static double getProb(int index){
		//System.out.println("possLenght: "+possibilitiesLength[0][index]);
		return possibilitiesLength[0][index];
	}
	
	/**
	 * @author Dnaiel Dehncke
	 * @functionality Gets a file.fasta and records the containing Lengths in the Array LengthsArraytemp. Gives the data to the print method which print it into filename.distribution
	 * @param filename	.fasta file which contains Lengths to be analysed
	 * @param outputName	Name of the file in which the Lengthdistribution is stored
	 * @throws IOException
	 */
	public static void saveSelectedLengths(String filename, String outputName) throws IOException
	{

		
		int approximationlongestSequence = 50000;		//just the longest Sequence i found, dont know if it is enough
		

		
		int[] LengthsArraytemp = new int[approximationlongestSequence];
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String currLine = "";
		
		boolean firstLine = true;
		String fastA = "";
		while((currLine = br.readLine()) != null)
		{
		if(currLine.isEmpty())
		{
			continue;
		}
			
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
		
		//window is the Size we look at
		br.close();
		
		
		
		print(LengthsArraytemp, outputName);	
	}
	/**
	 * @author Daniel Dehncke 
	 * @functionality Prints 	LengthsArray to the outputName.distribution
	 * @param LengthsArray	 contains the stored Lengths
	 * @param outputName	name of the output file
	 * @throws IOException
	 */
	public static void print(int[] LengthsArray, String outputName) throws IOException
	{
		BufferedWriter Output = new BufferedWriter(new FileWriter("src/lengthDistribution/"+outputName+".distribution"));
		List<Integer> saveLengths = new ArrayList<Integer>();
		Output.write("{");
		for(int i = 0; i < LengthsArray.length; i++)
		{
			if(LengthsArray[i] != 0)
			{
				Output.write(LengthsArray[i]+",");
				saveLengths.add(i);			//use list to store the lengths. 
			}
			
		}
		
		Output.write("}");
		
		Output.newLine();
		Iterator it = saveLengths.iterator();					//create Iterator to run over the the List

		Output.write("{");
		while(it.hasNext())
		{
			Object o = it.next();
			Output.write((Integer) o + ",");
		}
		
		Output.write("}");
		Output.newLine();
		Output.close();
		
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
		
		
		int[] LengthsArraytemp = new int[approximationlongestSequence];
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String currLine = "";
		
		boolean firstLine = true;
		String fastA = "";
		while((currLine = br.readLine()) != null)
		{
		if(currLine.isEmpty())
		{
			continue;
		}
			
			
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
		int[] savenumberofLengths = new int[5368];
		
		int[] saveLengths = new int[5368];
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
 * @throws IOException 
 */
//	public static void main(String args[]) throws IOException{
//		saveSelectedLengths("example4.txt", "neu");
//	}
//	public static void main(String[] args){
////		int[] b = {1,1,1,1,1,1,2,1,4,4,1,2,4,1,1,4,4,2,2,3,4,2,3,2,4,3,2,4,1,1,6,2,1,2,5,3,2,3,4,2,1,4,3,6,2,2,2,4,3,2,3,3,3,2,2,1,1,4,2,3,1,5,3,6,7,1,4,2,4,2,3,1,3,3,2,2,1,5,3,3,5,9,4,2,4,5,2,2,7,4,3,7,2,1,4,7,1,5,3,7,5,3,2,6,3,2,6,1,3,3,4,11,4,1,8,5,4,6,6,11,4,4,4,8,4,6,7,4,9,3,9,7,7,3,5,8,7,9,12,5,13,3,4,6,5,6,4,4,6,5,4,10,7,5,3,3,4,3,6,8,4,6,1,4,4,5,3,1,6,2,6,4,5,3,3,1,2,4,2,5,4,2,4,4,2,1,3,6,3,1,3,1,6,7,4,3,3,3,4,1,5,3,3,3,4,1,2,3,1,3,2,3,3,4,1,2,3,1,2,2,3,1,1,3,2,4,2,3,5,1,2,3,2,3,3,3,3,2,2,1,2,3,2,2,3,2,2,6,2,3,2,4,1,3,2,5,3,4,3,1,1,1,3,1,2,1,1,2,4,1,1,2,2,2,2,1,1,5,1,3,3,4,2,3,2,1,2,2,1,2,1,2,1,3,1,3,5,1,1,3,2,2,1,3,3,1,3,1,2,4,3,3,2,1,5,2,1,1,1,4,1,2,1,2,1,2,1,1,1,1,2,2,1,2,3,2,3,1,2,1,4,1,2,1,1,1,3,3,3,3,1,2,1,1,2,2,1,2,3,4,1,2,1,2,1,1,1,1,1,3,4,1,2,2,1,2,1,2,2,1,1,1,3,1,1,1,3,1,2,3,4,1,1,2,2,3,1,1,1,1,1,2,1,2,2,1,2,1,2,2,1,4,1,1,4,4,1,2,3,3,1,1,3,1,1,1,2,3,1,2,1,2,1,1,1,2,2,2,4,1,2,3,3,2,1,2,2,1,1,2,2,1,1,2,1,1,1,1,1,1,1,1,2,4,1,2,2,1,2,1,1,1,2,2,1,3,2,2,1,3,1,2,1,2,1,2,2,1,1,1,1,2,1,4,1,4,2,1,2,2,1,1,2,1,1,2,2,1,2,2,1,3,1,1,2,3,4,1,2,1,1,1,2,1,1,1,1,2,2,1,1,1,1,2,1,1,5,1,1,2,1,2,2,1,1,1,1,2,1,2,1,2,1,1,1,1,2,1,1,1,1,1,1,3,1,2,2,1,1,2,1,1,2,1,1,1,1,1,1,1,3,1,1,1,1,2,1,2,1,1,1,2,2,1,2,2,2,1,3,3,3,2,1,2,1,3,2,1,2,4,2,2,1,2,1,1,1,1,1,2,1,1,1,2,2,2,1,4,2,1,1,3,1,1,3,2,2,3,1,2,1,1,3,1,2,1,2,2,2,1,1,1,1,1,4,1,3,2,1,1,1,1,1,3,4,2,1,2,1,2,1,2,3,1,1,3,1,1,2,4,1,2,1,1,1,1,2,3,2,1,2,1,2,1,1,1,2,1,1,1,1,1,1,1,1,2,2,2,1,1,1,2,3,1,2,2,1,1,3,1,1,2,1,1,1,1,1,1,2,1,1,4,1,2,2,1,3,1,1,1,2,1,2,1,2,1,1,1,1,1,2,2,1,1,1,2,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,2,1,2,1,2,2,1,1,1,2,1,1,1,2,1,1,2,1,3,1,3,1,1,1,1,1,1,1,1,1,1,2,1,3,1,1,1,1,1,2,1,2,2,1,1,1,1,1,1,1,1,3,1,1,1,1,1,3,1,1,1,1,1,2,2,1,1,1,2,1,1,2,1,1,2,1,1,1,2,2,1,1,2,1,1,2,1,1,1,1,2,2,1,1,1,2,1,1,1,1,1,3,1,1,1,1,2,2,1,3,3,1,2,1,1,1,2,2,1,1,2,1,2,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,2,1,1,1,1,2,1,2,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,2,1,2,1,1,1,1,1,2,1,3,2,1,1,4,1,1,1,1,1,1,1,1,2,2,2,1,2,1,2,2,1,1,2,1,1,1,1,1,1,1,2,1,1,2,1,1,1,2,1,1,1,1,3,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,2,1,2,2,1,2,1,3,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,1,2,1,1,2,1,1,1,1,1,3,1,1,2,2,2,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,3,1,1,1,1,1,1,2,2,2,1,1,2,1,1,1,3,1,1,1,1,1,2,1,1,2,2,2,2,3,2,1,1,1,1,1,1,1,1,1,1,2,1,2,2,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,2,1,1,1,3,1,1,1,1,1,1,1,1,1,2,2,1,1,2,1,1,1,1,2,1,1,1,2,1,3,1,1,1,3,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,1,2,1,1,1,2,1,1,3,2,1,2,1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,1,1,3,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,2,2,1,1,2,1,1,1,2,3,1,1,1,2,1,2,2,1,1,2,1,1,1,1,1,1,2,2,2,4,1,2,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,2,1,4,1,2,1,1,1,2,1,2,2,1,1,1,1,1,1,1,1,2,2,1,2,2,2,1,1,2,1,1,1,1,1,1,1,1,3,2,2,1,2,1,1,3,1,2,2,1,1,1,1,3,1,1,1,3,1,2,3,1,2,2,1,1,2,2,2,2,1,2,2,2,1,1,2,2,1,1,1,1,1,1,1,2,2,1,1,2,2,2,2,1,1,2,2,1,2,1,1,1,1,2,1,1,1,5,2,1,1,2,1,2,3,3,1,1,1,1,2,4,2,1,1,1,1,1,2,2,1,4,2,3,1,1,1,1,1,2,1,3,1,2,1,1,2,2,4,4,2,1,1,1,2,2,1,3,1,2,1,2,2,1,1,2,1,1,1,2,2,2,3,1,3,2,1,1,2,2,1,2,2,3,2,1,1,2,2,2,2,2,3,4,2,1,1,1,1,1,1,1,4,1,1,2,4,3,1,2,3,3,1,1,1,1,4,2,3,3,2,2,3,1,5,1,2,5,2,1,1,2,1,3,2,2,1,1,2,2,1,2,2,3,3,1,1,3,4,1,1,1,1,3,2,2,1,1,1,1,1,3,1,1,1,3,2,3,1,2,3,1,1,3,1,1,2,2,1,2,2,2,1,1,3,1,1,2,2,3,3,3,1,1,1,1,1,1,2,1,1,1,1,2,1,3,2,1,1,1,2,1,1,1,1,2,1,2,1,4,1,1,1,2,1,2,2,1,2,2,3,2,1,2,1,1,3,2,2,1,1,2,1,5,1,1,3,3,1,3,2,4,2,1,2,2,3,4,1,1,5,2,2,1,6,2,1,1,4,1,2,1,2,2,2,1,1,4,4,1,2,1,1,3,1,2,1,2,2,1,1,1,1,2,3,4,1,1,1,2,1,1,2,1,1,2,1,1,2,3,1,2,1,2,1,1,1,1,1,2,2,3,2,1,1,2,2,2,1,1,4,1,1,1,1,1,1,2,1,1,2,1,1,1,1,3,2,1,1,1,1,3,1,4,1,1,1,1,1,2,1,1,1,1,3,2,1,1,1,2,2,1,2,1,1,4,1,2,2,1,1,1,1,3,2,2,1,1,2,2,3,3,1,1,3,3,1,1,1,1,3,1,2,4,1,1,1,1,2,1,2,1,1,1,1,2,1,1,1,1,2,1,1,1,3,2,1,3,2,1,1,1,1,1,1,2,1,1,2,2,1,1,1,1,1,3,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,2,2,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,2,1,1,2,2,3,1,1,1,1,1,2,1,2,2,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,2,1,2,1,1,1,1,1,1,1,1,1,3,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,2,2,1,1,2,1,2,1,1,2,1,2,1,1,1,1,2,1,1,1,1,1,1,1,1,2,1,2,1,2,1,1,1,1,2,1,1,2,2,3,3,1,1,1,1,1,1,1,1,1,3,1,2,1,1,1,1,1,1,1,1,1,1,2,1,2,1,2,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,1,1,1,1,1,1,2,1,1,4,1,1,1,2,1,1,1,1,2,1,1,1,2,1,1,1,2,1,1,1,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,2,2,1,2,1,1,2,1,1,1,1,2,1,1,2,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,2,1,4,1,1,1,1,2,1,1,2,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,1,2,1,1,2,1,1,2,1,1,2,1,1,3,1,2,1,2,1,1,2,1,2,1,1,1,2,1,1,1,1,2,1,1,2,1,1,2,2,3,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,3,1,1,1,1,1,3,1,1,2,1,2,2,1,1,1,3,1,1,2,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,2,1,1,2,2,1,1,1,1,1,1,1,1,1,2,1,2,2,1,1,1,1,2,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,2,1,1,1,1,1,1,1,1,2,1,1,2,2,1,1,1,2,1,2,1,1,1,1,1,1,1,3,2,2,2,1,1,1,4,1,2,2,1,2,2,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,3,1,1,1,1,2,1,1,1,1,3,1,3,1,1,1,2,1,1,3,1,1,1,1,1,1,2,1,1,1,1,2,2,2,3,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,2,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,3,2,2,2,1,1,2,1,2,1,2,2,3,2,1,2,1,1,1,1,2,1,1,1,1,3,1,1,1,2,1,2,2,1,1,1,1,1,1,1,2,1,1,1,1,2,2,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,2,1,2,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,2,2,1,2,1,2,1,1,1,1,1,2,1,3,1,2,1,1,1,1,1,2,1,2,1,2,1,3,1,1,1,1,2,1,2,1,1,2,2,1,1,1,1,3,1,1,1,2,1,2,1,1,1,1,1,2,1,1,1,1,2,1,2,1,2,3,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,2,3,2,2,2,1,2,1,4,1,2,2,1,2,1,1,1,1,1,1,1,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,2,1,1,1,1,1,2,1,2,1,1,1,2,1,1,2,4,1,1,1,1,1,1,1,1,2,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,1,1,1,1,1,2,2,1,1,2,1,1,2,1,1,1,1,1,4,2,1,1,1,2,2,3,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,1,2,1,1,1,1,1,2,1,1,1,1,1,1,1,2,2,1,1,3,1,1,1,1,2,1,1,1,2,1,1,1,1,1,2,1,1,1,1,1,2,1,1,1,2,2,2,1,2,1,1,4,1,2,3,1,1,1,2,1,1,1,1,1,1,2,1,1,2,1,1,2,1,2,1,1,1,1,2,1,1,1,3,1,1,1,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,2,1,1,1,2,1,1,1,2,1,2,1,2,2,1,4,2,1,2,1,1,1,1,2,1,1,1,1,1,1,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,1,3,1,1,1,2,1,2,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,2,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,2,1,1,2,2,1,1,1,1,2,1,1,2,2,1,1,2,1,1,1,1,3,1,1,1,3,1,1,1,1,1,1,1,1,1,1,2,1,1,1,2,1,1,1,2,3,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,2,1,1,2,3,2,1,2,1,1,1,2,2,2,1,1,2,1,1,1,1,1,2,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,3,1,1,1,2,2,1,1,1,1,3,1,1,2,1,1,2,1,1,2,1,1,2,2,1,2,1,1,1,1,1,1,1,2,2,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,1,1,1,3,2,1,2,2,2,1,2,3,2,1,1,1,2,1,1,2,1,1,2,1,1,1,1,3,1,1,2,1,1,1,1,3,1,2,1,2,2,2,1,1,2,3,1,1,1,1,1,1,1,1,1,1,2,2,1,2,2,2,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,2,1,2,2,2,1,2,1,1,1,1,1,1,1,1,2,2,3,1,4,1,2,1,1,1,1,2,2,1,1,1,1,1,2,1,1,1,1,1,1,2,1,2,1,2,1,1,1,2,1,1,3,1,1,1,1,1,1,1,1,2,2,1,1,2,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,2,1,1,1,2,2,1,1,1,1,1,1,1,2,2,1,1,2,1,2,2,3,1,1,1,1,2,1,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,3,1,1,2,1,2,1,1,2,1,1,2,1,1,2,2,3,1,2,1,1,1,1,2,1,1,1,2,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,2,2,1,2,1,2,1,1,1,1,1,1,1,2,1,1,1,1,1,2,1,1,3,1,1,1,2,1,1,1,3,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,2,1,1,1,1,1,1,1,1,1,2,1,1,1,2,2,1,1,1,2,1,1,3,1,1,2,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,3,2,2,1,1,3,1,3,1,2,1,1,1,1,1,1,1,1,1,1,2,3,1,2,2,1,1,1,1,1,1,1,1,1,1,4,2,2,1,2,2,2,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,3,1,2,1,1,1,1,1,1,2,1,1,2,2,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,2,1,1,1,1,2,2,2,1,1,1,1,1,1,1,1,2,1,1,1,1,3,1,1,1,1,1,2,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,4,1,1,1,1,1,1,1,3,3,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,4,1,1,1,1,1,2,1,1,1,2,1,1,2,1,1,1,1,3,1,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,1,1,1,1,1,1,1,1,1,2,1,2,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,2,1,1,2,1,2,1,1,1,1,1,2,1,1,1,2,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,1,2,1,1,1,1,1,1,2,2,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,2,1,1,1,1,1,2,1,2,2,3,1,2,1,1,2,2,2,1,2,2,1,1,1,1,3,1,1,2,1,1,1,2,1,1,1,1,1,1,1,1,2,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,1,1,2,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,3,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,2,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,2,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,2,1,1,2,1,2,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,3,1,1,1,2,1,1,1,1,1,1,2,2,1,1,1,2,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,2,1,1,1,1,1,2,2,1,1,1,1,2,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,2,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,2,1,1,2,1,1,1,1,1,1,1,2,1,1,1,2,1,1,2,1,1,1,1,2,1,1,1,1,3,1,1,1,1,1,1,2,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,2,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,2,1,1,1,1,2,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,1,2,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,};
//		try {
//			LengthDistribution l= new LengthDistribution(1000);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for(int i = 0; i <10;i++){
//			double a =  possibilitiesLength[0][i];
//			double b = possibilitiesLength[1][i];
//			System.out.println("a: "+ a+" b: "+b);
//		}
//	}
}
