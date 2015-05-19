package reader;

/**
 * 
 * @author Friederike Hanssen
 *This class creates a new fasta entry containing of a name and a sequence. 
 *As methods it only contains getters and setters for the attributes.
 */
public class FastAEntry {
	private String identity;
	private String sequence;
	
	/**
	 * 
	 * @param identity: store everything written in the first line after >: database, ID, name, optional description etc
	 * @param sequence: stores the sequence
	 */
	public FastAEntry(String identity, String sequence){
	
		this.identity= identity;
		this.sequence = sequence;
	}


	public String getIdentity() {
		return identity;
	}


	public void setIdentity(String identity) {
		this.identity = identity;
	}


	public String getSequence() {
		return sequence;
	}


	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	

}