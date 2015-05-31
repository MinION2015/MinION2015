package reader;
/**
 * 
 * @author Friederike Hanssen
 *
 */
public class Sequence {
	
	private String header;
	private String sequence;
	
	public Sequence(String header, String sequence){
		this.header = header;
		this.sequence = sequence;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	
}
