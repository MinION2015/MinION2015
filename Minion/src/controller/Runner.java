package controller;

import reader.FiletypeContainingSequences;
import reader.Sequence;
import error.Chance;


/**
 * 
 * @author Friederike Hanssen
*/
public class Runner extends Thread{
	
	private  Controller cd;
	private FiletypeContainingSequences inputFile;
	private FiletypeContainingSequences outputFile;
	private Flowcell flowcell;
	private int totalNumberOfTicks;
	private int currentNumberOfTicks;
	
	
	
	public Runner(Controller cd){
		this.cd = cd;
		initiliazeRun();
		currentNumberOfTicks = 0;
	}
	
	public void run(){
		
		try{	
			//Sequence seq = new FastASequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
//			checkIfPaused();
//			checkIfStoppedYet();
			while((currentNumberOfTicks < totalNumberOfTicks) && flowcell.getNumberOfAlivePores() > 0){
				int pos = Chance.getRandInt(0, inputFile.getSequence().size()-1);
				flowcell.tick(inputFile.getSequence().get(pos));
				if(cd.getOptions().getWriteInFileOption().equals("Real-Time")){
					try{
						flowcell.getFlowcellOutput().writeInFile(cd.getOptions().getOutputFilename());
						Thread.sleep(cd.getOptions().getDurationOfTick());
					}catch(Exception e){
						System.err.println(e.getMessage());
					}
				}else if(cd.getOptions().getWriteInFileOption().equals("Write all")){
					try{
						for(Sequence s : flowcell.getFlowcellOutput().getSequence()){
							outputFile.addSeq(s);
						}
						Thread.sleep(cd.getOptions().getDurationOfTick());
					}catch(Exception e){
						System.err.println(e.getMessage());
					}
				}
				currentNumberOfTicks++;
				
			}
			if(cd.getOptions().getWriteInFileOption().equals("Write all")){
				outputFile.writeInFile(cd.getOptions().getOutputFilename());
			}
			
			System.out.println("Run was executed without throwing errors");
		}catch(Exception e){
			System.err.println("Run method in Controller2: "+e.getMessage());
		}
	}

	private void initiliazeRun() {
		this.inputFile = cd.getInputFile();
		this.flowcell = cd.getFlowcell();
		this.totalNumberOfTicks = cd.getOptions().getTotalNumberOfTicks();
		this.outputFile = cd.getOutputFile();
		
	}
	
}	
