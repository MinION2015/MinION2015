package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	JMenuBar bar;
	JMenu file, tools, setting;
	JMenuItem moreToCome1, moreToCome2,outputSetting;
	JButton startButton, stopButton, loadButton; 
	JTextField pores, runningTime, ticksPerSecond;
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
		this.setSize(900, 600);
		panel = new JPanel();
		label = new JLabel();


		bar = new JMenuBar();
		
		file = new JMenu ("File");
		tools = new JMenu("Tools");
		setting = new JMenu("Settings");
		
		moreToCome1 = new JMenuItem("Some Menu Thingy");
		moreToCome2 = new JMenuItem("Some Menu Thingy");
		outputSetting = new JMenuItem("Outputfile-Settings");
		
		bar.add(file);
		bar.add(tools);
		bar.add(setting);
		
		file.add(moreToCome2);
		tools.add(moreToCome1);
		setting.add(outputSetting);
		
		
		this.setJMenuBar(bar);
		add(bar,BorderLayout.NORTH);
		
		startButton = new JButton("START SIMULATION");
		stopButton = new JButton ("STOP SIMULATION");
		loadButton = new JButton ("LOAD FILE");
		
		pores = new JTextField("NUMBER OF PORES");
		runningTime = new JTextField("Running Time in Seconds");
		ticksPerSecond = new JTextField("Ticks per Second");

		//ActionListener for Buttons
		startButton.addActionListener(this);
		loadButton.addActionListener(this);
		stopButton.addActionListener(this);
		
		//ActionsListener for Textfields
		pores.addActionListener(this);
		runningTime.addActionListener(this);
		ticksPerSecond.addActionListener(this);
		
		String[] parameter = {"1D","2D"};
		choice = new JComboBox(parameter);

		panel.setLayout( new BorderLayout() );
		JPanel box = new JPanel();
		//box.setLayout(new BoxLayout(box,BoxLayout.Y_AXIS));

		box.add(loadButton);
		box.add(choice);
		box.add(pores);
		box.add(runningTime);
		box.add(ticksPerSecond);
		box.add(startButton);
		box.add(stopButton);


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
	
	/*TODO Stop Button
	 * stop simulation keep gui open
	 * 
	 */
	
	/*
	 * TODO Output File Setting
	 */


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
	@SuppressWarnings("deprecation")
	public void actionPerformed (ActionEvent ae) {
		//Set default values for input parameters
		int numOfPores=1;
		int runTime=10;
		int ticksPSecond=5;
		int returnVal =0;
		
		/*if(ae.getSource() == this.pores){
			
		}
		if(ae.getSource() == this.runningTime){
			
		}
		if(ae.getSource() == this.ticksPerSecond){
			
		}

		if(ae.getSource() == this.loadButton){
			returnVal = fc.showOpenDialog(GUI.this);
		}*/
		
		//checks if loadButton has been pressed and a file has been chosen -> renames loadButton to file which has been chosen
		if(ae.getSource()== this.loadButton && returnVal==JFileChooser.APPROVE_OPTION){
			loadButton.setLabel(fc.getSelectedFile().getName());
		}

		if(ae.getSource() == this.startButton && returnVal==JFileChooser.APPROVE_OPTION){
			numOfPores = Integer.parseInt(pores.getText());
			runTime = Integer.parseInt(runningTime.getText());
			ticksPSecond = Integer.parseInt(ticksPerSecond.getText());
			String message= "<html>";
			Controller cd = null;
			
				int basecalling = 0;
				// moved the if clause to safe the choice 1D/2D and run the controller with that choice
				//TODO reassigned basecalling doesn't work
			

					String chosen = (String) choice.getSelectedItem();

					if (chosen.equals("1D")) {
						//TODO
						basecalling =1;
					} else if (chosen.equals("2D")) {
						//TODO
						basecalling=2;
					}

				
				
				GUIOptions options = new GUIOptions("test","test",1,1,1,1,1);
				cd = new Controller(options);
				System.out.println(numOfPores+" "+runTime+" "+ticksPSecond);
				cd.run();
				
			


			int length= cd.getFastAErrors().size();

			for(int i=0; i<length;i++)
			{
				if(cd.getFastAErrors().get(i).isCriticalError()) {
					JOptionPane.showMessageDialog(null, cd.getFastAErrors().get(i).getErrorMessage(), "Critical Error", JOptionPane.ERROR_MESSAGE);
				} else {
					message= message + " " + cd.getFastAErrors().get(i).getErrorMessage()+"<br>";
				}
			}

			message=message+"</html>";

			label.setText(message);  
		}
	}


}

