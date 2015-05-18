package error;

public class readerError {

	//!!! leerzeichen gelten als fehler
	public static int sequenceCheck(String name, String sequence) throws MyException{

		if(sequence=="")
			throw new MyException(ErrorCodes.NO_SEQUENCE);
		for(int i=0;i<sequence.length();i++){
			if((int) sequence.charAt(i)!=65&&(int) sequence.charAt(i)!=67&&(int) sequence.charAt(i)!=71&&(int) sequence.charAt(i)!=85){
				if((int) sequence.charAt(i)!=97&&(int) sequence.charAt(i)!=99&&(int) sequence.charAt(i)!=103&&(int) sequence.charAt(i)!=116)
					throw new MyException(ErrorCodes.CORRUPTED_SEQUENCE);
			}
		}
		for(int i=0;i<sequence.length();i++){
			if((int) sequence.charAt(i)!=65&&(int) sequence.charAt(i)!=67&&(int) sequence.charAt(i)!=71&&(int) sequence.charAt(i)!=85){
				if((int) sequence.charAt(i)==97||(int) sequence.charAt(i)==99||(int) sequence.charAt(i)==103||(int) sequence.charAt(i)==116)
					throw new MyException(ErrorCodes.LOWERCASE_SEQUENCE);
			}
		}
		if(name=="")
			throw new MyException(ErrorCodes.NO_SEQUENCE_NAME);
		
		
		
		return 0;
	}
	
	
	
	
}
