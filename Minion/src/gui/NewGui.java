
package gui;

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
/**
 * @author Sven
 */
public class NewGui extends javax.swing.JFrame {
    
    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    	private String inputFilename;
	private String outputFilename;
	private int basecalling;
	private int ticksPerSecond;
	private int numberOfPores;
	private int runningTime;
	private int windowSizeForLengthDistribution;
    /**
     * Creates new form NewJFrame
     */
    public NewGui() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        outputTextField = new javax.swing.JTextField();
        InputPanel = new javax.swing.JPanel();
        sourceField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        nopLabel = new javax.swing.JLabel();
        totalNumberOfTicksLabel = new javax.swing.JLabel();
        durationPerTickLabel = new javax.swing.JLabel();
        dimLabel = new javax.swing.JLabel();
        dimComboBox = new javax.swing.JComboBox();
        windowSizeForLengthDistributionLabel = new javax.swing.JLabel();
        outputFileTextField = new javax.swing.JTextField();
        outputFilenameLabel = new javax.swing.JLabel();
        numberOfPoresFormattedTextField = new javax.swing.JFormattedTextField();
        numberOfTicksTextField = new javax.swing.JFormattedTextField();
        durationPerTickFormattedTextField = new javax.swing.JFormattedTextField();
        windowSizeFormattedTextField = new javax.swing.JFormattedTextField();
        runPanel = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        loadFileMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        toolMenu = new javax.swing.JMenu();
        settingsMenu = new javax.swing.JMenu();
        InputSettingsMenuItem = new javax.swing.JMenuItem();
        outputSettingsMenuItem = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        outputTextField.setEditable(false);
        outputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputTextFieldActionPerformed(evt);
            }
        });

        InputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Input"));

        sourceField.setText("..\\desktop\\example.fasta");

        browseButton.setText("Browse..");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        nopLabel.setText("Number of pores:");

        totalNumberOfTicksLabel.setText("Total number of ticks:");

        durationPerTickLabel.setText("Duration per Tick:");

        dimLabel.setText("Dimension:");

        dimComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1D", "2D" }));

        windowSizeForLengthDistributionLabel.setText("Window size:");

        outputFileTextField.setText("Result");
        outputFileTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputFileTextFieldActionPerformed(evt);
            }
        });

        outputFilenameLabel.setText("Outputfile-name:");

        numberOfPoresFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        numberOfPoresFormattedTextField.setText("5");
        numberOfPoresFormattedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberOfPoresFormattedTextFieldActionPerformed(evt);
            }
        });

        numberOfTicksTextField.setText("5");

        durationPerTickFormattedTextField.setText("1");

        windowSizeFormattedTextField.setText("1000");

        javax.swing.GroupLayout InputPanelLayout = new javax.swing.GroupLayout(InputPanel);
        InputPanel.setLayout(InputPanelLayout);
        InputPanelLayout.setHorizontalGroup(
            InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputPanelLayout.createSequentialGroup()
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sourceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(InputPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nopLabel)
                            .addComponent(totalNumberOfTicksLabel)
                            .addComponent(durationPerTickLabel)
                            .addComponent(dimLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(browseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dimComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(numberOfPoresFormattedTextField)
                    .addComponent(numberOfTicksTextField)
                    .addComponent(durationPerTickFormattedTextField))
                .addGap(44, 44, 44)
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(windowSizeForLengthDistributionLabel)
                    .addComponent(outputFilenameLabel))
                .addGap(40, 40, 40)
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(outputFileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(windowSizeFormattedTextField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        InputPanelLayout.setVerticalGroup(
            InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputPanelLayout.createSequentialGroup()
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sourceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton)
                    .addComponent(outputFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputFilenameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nopLabel)
                    .addComponent(windowSizeForLengthDistributionLabel)
                    .addComponent(numberOfPoresFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(windowSizeFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalNumberOfTicksLabel)
                    .addComponent(numberOfTicksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(durationPerTickLabel)
                    .addComponent(durationPerTickFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dimLabel)
                    .addComponent(dimComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        runPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Run"));

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");

        pauseButton.setText("Pause");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout runPanelLayout = new javax.swing.GroupLayout(runPanel);
        runPanel.setLayout(runPanelLayout);
        runPanelLayout.setHorizontalGroup(
            runPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(runPanelLayout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pauseButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopButton)
                .addContainerGap(206, Short.MAX_VALUE))
        );
        runPanelLayout.setVerticalGroup(
            runPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(runPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(runPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stopButton)
                    .addComponent(pauseButton)
                    .addComponent(startButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fileMenu.setText("File");

        loadFileMenuItem.setLabel("Load File");
        fileMenu.add(loadFileMenuItem);

        exitMenuItem.setLabel("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        toolMenu.setText("Tools");
        menuBar.add(toolMenu);

        settingsMenu.setText("Settings");

        InputSettingsMenuItem.setLabel("Input Settings");
        settingsMenu.add(InputSettingsMenuItem);

        outputSettingsMenuItem.setLabel("Output Settings");
        settingsMenu.add(outputSettingsMenuItem);

        menuBar.add(settingsMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(outputTextField)
            .addComponent(InputPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(runPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(InputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(runPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void outputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        fileChooser.showOpenDialog(NewGui.this);
        sourceField.setText(fileChooser.getSelectedFile().getName());     
    }                                            

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

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

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void outputFileTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                    

    }                                                   

    private void numberOfPoresFormattedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                                
        // TODO add your handling code here:
    }                                                               

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
    System.exit(0);
    }                                            

 
    // Variables declaration - do not modify                     
    private javax.swing.JPanel InputPanel;
    private javax.swing.JMenuItem InputSettingsMenuItem;
    private javax.swing.JButton browseButton;
    private javax.swing.JComboBox dimComboBox;
    private javax.swing.JLabel dimLabel;
    private javax.swing.JFormattedTextField durationPerTickFormattedTextField;
    private javax.swing.JLabel durationPerTickLabel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem loadFileMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel nopLabel;
    private javax.swing.JFormattedTextField numberOfPoresFormattedTextField;
    private javax.swing.JFormattedTextField numberOfTicksTextField;
    private javax.swing.JTextField outputFileTextField;
    private javax.swing.JLabel outputFilenameLabel;
    private javax.swing.JMenuItem outputSettingsMenuItem;
    private javax.swing.JTextField outputTextField;
    private javax.swing.JButton pauseButton;
    private javax.swing.JPanel runPanel;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JTextField sourceField;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JMenu toolMenu;
    private javax.swing.JLabel totalNumberOfTicksLabel;
    private javax.swing.JLabel windowSizeForLengthDistributionLabel;
    private javax.swing.JFormattedTextField windowSizeFormattedTextField;
    // End of variables declaration                   
}

