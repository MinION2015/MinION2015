package reader;

public class IncorrectFastAEntry {

	private String errorMessage;
	private int errorCode;
	
	public IncorrectFastAEntry(String sequence, int errorNumber){
		this.errorMessage = sequence;
		this.errorCode = errorNumber;
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
