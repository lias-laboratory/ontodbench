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

import java.io.*;
import java.sql.*;
import java.util.*;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;
import fr.ensma.lias.ontodbench.model.conversion.ControlThread;

/**
 * @author Géraud FOKOU
 */
public class PostgresDAO implements ITripletDAO {

	public static double evolutionupload = 0;

	private boolean materialized;

	/**
	 * Constructor visible only in the package
	 */
	protected PostgresDAO() {

	}

	@Override
	public void initialisation(IOntologySession p, boolean materialized_V) {
		boolean fin;
		Process sgbdcmd;
		String requete;
		String cmd = "psql ";

		materialized = materialized_V;

		cmd = cmd + " -d " + p.getName();
		if (materialized_V) {
			cmd = cmd + " -f " + System.getProperty("user.dir")
					+ "\\src\\main\\resources\\Postgres\\Procedures\\V_INIT1.sql ";
		} else {
			cmd = cmd + " -f " + System.getProperty("user.dir")
					+ "\\src\\main\\resources\\Postgres\\Procedures\\V_INIT2.sql ";
		}
		cmd = cmd + " -h " + p.getnameHost();
		cmd = cmd + " -U " + p.getUser();
		cmd = cmd + " -p " + p.getPort();
		cmd = cmd + " -w ";

		fin = false;

		try {
			sgbdcmd = Runtime.getRuntime().exec(cmd);

			while (!fin) {
				try {
					fin = sgbdcmd.exitValue() == 0;

					BufferedReader input = new BufferedReader(new InputStreamReader(sgbdcmd.getErrorStream()));
					String line;
					while ((line = input.readLine()) != null) {
						System.out.println(line);
					}
				} catch (IllegalThreadStateException e1) {
					Thread.sleep(100);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		requete = "SELECT VSCHEMA()";
		try {
			p.getConnection().createStatement().executeQuery(requete);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT VMATERIALIZER()";
		try {
			p.getConnection().createStatement().executeQuery(requete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initialisation(IOntologySession p) {

		this.initialisation(p, true);
	}

	@Override
	public void updateDB(IOntologySession p) {

		BufferedReader inputfile;
		File logFile;
		boolean fin;
		String requete;
		Process sgbdcmd;

		if (materialized) {
			requete = "SELECT VUPDATE()";
			try {
				p.getConnection().createStatement().executeQuery(requete);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// init file evolution
		logFile = new File("d:\\vupload.txt");
		try {
			logFile.createNewFile();
			PrintStream writeFile = new PrintStream(logFile);
			writeFile.print(0);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		// end init

		requete = "psql -d " + p.getName();
		requete = requete + " -f " + System.getProperty("user.dir")
				+ "\\src\\main\\resources\\Postgres\\Requetes\\V_UPLOAD.sql ";
		requete = requete + " -h " + p.getnameHost();
		requete = requete + " -U " + p.getUser();
		requete = requete + " -p " + p.getPort();
		requete = requete + " -w ";

		fin = false;
		try {
			sgbdcmd = Runtime.getRuntime().exec(requete);
			while (!fin) {
				try {
					BufferedReader input = new BufferedReader(new InputStreamReader(sgbdcmd.getErrorStream()));
					String line;
					while ((line = input.readLine()) != null) {
						System.out.println(line);
					}

					fin = sgbdcmd.exitValue() == 0;

					// take the update on file
					try {
						inputfile = new BufferedReader(new FileReader(logFile));
						if (inputfile.ready()) {
							ControlThread.setEvolution(Double.parseDouble(inputfile.readLine()));
						}
						inputfile.close();

					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					}
					// End taking

				} catch (IllegalThreadStateException e1) {
					Thread.sleep(100);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addAtome(IOntologySession p, String sub, String pred, String obj) {

		sub = sub.replace("'", "''");
		pred = pred.replace("'", "''");
		obj = obj.replace("'", "''");
		String cmd = "INSERT INTO triplets (subject,predicat,object) VALUES ('" + sub + "','" + pred + "','" + obj
				+ "')";
		try {
			p.getConnection().createStatement().executeUpdate(cmd);
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int numberOfTriplets(IOntologySession p) {

		int num = -1;

		ResultSet stmt;
		String requete = "SELECT  COUNT(*) AS NUM_TRIPLETS FROM v_subject";

		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			num = stmt.getInt("NUM_TRIPLETS");
		} catch (SQLException e) {
			System.out.println(e.toString() + "\n" + e.fillInStackTrace().toString());
		}
		return num;
	}

	@Override
	public int numberOfdistinctSubject(IOntologySession p) {

		int num = -1;

		ResultSet stmt;
		String requete = "SELECT COUNT(*) AS NUM_SUBJECT FROM (SELECT DISTINCT ON (subject) subject FROM v_subject) AS REAL_SUBJECT";

		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			num = stmt.getInt("NUM_SUBJECT");
		} catch (SQLException e) {
			System.out.println(e.toString() + "\n" + e.fillInStackTrace().toString());
		}
		return num;
	}

	@Override
	public int numberOfdistinctPredicat(IOntologySession p) {

		int num = -1;

		ResultSet stmt;
		String requete = "SELECT COUNT(*) AS NUM_PREDICAT FROM (SELECT DISTINCT ON (predicat) predicat FROM v_predicat WHERE (predicat NOT LIKE '%#type')) AS REAL_PREDICAT";

		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			num = stmt.getInt("NUM_PREDICAT");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public int numberOfdistinctObject(IOntologySession p) {

		int num = -1;

		ResultSet stmt;
		String requete = "SELECT COUNT(*) AS NUM_OBJECT FROM (SELECT DISTINCT ON (object) object FROM v_object) AS REAL_OBJECT";

		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			num = stmt.getInt("NUM_OBJECT");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public int numberOfTypes(IOntologySession p) {

		int num = -1;

		ResultSet stmt;
		String requete = "SELECT COUNT(*) AS NUM_TYPE FROM (SELECT DISTINCT ON (object) object FROM v_object WHERE predicat  LIKE '%#type') AS TAB_TYPE ";

		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			num = stmt.getInt("NUM_TYPE");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public double averageTripletsBySubject(IOntologySession p) {

		double num = -1;
		ResultSet currentResult;
		String requete = "SELECT SUM(OUTDEGREE) AS SUM_OF_OUTDEGREE FROM (SELECT subject, COUNT(object) AS OUTDEGREE FROM v_predicat WHERE (predicat NOT LIKE '%#type') GROUP BY subject) AS TAB_OUTDEGREE";
		try {
			currentResult = p.getConnection().createStatement().executeQuery(requete);
			currentResult.next();
			num = currentResult.getInt(1);
			num = num / this.numberOfdistinctSubject(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (num);
	}

	@Override
	public double averageTripletsByObject(IOntologySession p) {

		double num = -1;
		ResultSet currentResult;
		String requete = "SELECT SUM(INDEGREE) AS SUM_OF_INTDEGREE FROM (SELECT object, COUNT(subject) AS INDEGREE FROM v_predicat WHERE (predicat NOT LIKE '%#type') GROUP BY object) AS TAB_INDEGREE";
		try {
			currentResult = p.getConnection().createStatement().executeQuery(requete);
			currentResult.next();
			num = currentResult.getInt(1);
			num = num / this.numberOfdistinctObject(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (num);
	}

	@Override
	public double averagePropertyByType(IOntologySession p) {

		double num = -1;
		ResultSet currentResult;
		String requete = "SELECT COUNT(type),SUM(NBRECMP) FROM (SELECT type,COUNT(champ) AS NBRECMP FROM propertytab GROUP BY(type)) AS TAB";
		try {
			currentResult = p.getConnection().createStatement().executeQuery(requete);
			currentResult.next();
			num = currentResult.getInt(1);
			num = currentResult.getInt(2) / num;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return num;
	}

	@Override
	public double numberOfNullChamps(IOntologySession p) {

		double num = -1;

		ResultSet stmt;
		String requete = "SELECT COUNT(*) AS NUM_NULL FROM ( " + "SELECT TAB.subject AS subject,P.champ AS CHAMP "
				+ "FROM (SELECT subject,object FROM v_predicat WHERE predicat LIKE '%#type' ORDER BY object ) AS TAB,propertytab AS P "
				+ "WHERE TAB.object=P.type " + "EXCEPT "
				+ "SELECT subject,predicat AS CHAMP FROM v_predicat ) AS TABNUL ";

		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			num = stmt.getInt("NUM_NULL");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	@SuppressWarnings("rawtypes")
	public List numberOfNullByTypes(IOntologySession p) {

		String[] elt;
		List<String[]> elts = new ArrayList<String[]>();

		ResultSet stmt;
		String requete = "SELECT TYPE1,COUNT(*) AS NBR FROM  ((SELECT TAB.subject AS subject,TAB.object AS TYPE1,P.champ AS champ "
				+ " FROM ( SELECT subject,object FROM v_predicat WHERE predicat LIKE '%#type' ORDER BY object ) AS TAB,propertytab AS P "
				+ " WHERE TAB.object=P.type) " + " EXCEPT "
				+ " (SELECT T.subject AS subject,TAB1.object AS TYPE1, T.predicat AS champ "
				+ " FROM ( SELECT subject,object FROM v_predicat WHERE predicat LIKE '%#type' ORDER BY object ) AS TAB1, triplets AS T "
				+ " WHERE TAB1.subject=T.subject)) AS TAB2 " + " GROUP BY TYPE1 ORDER BY (TYPE1) ";
		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			while (stmt.next()) {
				elt = new String[2];
				elt[0] = stmt.getString("TYPE1");
				elt[1] = Integer.toString(stmt.getInt("NBR"));
				elts.add(elt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return elts;
	}

	@SuppressWarnings("rawtypes")
	public List numberOfSubjectByTypes(IOntologySession p) {

		String[] elt;
		List<String[]> elts = new ArrayList<String[]>();

		ResultSet stmt;
		String requete = "SELECT type,nbrinstance FROM types ORDER BY (type) ";

		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			while (stmt.next()) {
				elt = new String[2];
				elt[0] = stmt.getString("TYPE");
				elt[1] = Integer.toString(stmt.getInt("nbrinstance"));
				elts.add(elt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return elts;
	}

	@SuppressWarnings("rawtypes")
	public List numberOfPropertyByTypes(IOntologySession p) {

		String[] elt;
		List<String[]> elts = new ArrayList<String[]>();

		ResultSet stmt;
		String requete = "SELECT type, COUNT(*) AS NBRE FROM propertytab GROUP BY (type) ORDER BY (type)";

		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			while (stmt.next()) {
				elt = new String[2];
				elt[0] = stmt.getString("type");
				elt[1] = Integer.toString(stmt.getInt("NBRE"));
				elts.add(elt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elts;
	}

	@SuppressWarnings("rawtypes")
	public List coverage(IOntologySession p) {

		String[] elt;
		List<String[]> elts = new ArrayList<String[]>();

		ResultSet stmt;
		String requete = " SELECT T.type, (OC*1.0)/(PT*nbrinstance*1.0) AS NBR_TT "
				+ " FROM types AS T,(SELECT type ,COUNT(champ) AS PT,SUM(occurrences) AS OC FROM propertytab GROUP BY type) AS TAB "
				+ " WHERE (T.type=TAB.type) ORDER BY type ";
		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			while (stmt.next()) {
				elt = new String[2];
				elt[0] = stmt.getString("type");
				elt[1] = Double.toString(stmt.getDouble("NBR_TT"));
				elts.add(elt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elts;
	}

	@SuppressWarnings("rawtypes")
	public List weightByType(IOntologySession p) {

		String[] elt;
		List<String[]> elts = new ArrayList<String[]>();
		ResultSet stmt;

		String requete = " SELECT T.type AS TYPE, (NBR_CM+T.nbrinstance)/TOTAL  AS POIDS "
				+ "	  FROM (SELECT type,COUNT(*) AS NBR_CM FROM propertytab GROUP BY type) AS TAB ,"
				+ "    	       (SELECT SUM(NBR_CM1+NBR_INST) AS TOTAL FROM (SELECT T1.type, NBR_CM1 ,T1.nbrinstance AS NBR_INST"
				+ "	 						    FROM (SELECT type,COUNT(*) AS NBR_CM1 FROM propertytab GROUP BY type) AS TAB3,types AS T1"
				+ "			                 		    WHERE (T1.type=TAB3.type)) AS TAB1) AS TAB2,"
				+ "		types AS T " + " WHERE (T.type=TAB.type) ORDER BY (type) ";
		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			while (stmt.next()) {
				elt = new String[2];
				elt[0] = stmt.getString("TYPE");
				elt[1] = Double.toString((stmt.getDouble("POIDS")));
				elts.add(elt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elts;
	}

	@Override
	public double coherence(IOntologySession p) {

		double num = -1;

		ResultSet stmt;
		String requete = "SELECT SUM(W.POIDS*C.NBR_TT) AS COH "
				+ " FROM 	(SELECT T.type AS TYPE, (NBR_CM+T.nbrinstance)/TOTAL  AS POIDS "
				+ " FROM (SELECT type,COUNT(*) AS NBR_CM FROM propertytab GROUP BY TYPE) AS TAB , "
				+ " (SELECT SUM(NBR_CM1+NBR_INST) AS TOTAL FROM (SELECT T1.type, NBR_CM1 ,T1.nbrinstance AS NBR_INST "
				+ " FROM (SELECT type,COUNT(*) AS NBR_CM1 FROM propertytab GROUP BY type) AS TAB3,types AS T1 "
				+ " WHERE (T1.type=TAB3.type)) AS TAB1) AS TAB2,types AS T "
				+ " WHERE (T.type=TAB.type) ORDER BY (TYPE)) AS W, "
				+ " (SELECT T.type AS TYPE, ((OC*1.0)/(PT*NBRINSTANCE*1.0)) AS NBR_TT "
				+ " FROM types AS T,(SELECT type ,COUNT(champ) AS PT,SUM(OCCURRENCES) AS OC FROM propertytab GROUP BY type) AS TAB "
				+ " WHERE (T.type=TAB.TYPE)) AS C " + " WHERE (C.TYPE=W.TYPE) ";
		try {
			stmt = p.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			num = stmt.getDouble("COH");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

}
