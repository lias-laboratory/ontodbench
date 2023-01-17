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
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Géraud FOKOU
 */
public class PostgresOntologySession extends AOntologySession {

	/**
	 * Tell if is the DB is created
	 */
	private static boolean dbCreated = false;

	/**
	 * Limit of connection
	 */
	private static int limitConnected = 100;

	/**
	 * Number of connection
	 */
	private static int numConnected = 0;

	/**
	 * Constructor
	 */
	private PostgresOntologySession() {

		TYPE = "postgres";
		JDBCCONNECT = "jdbc:postgresql";
		numConnected = numConnected + 1;
	}

	/**
	 * Creator of object
	 * 
	 * @return
	 */
	protected static PostgresOntologySession createConnector() {

		if (numConnected < limitConnected) {
			return new PostgresOntologySession();
		}
		return null;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getJdbcconnect() {
		return JDBCCONNECT;
	}

	@Override
	public void setnameHost(String host) {
		this.host = host;
	}

	@Override
	public String getnameHost() {
		return this.host;
	}

	@Override
	public void setPort(String port) {
		this.port = port;

	}

	@Override
	public String getPort() {
		return this.port;
	}

	@Override
	public void setUser(String user) {
		this.user = user;

	}

	@Override
	public String getUser() {
		return this.user;
	}

	@Override
	public void setPasswd(String pwd) {
		this.passwd = pwd;
	}

	@Override
	public String getPasswd() {
		return this.passwd;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void createDataBase() {

		try {
			connect = DriverManager.getConnection(this.getHost(), user, passwd);
			try {
				connect.createStatement().executeUpdate("DROP DATABASE IF EXISTS " + this.name);
			} catch (Exception e) {
				e.printStackTrace();
			}
			connect.createStatement()
					.executeUpdate("CREATE DATABASE " + this.getName() + " WITH   OWNER     = postgres "
							+ " ENCODING  = 'UTF8' " + " TABLESPACE = pg_default " + " CONNECTION LIMIT = 10" + " ; ");
			dbCreated = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connect = null;

	}

	@Override
	public Connection getConnection() {

		if (connect == null) {

			try {
				Class.forName("org.postgresql.Driver").newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			try {
				connect = DriverManager.getConnection(this.getUrl(), user, passwd);
				return connect;

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}
		return connect;
	}

	@Override
	public void closeSession() {

		if (connect != null) {
			try {
				connect.close();
				numConnected = numConnected - 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connect = null;
		}
	}

	@Override
	public String getHost() {
		return JDBCCONNECT + "://" + host + ":" + port + "/";
	}

	@Override
	public String getUrl() {
		return this.getHost() + this.getName();
	}

	@Override
	public boolean dbIsCreated() {
		return dbCreated;
	}

	@Override
	public void leaveConnection() {
		numConnected = numConnected - 1;
	}
}
