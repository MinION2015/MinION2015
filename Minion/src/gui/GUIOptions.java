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
	private int durationOfTick;
	private int numberOfPores;
	private int totalNumberOfTicks;
	private int windowSizeForLengthDistribution;
	
	public GUIOptions(String inputFilename, String outputFilename, int basecalling, int durationOfTick, int numberOfPores,int totalNumberOfTicks,int windowSizeForLengthDistribution){
		this.inputFilename = inputFilename;
		this.outputFilename = outputFilename;
		this.basecalling = basecalling;
		this.durationOfTick = durationOfTick;
		this.numberOfPores = numberOfPores;
		this.totalNumberOfTicks = totalNumberOfTicks;
		this.windowSizeForLengthDistribution = windowSizeForLengthDistribution;
	}

	public int getWindowSizeForLengthDistribution() {
		return windowSizeForLengthDistribution;
	}

	public int getTotalNumberOfTicks() {
		return totalNumberOfTicks;
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

	public int getDurationOfTick() {
		return durationOfTick;
	}

}
