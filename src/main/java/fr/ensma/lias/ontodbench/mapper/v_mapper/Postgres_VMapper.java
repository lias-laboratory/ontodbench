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
package fr.ensma.lias.ontodbench.mapper.v_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Géraud FOKOU
 */
public class Postgres_VMapper extends A_VMapper {

	private Postgres_VMapper() {

	}

	protected static Postgres_VMapper createMapper() {

		return new Postgres_VMapper();
	}

	@SuppressWarnings("unused")
	@Override
	public long query1() {

		long num = -1;
		ResultSet stmt;

		String requete = "(SELECT T1.subject " + "FROM triplets AS T1 "
				+ "WHERE (T1.predicat ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' "
				+ "AND T1.object='http://www.Department0.University0.edu/GraduateCourse0')) " + "INTERSECT "
				+ "(SELECT T2.subject " + "FROM triplets AS T2 "
				+ "WHERE (T2.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND T2.object ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent')) ";

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

	@SuppressWarnings("unused")
	@Override
	public long query2() {

		long num = -1;
		ResultSet stmt;

		String requete = "SELECT T1.subject " + "FROM triplets AS T1, triplets AS T2 "
				+ "WHERE (T1.predicat ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' "
				+ "AND T1.object='http://www.Department0.University0.edu/GraduateCourse0' "
				+ "AND T1.subject=T2.subject " + "AND T2.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND T2.object ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent') ";

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

	@SuppressWarnings("unused")
	@Override
	public long query3() {

		long num = -1;
		ResultSet stmt;

		String requete = "(SELECT T1.subject " + "FROM triplets AS T1 "
				+ "WHERE (T1.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication')) " + "INTERSECT "
				+ "(SELECT T2.subject " + "FROM triplets AS T2 "
				+ "WHERE (T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' "
				+ "AND T2.object = 'http://www.Department0.University0.edu/AssistantProfessor0')) ";

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

	@SuppressWarnings("unused")
	@Override
	public long query4() {

		long num = -1;
		ResultSet stmt;

		String requete = "SELECT T1.subject " + "FROM triplets AS T1,triplets AS T2 "
				+ "WHERE (T1.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication' "
				+ "AND T1.subject=T2.subject "
				+ "AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' "
				+ "AND T2.object = 'http://www.Department0.University0.edu/AssistantProfessor0') ";

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

	@SuppressWarnings("unused")
	@Override
	public long query5() {

		long num = -1;
		ResultSet stmt;

		String requete = "(SELECT T1.subject " + "FROM triplets T1 "
				+ "WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor')) "
				+ "INTERSECT " + "(SELECT T2.subject " + "FROM triplets AS T2 "
				+ "WHERE (T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor' "
				+ "AND T2.object = 'http://www.Department0.University0.edu')) ";

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

	@SuppressWarnings("unused")
	@Override
	public long query6() {

		long num = -1;
		ResultSet stmt;

		String requete = "SELECT T1.subject " + "FROM triplets T1,triplets AS T2 "
				+ "WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor' "
				+ "AND T1.subject=T2.subject "
				+ "AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor' "
				+ "AND T2.object = 'http://www.Department0.University0.edu') ";

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

	@SuppressWarnings("unused")
	@Override
	public long query7() {

		long num = -1;
		ResultSet stmt;

		String requete = "SELECT T1.subject, T5.subject, T3.subject "
				+ "FROM triplets AS T1, triplets AS T2, triplets AS T3, triplets AS T4, triplets AS T5, triplets AS T6 "
				+ "WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' "
				+ "AND T1.subject=T2.subject "
				+ "AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#memberOf' "
				+ "AND T2.object=T3.subject " + "AND T3.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND T3.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Department' "
				+ "AND T3.subject=T4.subject "
				+ "AND T4.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#subOrganizationOf' "
				+ "AND T4.object=T5.subject " + "AND T5.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND T5.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#University' "
				+ "AND T6.subject = T1.subject  " + "AND T6.object = T5.subject)  ";

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

	@SuppressWarnings("unused")
	@Override
	public long query8() {

		long num = -1;
		ResultSet stmt;

		String requete = "SELECT T1.subject,T3.object,T4.object,T5.object "
				+ "FROM triplets AS T1,triplets AS T2,triplets AS T3,triplets AS T4, triplets AS T5 "
				+ "WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND (T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssistantProfessor' "
				+ "OR T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssociateProfessor' "
				+ "OR T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor')  "
				+ "AND T1.subject=T2.subject "
				+ "AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor' "
				+ "AND T2.object ='http://www.Department0.University0.edu' " + "AND T2.subject=T3.subject "
				+ "AND T3.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name' "
				+ "AND T3.subject=T4.subject "
				+ "AND T4.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress' "
				+ "AND T4.subject=T5.subject "
				+ "AND T5.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone') "
				+ "ORDER BY (T1.subject)  ";

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

	@SuppressWarnings("unused")
	@Override
	public long query9() {

		long num = -1;
		ResultSet stmt;

		String requete = "SELECT T1.object,COUNT(T1.subject) " + "FROM triplets AS T1 "
				+ "WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication') "
				+ "GROUP BY T1.object  ";

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

	@SuppressWarnings("unused")
	@Override
	public long query10() {

		long num = -1;
		ResultSet stmt;

		String requete = "SELECT T1.subject,T2.object,T3.object "
				+ "FROM triplets AS T1,triplets AS T2,triplets AS T3,triplets AS T4 "
				+ "WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' "
				+ "AND (T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' "
				+ "OR T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#UndergraduateStudent') "
				+ "AND T1.subject=T2.subject "
				+ "AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#advisor' "
				+ "AND T2.object =T3.subject "
				+ "AND T3.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#teacherOf' "
				+ "AND T3.object=T4.object "
				+ "AND T4.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' "
				+ "AND T4.subject  = T1.subject " + "ORDER BY (T1.SUBJECT)   ";

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
