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
	private String writeInFileOption;
	private int basecalling;
	private int numberOfPores;
	private int maxAgeOfPores;
	private int durationOfTick;
	private int totalNumberOfTicks;
	private int windowSizeForLengthDistribution;
	private String basecallingSetup;
	private String lengthDistributionSetup;
	private String outputFormat;
	
	public GUIOptions(String inputFilename, 
			String outputFilename, 
			String writeInFileOption, String outputFormat,
			int basecalling, 
			int numberOfPores,
			int maxAgeOfPores,
			int durationOfTick, 
			int totalNumberOfTicks,
			int windowSizeForLengthDistribution){
		this.inputFilename = inputFilename;
		this.outputFilename = outputFilename;
		this.writeInFileOption = writeInFileOption;
		this.outputFormat = outputFormat;
		this.basecalling = basecalling;
		this.durationOfTick = durationOfTick;
		this.numberOfPores = numberOfPores;
		this.maxAgeOfPores = maxAgeOfPores;
		this.totalNumberOfTicks = totalNumberOfTicks;
		this.windowSizeForLengthDistribution = windowSizeForLengthDistribution;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public String getWriteInFileOption() {
		return writeInFileOption;
	}

	public int getMaxAgeOfPores() {
		return maxAgeOfPores;
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
