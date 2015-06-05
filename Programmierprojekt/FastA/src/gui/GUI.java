package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controller;
import error.MyException;


/**
 * 
 * @author Albert Langensiepen
 * this class contains a constructor for the GUI, a main method for
 * running the GUI and an ActionListener which implements the functions 
 * of the other classes through the buttons
 * The window contains 2 Buttons
 * one for choosing a fasta-file, for which it needs a JFileChooser
 *  and one to start the simulation
 * it also has a label where the error messages are shown 
 */

public class GUI extends JFrame implements ActionListener {
	JButton startButton;
	JButton loadButton;
	JLabel label;
	JPanel panel;
	JComboBox choice;
	final JFileChooser fc = new JFileChooser();

	/*
	 * the constructor for the GUI
	 * 
	 */
	public GUI() throws IOException{

		this.setTitle("MinION Simulator");
		this.setSize(800, 600);
		panel = new JPanel();
		label = new JLabel();


		startButton = new JButton("START SIMULATION");
		loadButton = new JButton ("LOAD FILE");

		startButton.addActionListener(this);
		loadButton.addActionListener(this);

		String[] parameter = {"1D","2D"};
		choice = new JComboBox(parameter);

		panel.setLayout( new java.awt.BorderLayout() );
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box,BoxLayout.Y_AXIS));
		box.add(loadButton);
		box.add(startButton);
		box.add(choice);

		panel.add(box, java.awt.BorderLayout.NORTH);
		panel.add(label, java.awt.BorderLayout.CENTER);

		this.getContentPane().add ( panel ) ;
		this.add(panel);
	}

	public static void main(String[] args) throws IOException
	{
		GUI bl = new GUI();
		bl.setVisible(true);
	}


	/**
	 * the ActionListener for the Buttons
	 * the Load Button opens a OpenDialog where a File is selected
	 * if the load button was pushed as well as the start button a controller object is generated
	 * with the file name, which is handed to the Parser-Class
	 * the ArrayList which is returned is then printed with a for-loop 
	 * 
	 * @param ae: a click on a button
	 *  
	 */
	public void actionPerformed (ActionEvent ae) {

		int returnVal =0;

		if(ae.getSource() == this.loadButton){
			returnVal = fc.showOpenDialog(GUI.this);
		}

		if(ae.getSource() == this.startButton && returnVal==JFileChooser.APPROVE_OPTION){

			String message= "<html>";
			Controller cd = null;
			try {
				int basecalling = 0;
				// moved the if clause to safe the choice 1D/2D and run the controller with that choice
				//TODO reassigned basecalling doesn't work
				if (ae.getSource() == this.choice) {

					String chosen = (String) choice.getSelectedItem();

					if (chosen.equals("1D")) {
						//TODO
						basecalling =1;
					} else if (chosen.equals("2D")) {
						//TODO
						basecalling=2;
					}

				}
				
				
				
				cd = new Controller(fc.getSelectedFile().getName());
				//For some reason results are not stored in the file but only null. tested the controller separatly,which worked just fine
				//seems like basecalling doens't get reassigned, as it stays 0;
				System.out.println(basecalling);
				cd.run(1);
				
				
			} catch (IOException e) {
				//TODO
				e.printStackTrace();
			} 


			int length= cd.getincorrectfastAList().size();

			for(int i=0; i<length;i++)
			{
				if(cd.getincorrectfastAList().get(i).isCriticalError()) {
					JOptionPane.showMessageDialog(null, cd.getincorrectfastAList().get(i).getErrorMessage(), "Critical Error", JOptionPane.ERROR_MESSAGE);
				} else {
					message= message + " " + cd.getincorrectfastAList().get(i).getErrorMessage()+"<br>";
				}
			}

			message=message+"</html>";

			label.setText(message);  

			//	        	 if (ae.getSource() == this.choice) {
			//		            	
			//		            	String chosen = (String) choice.getSelectedItem();
			//		            	
			//		            	if (chosen.equals("1D")) {
			//		            		//TODO
			//		      
			//		            	} else if (chosen.equals("2D")) {
			//		            		//TODO
			//		            	}    	
			//		            	}

		}
	}


}

