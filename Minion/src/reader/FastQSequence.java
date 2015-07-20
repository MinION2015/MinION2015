package reader;

/**
 * 
 * @author Friederike
 *
 */
public class FastQSequence implements Sequence{

		private String header;
		private String sequence;
		private String addInfo;
		private String score;
		
		public FastQSequence(String header, String sequence, String addInfo, String score){
			this.header = header;
			this.sequence = sequence;
			this.addInfo = addInfo;
			this.score = score;
		}
		
		
		@Override
		public String getHeader() {
			// TODO Auto-generated method stub
			return header;
		}

		@Override
		public String getSequence() {
			// TODO Auto-generated method stub
			return sequence;
		}

		public String getAddInfo(){
			return addInfo;
		}
		@Override
		public String getScore() {
			// TODO Auto-generated method stub
			return score;
		}

		@Override
		public int lengthOfSequence() {
			// TODO Auto-generated method stub
			return sequence.length();
		}
		
		public int lengthOfScore(){
			return score.length();
		}


		@Override
		public void setSequence(String seq) {
			this.sequence = seq;
		}


		@Override
		public void setScore(String score) {
			// TODO Auto-generated method stub
			this.score = score;
		}


		public void setHeader(String header) {
			this.header = header;
		}
	}



