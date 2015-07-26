package Basecalling;

import error.Chance;
import error.MyException;
import Basecalling.BasecallingErrorRate;


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

/**
 * changes the sequences and scores based on the parameters in basecallingErrorRate
 * @param seqType information if fasta to fasta /fasta to fastq / fastq to fastq
 * @param seq origin seq	
 * @param scoreI origin score
 * @return changed seq and score
 */
	public static String[] applyErrorBasecalling(String seqType,String seq,String scoreI) {
		
		String err = "";
		String score ="";
		String[] output = new String[2];
		String cache = "";
		double scoreCache;
		double cachetrash;
		int row;
		int column;
		double prob;
		int rand;
		switch(seqType){
		case "fasta":
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
			break;
		default:
			if(scoreI.length()<2){
			for(int i=0;i<seq.length();i++)
				scoreI=scoreI.concat("!");
			}
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
					prob = Chance.getRand()*0.2;
					cache=String.valueOf((char)((Math.log(prob)*-10)+33));
					score=score.concat(cache);
					do{
						prob = Chance.getRand();
						if(prob<=BasecallingErrorRate.getInsertionExtProb()){
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
				}else{
					err = err.concat(cache);
				row=BasecallingErrorRate.getRow(seq.charAt(i));
				column=BasecallingErrorRate.getRow(cache.charAt(0));
				scoreCache=(int) scoreI.charAt(i)-33;
				scoreCache=Math.pow(10,scoreCache/-10);
				cachetrash=BasecallingErrorRate.getValue(row, column);
				if(column!=0)
				cachetrash=cachetrash-BasecallingErrorRate.getValue(row, column-1);
				if(row!=column)
				cachetrash+=1;
				scoreCache=scoreCache*cachetrash;
				scoreCache=(Math.log(scoreCache)*-10)+33;
				score=score.concat(String.valueOf((char)scoreCache));
			}}
			
			
		}
		
		
		
		output[0] = err;
		output[1] = score;
		return output;
	}
	
	

	/**
	 * changes the base / insert a new one / deletes this one 
	 * @param letter origin base
	 * @return changed base or add/del for insertion/deletion
	 */
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
/*
	public static void main(String args[]){
		try {
			BasecallingErrorRate basecallingError = new BasecallingErrorRate("/Users/kevinlindner/Documents/null.setting");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String inputSeq="CCCCCC";
		String inputScore="######";
		String[] output = new String[2];
		try {
			output = applyErrorBasecalling("2",inputSeq, inputScore);
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<output[0].length();i++)
			System.out.print(output[0].charAt(i));
		System.out.println();
		for(int i=0;i<output[1].length();i++)
			System.out.print(output[1].charAt(i));
		
	}*/

}
