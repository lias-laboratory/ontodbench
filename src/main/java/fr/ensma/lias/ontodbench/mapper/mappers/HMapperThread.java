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
import fr.ensma.lias.ontodbench.mapper.h_mapper.HMapperFactory;
import fr.ensma.lias.ontodbench.mapper.h_mapper.I_HMapper;

/**
 * @author Géraud FOKOU
 */
public class HMapperThread extends A_XMapperThread {

	public HMapperThread(String name, String user, String pwd, String namehost, String port, String dbms) {

		String cmd;

		this.dbname = name;
		this.user = user;
		this.pwd = pwd;
		this.host = namehost;
		this.port = port;
		this.sgbd = dbms;
		connecter = OntologySessionFactory.createOntologySession(dbname, user, pwd, host, port, sgbd);

		cmd = "DROP TABLE IF EXISTS evolutionh CASCADE";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}

		cmd = "CREATE TABLE evolutionh (id SERIAL, evol float8, CONSTRAINT evolh_key PRIMARY KEY(id))";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}
		cmd = "INSERT INTO evolutionh (evol) VALUES (0);";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}
	}

	public HMapperThread(IOntologySession p) {

		String cmd;

		this.dbname = p.getName();
		this.host = p.getnameHost();
		this.user = p.getUser();
		this.port = p.getPort();
		this.sgbd = p.getType();
		this.pwd = p.getPasswd();
		connecter = OntologySessionFactory.createOntologySession(dbname, user, pwd, host, port, sgbd);
		cmd = "DROP TABLE IF EXISTS evolutionh CASCADE";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}

		cmd = "CREATE TABLE evolutionh (id SERIAL, evol float8, CONSTRAINT evolh_key PRIMARY KEY(id))";
		try {
			connecter.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}
		cmd = "INSERT INTO evolutionh (evol) VALUES (0);";
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
	public HMapperThread() {

	}

	@Override
	public double threadevol() {

		ResultSet stmt;
		String requete;
		double temp;

		requete = "SELECT id, evol FROM evolutionh order by id desc limit 1";
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
		I_HMapper hmapping = HMapperFactory.createHMapper(sgbd);
		hmapping.setConnecter(OntologySessionFactory.createOntologySession(dbname, user, pwd, host, port, sgbd));
		List queryresultTemp;
		this.queryAvresult.clear();
		this.queryMxresult.clear();
		this.queryMnresult.clear();

		if (mapping) {
			hmapping.createShema();
			hmapping.uploadData();
			return;
		}

		long temp;
		allResults = new Object[iterateQuery][10];
		queryresultTemp = new ArrayList();

		for (int i = 0; i < iterateQuery; i++) {
			allResults[i][0] = "ESSAI " + Integer.toString(i + 1);
		}

		for (int i = 0; i < iterateQuery; i++) {
			temp = hmapping.query1();
			while (temp == 0) { // redone iteration in the case of non execution
				// of the query
				temp = hmapping.query1();
			}
			queryresultTemp.add(temp);
			allResults[i][1] = temp;
		}
		this.drawResult(queryresultTemp, "Query1 On Horizontal Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = hmapping.query2();
			while (temp == 0) {
				temp = hmapping.query2();
			}
			queryresultTemp.add(temp);
			allResults[i][2] = temp;
		}
		this.drawResult(queryresultTemp, "Query2 On Horizontal Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = hmapping.query3();
			while (temp == 0) {
				temp = hmapping.query3();
			}
			queryresultTemp.add(temp);
			allResults[i][3] = temp;
		}
		this.drawResult(queryresultTemp, "Query3 On Horizontal Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = hmapping.query4();
			while (temp == 0) {
				temp = hmapping.query4();
			}
			queryresultTemp.add(temp);
			allResults[i][4] = temp;
		}
		this.drawResult(queryresultTemp, "Query4 On Horizontal Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = hmapping.query5();
			while (temp == 0) {
				temp = hmapping.query5();
			}
			queryresultTemp.add(temp);
			allResults[i][5] = temp;
		}
		this.drawResult(queryresultTemp, "Query5 On Horizontal Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = hmapping.query6();
			while (temp == 0) {
				temp = hmapping.query6();
			}
			queryresultTemp.add(temp);
			allResults[i][6] = temp;
		}
		this.drawResult(queryresultTemp, "Query6 On Horizontal Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = hmapping.query7();
			while (temp == 0) {
				temp = hmapping.query7();
			}
			queryresultTemp.add(temp);
			allResults[i][7] = temp;
		}
		this.drawResult(queryresultTemp, "Query7 On Horizontal Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = hmapping.query8();
			while (temp == 0) {
				temp = hmapping.query8();
			}
			queryresultTemp.add(temp);
			allResults[i][8] = temp;
		}
		this.drawResult(queryresultTemp, "Query8 On Horizontal Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = hmapping.query9();
			while (temp == 0) {
				temp = hmapping.query9();
			}
			queryresultTemp.add(temp);
			allResults[i][9] = temp;
		}
		this.drawResult(queryresultTemp, "Query9 On Horizontal Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		return;
	}
}
