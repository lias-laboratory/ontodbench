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

import java.io.File;

/**
 * @author Géraud FOKOU
 */
public interface IOntoTrunk {

	/**
	 * write the header of an ontology
	 */
	long writeHeaders();

	/**
	 * write a description of an Instance of ontology in the File
	 */
	long writeInstance(long size);

	/**
	 * write a small file
	 */
	long writeFile(File segmentfile);

	/**
	 * Return true is it the end of file
	 */
	boolean isTheEnd();

	/**
	 * Segment a big file into small file with size
	 */
	String segmentedFile(File sourcefile, long size);

	/**
	 * Segmentation of all the File in a directory
	 */
	String segmentFolder(String folderPath, long size);
}
