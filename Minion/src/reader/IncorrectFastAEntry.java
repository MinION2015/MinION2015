package reader;
/**
 * 
 * @author Friederike Hanssen
 *This class creates our error objects. They contain the current parsing position as well as all the information the viewer needs: error message, isCritical error and the code , 
 *in case the errorCode database needs to be accessed again
 */
public class IncorrectFastAEntry {

	private int positionParsed;
	private String errorMessage;
	private int errorCode;
	private boolean isCritical;
	
	
	public IncorrectFastAEntry(int positionParsed,String sequence, int errorCode,boolean isCritical){
		this.positionParsed = positionParsed;
		this.errorMessage = sequence;
		this.errorCode = errorCode;
		this.isCritical = isCritical;
	}


	public int getPositionParsed() {
		return positionParsed;
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
