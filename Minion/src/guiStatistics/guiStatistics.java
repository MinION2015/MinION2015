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

/**
 * Holds a ChartPanel
 */
public class guiStatistics extends ApplicationFrame {

	private ChartPanel chartPanel;
    /**
     * @author Daniel and Albert
     * @input gets the porestates containing the states of teh pore, the reads, containing the reads that are made at a special tick and the tick 
     * 		  are at the moment
     * @function generates two chartPanels
     */
    public guiStatistics(double[][] porestates, double[] reads, int tick) {

        super("Minion Simulation");

        // add the chart to a panel...
        chartPanel = new ChartPanel(createChart(porestates, reads, tick));
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }

    /**
     * Creates a dataset. 
     * @input gets the reads and the ticks
     * @return A dataset
     * @function computes a database containing the reads due the ticks. Every update the whole database is computed new
     */
    public CategoryDataset createDataset1(double[] reads, int tick) {

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
    public CategoryDataset createDataset2(double[][] porestates, int tick) {
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
     * @author Daniel and Albert
     * @input porestates, reads, ticks
     * @function calls the createdatabase functions. Gets back 2 databases and compute a LineAndShapeRenderer and a StackedBarRenderer. Mereges them
     * 	       into one CombinedDomainCategoryPlot.
     *
     * @return A chart.
     */
    private JFreeChart createChart(double[][] porestates, double[] reads, int tick) {

        final CategoryDataset dataset1 = createDataset1(reads,tick);
        final NumberAxis rangeAxis1 = new NumberAxis("Length of Reads");
        rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        final LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
        renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        final CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1, renderer1);
        subplot1.setDomainGridlinesVisible(true);
        
        final CategoryDataset dataset2 = createDataset2(porestates,tick);
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
//    	int tick = 4;
//    	//reads.length and porestates[0].length must be the same
//        double[] reads = {200,3343,1234,123,51,0,0};
//        double[][] porestates = {{1,1,1,1,1},{2,2,2,2,2},{3,3,3,3,3},{1,4,1,2,3},{1,4,1,2,3},{1,4,1,2,3},{1,4,1,2,3}};
//        
//        
//        
//        final String title = "Combined Category Plot Demo 1";
//        final guiStatistics demo = new guiStatistics(title, porestates, reads, tick);
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//       
//        boolean yes = true;
//        for(int i = 0; i < 10000; i++)
//        {
//        	demo.updateData(porestates,reads, tick);
//        	if(yes)
//        	{
//        		tick--;
//        		yes = false;
//        	}else
//        	{
//        		tick++;
//        		yes = true;
//        	}
//        }
//        
//        
//
//    }
    /**
     * @author Daniel and Albert
     * @param porestates 
     * @param reads
     * @param tick
     * @function updates the chart by computing a new one everytime.
     */
    public void updateData(double[][] porestates, double[] reads, int tick)
	   {
    			
		   		JFreeChart JFreetemp = createChart(porestates, reads, tick);
//		   		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		        this.chartPanel.setChart(JFreetemp);
		        chartPanel.updateUI();

	   }

}
