package controller;

import java.io.IOException;

import reader.*;
import error.*;

/**
 * 
 * @author Friederike Hanssen
 *
 */
public class Controller{
	
	private String filename;
	
	public Controller(String filename) throws IOException{
		this.filename = filename;
		run();
	}

	private void run() throws IOException{
		
		FastA f = new FastA();
		try{
			f.parse(filename);
		}catch(Exception e){
		}
		
		Pore pore = new Pore();
		ErrorBasecalling errorType = new ErrorBasecalling();
		FastA porePassed = new FastA();
		LengthDistribution lengthType = new LengthDistribution();
		
		for(Sequence entry: f.getSequence()){
			String simSeq = pore.simulate(entry.getSequence(),errorType,lengthType);
			Sequence seq = new Sequence(entry.getHeader(),simSeq);
			try{
				porePassed.addSeq(seq);
			}catch(MyException e){
				
			}
		}
		
		porePassed.writeInFile("C:/Users/Friederike/Desktop/MinionTestParser/MinionTestParser.txt");
	}
}
