package readerTest;

import java.io.IOException;
import java.util.ArrayList;

import error.MyException;

public interface TestFormat {

	
	public void parse(String inputFilename) throws IOException,MyException;
	public void writeInFile();
	public void printOutFile();
	
	
	
	public ArrayList<Sequence> getSequence();
	public ArrayList <ErrorInSequence> getErrorInSequence();
	
	
}
