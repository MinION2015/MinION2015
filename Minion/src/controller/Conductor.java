package controller;
import reader.FastAEntry;
import reader.IncorrectFastAEntry;
import reader.ParseFastA;

import java.io.IOException;
import java.util.ArrayList;

public class Conductor {
	ArrayList<FastAEntry> correctfastAList;

	/*
	 * called by the ActionListener of the gui. gets the filename and and calles the model functions.
	 * Returns the a ArrayList of incorrectFatAFiles.
	 */
	public ArrayList<IncorrectFastAEntry> modelFile(String filename) throws IOException
	{
		ParseFastA newParsedfile = new ParseFastA(filename);
		Conductor conductor = new Conductor(newParsedfile.getFastAList());
		
		
		return newParsedfile.getFastAErrorList();	//returns the ArrayList of incorrect FastA Files
	}
	/*
	 * gets the ArrayList of correct FastaEntrys. Stores it in the Conductor Object.
	 */
	private Conductor(ArrayList<FastAEntry> correctfastAList)
	{
		correctfastAList = correctfastAList;
	}
}
