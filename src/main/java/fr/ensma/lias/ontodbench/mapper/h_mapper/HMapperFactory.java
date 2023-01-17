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

/**
 * @author Géraud FOKOU
 */
public class HMapperFactory {

	public static I_HMapper createHMapper(String type) {

		if (type.compareToIgnoreCase("postgres") == 0) {

			return Postgres_HMapper.createMapper();
		}

		if (type.compareToIgnoreCase("oracle") == 0) {

			// Instantiation of OracleOntologySession
			return null;
		}

		if (type.compareToIgnoreCase("mysql") == 0) {

			// Instantiation of MySqlOntologySession
			return null;
		}
		if (type.compareToIgnoreCase("SqlServerOntologySession") == 0) {

			// Instantiation of SqlServerOntologySession
			return null;
		}

		return null;
	}
}
