package reader;

public class IncorrectFastAEntry {

	private int amountParsed;
	private String errorMessage;
	private int errorCode;
	private boolean isCritical;
	
	
	public IncorrectFastAEntry(int amountParsed,String sequence, int errorNumber,boolean isCritical){
		this.amountParsed = amountParsed;
		this.errorMessage = sequence;
		this.errorCode = errorNumber;
		this.isCritical = isCritical;
	}

	public boolean isCritical() {
		return isCritical;
	}

	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}

	public String getSequence() {
		return errorMessage;
	}

	public void setSequence(String sequence) {
		this.errorMessage = sequence;
	}

	public int getErrorNumber() {
		return errorCode;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorCode = errorNumber;
	}
}
