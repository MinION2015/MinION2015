package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Input extends JFrame implements ActionListener {
	
	JButton buttonstart;
	JLabel fastQName;
	String fastQNameInput;
	
	public Input()
	{
		buttonstart = new JButton("Start");		//Start button, must be implemented in the gui
		buttonstart.addActionListener(this);	//implement with panel.add(buttonstart)
		
		fastQName = new JLabel("name of the FastQ-File");	//InputField for FastQ file name
															//add to panel with panel.add(fastQName)
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * waits for action to be performed. Checks which button generated the action.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource() == this.buttonstart)
		{
			this.fastQNameInput = fastQName.getText();
		}
	}
	public String getFastQNameInput()
	{
		return fastQNameInput;
	}

}
