package reader;

import java.io.IOException;
import java.util.ArrayList;

import error.MyException;
/**
 * 
 * @author Friederike Hanssen
 * Interface for implementing a new readable filetype
 *
 */
public interface Filetype {

	
	public void parse(String inputFilename) throws IOException,MyException;
	public void writeInFile(String filename);
	public void addSeq(Sequence seq) throws MyException;
	//doesn't seem to be necessary anymore since the controller will save a fastA object that contains all information needed
	//public void printOutFile();
	
	
	
	public ArrayList<Sequence> getSequence();
	public ArrayList <MyException> getErrorInSequence();
	
	
}

