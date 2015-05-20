package error;

/**
 * This is a subclass of Exception, so that we can throw back our own errors
 * 
 * @author kevinlindner
 *
 */
public class MyException extends Exception{

	  private int errorCode;
	  private String errorMsg;
	  private boolean isCriticalError;

	  /**
	   * the created exception gets the values of the error
	   * @param error
	   */
	  public MyException(ErrorCodes error) {
	    this.errorMsg = error.getMessage();
	    this.errorCode = error.getIdentity();
	    this.isCriticalError = error.getCritical();
	  }
	  
	  public boolean isCriticalError() {
		return isCriticalError;
	}

	public int getErrorCode() {
	    return errorCode;
	  }

	  public String getErrorMessage() {
	    return errorMsg;
	  }
}
