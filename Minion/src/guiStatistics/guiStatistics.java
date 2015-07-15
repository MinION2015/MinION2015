package guiStatistics;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;


import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.StandardGradientPaintTransformer;
/**
* A histogram with dynamically added data.
* @author Albert and Daniel
* shows a histogram of the pore states
*/
public class guiStatistics extends JFrame{		//not sure if this.chartPanel is needed
    		
			private ChartPanel chartPanel;
			
		   private static final long serialVersionUID = 1L;

		   public guiStatistics(String applicationTitle, String chartTitle, double[] porestates) {
		        super(applicationTitle);

		        // based on the dataset we create the chart
		       
		        
		        // Adding chart into a chart panel
		        this.chartPanel = new ChartPanel(createDataset(applicationTitle, chartTitle, porestates[0],porestates[1], porestates[2], porestates[3],porestates[4]));
		      
		        // setting default size
		        this.chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		        
		      
		        // add to contentPane
		        setContentPane(this.chartPanel);
		        

		    }
		  
		   private JFreeChart createDataset(String applicationTitle, String chartTitle, double running, double bored, double dead, double finished, double sleeping) {
		     
		      // row keys...
		      final String sdead = "Dead";
		      final String sbored = "Bored";
		      final String srunning = "Running";
		      final String sfinished = "Finished";
		      final String ssleeping = "Sleeping";
		      
		      //colum Pores
		      final String pores = "Pores";

		      // create the dataset...
		      final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		      dataset.addValue(running, pores, sdead);
		      dataset.addValue(bored, pores, sbored);
		      dataset.addValue(dead, pores, srunning);
		      dataset.addValue(finished, pores, sfinished);
		      dataset.addValue(sleeping, pores, ssleeping);
		      
		      JFreeChart pieChart = ChartFactory.createBarChart(chartTitle, null, "Number of Pores", dataset,PlotOrientation.VERTICAL, true, true, false);
		      
		      return pieChart;
		     
		  }
		   public void updateData(double[] porestates)
		   {
			   		JFreeChart JFreetemp = createDataset("Pore Statistics", "Pore States",porestates[0],porestates[1], porestates[2], porestates[3],porestates[4]);
			   		this.chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
			        this.chartPanel.setChart(JFreetemp);
			        this.chartPanel.updateUI();

		   }
		   


//		   public static void main(String[] args) {
//		      guiStatistics chart = new guiStatistics("Pore Statistics", "Pore States", 2 , 3 , 4 , 5 ,6);
//		      chart.pack();
//		      chart.setVisible(true);
//		   }
}