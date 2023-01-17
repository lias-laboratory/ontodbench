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
package fr.ensma.lias.ontodbench.databasedao.databasesession;

import java.sql.Connection;

/**
 * @author Géraud FOKOU
 */
public abstract class AOntologySession implements IOntologySession {

	/**
	 * Show the type of the DAO
	 */
	protected String TYPE;

	/**
	 * jdbc class
	 */
	protected String JDBCCONNECT;

	/**
	 * host'name of DB server
	 */
	protected String host = "";

	/**
	 * port of connection
	 */
	protected String port = "";

	/**
	 * Name of the DataBase on which we are connected
	 */
	protected String name = "";

	/**
	 * Name of user
	 */
	protected String user = "";

	/**
	 * password of user
	 */
	protected String passwd = "";

	/**
	 * Object Connection
	 */
	protected Connection connect;
}
