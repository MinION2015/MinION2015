package controller;

import java.io.IOException;
import java.util.ArrayList;

import reader.ErrorInSequence;
import reader.FastA;
import reader.Sequence;
import error.ErrorBasecalling;
import error.MyException;

/**
 * 
 * @author Friederike Hanssen
 *
 */
public class Controller{
	
	private String filename;
	private FastA f;
	
	public Controller(String filename) throws IOException{
		this.filename = filename;
		this.f = new FastA();
		try{
			f.parse(filename);
		}catch(Exception e){
		}
	}

	public void run(int basecalling) throws IOException{
		
		
		
		Pore pore = new Pore();
		ErrorBasecalling errorType = new ErrorBasecalling();
		FastA porePassed = new FastA();
		LengthDistribution lengthType = new LengthDistribution();
		
		for(Sequence entry: f.getSequence()){
			if(entry.getSequence() != null){
			String simSeq = pore.simulate(entry.getSequence(),errorType,lengthType,basecalling);
			Sequence seq = new Sequence(entry.getHeader(),simSeq);
			try{
				porePassed.addSeq(seq);
			}catch(MyException e){
				
			}
			}
		}
		
		porePassed.writeInFile("C:/Users/Friederike/Desktop/MinionTestParser/MinionTestParser.txt");
	
	}
	
//	public ArrayList<FastA> getcorrectfastAList() {
//		return this.correctfastAList;
//	}
//
	public ArrayList<ErrorInSequence> getincorrectfastAList() {
		return this.f.getErrorInSequence();
	}
	
//	public static void main(String[] args) throws IOException, MyException{
//		if(args.length != 1){
//			System.err.println("You should specify a FastA file as input!");
//			System.exit(1);
//		}else{
//
//			Controller c = new Controller(args[0]);
//
//		}
//	}
}



