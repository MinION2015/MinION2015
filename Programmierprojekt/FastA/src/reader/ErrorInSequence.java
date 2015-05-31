package reader;
/**
 * 
 * @author Friederike Hanssen
 *
 */
public class ErrorInSequence {
	
	private String errorMessage;
	private int errorCode;
	private boolean isCritical;
	
	
	public ErrorInSequence(int errorCode,String errorMessage, boolean isCritical){
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

