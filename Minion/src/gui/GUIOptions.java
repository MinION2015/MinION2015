package gui;

/**
 * 
 * @author Friederike
 *@functionality Class holds all options that the user can add, the controller will then be able to retrieve this information as needed
 *@input All possible options
 *@Output Getters for the options
 */
public class GUIOptions {
	
	private String inputFilename;
	private String outputFilename;
	private int basecalling;
	private int ticksPerSecond;
	private int numberOfPores;
	private int runningTime;
	private int windowSizeForLengthDistribution;
	
	public GUIOptions(String inputFilename, String outputFilename, int basecalling, int ticksPerSecond, int numberOfPores,int runningTime,int windowSizeForLengthDistribution){
		this.inputFilename = inputFilename;
		this.outputFilename = outputFilename;
		this.basecalling = basecalling;
		this.ticksPerSecond = ticksPerSecond;
		this.numberOfPores = numberOfPores;
		this.runningTime = runningTime;
		this.windowSizeForLengthDistribution = windowSizeForLengthDistribution;
	}

	public int getWindowSizeForLengthDistribution() {
		return windowSizeForLengthDistribution;
	}

	public int getRunningTime() {
		return runningTime;
	}

	public int getNumberOfPores() {
		return numberOfPores;
	}

	public String getInputFilename() {
		return inputFilename;
	}

	public String getOutputFilename() {
		return outputFilename;
	}

	public int getBasecalling() {
		return basecalling;
	}

	public int getTicksPerSecond() {
		return ticksPerSecond;
	}

}
