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
package fr.ensma.lias.ontodbench.mapper.b_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Géraud FOKOU
 */
public class Postgres_BMapper extends A_BMapper {

	private Postgres_BMapper() {

	}

	protected static Postgres_BMapper createMapper() {

		return new Postgres_BMapper();
	}

	@Override
	public void createShema() {
		String requete;
		Process sgbdcmd;
		boolean fin;

		requete = "psql -d " + connecter.getName();
		requete = requete + " -f " + System.getProperty("user.dir")
				+ "\\src\\main\\resources\\Postgres\\Procedures\\B_INIT.sql ";
		requete = requete + " -h " + connecter.getnameHost();
		requete = requete + " -U " + connecter.getUser();
		requete = requete + " -p " + connecter.getPort();
		requete = requete + " -w";

		fin = false;
		try {
			sgbdcmd = Runtime.getRuntime().exec(requete);
			while (!fin) {
				try {
					fin = sgbdcmd.exitValue() == 0;
				} catch (IllegalThreadStateException e1) {
					e1.printStackTrace();
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("debutBschema");
		requete = "SELECT BSCHEMA()";
		try {
			connecter.getConnection().createStatement().executeQuery(requete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("finBschema");
	}

	@Override
	public void uploadData() {
		String requete;
		System.out.println("debutBupload");
		requete = "SELECT BUPLOAD()";
		try {
			connecter.getConnection().createStatement().executeQuery(requete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("finBupload");
	}

	@Override
	public long query1() {
		long num = -1;
		ResultSet stmt;
		String idprop1 = null, idprop2 = null;

		String requete = "SELECT id FROM idproperty WHERE property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idproperty WHERE property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " (SELECT P1.subject " + " FROM " + idprop1 + " AS P1 "
				+ " WHERE P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent') "
				+ " INTERSECT " + " (SELECT P2.subject " + " FROM " + idprop2 + " AS P2 "
				+ " WHERE (P2.pr_value = 'http://www.Department1.University0.edu/GraduateCourse0')) ";

		try {
			num = System.currentTimeMillis();
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			num = System.currentTimeMillis() - num;
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	public long query2() {
		long num = -1;
		ResultSet stmt;
		String idprop1 = null, idprop2 = null;

		String requete = "SELECT id FROM idproperty WHERE property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idproperty WHERE property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT P1.subject " + " FROM " + idprop1 + " AS P1," + idprop2 + " AS P2 "
				+ " WHERE (P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' "
				+ " AND P1.subject=P2.subject "
				+ " AND P2.pr_value = 'http://www.Department0.University0.edu/GraduateCourse0') ";

		try {
			num = System.currentTimeMillis();
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			num = System.currentTimeMillis() - num;
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long query3() {
		long num = -1;
		ResultSet stmt;
		String idprop1 = null, idprop2 = null;

		String requete = "SELECT id FROM idproperty WHERE (property ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " (SELECT P1.subject " + " FROM " + idprop1 + " AS P1 "
				+ " WHERE P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication') "
				+ " INTERSECT " + " (SELECT P2.subject " + " FROM   " + idprop2 + " AS P2 "
				+ " WHERE  P2.pr_value = 'http://www.Department0.University0.edu/AssistantProfessor0') ";

		try {
			num = System.currentTimeMillis();
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			num = System.currentTimeMillis() - num;
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long query4() {
		long num = -1;
		ResultSet stmt;
		String idprop1 = null, idprop2 = null;

		String requete = "SELECT id FROM idproperty WHERE (property ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT P1.subject " + " FROM " + idprop1 + " AS P1," + idprop2 + " AS P2 "
				+ " WHERE (P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication' "
				+ " AND P1.subject=P2.subject "
				+ " AND P2.pr_value = 'http://www.Department0.University0.edu/AssistantProfessor0')  ";

		try {
			num = System.currentTimeMillis();
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			num = System.currentTimeMillis() - num;
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long query5() {
		long num = -1;
		ResultSet stmt;
		String idprop1 = null, idprop2 = null;

		String requete = "SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " (SELECT P1.subject " + " FROM  " + idprop1 + " AS P1 "
				+ " WHERE P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor') "
				+ " INTERSECT " + " (SELECT P2.subject " + " FROM " + idprop2 + " AS P2 "
				+ " WHERE (P2.pr_value = 'http://www.Department0.University0.edu'))  ";

		try {
			num = System.currentTimeMillis();
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			num = System.currentTimeMillis() - num;
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long query6() {

		long num = -1;
		ResultSet stmt;
		String idprop1 = null, idprop2 = null;

		String requete = "SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT P1.subject " + "FROM  " + idprop1 + " AS P1," + idprop2 + " AS P2 "
				+ " WHERE (P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor' "
				+ " AND P1.subject=P2.subject " + " AND P2.pr_value = 'http://www.Department0.University0.edu')  ";

		try {
			num = System.currentTimeMillis();
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			num = System.currentTimeMillis() - num;
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long query7() {

		long num = -1;
		ResultSet stmt;
		String idprop1 = null, idprop2 = null, idprop3 = null, idprop4 = null;

		String requete = " SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#memberOf')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#subOrganizationOf')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop3 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#undergraduateDegreeFrom')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop4 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT P1.subject,P3.subject,P4.subject " + " FROM " + idprop1 + " AS P1, " + idprop2 + " AS P2, "
				+ idprop3 + " AS P3, " + idprop4 + " AS P4, " + idprop1 + " AS P5  "
				+ " WHERE (P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' "
				+ " AND P1.subject=P2.subject  " + " AND P2.pr_value=P3.subject " + " AND P3.pr_value=P4.subject "
				+ " AND P5.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#University' "
				+ "AND P4.subject = P1.subject " + " AND P4.pr_value = P5.subject) ";

		try {
			num = System.currentTimeMillis();
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			num = System.currentTimeMillis() - num;
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long query8() {
		long num = -1;
		ResultSet stmt;
		String idprop1 = null, idprop2 = null, idprop3 = null, idprop4 = null, idprop5 = null;

		String requete = " SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop3 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop4 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop5 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT P1.subject,P3.pr_value,P4.pr_value,P5.pr_value " + " FROM " + idprop1 + " AS P1, " + idprop2
				+ " AS P2, " + idprop3 + " AS P3, " + idprop4 + " AS P4, " + idprop5 + " AS P5 "
				+ " WHERE ((P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssistantProfessor' "
				+ " OR P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssociateProfessor' "
				+ " OR P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor' ) "
				+ " AND P1.subject=P2.subject " + " AND P2.pr_value = 'http://www.Department0.University0.edu' "
				+ " AND P1.subject=P3.subject " + "AND P1.subject=P4.subject " + " AND P1.subject=P5.subject) "
				+ " ORDER BY (P1.subject) ";

		try {
			num = System.currentTimeMillis();
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			num = System.currentTimeMillis() - num;
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long query9() {
		long num = -1;
		ResultSet stmt;
		String idprop1 = null;

		String requete = " SELECT id FROM idproperty WHERE (property LIKE 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT P.pr_value, COUNT(P.subject) " + " FROM " + idprop1 + " AS P "
				+ " WHERE (P.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication') "
				+ " GROUP BY(P.pr_value) ";

		try {
			num = System.currentTimeMillis();
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			num = System.currentTimeMillis() - num;
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long query10() {
		long num = -1;
		ResultSet stmt;
		String idprop1 = null, idprop2 = null, idprop3 = null, idprop4 = null;

		String requete = " SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#advisor')')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#teacherOf')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop3 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idprop4 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT P1.subject,P3.subject,P4.pr_value " + " FROM " + idprop1 + " AS P1, " + idprop2 + " AS P2, "
				+ idprop3 + " AS P3, " + idprop4 + " AS P4 "
				+ " WHERE ((P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' "
				+ " OR P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#UndergraduateStudent') "
				+ " AND P1.subject=P2.subject " + " AND P2.pr_value = P3.subject " + " AND P3.pr_value=P4.pr_value "
				+ " AND P4.subject=P1.subject) ";

		try {
			num = System.currentTimeMillis();
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			num = System.currentTimeMillis() - num;
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long query11() {
		return 0;
	}

	@Override
	public long query12() {
		return 0;
	}

	@Override
	public long query13() {
		return 0;
	}
}
