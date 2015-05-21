package controller;
import reader.FastAEntry;
import reader.IncorrectFastAEntry;
import reader.ParseFastA;

import java.io.IOException;
import java.util.ArrayList;

public class Conductor {
	ArrayList<FastAEntry> correctfastAList;
	ArrayList<IncorrectFastAEntry> incorrectfastAList;
	

	/*
	 * called by the ActionListener of the gui. gets the filename and and calles the model functions.
	 * Returns the a ArrayList of incorrectFatAFiles.
	 */

	/*
	 * gets the ArrayList of correct FastaEntrys. Stores it in the Conductor Object.
	 */
	private Conductor(String filename) throws IOException
	{
		ParseFastA newParsedfile = new ParseFastA(filename);
		this.correctfastAList = newParsedfile.getFastAList();
		this.incorrectfastAList = newParsedfile.getFastAErrorList();
	}
	public ArrayList<FastAEntry> getcorrectfastAList()
	{
		return this.correctfastAList;
	}
	public ArrayList<IncorrectFastAEntry> getincorrectfastAList()
	{
		return this.incorrectfastAList;
	}
}
