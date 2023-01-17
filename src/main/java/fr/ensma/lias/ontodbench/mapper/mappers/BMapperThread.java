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
package fr.ensma.lias.ontodbench.mapper.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;
import fr.ensma.lias.ontodbench.databasedao.databasesession.OntologySessionFactory;
import fr.ensma.lias.ontodbench.mapper.b_mapper.BMapperFactory;
import fr.ensma.lias.ontodbench.mapper.b_mapper.I_BMapper;

/**
 * @author Géraud FOKOU
 */
public class BMapperThread extends A_XMapperThread {

	public BMapperThread(String name, String user, String pwd, String namehost, String port, String dbms) {

		String cmd;

		this.dbname = name;
		this.user = user;
		this.pwd = pwd;
		this.host = namehost;
		this.port = port;
		this.sgbd = dbms;
		connecter = OntologySessionFactory.createOntologySession(dbname, user, pwd, host, port, sgbd);
		cmd = "DROP TABLE IF EXISTS evolutionb CASCADE";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}

		cmd = "CREATE TABLE evolutionb (id SERIAL, evol float8, CONSTRAINT evolb_key PRIMARY KEY(id))";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}
		cmd = "INSERT INTO evolutionb (evol) VALUES (0);";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}
	}

	public BMapperThread(IOntologySession p) {

		String cmd;

		this.dbname = p.getName();
		this.host = p.getnameHost();
		this.user = p.getUser();
		this.port = p.getPort();
		this.sgbd = p.getType();
		this.pwd = p.getPasswd();
		connecter = OntologySessionFactory.createOntologySession(dbname, user, pwd, host, port, sgbd);
		cmd = "DROP TABLE IF EXISTS evolutionb CASCADE";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}

		cmd = "CREATE TABLE evolutionb (id SERIAL, evol float8, CONSTRAINT evolb_key PRIMARY KEY(id))";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}
		cmd = "INSERT INTO evolutionb (evol) VALUES (0);";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param group
	 * @param target
	 * @param name
	 * @param stackSize
	 */
	public BMapperThread() {

	}

	@Override
	public double threadevol() {

		ResultSet stmt;
		String requete;
		double temp;

		requete = "SELECT id, evol FROM evolutionb order by id desc limit 1";
		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			temp = Double.parseDouble(stmt.getString("evol"));
			return temp;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void run() {

		I_BMapper bmapping = BMapperFactory.createBMapper(sgbd);
		bmapping.setConnecter(OntologySessionFactory.createOntologySession(dbname, user, pwd, host, port, sgbd));

		List queryresultTemp;
		this.queryAvresult.clear();
		this.queryMxresult.clear();
		this.queryMnresult.clear();

		if (mapping) {
			bmapping.createShema();
			bmapping.uploadData();
			return;
		}

		long temp;
		allResults = new Object[iterateQuery][10];
		queryresultTemp = new ArrayList();

		for (int i = 0; i < iterateQuery; i++) {
			allResults[i][0] = "ESSAI " + Integer.toString(i + 1);
		}

		for (int i = 0; i < iterateQuery; i++) {
			temp = bmapping.query1();
			while (temp == 0) { // redone iteration in the case of non execution
				// of the query
				temp = bmapping.query1();
			}
			queryresultTemp.add(temp);
			allResults[i][1] = temp;
		}
		this.drawResult(queryresultTemp, "Query1 On Binary Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = bmapping.query2();
			while (temp == 0) {
				temp = bmapping.query2();
			}
			queryresultTemp.add(temp);
			allResults[i][2] = temp;
		}
		this.drawResult(queryresultTemp, "Query2 On Binary Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = bmapping.query3();
			while (temp == 0) {
				temp = bmapping.query3();
			}
			queryresultTemp.add(temp);
			allResults[i][3] = temp;
		}
		this.drawResult(queryresultTemp, "Query3 On Binary Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = bmapping.query4();
			while (temp == 0) {
				temp = bmapping.query4();
			}
			queryresultTemp.add(temp);
			allResults[i][4] = temp;
		}
		this.drawResult(queryresultTemp, "Query4 On Binary Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = bmapping.query5();
			while (temp == 0) {
				temp = bmapping.query5();
			}
			queryresultTemp.add(temp);
			allResults[i][5] = temp;
		}
		this.drawResult(queryresultTemp, "Query5 On Binary Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = bmapping.query6();
			while (temp == 0) {
				temp = bmapping.query6();
			}
			queryresultTemp.add(temp);
			allResults[i][6] = temp;
		}
		this.drawResult(queryresultTemp, "Query6 On Binary Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = bmapping.query7();
			while (temp == 0) {
				temp = bmapping.query7();
			}
			queryresultTemp.add(temp);
			allResults[i][7] = temp;
		}
		this.drawResult(queryresultTemp, "Query7 On Binary Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = bmapping.query8();
			while (temp == 0) {
				temp = bmapping.query8();
			}
			queryresultTemp.add(temp);
			allResults[i][8] = temp;
		}
		this.drawResult(queryresultTemp, "Query8 On Binary Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = bmapping.query9();
			while (temp == 0) {
				temp = bmapping.query9();
			}
			queryresultTemp.add(temp);
			allResults[i][9] = temp;
		}
		this.drawResult(queryresultTemp, "Query9 On Binary Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		return;
	}

}
