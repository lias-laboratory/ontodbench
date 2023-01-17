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

import java.util.ArrayList;
import java.util.List;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;
import fr.ensma.lias.ontodbench.databasedao.databasesession.OntologySessionFactory;
import fr.ensma.lias.ontodbench.mapper.v_mapper.I_VMapper;
import fr.ensma.lias.ontodbench.mapper.v_mapper.VMapperFactory;

/**
 * @author Géraud FOKOU
 */
public class VMapperThread extends A_XMapperThread {
	/**
	 * 
	 */
	public VMapperThread(String name, String user, String pwd, String namehost, String port, String dbms) {

		this.dbname = name;
		this.user = user;
		this.pwd = pwd;
		this.host = namehost;
		this.port = port;
		this.sgbd = dbms;

	}

	public VMapperThread(IOntologySession p) {

		this.dbname = p.getName();
		this.host = p.getnameHost();
		this.user = p.getUser();
		this.port = p.getPort();
		this.sgbd = p.getType();
		this.pwd = p.getPasswd();
	}

	/**
	 * @param group
	 * @param target
	 * @param name
	 * @param stackSize
	 */
	public VMapperThread() {

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void run() {

		I_VMapper vmapping = VMapperFactory.createVMapper(sgbd);
		vmapping.setConnecter(OntologySessionFactory.createOntologySession(dbname, user, pwd, host, port, sgbd));
		List queryresultTemp;
		this.queryAvresult.clear();
		this.queryMxresult.clear();
		this.queryMnresult.clear();

		if (mapping) {
			return;
		}
		long temp;
		allResults = new Object[iterateQuery][10];
		queryresultTemp = new ArrayList();

		for (int i = 0; i < iterateQuery; i++) {
			allResults[i][0] = "ESSAI " + Integer.toString(i + 1);
		}

		for (int i = 0; i < iterateQuery; i++) {
			temp = vmapping.query1();
			while (temp == 0) { // redone iteration in the case of non execution of the query
				temp = vmapping.query1();
			}
			queryresultTemp.add(temp);
			allResults[i][1] = temp;
		}
		this.drawResult(queryresultTemp, "Query1 On Vertical Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = vmapping.query2();
			while (temp == 0) {
				temp = vmapping.query2();
			}
			queryresultTemp.add(temp);
			allResults[i][2] = temp;
		}
		this.drawResult(queryresultTemp, "Query2 On Vertical Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = vmapping.query3();
			while (temp == 0) {
				temp = vmapping.query3();
			}
			queryresultTemp.add(temp);
			allResults[i][3] = temp;
		}
		this.drawResult(queryresultTemp, "Query3 On Vertical Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = vmapping.query4();
			while (temp == 0) {
				temp = vmapping.query4();
			}
			queryresultTemp.add(temp);
			allResults[i][4] = temp;
		}
		this.drawResult(queryresultTemp, "Query4 On Vertical Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = vmapping.query5();
			while (temp == 0) {
				temp = vmapping.query5();
			}
			queryresultTemp.add(temp);
			allResults[i][5] = temp;
		}
		this.drawResult(queryresultTemp, "Query5 On Vertical Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = vmapping.query6();
			while (temp == 0) {
				temp = vmapping.query6();
			}
			queryresultTemp.add(temp);
			allResults[i][6] = temp;
		}
		this.drawResult(queryresultTemp, "Query6 On Vertical Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = vmapping.query7();
			while (temp == 0) {
				temp = vmapping.query7();
			}
			queryresultTemp.add(temp);
			allResults[i][7] = temp;
		}
		this.drawResult(queryresultTemp, "Query7 On Vertical Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = vmapping.query8();
			while (temp == 0) {
				temp = vmapping.query8();
			}
			queryresultTemp.add(temp);
			allResults[i][8] = temp;
		}
		this.drawResult(queryresultTemp, "Query8 On Vertical Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		queryresultTemp.clear();
		for (int i = 0; i < iterateQuery; i++) {
			temp = vmapping.query9();
			while (temp == 0) {
				temp = vmapping.query9();
			}
			queryresultTemp.add(temp);
			allResults[i][9] = temp;
		}
		this.drawResult(queryresultTemp, "Query9 On Vertical Representation");
		this.queryAvresult.add(this.averageTime(queryresultTemp));
		this.queryMxresult.add(this.maximalTime(queryresultTemp));
		this.queryMnresult.add(this.minimalTime(queryresultTemp));

		return;
	}

	@Override
	public double threadevol() {
		return 0;
	}

}
