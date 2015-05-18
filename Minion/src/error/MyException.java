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

	  /**
	   * the created exception gets the values of the error
	   * @param error
	   */
	  public MyException(ErrorCodes error) {
	    this.errorMsg = error.getMsg();
	    this.errorCode = error.getId();
	  }
	  
	  public int getErrorCode() {
	    return errorCode;
	  }

	  public String getErrorMsg() {
	    return errorMsg;
	  }
}
