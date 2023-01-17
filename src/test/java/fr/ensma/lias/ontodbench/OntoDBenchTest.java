/*********************************************************************************
* This file is part of OntologyMetric Project.
* Copyright (C) 2011  LISI - ENSMA
*   Teleport 2 - 1 avenue Clement Ader
*   BP 40109 - 86961 Futuroscope Chasseneuil Cedex - FRANCE
* 
* OntologyMetric is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* OntologyMetric is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License
* along with OntologyMetric.  If not, see <http://www.gnu.org/licenses/>.
**********************************************************************************/
package fr.ensma.lias.ontodbench;

import java.io.File;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;
import fr.ensma.lias.ontodbench.databasedao.databasesession.OntologySessionFactory;
import fr.ensma.lias.ontodbench.metricprocess.OntologyMetric;

/**
 * @author Géraud FOKOU
 */
public class OntoDBenchTest {

	private IOntologySession instance;

	@Before
	public void initializeSession() {
		/*
		 * instance = OntologySessionFactory.createOntologySession( "ontologymetric",
		 * "postgres", "!Psql2011", "s-db-lisi.w.ensma.fr","5433", "postgres");
		 */
		instance = OntologySessionFactory.createOntologySessionDB("uba", "postgres", "psql", "localhost", "5432",
				"postgres");
	}

	/**
	 * test on the UBA benchmark
	 */
	@Test
	public void applyMetricTest1() {

		OntologyMetric ontologiemetric = new OntologyMetric();
		ontologiemetric.generateBeforeMeasure(System.getProperty("user.dir") + "\\target", 128 * 1024);
	}

	/**
	 * Test for the bsbm.xml file in the resource folder
	 */
	@Test
	public void applyMetricTest2() {
		OntologyMetric ontologiemetric = new OntologyMetric();
		ontologiemetric.convertBeforeSegmented(System.getProperty("user.dir") + "\\src\\test\\resources\\bsbm.ttl",
				"ttl", 128 * 1024, System.getProperty("user.dir") + "\\target");
	}

	/**
	 * Test for the sp2b.n3 file in the resource folder
	 */
	@Test
	public void applyMetricTest3() {
		OntologyMetric ontologiemetric = new OntologyMetric();
		ontologiemetric.segmentedBeforeMeasure(System.getProperty("user.dir") + "\\src\\test\\resources\\", "n3",
				128 * 1024, System.getProperty("user.dir") + "\\target");
	}

	/**
	 * test on the yago's ontology file in the ressource folder
	 */
	@Test
	public void applyMetricTest4() {
		OntologyMetric ontologiemetric = new OntologyMetric();
		ontologiemetric.segmentedFileBeforeMeasure(
				System.getProperty("user.dir") + "\\src\\test\\resources\\yago2core.rdf", "rdf",
				(long) (1 * 1024 * 1024), System.getProperty("user.dir") + "\\target");
	}

	/**
	 * Test for the ntriple's file in the resource folder
	 */
	@Test
	public void applyMetricTest5() {
		OntologyMetric ontologiemetric = new OntologyMetric();
		ontologiemetric.measureOnFile(System.getProperty(System.getProperty("user.dir")) + "\\src\\test\\resources",
				128 * 1024, System.getProperty(System.getProperty("user.dir")));
	}

	/**
	 * Test for the triplets in the BD
	 */
	@Test
	public void applyMetricTest6() {
		OntologyMetric ontologiemetric = new OntologyMetric();
		ontologiemetric.measureOntheBD(instance, System.getProperty("user.dir") + "\\target");
	}

	@After
	public void resultVerification() {

		instance.closeSession();
		Assert.assertTrue(new File("C:\\Users\\fokoug\\LogMetric.txt").exists());
	}
}
