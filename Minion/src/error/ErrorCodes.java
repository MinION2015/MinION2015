package error;
/**
 * This enum file contains all defined Errors with their ids and messages 
 * @author kevinlindner
 *
 */
public enum ErrorCodes {
	//definition of all Errors
	BAD_FILENAME(1000, "The filename is not legit!", true),
	BAD_FILETYPE(1001, "The filetype is wrong!", true),
	
	NO_SEQUENCE(2000, "There is no sequence!", true),
	CORRUPTED_SEQUENCE(2001, "The sequence has a illegal character in it!", true),
	LOWERCASE_SEQUENCE(2002, "There is at least one lowercase nucleotide in the sequence.", false),
	
	
	NO_SEQUENCE_NAME(2010, "There is no sequence name.", false)	
	;
	
	
	private final int id;
	private final String msg;
	private final boolean crit;
	
	/**
	 * if u dont understand this constructor u will have a hard time 
	 * @param id the id of the errors
	 * @param msg the message of the errors
	 */
	ErrorCodes(int id, String msg, boolean crit) {
		this.id = id;
		this.msg = msg;
		this.crit = crit;
	}
	
	public int getId(){
		return this.id;
	}

	public String getMsg(){
		return this.msg;
	}
	
	public boolean getCrit(){
		return this.crit;
	}
	
}
