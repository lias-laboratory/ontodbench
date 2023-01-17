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

/**
 * @author Géraud FOKOU
 */
public class TripletDAOFactory {

	private static PostgresDAO accessDBPG;

	public static ITripletDAO createITripletDAO(String dbms) {

		if (dbms.compareToIgnoreCase("postgres") == 0) {

			if (accessDBPG == null) {
				accessDBPG = new PostgresDAO();
			}
			return (accessDBPG);
		}

		if (dbms.compareToIgnoreCase("oracle") == 0) {

			// Instantiation of the OracleDAO
			return null;
		}

		if (dbms.compareToIgnoreCase("mysql") == 0) {

			// Instantiation of the MySqlDAO
			return null;
		}

		if (dbms.compareToIgnoreCase("SqlServer") == 0) {

			// Instantiation of the SqlServerDAO
			return null;
		}

		return null;
	}

}
