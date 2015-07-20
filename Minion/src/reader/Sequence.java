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
	
	public void setSequence(String seq);
	public void setScore(String score);
	public void setHeader(String header);
	
	public int lengthOfSequence();
	
}
