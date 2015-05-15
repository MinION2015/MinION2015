package reader;


public class FastQEntry {
	
	private String identity;
	private String sequence;
	private String addInfo;
	private String score;
	
	public FastQEntry(String identity, String sequence, String addInfo, String score){
		this.identity = identity;
		this.sequence = sequence;
		this.addInfo = addInfo;
		this.score =score;
		
	}

	public String getIdentity() {
		return identity;
	}

	public String getSequence() {
		return sequence;
	}

	public String getAddInfo() {
		return addInfo;
	}

	public String getScore() {
		return score;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}

	public void setScore(String score) {
		this.score = score;
	}

}

