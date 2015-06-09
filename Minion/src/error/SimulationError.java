package error;


/**
 * 
 * @author Friederike Hanssen
 *A ErrorBAsecalling object is used to apply a given error rate to a sequence. 
 */
public class SimulationError{

	public SimulationError(){
		
	}

	public String applyErrorBasecalling(String seq, int basecalling) {
		BasecallingErrorRate rate = new BasecallingErrorRate(basecalling);
		String err = "";
		for(int i = 0; i < seq.length();i++){
			err = err.concat(callBase(seq.charAt(i),rate));
		}
		
		return err;
	}

	
	private String callBase(char letter, BasecallingErrorRate rate){
	
		double prob = Chance.getRand();
		int row = rate.getRow(letter);
		//System.out.println(prob+" " +row);
		
		if(prob <= rate.getValue(row, 0)){
			//System.out.println(rate.getBase(0));
			return rate.getBase(0);
		}else if(prob > rate.getValue(row, 0) && prob <= rate.getValue(row, 1)){
			//System.out.println(rate.getBase(1));
			return rate.getBase(1);
		}else if(prob > rate.getValue(row, 1) && prob <= rate.getValue(row, 2)){
			//System.out.println(rate.getBase(2));
			return rate.getBase(2);
		}else if(prob > rate.getValue(row, 2) && prob <= rate.getValue(row, 3)){
			//System.out.println(rate.getBase(3));
			return rate.getBase(3);
		}
		return "";
	}

	/**
	 * Tests
	 */
//	public static void main(String args[]){
//	
//		ErrorBasecalling base = new ErrorBasecalling();
//		String err = base.apply("ACTGTGACGT",1);
//		System.out.println(err);
//
//	}
	

}
