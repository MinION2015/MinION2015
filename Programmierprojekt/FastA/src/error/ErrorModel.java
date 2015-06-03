package error;
/**
 * 
 * @author Friederike Hanssen
 *Interface to apply an error to a sequence represented as string.
 */
public interface ErrorModel {
	
	/**
	 * So far the parameter are coded as integers.
	 * The sequence is than returned  with a certain error rate as a string.
	 * @param seq
	 * @param basecalling
	 * @return
	 */
	public String apply(String seq, int parameter);

}
