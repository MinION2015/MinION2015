package error;

/**
 * This is a subclass of Exception, so that we can throw back our own errors
 * 
 * @author kevinlindner
 *
 */
public class MyException extends Exception{

	  private int errorCode;
	  private String errorMessage;
	  private boolean isCriticalError;

	  /**
	   * the created exception gets the values of the error
	   * @param error
	   */
	  public MyException(ErrorCodes error) {
	    this.errorMessage = error.getMessage();
	    this.errorCode = error.getIdentity();
	    this.isCriticalError = error.getIsCritical();
	  }
	  
	 

	public boolean isCriticalError() {
		return isCriticalError;
	}



	public int getErrorCode() {
	    return errorCode;
	  }

	  public String getErrorMessage() {
	    return errorMessage;
	  }
}
