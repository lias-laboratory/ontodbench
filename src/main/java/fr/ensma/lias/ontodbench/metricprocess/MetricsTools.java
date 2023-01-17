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

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;
import fr.ensma.lias.ontodbench.databasedao.databasesession.OntologySessionFactory;
import fr.ensma.lias.ontodbench.databasedao.querymetric.ITripletDAO;
import fr.ensma.lias.ontodbench.databasedao.querymetric.TripletDAOFactory;
import fr.ensma.lias.ontodbench.metric.Metrics;
import fr.ensma.lias.ontodbench.model.conversion.ControlThread;
import fr.ensma.lias.ontodbench.model.conversion.NTripleModel;
import fr.ensma.lias.ontodbench.model.segmentation.IOntoTrunk;
import fr.ensma.lias.ontodbench.model.segmentation.OntoTrunkFactory;

/**
 * @author Géraud FOKOU
 */
public class MetricsTools {

	private IOntologySession instance;

	private ITripletDAO access;

	private String bdName;

	private String sgbd;

	private String user;

	private String host;

	private String port;

	private String pwd;

	/**
	 * Default Constructor
	 */
	public MetricsTools() {

	}

	public MetricsTools(String sgbd, String bdName, String pUser, String pHost, String pPort, String pPwd) {

		this.sgbd = sgbd;
		this.bdName = bdName;
		user = pUser;
		host = pHost;
		port = pPort;
		pwd = pPwd;
	}

	/**
	 * Segmented a File "source" with format "formatSource"
	 * 
	 * @param source
	 * @param formatSource
	 * @param length
	 * @return
	 */
	public String processSegmentFile(String source, String formatSource, long length) {

		IOntoTrunk segmenter = OntoTrunkFactory.createOntoTrunk(formatSource);
		return segmenter.segmentedFile(new File(source), length);
	}

	/**
	 * Segmented All the file containing in the Folder "source" with "formatSource"
	 * 
	 * @param source
	 * @param formatSource
	 * @param length
	 * @return
	 */
	public String processSegmentFolder(String source, String formatSource, long length) {

		IOntoTrunk segmenter = OntoTrunkFactory.createOntoTrunk(formatSource);
		return segmenter.segmentFolder(source, length);
	}

	/**
	 * convert the file "source" in the original format "formatSource" to a file put
	 * in the folder "destination" in the format "formatDestination"
	 * 
	 * @param source
	 * @param formatSource
	 * @param destination
	 * @param formatDestination
	 * @return
	 */
	public String processConvertFile(String source, String formatSource, String destination, String formatDestination) {

		NTripleModel converteur = new NTripleModel();
		converteur.setParameter(source, destination, formatSource, formatDestination);
		converteur.multipleFile(false);
		return converteur.convertAfile();
	}

	/**
	 * convert the all the file in format "formatSource" containing in the folder
	 * "source" to file in format "formatDestination" containing in folder
	 * destination
	 * 
	 * @param source
	 * @param formatSource
	 * @param destination
	 * @param formatDestination
	 * @param mode
	 * @return
	 */
	public boolean processConvertFolder(String source, String formatSource, String destination,
			String formatDestination, boolean mode) {

		NTripleModel converteur = new NTripleModel();
		converteur.setParameter(source, destination, formatSource, formatDestination);
		converteur.multipleFile(mode);
		return converteur.convert();
	}

	/**
	 * Insert all the triplet in the folder or file source and put it in the
	 * database
	 * 
	 * @param source
	 * @param p
	 * @param t
	 */
	public void processInsertion(String source) {

		ControlThread mainConvert = new ControlThread();

		if (instance == null) {
			this.instance = OntologySessionFactory.createOntologySessionDB(this.bdName, user, pwd, host, port,
					this.sgbd);
			this.access = TripletDAOFactory.createITripletDAO(this.sgbd);
		}

		mainConvert.setParameter(source, "nt", this.instance, access);
		mainConvert.execute();
		while (!mainConvert.isFinish()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Convert and insert into the BD
	 * 
	 * @param source
	 * @param formatSource
	 * @param destination
	 * @param formatDestination
	 * @param p
	 * @param t
	 */
	public void processConvertAndInsert(String source, String formatSource, String destination,
			String formatDestination) {

		ControlThread mainConvert = new ControlThread();

		if (instance == null) {
			this.instance = OntologySessionFactory.createOntologySessionDB(this.bdName, user, pwd, host, port,
					this.sgbd);
			this.access = TripletDAOFactory.createITripletDAO(this.sgbd);
		}

		mainConvert.setParameter(source, formatSource, destination, formatDestination, this.instance, access);
		mainConvert.execute();

		while (!mainConvert.isFinish()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void processMetric(String url) {

		if (instance == null) {
			this.instance = OntologySessionFactory.createOntologySession(this.bdName, user, pwd, host, port, this.sgbd);
			this.access = TripletDAOFactory.createITripletDAO(this.sgbd);
		}
		Metrics ruban = new Metrics(this.instance, url);

		ruban.computeMetricsOnDB();
		ruban.genereLogFileMetrics();
		ruban.visualMetrics();
	}
}
