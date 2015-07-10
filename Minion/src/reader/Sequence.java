package reader;
/**
 * 
 * @author Friederike
 *
 */
public interface Sequence {

	public String getHeader();
	public String getSequence();
	public String getScore();
	
	public int lengthOfSequence();
}
