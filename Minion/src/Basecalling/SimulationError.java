package Basecalling;

import error.Chance;


/**
 * 
 * @author Kevin Lindner & Friederike Hanssen
 * S started  to make class static, not sure how to test, as the whole class is not functioning for me with the settingFilename etc.(Friederike)
 * @Functionailty A SimulationError object is used to apply a given error rate to a sequence. So far it contains an applyErrorBasecalling function. It will apply base calling errors on a given sequence
 * @Input: SimulationError has an empty constructor
 * @Output: a new sequence with an applied error model
 */
public class SimulationError{

	public SimulationError(){
		
	}

	public static String applyErrorBasecalling(String seq) throws Exception {
		
		String err = "";
		for(int i = 0; i < seq.length();i++){
			
			err = err.concat(callBase(seq.charAt(i)));	
			//Test: expected: t
			//err = err.concat("t");
		}
		
		return err;
	}

	
	private static String callBase(char letter){
		
		double prob = Chance.getRand();
		int row = BasecallingErrorRate.getRow(letter);
		//Test: expected for letter A: stays A
		//double prob = 0.45;
		//double prob = 0.8;
		
		
		if(prob <= BasecallingErrorRate.getValue(row, 0)){
			//System.out.println(rate.getBase(0));
			return BasecallingErrorRate.getBase(0);
		}else if(prob > BasecallingErrorRate.getValue(row, 0) && prob <= BasecallingErrorRate.getValue(row, 1)){
			//System.out.println(rate.getBase(1));
			return BasecallingErrorRate.getBase(1);
		}else if(prob > BasecallingErrorRate.getValue(row, 1) && prob <= BasecallingErrorRate.getValue(row, 2)){
			//System.out.println(rate.getBase(2));
			return BasecallingErrorRate.getBase(2);
		}else if(prob > BasecallingErrorRate.getValue(row, 2) && prob <=BasecallingErrorRate.getValue(row, 3)){
			//System.out.println(rate.getBase(3));
			return BasecallingErrorRate.getBase(3);
		}else if(prob > BasecallingErrorRate.getValue(row, 3) && prob <=BasecallingErrorRate.getValue(row, 4)){
			//System.out.println(rate.getBase(4));
			return BasecallingErrorRate.getBase(4);
		}
		return "";
	}

	/**
	 * Tests
	 */
//	public static void main(String args[]){
//		
//		SimulationError base = new SimulationError();
//		
//		base.callBase('A');
//		//use 0.45 as prob, expected : A stays A 
//		//use 0.8 as prob, expected: A mutates to C
//		String output="";
//		try {
//			output = base.applyErrorBasecalling("A",1,"");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(output);
//
//	}
	

}
