package controller;

import org.jfree.ui.RefineryUtilities;

import reader.FiletypeContainingSequences;
import reader.Sequence;
import error.Chance;
import guiStatistics.guiStatistics;


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
	private guiStatistics statistics;
	
	
	
	public Runner(Controller cd){
		this.cd = cd;
		initiliazeRun();
		currentNumberOfTicks = 0;
	}
	
	public void run(){
		statistics = new guiStatistics();
		try{	
			//Sequence seq = new FastASequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
//			checkIfPaused();
//			checkIfStoppedYet();
			while((currentNumberOfTicks < totalNumberOfTicks) && flowcell.getNumberOfAlivePores() > 0){
				int pos = Chance.getRandInt(0, inputFile.getSequence().size()-1);
				flowcell.tick(inputFile.getSequence().get(pos));
				
				if(currentNumberOfTicks % 100 == 0){//every 100 ticks the statistics are getting updated
					statistics.updateData(flowcell.getStates(),flowcell.getFlowcellOutput().getSequence().size());
					visualize(statistics);
				}
				
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
			//System.err.println("Run method in Runner: "+e.getMessage());
			//TODO 
			e.printStackTrace();
		}
	}

	private void visualize(guiStatistics statistics) {
		//final guiStatistics statistics = new guiStatistics();
		statistics.pack();
        RefineryUtilities.centerFrameOnScreen(statistics);
        statistics.setVisible(true);
        
		
	}

	private void initiliazeRun() {
		this.inputFile = cd.getInputFile();
		this.flowcell = cd.getFlowcell();
		this.totalNumberOfTicks = cd.getOptions().getTotalNumberOfTicks();
		this.outputFile = cd.getOutputFile();
		this.statistics = cd.getStatistic();
	}
	
}	
