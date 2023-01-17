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
public interface IOntologySession {

	/**
	 * return the DB server'type (DBMS'name)
	 */
	String getType();

	/**
	 * @return the DBMS's JDBC Driver
	 */
	String getJdbcconnect();

	/**
	 * set the host'name of the DB server
	 */
	void setnameHost(String host);

	/**
	 * return the host'name of the DB server
	 */
	String getnameHost();

	/**
	 * @param port set the port of connection
	 */
	void setPort(String port);

	/**
	 * @return the port
	 */
	String getPort();

	/**
	 * set the current user of the DB's server
	 */
	void setUser(String user);

	/**
	 * return the current user of the DB's server
	 * 
	 * @return
	 */
	String getUser();

	/**
	 * set the password use for the connection
	 */
	void setPasswd(String pwd);

	/**
	 * return the password use for the connection
	 * 
	 * @return
	 */
	String getPasswd();

	/**
	 * set the DataBase's name
	 */
	void setName(String name);

	/**
	 * return the dataBase's name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Create or replace the DB if necessary
	 */
	void createDataBase();

	/**
	 * return a connection to a DB
	 * 
	 * @return
	 */
	Connection getConnection();

	/**
	 * Close a connection to a DB
	 */
	void closeSession();

	/**
	 * return the complete host'path of the DB server
	 * 
	 * @return
	 */
	String getHost();

	/**
	 * return DataBase's URL
	 * 
	 * @return
	 */
	String getUrl();

	/**
	 * @return true if the Database already created
	 */
	boolean dbIsCreated();

	/**
	 * decrement the number of connection
	 */
	void leaveConnection();

}
