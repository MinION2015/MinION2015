package controller;

import java.io.IOException;
import java.util.ArrayList;

import reader.*;
import error.*;

/**
 * 
 * @author Friederike Hanssen
 * Gets a filename, parses the content in a FastA object. Sofar the gui is not able to display if we have an invalid filename.
 * Then a pore is simulated and the content of the file will be 'sent' through the pore and according to the parameters 1D/2D and
 * the error type(in our case only basecalling errors are relevant, time and length-basecalling has not been implemented,)will be applied.
 * The results are written in a file.
 * 
 * Should prob be redone with adding things like chosing the filename the results get written into and considering for the gui that the controller now holds a fasta object
 * Was just a quick solution to get our code to work together.
 */
public class Controller{
	
	private String filename;
	private FastA f;
	
	/**
	 * controller gets source filename, creates a fasta object and parses the filename saving it as the fasta object
	 * @param sourceFilename
	 * @throws IOException
	 */
	public Controller(String sourceFilename) throws IOException{
		this.filename = sourceFilename;
		this.f = new FastA();
		try{
			f.parse(sourceFilename);
		}catch(Exception e){
			
		}
	}

	
	/**
	 * A pore is simulated, through which the sequences in from the file are sent. Basecalling errors are simulated.
	 * @param basecalling: Basecalling type (1D=1/2D=2) as selected in the gui
	 * @throws IOException
	 * @throws MyException 
	 */
	public void run(int basecalling) throws IOException, MyException{
		
		
		
		Pore pore = new Pore();
		SimulationError errorType = new SimulationError();
		FastA porePassed = new FastA();
		LengthDistribution lengthType = new LengthDistribution();
		
		for(Sequence entry: f.getSequence()){
			if(entry.getSequence() != null){
			Sequence seq = pore.simulate(entry.getSequence(),errorType,lengthType,basecalling);
			
			seq.setHeader(entry.getHeader());
			
			try{
				
				porePassed.addSeq(seq);
			
			}catch(MyException e){
				
			}
			}
		}
		
		//porePassed.writeInFile("C:/Users/Friederike/Desktop/MinionTestParser/Test.txt");
		porePassed.writeInFile("C:/Users/Friederike/Desktop/MinionTestParser/MinionTestParser.txt");
	}
	
	/**
	 * MEthod for the gui to get the Error messages. Can't see incorrect filename or such.
	 * @return
	 */
	public ArrayList<MyException> getincorrectfastAList() {
		return this.f.getErrorInSequence();
	}
	
//	public static void main(String[] args) throws IOException, MyException{
//		
//		if(args.length != 1){
//			System.err.println("You should specify a FastA file as input!");
//			System.exit(1);
//		}else{
//			Controller c = new Controller(args[0]);
//			c.run(1);
//		}
//	}
}



