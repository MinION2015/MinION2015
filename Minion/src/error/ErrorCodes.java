package error;
/**
 * This enum file contains all defined Errors with their ids and messages 
 * @author kevinlindner
 *
 */
public enum ErrorCodes {
	//definition of all Errors
	
	

	
	
	
	NO_SEQUENCE(1000, "There is no sequence "),
	CORRUPTED_SEQUENCE(1001, "The sequence has a failure in it."),
	
	
	NO_SEQUENCE_NAME(1010, "There is no sequence name")
	
	
	
	
	
	
	;
	
	
	
	
	
	
	
	
	private final int id;
	private final String msg;
	
	/**
	 * if u dont understand this u will have a hard time 
	 * @param id the id of the errors
	 * @param msg the message of the errors
	 */
	ErrorCodes(int id, String msg) {
		this.id = id;
		this.msg = msg;
	}
	
	public int getId() {
		return this.id;
	}

	public String getMsg() {
		return this.msg;
	}
	
}
