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
package fr.ensma.lias.ontodbench.databasedao.querymetric;

import java.util.List;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;

/**
 * @author Géraud FOKOU
 */
public interface ITripletDAO {

	/**
	 * Initialization of DataBase with Materialization
	 */
	void initialisation(IOntologySession p);

	/**
	 * Initialization of The DataBase
	 */
	void initialisation(IOntologySession p, boolean materialized_V);

	/**
	 * Update the database by initializing the table TYPES and PROPERTYTAB
	 */
	void updateDB(IOntologySession p);

	/**
	 * Insert element in the database
	 */
	void addAtome(IOntologySession p, String sub, String pred, String obj);

	/**
	 * return the number of Triplets present in the Database
	 * 
	 * @return
	 */
	int numberOfTriplets(IOntologySession p);

	/**
	 * return the number of distinct subject present in the Database
	 * 
	 * @return
	 */
	int numberOfdistinctSubject(IOntologySession p);

	/**
	 * return the number of distinct Predicate present in the Database
	 * 
	 * @return
	 */
	int numberOfdistinctPredicat(IOntologySession p);

	/**
	 * return the number of distinct Object present in the Database
	 * 
	 * @return
	 */
	int numberOfdistinctObject(IOntologySession p);

	/**
	 * Compute the number of type in the definition of the ontology
	 */
	int numberOfTypes(IOntologySession p);

	/**
	 * Compute the average of outdegree
	 * 
	 * @return
	 */
	double averageTripletsBySubject(IOntologySession p);

	/**
	 * Compute the average of indegree
	 */
	double averageTripletsByObject(IOntologySession p);

	/**
	 * Compute the average Property by Type
	 */
	double averagePropertyByType(IOntologySession p);

	/**
	 * Compute the number of null in the definition of the ontology
	 */
	double numberOfNullChamps(IOntologySession p);

	/**
	 * Compute the number of null by Type in the definition of the ontology
	 */
	@SuppressWarnings("rawtypes")
	List numberOfNullByTypes(IOntologySession p);

	/**
	 * Compute the number of Subject By Type in the definition of the Ontology
	 */
	@SuppressWarnings("rawtypes")
	List numberOfSubjectByTypes(IOntologySession p);

	/**
	 * Compute the number of Property by Types
	 */
	@SuppressWarnings("rawtypes")
	List numberOfPropertyByTypes(IOntologySession p);

	/**
	 * Compute the coverage by type of the Ontologie
	 */
	@SuppressWarnings("rawtypes")
	List coverage(IOntologySession p);

	/**
	 * Compute the weight of each type
	 * 
	 * @param p
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List weightByType(IOntologySession p);

	/**
	 * Compute the coherence of the Ontologie
	 */
	double coherence(IOntologySession p);
}
