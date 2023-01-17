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
package fr.ensma.lias.ontodbench.model.segmentation;

/**
 * @author Géraud FOKOU
 */
public class OntoTrunkFactory {

	public static IOntoTrunk createOntoTrunk(String ext) {

		if (ext.compareToIgnoreCase("Daml") == 0) {
			return (new DamlTrunk());
		}
		if (ext.compareToIgnoreCase("Owl") == 0) {
			return (new OwlTrunk());
		}
		if (ext.compareToIgnoreCase("NT") == 0) {
			return (new NTripleTrunk());
		}
		if (ext.compareToIgnoreCase("N3") == 0) {
			return (new N3Trunk());
		}
		if (ext.compareToIgnoreCase("rdf") == 0) {
			return (new RDFTrunk());
		}
		if (ext.compareToIgnoreCase("rdfs") == 0) {
			return (new RDFSTrunk());
		}
		if (ext.compareToIgnoreCase("ttl") == 0) {
			return (new TurtleTrunk());
		}
		return null;

	}

}
