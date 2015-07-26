package controller;

import error.*;
import gui.GUIOptions;
import guiStatistics.guiStatistics;

import org.jfree.ui.RefineryUtilities;

import reader.*;


/**
 * 
 * @author Friederike Hanssen
 * @functionality Runner is a thread executing the run function and displaying the graphics
 * @input controller
 * @output prints sequences to file, displays results as graphic
*/
public class Runner extends Thread{
	
	private  Controller cd;
	private FiletypeContainingSequences inputFile;
	private FiletypeContainingSequences outputFile;
	private Flowcell flowcell;
	private int totalNumberOfTicks;
	private int currentNumberOfTicks;
	private boolean hasAlivePores;
	

	private guiStatistics statistics;
	private int counterStat = 0;
	
	
	
	public Runner(Controller cd){
		this.cd = cd;
		initiliaze();
		this.currentNumberOfTicks = 0;
		if(flowcell.getNumberOfAlivePores() > 0){
			this.hasAlivePores = true;
		}else{
			this.hasAlivePores = false;
		}
		 
		
	}

	
	/**
	 * Chooses randomly a sequence from the input file and applies the tick function it. The output is collected in an output file. If the user picked realtime the flowcell output is written to the continuously, otherwise the outputfile is written out in the end. For each totalnumber/10th tick a new statisrtics object is displayed.
	 * A last statistivs object is always displayed. This can either happen because the time is up or because the flowcell doesn't have any alive pores left.
	 */
	public void run(){
		
		this.statistics = new guiStatistics();
		
		try{
			
			//Sequence seq = new FastASequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
			
			while((currentNumberOfTicks < totalNumberOfTicks) && hasAlivePores){
				
				int pos = Chance.getRandInt(0, inputFile.getSequence().size()-1);
				flowcell.tick(inputFile.getSequence().get(pos));
				for(Sequence s : flowcell.getFlowcellOutput().getSequence()){
					outputFile.addSeq(s);
				}
				
				if(cd.getOptions().getWriteInFileOption().equals("Real-Time")){
					try{
						flowcell.getFlowcellOutput().writeInFile(cd.getOptions().getOutputFilename());
					}catch(Exception e){
						System.err.println(e.getMessage());
					}
				}
				Thread.sleep(cd.getOptions().getDurationOfTick());
				
				if(flowcell.getNumberOfAlivePores() == 0){
					hasAlivePores = false;
					System.err.println("All pores were dead at tick: "+currentNumberOfTicks);
				}
				if(currentNumberOfTicks % (totalNumberOfTicks/10) == 0 && counterStat < 9 && hasAlivePores){//every maxNUmberfTicks/10 the statistics are getting updated
					statistics.updateData(flowcell.getStates(),flowcell.getFlowcellOutput().getSequence().size());
					visualize(statistics);
					counterStat++;
				}
				//print out data from last tick, either when the run is over or when all pores are dead
				if(currentNumberOfTicks == totalNumberOfTicks || !hasAlivePores){
					statistics.updateData(flowcell.getStates(),flowcell.getFlowcellOutput().getSequence().size());
					visualize(statistics);
				}
				currentNumberOfTicks++;
				
			}
			
			
			if(cd.getOptions().getWriteInFileOption().equals("Write all")){
				outputFile.writeInFile(cd.getOptions().getOutputFilename());
			}
			
			System.out.println("Outputfile size: "+outputFile.getErrorInSequence().size());
			this.cd.setOutputFile(outputFile);
			System.out.println("Run was executed without throwing errors");
			
		}catch(Exception e){
			System.err.println("Run method in Runner throws error: "+e.getMessage());
			e.printStackTrace();
		}
		
		
	}

	//works
	/**
	 * Displays the calculated statistics
	 * @param statistics
	 */
	private void visualize(guiStatistics statistics) {
		statistics.pack();
        RefineryUtilities.centerFrameOnScreen(statistics);
        statistics.setVisible(true);

	}
	
	//works
	/**
	 * Sets currentNumberOfTicks
	 */
	public void setCurrentNumberOfTicks(int ticks){
		this.currentNumberOfTicks = ticks;
	}

	//works
	/**
	 * Assigns all needed parameters needed to execute the run function: inputfile, outputfile, flowcell and statistics
	 */
	private void initiliaze() {
		this.inputFile = cd.getInputFile();
		this.flowcell = cd.getFlowcell();
		this.totalNumberOfTicks = cd.getOptions().getTotalNumberOfTicks();
		this.outputFile = cd.getOutputFile();
		this.statistics = cd.getStatistic();
	}
	
	public int getCurrentNumberOfTicks() {
		return currentNumberOfTicks;
	}
//	public static void main(String[] args){
//		
//		GUIOptions op = new GUIOptions("src/example4.fasta","TestRunner.txt","Real-Time","fasta","C:/Users/Friederike/University/Fourth Semester/Programmierprojekt/git/MinION2015/Minion/setting files/default.setting",1,5,1000,10,100,10);
//		Controller cd = new Controller(op);
//		Runner r = new Runner(cd);
//		
////		//check initialze:
////		System.out.println(r.inputFile.getSequence().get(0).getSequence()); //should print first sequence from test file
////		System.out.println(r.flowcell.getNumberOfAlivePores()); //should be 1
////		System.out.println(r.totalNumberOfTicks); //100
////		Sequence seq = new FastASequence("me","GGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAAGGTTAAGCGACTAAGCGTACACGGTGGATGCCTAGGCAGTCAGAGGCGATGAAGGGCGTGCTAATCTGCGAAAAGCGTCGGTAAGCTGATATGAAGCGTTATAACCGACGATACCCGAATGGGGAAACCCAGTGCAATACGTTGCACTATCGTTAGATGAATACATAGTCTAACGAGGCGAACCGGGGGAACTGAAACATCTAAGTACCCCGAGGAAAAGAAATCAACCGAGATTCCCCCAGTAGCGGCGAGCGAACGGGGAGGAGCCCAGAGTCTGAATCAGTTTGTGTGTTAGTGGAAGCGTCTGGAAAGTCGCACGGTACAGGGTGATAGTCCCGTACACCAAAATGCACAGGCTGTGAACTCGATGAGTAGGGCGGGACACGTGACATCCTGTCTGAATATGGGGGGACCATCCTCCAAGGCTAAATACTCCTGACTGACCGATAGTGAACCAGTACCGTGAGGGAAAGGCGAAAAGAACCCCGGCGAGGGGAGTGAAATAGAACCTGAAACCGTGTACGTACAAGCAGTGGGAGCACCTTCGTGGTGTGACTGCGTACCTTTTGTATAATGGGTCAGCGACTTATATTTTGTAGCAAGGTTAACCGAATAGGGGAGCCGTAGGGAAACCGAGTCTTAACTAGGCGTCTAGTTGCAAGGTATAGACCCGAAACCCGGTGATCTAGCCATGGGCAGGTTGAAGGTTGGGTAACACTAACTGGAGGACCGAACCGACTAATGTTGAAAAATTAGCGGATGACTTGTGGTGGGGGTGAAAGGCCAATCAAACCGGGAGATAGCTGGTTCTCCCCGAAAGCTATTTAGGTAGCGCCTCGTGAACTCATCTTCGGGGGTAGAGCACTGTTTCGGCTAGGGGGCCATCCCGGCTTACCAAACCGATGCAAA");
////		try {
////			r.outputFile.addSeq(seq);
////		} catch (MyException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		System.out.println(r.outputFile.getSequence().get(0).getHeader()); // should print out 'me'
////		
//		//check run
//		r.run();
//		
//		//check stopped()
//		r.stopped();
//		System.out.println("current number Ofticks after stopped() (=100): " + r.currentNumberOfTicks);
//		
//		
//		
//	}
}	
