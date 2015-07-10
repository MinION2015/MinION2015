package reader;
/**
 * 
 * @author Friederike Hanssen
 *Each sequence contains a header and the sequence, metadata won't be stored
 *Input: header, sequence, both of them are Strings
 *Output: getters
 */
public class FastASequence implements Sequence{
	
	private String header;
	private String sequence;
	
	public FastASequence(String header, String sequence){
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
	
	public int lengthOfSequence(){
		return sequence.length();
	}

	@Override
	public String getScore() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Test
	 */
//	public static void main(String args[]){
//		Sequence seq = new Sequence("me", "blub");
//		System.out.println(seq.getHeader());
//		System.out.println(seq.getSequence());
//		
//	}
}
