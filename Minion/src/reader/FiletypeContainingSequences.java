package reader;

import java.io.IOException;
import java.util.ArrayList;

import error.MyException;
/**
 * 
 * @author Friederike Hanssen
 * Interface for implementing a new readable filetype
 * No input
 * Output: data is written into a file, arraylists can be called
 *
 */
public interface FiletypeContainingSequences {

	
	public void parse(String inputFilename) throws IOException,MyException;
	public void writeInFile(String filename);
	public void addSeq(Sequence seq) throws MyException;
		
	
	public ArrayList<Sequence> getSequence();
	public ArrayList <MyException> getErrorInSequence();
	
	
}

