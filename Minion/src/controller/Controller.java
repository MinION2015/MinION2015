package controller;

import reader.*;
import error.*;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
	private ArrayList<FastAEntry> correctfastAList;
	private ArrayList<IncorrectFastAEntry> incorrectfastAList;

	/*
	 * called by the ActionListener of the gui. gets the filename and and calls
	 * the model functions. Returns the a ArrayList of incorrectFastAFiles.
	 */

	/*
	 * gets the ArrayList of correct FastaEntrys. Stores it in the Controller
	 * Object.
	 */
	public Controller(String filename) throws IOException, MyException {
		try {
			ParseFastA newParsedfile = new ParseFastA(filename);
			this.correctfastAList = newParsedfile.getFastAList();
			this.incorrectfastAList = newParsedfile.getFastAErrorList();
		} catch (MyException e) {

		}
	}

	public ArrayList<FastAEntry> getcorrectfastAList() {
		return this.correctfastAList;
	}

	public ArrayList<IncorrectFastAEntry> getincorrectfastAList() {
		return this.incorrectfastAList;
	}
}
