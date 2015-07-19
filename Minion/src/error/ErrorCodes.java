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
	INCORRECT_SEQUENCE_SCORE(2004,"The scoring line is faulty",true),
	
	
	NO_SEQUENCE_NAME(2010, "There is no sequence name.", false),
	StringIndexOutOfBoundsException(2011, "There are paragraphs in the LengthDistributionFile!", true),
	
	PORE_HAS_DIED(3000,"Pore has died.",true),
	PORE_INITIATED(3001,"The Pore has started sequencing.",true),
	PORE_FINISHED(3002,"The Pore has finished without problems.",true),
	PORE_NO_CAPABLE_SEQUENCE_LENGTH(3003,"Die Sequenz ist zu kurz!", true),
	
	FLOWCELL_EMPTY(4000,"Flowcell is empty.",true),
	FLOWCELL_CONTAINS_PORES(4001,"Current number of pores in flowcell: ",false),
	FLOWCELL_Invalid_Pore_Status(4002,"Invalide Pore Status ",false),
	FLOWCELL_ONLY_CONTAINS_DEAD_PORES(4003,"All pores are dead",true),
	FLOWCELL_OUTPUT_FORMAT_COULD_NOT_BE_CREATED(4004,"Flowcell output format could not be created.", true),
	
	CONTROLLER_NOT_RUNNING(5000,"Program doesn't run.",true),
	CONTROLLER_NOT_PAUSING(5001,"Program deosn't pause",true),
	CONTROLLER_NOT_RESUMING(5002,"Program doesn't resume",true),
	CONTROLLER_NOT_STOPPING(5003,"Program doesn't stop",true),	
	CONTROLLER_IS_PAUSING(5004,"Program is currently paused",true),
	LENGTHRATE_NO_INTEGER_FOUND(6000,"There was no integer found at this place when parsing the length input file",true);
	
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

