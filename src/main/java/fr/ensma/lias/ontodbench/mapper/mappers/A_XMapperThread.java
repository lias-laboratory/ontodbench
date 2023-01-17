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
package fr.ensma.lias.ontodbench.mapper.mappers;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;

/**
 * @author Géraud FOKOU
 */
public abstract class A_XMapperThread extends Thread {

	protected String sgbd;

	protected String dbname;

	protected String user;

	protected String port;

	protected String host;

	protected String pwd;

	protected IOntologySession connecter;

	@SuppressWarnings("rawtypes")
	protected List queryAvresult = new ArrayList();

	@SuppressWarnings("rawtypes")
	protected List queryMxresult = new ArrayList();

	@SuppressWarnings("rawtypes")
	protected List queryMnresult = new ArrayList();

	protected Object[][] allResults;

	protected String[] entete = { " ", "Query1", "Query2", "Query3", "Query4", "Query5", "Query6", "Query7", "Query8",
			"Query9" };

	protected boolean mapping = true;

	protected int iterateQuery = 0;

	protected File logFile;

	@SuppressWarnings("rawtypes")
	protected void drawResult(List arg3, String title) {

		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();

		for (int i = 0; i < arg3.size(); i++) {
			try {
				dataset1.addValue((Number) arg3.get(i), "Duration", Integer.toString(i + 1));
			} catch (Exception e) {
			}
		}
		JFreeChart chart1 = ChartFactory.createBarChart3D(title, "ESSAI", "Time(ms)", dataset1,
				PlotOrientation.VERTICAL, true, true, false);

		chart1.setBackgroundPaint(Color.white);
		chart1.getTitle().setPaint(Color.BLACK);
		CategoryPlot p = chart1.getCategoryPlot();
		p.getRenderer().setSeriesPaint(0, Color.GRAY);
		p.setRangeGridlinePaint(Color.white);
		ChartFrame frame = new ChartFrame(title, chart1);
		frame.setVisible(true);
		frame.setSize(700, 400);
		return;
	}

	@SuppressWarnings("rawtypes")
	protected long averageTime(List times) {

		long sum = 0;

		for (int i = 0; i < times.size(); i++) {
			try {
				sum = sum + (Long) times.get(i);
			} catch (Exception e) {
			}

		}

		return sum / times.size();
	}

	@SuppressWarnings("rawtypes")
	protected long minimalTime(List times) {
		long min = 0;

		min = (Long) times.get(0);
		for (int i = 1; i < times.size(); i++) {
			if ((Long) times.get(i) < min) {
				min = (Long) times.get(i);
			}
		}
		return min;
	}

	@SuppressWarnings("rawtypes")
	protected long maximalTime(List times) {
		long max = 0;

		max = (Long) times.get(0);
		for (int i = 1; i < times.size(); i++) {
			if ((Long) times.get(i) > max) {
				max = (Long) times.get(i);
			}
		}
		return max;
	}

	public void setParameter(String name, String user, String pwd, String namehost, String port, String dbms) {

		this.dbname = name;
		this.user = user;
		this.pwd = pwd;
		this.host = namehost;
		this.port = port;
		this.sgbd = dbms;
	}

	@SuppressWarnings("rawtypes")
	public List getAvResult() {

		return queryAvresult;
	}

	@SuppressWarnings("rawtypes")
	public List getMxResult() {

		return queryMxresult;
	}

	@SuppressWarnings("rawtypes")
	public List getMnResult() {

		return queryMnresult;
	}

	public Object[][] getallResults() {

		return allResults;
	}

	public String[] getentete() {
		return entete;
	}

	public void setMapping(boolean arg) {

		mapping = arg;
		return;
	}

	public void setIterationQuery(int nbre) {

		iterateQuery = nbre;
		return;
	}

	public abstract double threadevol();

	public abstract void run();
}
