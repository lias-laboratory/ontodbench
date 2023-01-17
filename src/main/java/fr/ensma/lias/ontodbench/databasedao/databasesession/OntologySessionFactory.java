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

/**
 * @author Géraud FOKOU
 */
public class OntologySessionFactory {

	public static IOntologySession createOntologySession(String name, String user, String pwd, String namehost,
			String port, String dbms) {

		if (dbms.compareToIgnoreCase("postgres") == 0) {

			PostgresOntologySession currentInstance = PostgresOntologySession.createConnector();

			currentInstance.setName(name);
			currentInstance.setUser(user);
			currentInstance.setPasswd(pwd);
			currentInstance.setPort(port);
			currentInstance.setnameHost(namehost);

			return currentInstance;
		}

		if (dbms.compareToIgnoreCase("oracle") == 0) {

			// Instantiation of OracleOntologySession
			return null;
		}

		if (dbms.compareToIgnoreCase("mysql") == 0) {

			// Instantiation of MySqlOntologySession
			return null;
		}
		if (dbms.compareToIgnoreCase("SqlServerOntologySession") == 0) {

			// Instantiation of SqlServerOntologySession
			return null;
		}

		return null;

	}

	public static IOntologySession createOntologySessionDB(String name, String user, String pwd, String namehost,
			String port, String dbms) {

		if (dbms.compareToIgnoreCase("postgres") == 0) {

			PostgresOntologySession currentInstance = PostgresOntologySession.createConnector();

			currentInstance.setName(name);
			currentInstance.setUser(user);
			currentInstance.setPasswd(pwd);
			currentInstance.setPort(port);
			currentInstance.setnameHost(namehost);

			if (!currentInstance.dbIsCreated()) {
				currentInstance.createDataBase();
			}
			return currentInstance;
		}

		if (dbms.compareToIgnoreCase("oracle") == 0) {

			// Instantiation of OracleOntologySession
			return null;
		}

		if (dbms.compareToIgnoreCase("mysql") == 0) {

			// Instantiation of MySqlOntologySession
			return null;
		}
		if (dbms.compareToIgnoreCase("SqlServerOntologySession") == 0) {

			// Instantiation of SqlServerOntologySession
			return null;
		}
		return null;
	}
}
