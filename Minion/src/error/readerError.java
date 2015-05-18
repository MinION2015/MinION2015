package error;

public class readerError {

	//!!! leerzeichen gelten als fehler
	public static int sequenceCheck(String name, String sequence) throws MyException{
		if(name=="")
			throw new MyException(ErrorCodes.NO_SEQUENCE_NAME);
		if(sequence=="")
			throw new MyException(ErrorCodes.NO_SEQUENCE);
		for(int i=0;i<sequence.length();i++){
			if((int) sequence.charAt(i)!=97&&(int) sequence.charAt(i)!=99&&(int) sequence.charAt(i)!=103&&(int) sequence.charAt(i)!=116){
				if((int) sequence.charAt(i)!=97&&(int) sequence.charAt(i)!=99&&(int) sequence.charAt(i)!=103&&(int) sequence.charAt(i)!=116){
					throw new MyException(ErrorCodes.CORRUPTED_SEQUENCE);
				}else
					
			}
				
		}
		
		
		
		return 0;
	}
	
	
	
	
}
