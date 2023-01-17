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
package fr.ensma.lias.ontodbench.mapper.h_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Géraud FOKOU
 */
public class Postgres_HMapper extends A_HMapper {

	private Postgres_HMapper() {
	}

	protected static Postgres_HMapper createMapper() {
		return new Postgres_HMapper();
	}

	@Override
	public void createShema() {
		String requete;
		Process sgbdcmd;
		boolean fin;

		requete = "psql -d " + connecter.getName();
		requete = requete + " -f " + System.getProperty("user.dir")
				+ "\\src\\main\\resources\\Postgres\\Procedures\\H_INIT.sql ";
		requete = requete + " -h " + connecter.getnameHost();
		requete = requete + " -U " + connecter.getUser();
		requete = requete + " -p " + connecter.getPort();
		requete = requete + " -w ";

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

		System.out.println("debutHschema");
		requete = "SELECT HSCHEMA()";
		try {
			connecter.getConnection().createStatement().executeQuery(requete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("finHschema");
	}

	@Override
	public void uploadData() {

		String requete;

		System.out.println("debutHupload");
		requete = "SELECT HUPLOAD()";
		try {
			connecter.getConnection().createStatement().executeQuery(requete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("finHupload");
	}

	@Override
	public long query1() {

		long num = -1;
		ResultSet stmt;
		String idclass = null, idpred = null;

		String requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE (id_class ='" + idclass
				+ "'  AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "(SELECT C1.subject " + "FROM " + idclass + " AS C1 " + "WHERE (C1." + idpred
				+ " = 'http://www.Department0.University0.edu/GraduateCourse0')) " + "UNION " + "(SELECT L.subject "
				+ "FROM left_over AS L, " + idclass + " AS C2 "
				+ "WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' "
				+ "AND L.object = 'http://www.Department0.University0.edu/GraduateCourse0' "
				+ "AND L.subject=C2.subject)) ";

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
		String idclass = null, idpred = null;

		String requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE (id_class = '" + idclass
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse')";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "(SELECT C1.subject " + "FROM " + idclass + " AS C1 " + "WHERE (C1." + idpred
				+ " = 'http://www.Department0.University0.edu/GraduateCourse0')) " + "UNION " + "(SELECT L.subject "
				+ "FROM left_over AS L, " + idclass + " AS C2 "
				+ "WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' "
				+ "AND L.object = 'http://www.Department0.University0.edu/GraduateCourse0' "
				+ "AND L.subject=C2.subject)) ";

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
		String idclass1 = null;
		String idpred1 = null;

		String requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class = '" + idclass1
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "(SELECT C1.subject " + "FROM " + idclass1 + " AS C1 " + "WHERE (C1." + idpred1
				+ " = 'http://www.Department0.University0.edu/AssistantProfessor0')) " + "UNION (SELECT L.subject "
				+ "FROM left_over AS L, " + idclass1 + " AS C2 "
				+ "WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' "
				+ "AND L.object = 'http://www.Department0.University0.edu/AssistantProfessor0' "
				+ "AND L.subject=C2.subject))  ";

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
		String idclass1 = null;
		String idpred1 = null;

		String requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class = '" + idclass1
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "(SELECT C1.subject " + "FROM " + idclass1 + " AS C1 " + "WHERE (C1." + idpred1
				+ " = 'http://www.Department0.University0.edu/AssistantProfessor0')) " + "UNION (SELECT L.subject "
				+ "FROM left_over AS L, " + idclass1 + " AS C2 "
				+ "WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' "
				+ "AND L.object = 'http://www.Department0.University0.edu/AssistantProfessor0' "
				+ "AND L.subject=C2.subject))  ";

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
		String idclass1 = null;
		String idpred1 = null;

		String requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class = '" + idclass1
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT C1.subject " + "FROM " + idclass1 + " AS C1 " + "WHERE (C1." + idpred1
				+ " = 'http://www.Department0.University0.edu')  ";

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
		String idclass1 = null;
		String idpred1 = null;

		String requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class = '" + idclass1
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT C1.subject " + " FROM " + idclass1 + " AS C1 " + " WHERE (C1." + idpred1
				+ " = 'http://www.Department0.University0.edu')  ";

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
		String idclass1 = null, idclass2 = null, idclass3 = null;
		String idpred1 = null, idpred2 = null, idpred12 = null;

		String requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Department'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#University'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass3 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class= '" + idclass1
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#memberOf'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class= '" + idclass1
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#undergraduateDegreeFrom'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred12 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class= '" + idclass2
				+ "'  AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#subOrganizationOf'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		requete = "SELECT C1.subject,C2.subject,C3.subject " + "FROM   " + idclass1 + " AS C1," + idclass2 + " AS C2, "
				+ idclass3 + " AS C3 " + "WHERE (C1." + idpred1 + "=C2.subject " + "AND C2." + idpred2
				+ "=C3.subject AND C1." + idpred12 + " = C3.subject ) ";

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
		String idclass1 = null, idclass2 = null, idclass3 = null;
		String idpred11 = null, idpred12 = null, idpred13 = null, idpred14 = null, idpred21 = null, idpred22 = null;
		String idpred23 = null, idpred24 = null, idpred31 = null, idpred32 = null, idpred33 = null, idpred34 = null;

		String requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass1 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssociateProfessor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass2 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssistantProfessor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass3 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idpredicat WHERE id_class= '" + idclass1
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred14 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idpredicat WHERE id_class= '" + idclass2
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred24 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = " SELECT id FROM idpredicat WHERE id_class= '" + idclass3
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred34 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class='" + idclass1
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred11 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class='" + idclass2
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred21 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class='" + idclass3
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred31 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class= '" + idclass1
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred12 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class='" + idclass2
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred22 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class='" + idclass3
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred32 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class='" + idclass1
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred13 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class='" + idclass2
				+ "'  AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred23 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT id FROM idpredicat WHERE id_class='" + idclass3
				+ "' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idpred33 = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "(SELECT SUB,NAME,MAIL,TEL " + "FROM ((SELECT C1.subject AS SUB, C1." + idpred11 + " AS NAME, C1."
				+ idpred12 + " AS MAIL, C1." + idpred13 + " AS TEL " + "FROM  " + idclass1 + " AS C1 " + "WHERE (C1."
				+ idpred14 + " = 'http://www.Department0.University0.edu')) " + "UNION "
				+ "(SELECT C2.subject AS SUB, C2." + idpred21 + " AS NAME, C2." + idpred22 + " AS MAIL ,C2." + idpred23
				+ " AS TEL " + "FROM  " + idclass2 + " AS C2 " + "WHERE (C2." + idpred24
				+ " = 'http://www.Department0.University0.edu'))) AS T1) " + "UNION " + "(SELECT C3.subject AS SUB,C3."
				+ idpred31 + " AS NAME ,C3." + idpred32 + " AS MAIL, C3." + idpred33 + " AS TEL " + "FROM  " + idclass3
				+ " AS C3 " + "WHERE (C3." + idpred34 + " = 'http://www.Department0.University0.edu')) ";

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
		String idclass = null;

		String requete = "SELECT id FROM idclass WHERE type='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'";

		try {
			stmt = connecter.getConnection().createStatement().executeQuery(requete);
			stmt.next();
			idclass = stmt.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requete = "SELECT COUNT(*) FROM " + idclass + " ";

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
