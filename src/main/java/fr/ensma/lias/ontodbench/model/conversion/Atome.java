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
package fr.ensma.lias.ontodbench.model.conversion;

/**
 * @author Géraud FOKOU
 * 
 */
public class Atome {

	/**
	 * String value of subject,predicate and object
	 */
	private String[] element = new String[3];

	public Atome(String subj, String pred, String obj) {

		element[0] = subj;
		element[1] = pred;
		element[2] = obj;
	}

	/**
	 * return the value of the Atom
	 * 
	 * @return
	 */
	public String getSubject() {

		return (element[0]);
	}

	/**
	 * Return the type of the atom
	 * 
	 * @return
	 */
	public String getObject() {

		return (element[2]);
	}

	/**
	 * return the string value of the predicate
	 */
	public String getPredicat() {

		return (element[1]);
	}

	/**
	 * return the value corresponding to the index i
	 * 
	 * @param i
	 * @return
	 */
	public String getElement(int i) {

		return (element[i]);
	}
}
