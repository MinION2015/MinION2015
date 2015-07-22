package controller;

import error.Chance;
import gui.GUIOptions;
import guiStatistics.guiStatistics;

import org.jfree.ui.RefineryUtilities;

import reader.FiletypeContainingSequences;
import reader.Sequence;


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
		this.currentNumberOfTicks = 0;
	}
	
	public void run(){
		statistics = new guiStatistics();
		boolean hasAlivePores = true;
		try{	
			//Sequence seq = new FastASequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
//			checkIfPaused();
//			checkIfStoppedYet();
			int counterStat = 0;
			while((currentNumberOfTicks < totalNumberOfTicks)){ //&& hasAlivePores){
				int pos = Chance.getRandInt(0, inputFile.getSequence().size()-1);
				flowcell.tick(inputFile.getSequence().get(pos));
				
//				if(currentNumberOfTicks % (totalNumberOfTicks/10) == 0 && counterStat < 9){//every 100 ticks the statistics are getting updated
//					statistics.updateData(flowcell.getStates(),flowcell.getFlowcellOutput().getSequence().size());
//					visualize(statistics);
//					counterStat++;
//				}
//				
				if(cd.getOptions().getWriteInFileOption().equals("Real-Time")){
					try{
						flowcell.getFlowcellOutput().writeInFile(cd.getOptions().getOutputFilename());
						System.out.println("flowcell size when write in file: "+flowcell.getFlowcellOutput().getSequence().size());
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
				if(flowcell.getNumberOfAlivePores() <= 0){
					hasAlivePores = false;
				}
				if(counterStat == 9){
					statistics.updateData(flowcell.getStates(),flowcell.getFlowcellOutput().getSequence().size());
					visualize(statistics);
				}
				
			}
			
			
			if(cd.getOptions().getWriteInFileOption().equals("Write all")){
				outputFile.writeInFile(cd.getOptions().getOutputFilename());
			}
			
			System.out.println("Run was executed without throwing errors");
		}catch(Exception e){
			System.err.println("Run method in Runner throws error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private void visualize(guiStatistics statistics) {
		statistics.pack();
        RefineryUtilities.centerFrameOnScreen(statistics);
        statistics.setVisible(true);
        
		
	}
	
	public void stopped(){
		this.currentNumberOfTicks = this.totalNumberOfTicks;
	}

	private void initiliazeRun() {
		this.inputFile = cd.getInputFile();
		this.flowcell = cd.getFlowcell();
		this.totalNumberOfTicks = cd.getOptions().getTotalNumberOfTicks();
		this.outputFile = cd.getOutputFile();
		this.statistics = cd.getStatistic();
	}
	
//	public static void main(String[] args){
//		
//		GUIOptions op = new GUIOptions("src/example4.fasta","TestRunner.txt","Real-Time","fasta","C:/Users/Friederike/University/Fourth Semester/Programmierprojekt/git/MinION2015/Minion/setting files/default.setting",1,1,100,10,100,10);
//		Controller cd = new Controller(op);
//		Runner r = new Runner(cd);
//	}
}	
