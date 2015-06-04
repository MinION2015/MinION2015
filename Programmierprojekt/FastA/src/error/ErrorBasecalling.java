package error;


/**
 * 
 * @author Friederike Hanssen
 *
 */
public class ErrorBasecalling implements ErrorModel{

	public ErrorBasecalling(){
		
	}
	@Override
	public String apply(String seq, int basecalling) {
		// TODO Auto-generated method stub
		ErrorRate rate = new ErrorRate(basecalling);
		String err = "";
		for(int i = 0; i < seq.length();i++){
			err.concat(mutate(seq.charAt(i),rate));
		}
		return err;
	}
	
	
	
	
	private String mutate(char letter, ErrorRate rate){
		
		Chance rand = new Chance();
		double prob = rand.getRand(0,1);
		int row = rate.getRow(letter);
		
		if(prob <= rate.getValue(row, 0)){
			return rate.getBase(0);
		}else if(prob > rate.getValue(row, 0) && prob <= rate.getValue(row, 1)){
			return rate.getBase(1);
		}else if(prob > rate.getValue(row, 1) && prob <= rate.getValue(row, 2)){
			return rate.getBase(2);
		}else if(prob > rate.getValue(row, 2) && prob <= rate.getValue(row, 3)){
			return rate.getBase(3);
		}
		return "";
	}
	

}
