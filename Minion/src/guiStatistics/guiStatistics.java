package guiStatistics;

import java.awt.Font;



import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * Holds a ChartPanel
 */
public class guiStatistics extends ApplicationFrame {

	private ChartPanel chartPanel;
	private double[][] porestates = new double[24][5];
	private double[] reads = new double[24];
	private int tick;
    /**
     * @author Daniel and Albert
     * @input gets the porestates containing the states of teh pore, the reads, containing the reads that are made at a special tick and the tick 
     * 		  are at the moment
     * @function generates two chartPanels
     */
    public guiStatistics(double[] porestates, double reads) {

        super("Minion Simulation");
        
        this.porestates[tick] = porestates;
        this.reads[tick] = reads;
        this.tick = 0;

        // add the chart to a panel...
        chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }

    /**
     * Creates a dataset. 
     * @input gets the reads and the ticks
     * @return A dataset
     * @function computes a database containing the reads due the ticks. Every update the whole database is computed new
     */
    public CategoryDataset createDataset1() {

        final DefaultCategoryDataset result = new DefaultCategoryDataset();
        
        //just need to add some more values. rest is filled with null
        final String series1 = "Read-Lengths";

        for(Integer i = 0; i < tick; i++)
        {
        	result.addValue(reads[i], series1, i.toString());
        }
        for(Integer i = tick; i < reads.length;i++)
        {
        	result.addValue(null, series1, i.toString());
        }


        return result;

    }

    /**
     * Creates a dataset. 
     * @input gets the porestates and the ticks
     * @return A dataset
     * @function computes a database containing the porestates due the ticks. Every update the whole database is computed new
     */
    public CategoryDataset createDataset2() {
//		porestates[i]
//    	[0] Running, [1]Bored, [2] Dead,[3] Finished, [4] Sleeping,[5]
        final DefaultCategoryDataset result = new DefaultCategoryDataset();


        
        String[] states = {"Running","Bored","Dead","Finished","Sleeping"};
        
        //add every tick all the states data stored in porestates.
        for(Integer i = 0; i < tick; i++)
        {
        	for(int j = 0; j < porestates[i].length; j++)
        	{
        		result.addValue(porestates[i][j], states[j], i.toString());
        	}
        	
        }
        //fill the rest of the chart with null
        for(Integer i = tick; i < porestates.length;i++)
        {
        	result.addValue(null, "remain", i.toString());
        }
     
        
        return result;

    }

    /**
     * @author Daniel
     * @input porestates, reads, ticks
     * @function calls the createdatabase functions. Gets back 2 databases and compute a LineAndShapeRenderer and a StackedBarRenderer. Mereges them
     * 	       into one CombinedDomainCategoryPlot.
     *
     * @return A chart.
     */
    private JFreeChart createChart() {

        final CategoryDataset dataset1 = createDataset1();
        final NumberAxis rangeAxis1 = new NumberAxis("Length of Reads");
        rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        final LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
        renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        final CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1, renderer1);
        subplot1.setDomainGridlinesVisible(true);
        
        final CategoryDataset dataset2 = createDataset2();
        final NumberAxis rangeAxis2 = new NumberAxis("Number of Pores");
        rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        final StackedBarRenderer renderer11 = new StackedBarRenderer();
        renderer11.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        final CategoryPlot subplot2 = new CategoryPlot(dataset2, null, rangeAxis2, renderer11);
        subplot2.setDomainGridlinesVisible(true);

        final CategoryAxis domainAxis = new CategoryAxis("Time");
        final CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);

        plot.add(subplot1, 2);
        plot.add(subplot2, 1);
        
        final JFreeChart result = new JFreeChart(
            "Combined Domain Category Plot Demo",
            new Font("SansSerif", Font.BOLD, 12),
            plot,
            true
        );
  //      result.getLegend().setAnchor(Legend.SOUTH);
        return result;

    }

    /**
     * Test
     *
     * @param args  ignored.
     */
//    public static void main(final String[] args) {
//    	//reads.length and porestates[0].length must be the same
//        double[] porestates = {1,1,1,1,2};
//        double reads = 3;
//        
//    	final guiStatistics demo = new guiStatistics(porestates, reads);
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//        
//        for(int i = 0; i< 10; i++)
//        {
//        	demo.updateData(porestates, reads+i);
//        }
//
//    }
    
    /**
     * @author Daniel
     * @input porestates, reads, ticks
     * @function creates a guiStatisticsObject and visualizes it
     * 
     */
    public void createguiStatistics(double[] porestates, double reads)
    {
    	final guiStatistics demo = new guiStatistics(porestates, reads);
    	demo.pack();
    	 RefineryUtilities.centerFrameOnScreen(demo);
    	 demo.setVisible(true);
    }
    
    /**
     * @author Daniel
     * @param porestates 
     * @param reads
     * @param tick
     * @function updates the chart by computing a new one everytime.
     */
    public void updateData(double[] porestates, double reads)
	   {
    			this.tick++;
    			this.porestates[tick] = porestates;
    			this.reads[tick] = reads;
		   		JFreeChart JFreetemp = createChart();
//		   		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		        this.chartPanel.setChart(JFreetemp);
		        chartPanel.updateUI();

	   }
    
    

}
