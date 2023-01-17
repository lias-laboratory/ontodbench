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

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;
import fr.ensma.lias.ontodbench.databasedao.querymetric.ITripletDAO;
import fr.ensma.lias.ontodbench.databasedao.querymetric.TripletDAOFactory;

import java.util.List;

/**
 * @author Géraud FOKOU
 */
public class ApplyMetrics {

	/**
	 * access class to the Database
	 */
	private ITripletDAO accesDB;

	private IOntologySession refSession;

	public ApplyMetrics(IOntologySession refSession, String url) {

		this.refSession = refSession;
		accesDB = TripletDAOFactory.createITripletDAO(refSession.getType());
	}

	/**
	 * evaluation of the number of triplet
	 */
	public int numberOfTripletMetrics() {
		return accesDB.numberOfTriplets(refSession);
	}

	/**
	 * Evaluation of the number of Subject
	 */
	public int numberOfSubjectMetrics() {

		return accesDB.numberOfdistinctSubject(refSession);
	}

	/**
	 * Evaluation of the number of Predicate
	 */
	public int numberOfPredicatMetrics() {

		return accesDB.numberOfdistinctPredicat(refSession);
	}

	/**
	 * Evaluation of the number of Object
	 */
	public int numberOfObjectMetrics() {

		return accesDB.numberOfdistinctObject(refSession);
	}

	/**
	 * Evaluation of the number of type in the ontology
	 */
	public int numberOfTypeMetrics() {

		return accesDB.numberOfTypes(refSession);
	}

	/**
	 * Evaluation of the average of property by type in the ontology
	 * 
	 * @return
	 */
	public double averageOfPropertyMetrics() {
		return accesDB.averagePropertyByType(refSession);
	}

	/**
	 * Evaluation of the Number of the null fields
	 * 
	 * @return
	 */
	public double numberOfNullMetrics() {

		return accesDB.numberOfNullChamps(refSession);
	}

	/**
	 * Evaluation of the number of triplet for each subject
	 */
	public double outdegreeMetrics() {

		return accesDB.averageTripletsBySubject(refSession);
	}

	/**
	 * Evaluation of the number of triplet for each object
	 */
	public double indegreeMetrics() {

		return accesDB.averageTripletsByObject(refSession);
	}

	/**
	 * Evaluation of the number of bul by type
	 */
	@SuppressWarnings("rawtypes")
	public List numberOfNullByType() {

		return accesDB.numberOfNullByTypes(refSession);
	}

	/**
	 * Evaluation of the number of Subject by Type
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List numberOfSubjectByType() {

		return accesDB.numberOfSubjectByTypes(refSession);
	}

	/**
	 * Evaluation of the number of property by Type
	 */
	@SuppressWarnings("rawtypes")
	public List numberOfPropertyByType() {

		return accesDB.numberOfPropertyByTypes(refSession);
	}

	/**
	 * Evaluation of the coverage for each Type of the Ontology
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List valueOfCoverage() {

		return accesDB.coverage(refSession);
	}

	/**
	 * Evaluation of the weight of each type
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List valueOfweight() {

		return accesDB.weightByType(refSession);
	}

	public double theCoherence() {

		return accesDB.coherence(refSession);
	}

}
