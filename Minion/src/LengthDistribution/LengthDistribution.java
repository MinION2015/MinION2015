package LengthDistribution;

import java.io.IOException;
import java.util.Random;

import error.Chance;
/**
 * 
 * @author Albert Langensiepen und Daniel Dehncke
 * LengthDistribution object will return a rand length with a prob found in the lengthrate class.
 * Same as with errorbasecalling, should prob not be a class, also length rate and length distribution can be merged, thus the commented lines of code, but i'm aunable to test it right now.
 */
public class LengthDistribution {

	private static LengthRate l;
	
	/**
	 * 
	 * @param filename	input Filename for own Length Possibilities for fasta files. needs .fasta
	 * @param window	the sequences from this windows size get one possibilities. So for window size 1000 u get a possibility for 1*1000, for 2*1000 and so on
	 * @throws IOException
	 */
	public LengthDistribution(String filename,int window) throws IOException{
		
		l = new LengthRate(filename, window);
	}
	
	public LengthRate getRate(){
		return this.l;
		
	}
	
	
	//scond constructor for default LengthDistribution
	/**
	 * Only needs the Windowsize, gets the lengths from default file
	 * @param window the sequences from this windows size get one possibilities. So for window size 1000 u get a possibility for 1*1000, for 2*1000 and so on
	 * @throws IOException
	 */
	public LengthDistribution(int window) throws IOException{

		l = new LengthRate(window);
	}

	/**
	 * @author Daniel Dehncke
	 * generates a Random Length from the analyzed Data in LengthRate and returns it
	 */
	public static double getRandLength(){
		
		
		Random r = new Random(); 
		double d = r.nextDouble(); 
	//	System.out.println("d: "+ d);
		int i = 0;

		while(d > LengthRate.getProb(i))
		{
			i++;
			///System.out.println("while exe");
		}
		//System.out.println("i: "+i);
		return (int)LengthRate.getLength(i);
	}

	
/**
 * Test	
 * @throws IOException 
 */
//	public static void main(String args[]) throws IOException{
//		//LengthDistribution l = new LengthDistribution("ownLengthDistribution.txt",1000);
//		//double rand = l.getRandLength();
//		//System.out.println(rand);
//		LengthDistribution l = new LengthDistribution(10);
//		
//	}
}
