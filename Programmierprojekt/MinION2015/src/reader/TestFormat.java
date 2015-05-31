package reader;

import java.io.IOException;
import java.util.ArrayList;

import error.MyException;

public interface TestFormat {

	
	public void parse(String inputFilename) throws IOException,MyException;
	public void writeInFile(String filename);
	//doesn't seem to be necessary anymore since the controller will save a fastA object that contains all information needed
	//public void printOutFile();
	
	
	
	public ArrayList<Sequence> getSequence();
	public ArrayList <ErrorInSequence> getErrorInSequence();
	
	
}

