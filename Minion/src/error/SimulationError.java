package error;


/**
 * 
 * @author Friederike Hanssen
 * A SimulationError object is used to apply a given error rate to a sequence. So far it contains an applyErrorBasecalling function. It will apply base calling errors on a given sequence
 * Input: SimulationError has an empty constructor
 * Output: a new sequence with an applied error model
 */
public class SimulationError{

	public SimulationError(){
		
	}

	public String applyErrorBasecalling(String seq, int basecalling) {
		BasecallingErrorRate rate = new BasecallingErrorRate(basecalling);
		String err = "";
		for(int i = 0; i < seq.length();i++){
			err = err.concat(callBase(seq.charAt(i),rate));	
			//Test: expected: t
			//err = err.concat("t");
		}
		
		return err;
	}

	
	private String callBase(char letter, BasecallingErrorRate rate){
		
		double prob = Chance.getRand();
		int row = rate.getRow(letter);
		//Test: expected for letter A: stays A
		//double prob = 0.45;
		//double prob = 0.8;
		
		
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
//		SimulationError base = new SimulationError();
//		//BasecallingErrorRate already tested and working
//		BasecallingErrorRate err = new BasecallingErrorRate(1);
//		base.callBase('A', err);
//		//use 0.45 as prob, expected : A stays A 
//		//use 0.8 as prob, expected: A mutates to C
//		String output = base.applyErrorBasecalling("A",1);
//		System.out.println(output);
//
//	}
//	

}
