package controller;

import java.util.ArrayList;

import reader.FastQEntry;
import reader.FastQParser;
import controller.Input;

public class Conductor {

	public void main(String args){
		
		Input in = new Input();
		String fastQName = in.getFastQNameInput();		//gets name of the fastQ file as string
		
		FastQParser fQParser = new FastQParser(fastQName);			//new ParserObject
		fQParser.getIncorrectArray();
		
	}
	/*
	 * check the Error id and returns it
	 */
	public String checkErrorID(int id)
	{
		return "";
	}
	
	/*
	 * gets MEssages and outputs them on the gui
	 */
	public void Output(String ErrorMessage, String CorrectMessage)
	{
		gui.textfield_Error.setText(ErrorMessage);		//gui as placeholder
		textfield_Correct.setText(CorrectMessage);
	}
}
