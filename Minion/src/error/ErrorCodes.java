package error;
/**
 * This enum file contains all defined Errors with their ids and messages 
 * @author kevinlindner
 *
 */
public enum ErrorCodes {
	//definition of all Errors
	
	NO_ERROR(-1,null,false),
	BAD_FILETYPE(1001, "The filetype is wrong!", true),
	
	NO_SEQUENCE(2000, "There is no sequence!", true),
	CORRUPTED_SEQUENCE(2001, "The sequence has an illegal character in it!", true),
	LOWERCASE_SEQUENCE(2002, "There is at least one lowercase nucleotide in the sequence.", false),
	GAPPED_SEQUENCE(2003,"This sequence contains gaps!",true),
	
	
	NO_SEQUENCE_NAME(2010, "There is no sequence name.", false),
	StringIndexOutOfBoundsException(2011, "There are paragraphs in the LengthDistributionFile!", true),
	
	PORE_HAS_DIED(3000,"Pore has died.",true),
	PORE_INITIATED(3001,"The Pore has started sequencing.",true),
	PORE_FINISHED(3002,"The Pore has finished without problems.",true),
	Pore_NOCAPABLESEQUENCELENGTH(3003,"Die Sequenz ist zu kurz!", true),
	
	FLOWCELL_EMPTY(4000,"Flowcell is empty.",true),
	FLOWCELL_CONTAINS_PORES(4001,"Current number of pores in flowcell: ",false);
	
	private final int identity;
	private final String message;
	private final boolean isCritical;
	
	/**
	 * if u dont understand this constructor u will have a hard time 
	 * @param identity the id of the errors
	 * @param message the message of the errors
	 */
	ErrorCodes(int identity, String message, boolean isCritical) {
		this.identity = identity;
		this.message = message;
		this.isCritical = isCritical;
	}
	
	public int getIdentity(){
		return this.identity;
	}

	public String getMessage(){
		return this.message;
	}
	
	public boolean getIsCritical(){
		return this.isCritical;

	}
	
}

