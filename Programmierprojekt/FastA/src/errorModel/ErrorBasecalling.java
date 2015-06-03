package errorModel;
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
		String err = "";
		for(int i = 0; i < seq.length();i++){
			err.concat(mutate(seq.charAt(i),basecalling));
		}
		return err;
	}
	
	
	
	
	private String mutate(char letter,int basecalling){
		Chance rand = new Chance();
		
		return "";
	}
	

}
