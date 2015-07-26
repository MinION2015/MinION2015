package guiStatistics;

import java.awt.Font;



import java.util.Random;

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

import error.ErrorCodes;
import error.MyException;

/**
 * Holds a ChartPanel
 */
public class guiStatistics extends ApplicationFrame {

	private ChartPanel chartPanel;
	private double[][] porestates = new double[14][5];
	private double[] reads = new double[14];
	private int tick;
	private Integer time;
    /**
     * @author Daniel and Albert
     * @input gets the porestates containing the states of the pore, the reads, containing the reads that are made at a special tick and the tick 
     * 		  are at the moment
     * @function generates two chartPanels
     */
    public guiStatistics(int timeinput) {

        super("Minion Simulation");
        this.tick = 0;
        time = 120;
//        System.out.println(timeinput);
//        this.time = timeinput;

        // add the chart to a panel...
        chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(chartPanel);

    }

    /**
     * Creates a dataset. 
     * @input gets the reads and the ticks
     * @return A dataset
     * @function computes a database containing the reads due the ticks. Every update the whole database is computed new
     */
    private CategoryDataset createDataset1() {

        final DefaultCategoryDataset result = new DefaultCategoryDataset();
        
        //just need to add some more values. rest is filled with null
        final String series1 = "Number of Reads";

        for(Integer i = 0; i < tick; i++)
        {
        	Integer convertToInteger = i* time;
        	result.addValue(reads[i], series1, convertToInteger.toString());
        }
        for(Integer i = tick; i < reads.length;i++)
        {
        	Integer convertToInteger = i* time;
        	result.addValue(null, series1, convertToInteger.toString());
        }


        return result;

    }

    /**
     * Creates a dataset. 
     * @input gets the porestates and the ticks
     * @return A dataset
     * @function computes a database containing the porestates due the ticks. Every update the whole database is computed new
     */
    private CategoryDataset createDataset2() {
//		porestates[i]
//    	[0] Running, [1]Bored, [2] Dead,[3] Finished, [4] Sleeping,[5]
        final DefaultCategoryDataset result = new DefaultCategoryDataset();


        
        String[] states = {"Running","Bored","Dead","Finished","Sleeping"};
        
        //add every tick all the states data stored in porestates.
        for(Integer i = 0; i < tick; i++)
        {
        	for(int j = 0; j < porestates[i].length; j++)
        	{
        		Integer convertToInteger = i* time;
        		result.addValue(porestates[i][j], states[j], convertToInteger.toString());
        	}
        	
        }
        //fill the rest of the chart with null
        for(Integer i = tick; i < porestates.length;i++)
        {
        	Integer convertToInteger = i* time;
        	result.addValue(null, "remain", convertToInteger.toString());
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
        final NumberAxis rangeAxis1 = new NumberAxis("Number of Reads");
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

        final CategoryAxis domainAxis = new CategoryAxis("Time(in min)");
        final CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);

        plot.add(subplot1, 2);
        plot.add(subplot2, 1);
        
        final JFreeChart result = new JFreeChart(
            "",
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
//        double[] porestates = {1,1,0,0,2};
//        double reads = 3;
//        
//        
//    	final guiStatistics demo = new guiStatistics(1);
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//        
//        for(int i = 0; i< 10; i++)
//        {
//        	double[] porestates2 = new double[5];
//        	for(int j = 0; j < 5; j++)
//        	{
//        		Random r = new Random(); 
//                int zahl = r.nextInt(5);
//                
//        		porestates2[zahl]++;
//        	}
//        	Random r = new Random(); 
//            int zahl = r.nextInt(4)+1;
//            
//            porestates2[zahl]++;
//            reads+=zahl;
//            
//        	demo.updateData(porestates2, reads);
//        }
//
//    }
   
    
    /**
     * @author Daniel
     * @param porestates 
     * @param reads
     * @param tick
     * @throws MyException 
     * @function updates the chart by computing a new one everytime.
     */
    public void updateData(double[] porestatestoadd, double readstoadd) throws MyException
	   {
    			if(porestatestoadd == null)
    			{
    				throw new MyException(ErrorCodes.GUISTATIATICS_NO_OUTPUR_DATA);
    			}
    			
    			porestates[tick] = porestatestoadd;
    			reads[tick] = readstoadd;

    			JFreeChart JFreetemp = createChart();
		   		//chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		        this.chartPanel.setChart(JFreetemp);
		        chartPanel.updateUI();
		        this.tick++;
	   }
    
    

}
