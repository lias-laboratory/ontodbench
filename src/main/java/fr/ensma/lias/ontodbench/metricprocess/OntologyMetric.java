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
package fr.ensma.lias.ontodbench.metricprocess;

import java.io.File;
import java.util.Date;
import edu.lehigh.swat.bench.uba.Generator;
import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;
import fr.ensma.lias.ontodbench.databasedao.databasesession.OntologySessionFactory;
import fr.ensma.lias.ontodbench.databasedao.querymetric.ITripletDAO;
import fr.ensma.lias.ontodbench.databasedao.querymetric.TripletDAOFactory;
import fr.ensma.lias.ontodbench.metric.*;
import fr.ensma.lias.ontodbench.model.conversion.ControlThread;
import fr.ensma.lias.ontodbench.model.conversion.NTripleModel;
import fr.ensma.lias.ontodbench.model.segmentation.*;

/**
 * @author Géraud FOKOU
 */
public class OntologyMetric {

	public static IOntologySession instance = OntologySessionFactory.createOntologySession("ontologymetric", "postgres",
			"psql", "localhost", "5432", "postgres");
	public static ITripletDAO access = TripletDAOFactory.createITripletDAO("postgres");

	public OntologyMetric() {
	}

	private void processBenchmark() {
		String[] args = new String[9];

		args[0] = "-univ";
		args[1] = "1";
		args[2] = "-index";
		args[3] = "0";
		args[4] = "-seed";
		args[5] = "0";
		args[6] = "-daml";
		args[7] = "-onto";
		args[8] = "http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric";
		Generator.main(args);
		return;
	}

	private String processSegmentFile(String source, String formatSource, Long length) {

		IOntoTrunk segmenter = OntoTrunkFactory.createOntoTrunk(formatSource);
		System.out.println("*********OntologyMetric:Segmentation Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		return segmenter.segmentedFile(new File(source), length);
	}

	private String processSegmentFolder(String source, String formatSource, Long length) {

		IOntoTrunk segmenter = OntoTrunkFactory.createOntoTrunk(formatSource);

		System.out.println("*********OntologyMetric:Segmentation Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		return segmenter.segmentFolder(source, length);
	}

	private void processConvertFolder(String source, String formatSource, String destination, String formatDestination,
			boolean mode) {

		NTripleModel converteur = new NTripleModel();
		converteur.setParameter(source, destination, formatSource, formatDestination);
		converteur.multipleFile(mode);
		converteur.convert();
	}

	private void processConvertFile(String source, String formatSource, String destination, String formatDestination) {

		NTripleModel converteur = new NTripleModel();
		converteur.setParameter(source, destination, formatSource, formatDestination);
		converteur.multipleFile(false);
		converteur.convertAfile();
	}

	private void processInsertion(String source, IOntologySession p, ITripletDAO t) {

		ControlThread mainConvert = new ControlThread();
		mainConvert.setParameter(source, "nt", p, t);

		System.out.println("*********OntologyMetric:Insertion Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		mainConvert.execute();
		while (!mainConvert.isFinish()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println(e.toString() + "\n" + e.fillInStackTrace().toString());
			}
		}
		System.out.println("*********OntologyMetric:Insertion Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	private void processConvertAndInsert(String source, String formatSource, String destination,
			String formatDestination, IOntologySession p, ITripletDAO t) {

		ControlThread mainConvert = new ControlThread();
		mainConvert.setParameter(source, formatSource, destination, formatDestination, p, t);

		System.out.println("*********OntologyMetric:Conversion and Insertion Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		mainConvert.execute();
		while (!mainConvert.isFinish()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println(e.toString() + "\n" + e.fillInStackTrace().toString());
			}
		}
		System.out.println("*********OntologyMetric:Conversion and Insertion Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	private void processMetric(IOntologySession p, String url) {

		Metrics ruban = new Metrics(p, url);
		System.out.println("********OntologyMetric:The Compute Of Metric Started! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		ruban.computeMetricsOnDB();
		System.out.println("********OntologyMetric:The Compute Of Metric Completed! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");

		System.out.println("********OntologyMetric:The Generation of LogFile LogMetric.txt started! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		ruban.genereLogFileMetrics();
		System.out.println("********OntologyMetric:The Generation of LogFile LogMetric.txt Completed! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");

		System.out.println("********OntologyMetric:The Visualisation of The Metrics Started! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		ruban.visualMetrics();
		System.out.println("********OntologyMetric:The Visualisation of The Metrics Completed! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	public void generateBeforeMeasure(String url, long size) {

		this.processBenchmark();

		System.out.println("*********OntologyMetric:Conversion Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		this.processConvertFolder(this.processSegmentFolder(System.getProperty("user.home"), "daml", size), "daml", url,
				"nt", true);
		System.out.println("*********OntologyMetric:Conversion Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");

		this.processInsertion(url, instance, access);

		System.out.println("*********OntologyMetric:Computation Metric Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		this.processMetric(instance, url);
		System.out.println("********OntologyMetric:Computation Metric Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	public void generateBeforeMeasure(String url) {

		this.processBenchmark();

		System.out.println("*********OntologyMetric:Conversion Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		this.processConvertAndInsert(System.getProperty("user.home"), "daml", url, "nt", instance, access);
		System.out.println("*********OntologyMetric:Conversion Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");

		System.out.println("*********OntologyMetric:Computation Metric Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		this.processMetric(instance, url);
		System.out.println("********OntologyMetric:Computation Metric Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	public void convertBeforeSegmented(String url, String formatSource, long length, String dest) {

		this.processConvertFile(url, formatSource, dest, "nt");
		this.processInsertion(this.processSegmentFolder(dest, "nt", length), instance, access);
		System.out.println("*********OntologyMetric:Computation Metric Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		this.processMetric(instance, dest);
		System.out.println("********OntologyMetric:Computation Metric Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	public void convertFolder(String url, String formatSource, long length, String dest, IOntologySession p,
			ITripletDAO a) {

		this.processConvertAndInsert(url, formatSource, dest, "nt", p, a);
		System.out.println("*********OntologyMetric:Computation Metric Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		this.processMetric(instance, dest);
		System.out.println("********OntologyMetric:Computation Metric Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	public void segmentedBeforeMeasure(String url, String formatSource, long length, String dest) {

		this.processConvertAndInsert(this.processSegmentFolder(url, formatSource, length), formatSource, dest, "nt",
				instance, access);

		System.out.println("*********OntologyMetric:Computation Metric Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		this.processMetric(instance, dest);

		System.out.println("********OntologyMetric:Computation Metric Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	public void segmentedFileBeforeMeasure(String url, String formatSource, long length, String dest) {

		this.processConvertAndInsert(this.processSegmentFile(url, formatSource, length), formatSource, dest, "nt",
				instance, access);

		System.out.println("*********OntologyMetric:Computation Metric Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		this.processMetric(instance, dest);
		System.out.println("*********OntologyMetric:Computation Metric Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	public void measureOnFile(String source, long size, String url) {

		this.processInsertion(this.processSegmentFolder(source, "nt", size), instance, access);

		System.out.println("*********OntologyMetric:Computation Metric Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		this.processMetric(instance, url);
		System.out.println("********OntologyMetric:Computation Metric Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	public void measureOntheBD(IOntologySession p, String url) {

		System.out.println("*********OntologyMetric:Computation Metric Start ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
		this.processMetric(p, url);
		System.out.println("********OntologyMetric:Computation Metric Completed ! At "
				+ new Date((System.currentTimeMillis())).toString() + "*********");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args1) {

		/*
		 * OntologyMetric ontologiemetric = new OntologyMetric();
		 * ontologiemetric.segmentedFileBeforeMeasure( System.getProperty("user.dir") +
		 * "\\src\\test\\resources\\yago2core.rdf", "rdf", (long) (1 * 1024 * 1024),
		 * System.getProperty("user.dir") + "\\target");
		 */

		String[] args = new String[8];
		args[0] = "-univ";
		args[1] = "1";
		args[2] = "-index";
		args[3] = "0";
		args[4] = "-seed";
		args[5] = "1";
		// args[6] = "-daml";
		args[6] = "-onto";
		args[7] = "http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric";
		Generator.main(args);
		return;

	}
}
