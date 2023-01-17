/*********************************************************************************
* This file is part of OntoDBench Project.
* Copyright (C) 2012  LIAS - ENSMA
*   Teleport 2 - 1 avenue Clement Ader
*   BP 40109 - 86961 Futuroscope Chasseneuil Cedex - FRANCE
* 
* OntoDBench is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* OntoDBench is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License
* along with OntoDBench.  If not, see <http://www.gnu.org/licenses/>.
**********************************************************************************/
package fr.ensma.lias.ontodbench.metric;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * @author Géraud FOKOU
 */
public class MetricsUI extends JFrame {

	private static final long serialVersionUID = 5436951392403058418L;

	/**
	 * Constructors of the class
	 */
	public MetricsUI() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private void drawPart(List arg3) {

		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		String[] elt;

		for (int i = 0; i < arg3.size(); i++) {

			elt = (String[]) arg3.get(i);
			try {
				dataset1.addValue(Double.parseDouble(elt[1]), "Property", (URI.create(elt[0])).getFragment());
			} catch (Exception e) {
				dataset1.addValue(Double.parseDouble(elt[1]), "Property",
						(URI.create(elt[0])).getRawSchemeSpecificPart());
			}
		}
		JFreeChart chart1 = ChartFactory.createBarChart3D("Property By Type", "TYPE", "Valeurs", dataset1,
				PlotOrientation.VERTICAL, true, true, false);

		chart1.setBackgroundPaint(Color.white);
		chart1.getTitle().setPaint(Color.BLACK);
		CategoryPlot p = chart1.getCategoryPlot();
		p.getRenderer().setSeriesPaint(0, Color.GRAY);
		p.setRangeGridlinePaint(Color.white);
		ChartFrame frame = new ChartFrame("Metrics On The Ontology's Type", chart1);
		frame.setVisible(true);
		frame.setSize(700, 400);
		return;
	}

	/**
	 * Drawing of the graphic which show the coverage of the ontology
	 * 
	 * @param arg
	 * @param nul
	 */
	@SuppressWarnings("rawtypes")
	public void drawCoverageAndNull(List arg, double nul) {

		String[] elt;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 0; i < arg.size(); i++) {

			elt = (String[]) arg.get(i);
			try {
				dataset.addValue(Double.parseDouble(elt[1]) * 100, "Couvert", (URI.create(elt[0])).getFragment());
				dataset.addValue((1 - Double.parseDouble(elt[1])) * 100, "Null", (URI.create(elt[0])).getFragment());
			} catch (Exception e) {
				dataset.addValue(Double.parseDouble(elt[1]) * 100, "Couvert",
						(URI.create(elt[0])).getRawSchemeSpecificPart());
				dataset.addValue((1 - Double.parseDouble(elt[1])) * 100, "Null",
						(URI.create(elt[0])).getRawSchemeSpecificPart());
			}
		}

		JFreeChart chart = ChartFactory.createStackedBarChart3D("Metric Coverage : Null= " + Double.toString(nul),
				"Type", "Coverage", dataset, PlotOrientation.VERTICAL, true, true, false);
		chart.setBackgroundPaint(Color.white);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getRenderer().setSeriesPaint(0, Color.GRAY);
		plot.getRenderer().setSeriesPaint(1, Color.DARK_GRAY);
		chart.setBorderVisible(true);
		ChartFrame frame = new ChartFrame("Type's Coverage Metrics", chart);
		frame.setVisible(true);
		frame.setSize(700, 400);
	}

	/**
	 * Drawing of the graphic which show the weigth of each Type in the ontology
	 * definition
	 * 
	 * @param arg
	 * @param coherence
	 */
	@SuppressWarnings("rawtypes")
	public void drawWeightPerType(List arg, double coherence) {

		String[] elt;

		DefaultPieDataset pieDataset = new DefaultPieDataset();
		for (int i = 0; i < arg.size(); i++) {
			elt = (String[]) arg.get(i);

			try {
				pieDataset.setValue((URI.create(elt[0])).getFragment(), Double.parseDouble(elt[1]));
			} catch (Exception e) {
				pieDataset.setValue((URI.create(elt[0])).getRawSchemeSpecificPart(), Double.parseDouble(elt[1]));
			}
		}
		JFreeChart chart = ChartFactory.createPieChart3D(
				"Weight of each type. Coherence=" + Double.toString(coherence * 100) + "%", pieDataset, true, true,
				true);
		PiePlot3D p = (PiePlot3D) chart.getPlot();
		p.setForegroundAlpha(0.5f);
		ChartFrame frame = new ChartFrame("Type's Weigth Metrics", chart);
		frame.setVisible(true);
		frame.setSize(700, 400);
	}

	/**
	 * Drawing of the graphic which show all the metrics computing on the Type of
	 * the ontology
	 * 
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	@SuppressWarnings("rawtypes")
	public void drawMetricOnType(List arg1, List arg2, List arg3, double nul) {

		String[] elt, elt1 = null;
		double factor = 0;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int j;
		boolean trouve;

		for (int i = 0; i < arg1.size(); i++) {

			elt = (String[]) arg1.get(i);

			j = 0;
			trouve = false;
			while ((j < arg3.size()) && (!trouve)) {
				elt1 = (String[]) arg3.get(j);
				trouve = ((String) elt1[0]).compareToIgnoreCase(elt[0]) == 0;
				j = j + 1;
			}
			j = j - 1;
			if (trouve) {
				factor = Double.parseDouble(elt1[1]);
			} else {
				factor = 0;
			}

			try {
				dataset.addValue(factor * Double.parseDouble(elt[1]), "Propriétés", (URI.create(elt[0])).getFragment());
			} catch (Exception e) {
				dataset.addValue(Double.parseDouble(elt[1]), "Propriétés",
						(URI.create(elt[0])).getRawSchemeSpecificPart());
			}

			j = 0;
			trouve = false;
			while ((j < arg2.size()) && (!trouve)) {
				elt1 = (String[]) arg2.get(j);
				trouve = ((String) elt1[0]).compareToIgnoreCase(elt[0]) == 0;
				j = j + 1;
			}
			j = j - 1;
			if (trouve) {
				try {
					dataset.addValue(Double.parseDouble(elt1[1]), "Nuls", (URI.create(elt[0])).getFragment());
				} catch (Exception e) {
					dataset.addValue(Double.parseDouble(elt1[1]), "Nuls",
							(URI.create(elt[0])).getRawSchemeSpecificPart());
				}
			} else {
				try {
					dataset.addValue(0, "Nuls", (URI.create(elt[0])).getFragment());
				} catch (Exception e) {
					dataset.addValue(0, "Nuls", (URI.create(elt[0])).getRawSchemeSpecificPart());
				}
			}
		}
		JFreeChart chart1 = ChartFactory.createBarChart3D("Metric On Type: Null=" + Double.toString(nul), "TYPE",
				"Values", dataset, PlotOrientation.VERTICAL, true, true, false);
		chart1.setBackgroundPaint(Color.white);
		chart1.getTitle().setPaint(Color.BLACK);
		CategoryPlot p = chart1.getCategoryPlot();
		p.getRenderer().setSeriesPaint(0, Color.darkGray);
		p.getRenderer().setSeriesPaint(1, Color.gray);
		p.setRangeGridlinePaint(Color.white);
		ChartFrame frame = new ChartFrame("Metrics On The Ontology's Type", chart1);
		frame.setVisible(true);
		frame.setSize(700, 400);
		this.drawPart(arg3);
	}

	/**
	 * Drawing of the graphic which all the average metrics computing on the
	 * ontology
	 * 
	 * @param intd
	 * @param outd
	 * @param ppt
	 */
	public void drawAverageMetrics(double intd, double outd, double ppt) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(intd, "Average", "Indegree");
		dataset.setValue(outd, "Average", "Outdegree");
		dataset.setValue(ppt, "Average", "Property/Type");
		JFreeChart chart = ChartFactory.createBarChart3D("Average Metrics", "Averages", "Values", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setPaint(Color.BLACK);
		CategoryPlot p = chart.getCategoryPlot();
		p.getRenderer().setSeriesPaint(0, Color.GRAY);
		p.setRangeGridlinePaint(Color.WHITE);
		ChartFrame frame1 = new ChartFrame("Metric On Type", chart);
		frame1.setVisible(true);
		frame1.setSize(700, 400);

	}

	/**
	 * Drawing of the graphic which show all the Metrics which compte th element
	 * 
	 * of the ontology
	 * 
	 * @param numSubj
	 * @param numPred
	 * @param numObject
	 * @param numTypes
	 * @param numDouble
	 */
	public void drawCompteMetrics(int numSubj, int numPred, int numObject, int numTypes, int numTriplets) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();

		dataset.setValue(numSubj, "NumberOf", "Subject");
		dataset.setValue(numObject, "NumberOf", "Object");

		dataset1.setValue(numPred, "NumberOf", "Predicat");
		dataset1.setValue(numTypes, "NumberOf", "Types");

		JFreeChart chart = ChartFactory.createBarChart3D(
				"Computing Metrics Number Of Triplets : " + Integer.toString(numTriplets), "Number Of", "Values",
				dataset, PlotOrientation.VERTICAL, true, true, false);

		JFreeChart chart1 = ChartFactory.createBarChart3D("Computing Metrics", "Number Of", "Values", dataset1,
				PlotOrientation.VERTICAL, true, true, false);

		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setPaint(Color.BLACK);
		CategoryPlot p = chart.getCategoryPlot();
		p.getRenderer().setSeriesPaint(0, Color.GRAY);
		p.setRangeGridlinePaint(Color.WHITE);
		ChartFrame frame = new ChartFrame("Computing Metrics", chart);
		frame.setVisible(true);
		frame.setSize(700, 400);

		chart1.setBackgroundPaint(Color.white);
		chart1.getTitle().setPaint(Color.BLACK);
		CategoryPlot p1 = chart1.getCategoryPlot();
		p1.getRenderer().setSeriesPaint(0, Color.GRAY);
		p1.setRangeGridlinePaint(Color.WHITE);
		ChartFrame frame1 = new ChartFrame("Computing Metrics", chart1);
		frame1.setVisible(true);
		frame1.setSize(700, 400);

	}

}
