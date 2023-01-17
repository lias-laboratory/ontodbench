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

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;

/**
 * @author Géraud FOKOU
 */
@SuppressWarnings({ "rawtypes" })
public class Metrics {

	public static int evolutionmetric = 0;
	/**
	 * path of the folder in which it create the log file
	 */
	private String url;

	/**
	 * the Session of the current computation metrics
	 */
	private IOntologySession refSession;

	/**
	 * The values of the Metrics
	 */
	private int numberOfTriplets;
	private int numberOfSubject;
	private int numberOfObject;
	private int numberOfPredicat;
	private int numberOfType;
	private double outDegree;
	private double inDegree;
	private double averagePropertyByType;
	private double numberOfNull;
	private List nullByType;
	private List subjectByType;
	private List propertyByType;
	private List coverageOfType;
	private List weigthOfType;
	private double coherence;

	public Metrics(IOntologySession session, String path) {
		url = path;
		refSession = session;

	}

	/**
	 * Compute the metrics of the data of the current session
	 */
	private void metricsComputer() {

		ApplyMetrics theMesure = new ApplyMetrics(refSession, url);

		numberOfTriplets = theMesure.numberOfTripletMetrics();
		evolutionmetric = 2;
		numberOfSubject = theMesure.numberOfSubjectMetrics();
		evolutionmetric = 4;
		numberOfPredicat = theMesure.numberOfPredicatMetrics();
		evolutionmetric = 6;
		numberOfObject = theMesure.numberOfObjectMetrics();
		evolutionmetric = 8;
		numberOfType = theMesure.numberOfTypeMetrics();
		evolutionmetric = 10;
		evolutionmetric = 12;
		outDegree = theMesure.outdegreeMetrics();
		evolutionmetric = 17;
		inDegree = theMesure.indegreeMetrics();
		evolutionmetric = 22;
		averagePropertyByType = theMesure.averageOfPropertyMetrics();
		evolutionmetric = 29;
		numberOfNull = theMesure.numberOfNullMetrics();
		evolutionmetric = 39;
		nullByType = theMesure.numberOfNullByType();
		evolutionmetric = 46;
		subjectByType = theMesure.numberOfSubjectByType();
		evolutionmetric = 49;
		propertyByType = theMesure.numberOfPropertyByType();
		evolutionmetric = 51;
		coverageOfType = theMesure.valueOfCoverage();
		evolutionmetric = 60;
		weigthOfType = theMesure.valueOfweight();
		evolutionmetric = 65;
		coherence = theMesure.theCoherence();
		evolutionmetric = 70;

	}

	/**
	 * compute the metric with the data in the database
	 */
	public void computeMetricsOnDB() {
		this.metricsComputer();
	}

	/**
	 * Create The metric.txt file which contains the resume of the apllycation of
	 * metrics on the current data
	 */
	public void genereLogFileMetrics() {

		File logFile = new File(url + "\\LogMetric.txt");

		try {
			logFile.createNewFile();
			PrintStream writeFile = new PrintStream(logFile);
			writeFile.println("Computation of complex dataset and workload metrics result " + refSession.getName());
			writeFile.println();
			writeFile.println("Number of triples \t \t \t " + Integer.toString(numberOfTriplets));
			writeFile.println("Number of subjets  \t \t \t " + Integer.toString(numberOfSubject));
			writeFile.println("Number of predicats  \t \t \t " + Integer.toString(numberOfPredicat));
			writeFile.println("Number of objects  \t \t \t " + Integer.toString(numberOfObject));
			writeFile.println("Number of nulls \t  \t \t \t " + Double.toString(this.numberOfNull));
			writeFile.println("Number of types \t \t \t \t " + Integer.toString(numberOfType));
			writeFile.println("Property/type average \t \t " + Double.toString(this.averagePropertyByType));
			writeFile.println("Outdegree average   \t \t" + Double.toString(outDegree));
			writeFile.println("Indegree average   \t \t" + Double.toString(inDegree));
			writeFile.println("Coherence  \t \t" + Double.toString(coherence));
			writeFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		evolutionmetric = 80;
	}

	public void visualMetrics() {

		MetricsUI visual = new MetricsUI();
		visual.drawCoverageAndNull(coverageOfType, numberOfNull);
		evolutionmetric = 84;
		visual.drawWeightPerType(weigthOfType, coherence);
		evolutionmetric = 88;
		visual.drawMetricOnType(subjectByType, nullByType, propertyByType, numberOfNull);
		evolutionmetric = 92;
		visual.drawAverageMetrics(inDegree, outDegree, averagePropertyByType);
		evolutionmetric = 96;
		visual.drawCompteMetrics(numberOfSubject, numberOfPredicat, numberOfObject, numberOfType, numberOfTriplets);
		evolutionmetric = 100;

	}
}
