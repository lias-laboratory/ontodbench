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
package fr.ensma.lias.ontodbench.mapper.h_mapper;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;

/**
 * @author Géraud FOKOU
 */
public interface I_HMapper {

	/**
	 * create the table schema of the current ontology's representation
	 */
	void createShema();

	/**
	 * upload data in the table of the current ontology's representation
	 */
	void uploadData();

	/**
	 * Update the connection to the database
	 */
	void setConnecter(IOntologySession p);

	/**
	 * The execution of query1's translation into sql for the current representation
	 */
	long query1();

	/**
	 * The execution of query2's translation into sql for the current representation
	 */
	long query2();

	/**
	 * The execution of query3's translation into sql for the current representation
	 */
	long query3();

	/**
	 * The execution of query4's translation into sql for the current representation
	 */
	long query4();

	/**
	 * The execution of query5's translation into sql for the current representation
	 */
	long query5();

	/**
	 * The execution of query6's translation into sql for the current representation
	 */
	long query6();

	/**
	 * The execution of query7's translation into sql for the current representation
	 */
	long query7();

	/**
	 * The execution of query8's translation into sql for the current representation
	 */
	long query8();

	/**
	 * The execution of query9's translation into sql for the current representation
	 */
	long query9();

	/**
	 * The execution of query10's translation into sql for the current
	 * representation
	 */
	long query10();

	/**
	 * The execution of query10's translation into sql for the current
	 * representation
	 */
	long query11();

	/**
	 * The execution of query12's translation into sql for the current
	 * representation
	 */
	long query12();

	/**
	 * The execution of query13's translation into sql for the current
	 * representation
	 */
	long query13();

	/**
	 * return the database'host
	 * 
	 * @return
	 */
	String getHost();

	/**
	 * update the database'host
	 * 
	 * @param host
	 */

	void setHost(String host);

	/**
	 * return the database'name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * update the database'name
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * return the owner of the current connection
	 * 
	 * @return
	 */
	String getUser();

	/**
	 * set the owner of the current connection
	 * 
	 * @param user
	 */
	void setUser(String user);

	/**
	 * return the password of the owner of the current connection
	 * 
	 * @return
	 */
	String getPasswd();

	/**
	 * set the password of the owner of the current connection
	 * 
	 * @param passwd
	 */
	void setPasswd(String passwd);
}
