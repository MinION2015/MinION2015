package readerTest;

public class ErrorInSequence {
	
	private String errorMessage;
	private int errorCode;
	private boolean isCritical;
	
	
	public ErrorInSequence(int errorCode,String ErrorMessage, boolean isCritical){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.isCritical = isCritical;
		
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public int getErrorCode() {
		return errorCode;
	}


	public boolean isCritical() {
		return isCritical;
	}


}

