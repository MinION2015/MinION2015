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
			err = err.concat(mutate(seq.charAt(i),rate));
		}
		
		return err;
	}
	
	
	
	
	private String mutate(char letter, ErrorRate rate){
	
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
	
//	public static void main(String args[]){
//	
//		ErrorBasecalling base = new ErrorBasecalling();
//		String err = base.apply("ACTGTGACGT",1);
//		System.out.println(err);
//
//	}
	

}
