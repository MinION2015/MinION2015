package Basecalling;

import error.Chance;
import error.MyException;


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
	//String seqtype determins fasta/fastq
	public static String[] applyErrorBasecalling(String seqType,String seq,String score) throws MyException {
		
		String err = "";
		String generatedScore ="";
		String[] output = new String[2];
		
		String cache = "";
		double prob;
		int rand;
		for(int i = 0; i < seq.length();i++){
			cache = callBase(seq.charAt(i));
			if(cache=="add"){
				i--;
				rand = Chance.getRandInt(1, 4);
				switch(rand){
				case 1: err = err.concat("A");
				break;
				case 2: err = err.concat("T");
				break;
				case 3: err = err.concat("G");
				break;
				case 4: err = err.concat("C");
				break;
				}
				do{
					prob = Chance.getRand();
					if(prob<=BasecallingErrorRate.getInsertionExtProb()){
						i--;
						rand = Chance.getRandInt(1, 4);
						switch(rand){
						case 1: err = err.concat("A");
						break;
						case 2: err = err.concat("T");
						break;
						case 3: err = err.concat("G");
						break;
						case 4: err = err.concat("C");
						break;
						}
					}
				}while(prob<=BasecallingErrorRate.getInsertionExtProb());
			}else if(cache=="del"){
				do{
					prob = Chance.getRand();
					if(prob<=BasecallingErrorRate.getDeletionExtProb()){
						i++;
					}
				}while(prob<=BasecallingErrorRate.getDeletionExtProb());
			}else
				err = err.concat(cache);
			//Test: expected: t
			//err = err.concat("t");
		}
		output[0] = err;
		output[1] = generatedScore;
		return output;
	}

	
	private static String callBase(char letter){
		
		double prob = Chance.getRand();
		double probBase = Chance.getRand();
		int row = BasecallingErrorRate.getRow(letter);
		//Test: expected for letter A: stays A
		//double prob = 0.45;
		//double prob = 0.8;
		
		if(prob <= BasecallingErrorRate.getInsertionProb()){
			return "add";
		}else if(prob > BasecallingErrorRate.getInsertionProb() && prob <= BasecallingErrorRate.getDeletionProb()){
			return "del";
		}else{
			if(probBase <= BasecallingErrorRate.getValue(row, 0)){
				//System.out.println(rate.getBase(0));
				return BasecallingErrorRate.getBase(0);
			}else if(probBase > BasecallingErrorRate.getValue(row, 0) && probBase <= BasecallingErrorRate.getValue(row, 1)){
				//System.out.println(rate.getBase(1));
				return BasecallingErrorRate.getBase(1);
			}else if(probBase > BasecallingErrorRate.getValue(row, 1) && probBase <= BasecallingErrorRate.getValue(row, 2)){
				//System.out.println(rate.getBase(2));
				return BasecallingErrorRate.getBase(2);
			}else if(probBase > BasecallingErrorRate.getValue(row, 2)){
				//System.out.println(rate.getBase(3));
				return BasecallingErrorRate.getBase(3);
			}
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
//		//use 0.45 as probBase, expected : A stays A 
//		//use 0.8 as probBase, expected: A mutates to C
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
