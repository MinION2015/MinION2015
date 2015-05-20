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
	
	
	NO_SEQUENCE_NAME(2010, "There is no sequence name.", false);
	
	
	private final int identity;
	private final String message;
	private final boolean critical;
	
	/**
	 * if u dont understand this constructor u will have a hard time 
	 * @param identity the id of the errors
	 * @param message the message of the errors
	 */
	ErrorCodes(int identity, String message, boolean critical) {
		this.identity = identity;
		this.message = message;
		this.critical = critical;
	}
	
	public int getIdentity(){
		return this.identity;
	}

	public String getMessage(){
		return this.message;
	}
	
	public boolean getCritical(){
		return this.critical;

	}
	
}
