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

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.util.*;
import com.hp.hpl.jena.rdf.model.*;

/**
 * @author Géraud FOKOU
 */
public class ConvertToModel {

	/**
	 * RDF data File
	 */
	private String RdfSourceUrl;

	/**
	 * RDF data Format;
	 */
	private String dataFormat;

	/**
	 * Model which represent RDF Data in rdfFile
	 */
	private Model dataModel;

	/**
	 * List of Atom containing in the model
	 */
	private List<Atome> allAtomes = new ArrayList<Atome>();

	/**
	 * True if the conversion of data in rdfFile to dataModel have been done
	 */
	private boolean converted = false;

	/**
	 * true if the updating of the model are been done
	 */
	private boolean updated = false;

	/**
	 * True if the model have been save
	 */
	private boolean saved = false;

	/**
	 * Default constructor
	 */
	public ConvertToModel() {
	}

	/**
	 * Constructor and initializer
	 */
	public ConvertToModel(String RdfUrl, String format) {

		RdfSourceUrl = RdfUrl;
		dataFormat = format;
		converted = false;
		return;
	}

	/**
	 * Move all the Model's triples into the vector of atom
	 */
	private void extractAtoms() {

		allAtomes.clear();
		StmtIterator tempTriple;
		Statement temp;
		Atome currentAtome;

		tempTriple = dataModel.listStatements();
		while (tempTriple.hasNext()) {
			temp = tempTriple.nextStatement();
			currentAtome = new Atome(temp.getSubject().toString(), temp.getPredicate().toString(),
					temp.getObject().toString());
			allAtomes.add(currentAtome);
		}
		return;
	}

	/**
	 * update the parameter of the converter
	 * 
	 * @param rdfFile
	 * @param format
	 */
	public void setSourceParam(String RdfUrl, String format) {

		RdfSourceUrl = RdfUrl;
		dataFormat = format;
		converted = false;
		return;
	}

	/**
	 * Create model representation of the data RDF file
	 * 
	 * @throws Exception
	 */
	public void createModel() throws Exception {

		try {
			dataModel = ModelFactory.createDefaultModel();
			dataModel.read(RdfSourceUrl, dataFormat);
			converted = true;
		} catch (Exception e) {
			converted = false;
			throw e;
		}
		return;
	}

	/**
	 * add in the current Model description containing in the current file
	 * 
	 */
	public void updateModel() throws Exception {

		Model temp;
		try {
			if (dataModel == null) {
				createModel();
				updated = true;
			} else {
				temp = ModelFactory.createDefaultModel();
				temp.read(RdfSourceUrl, dataFormat);
				dataModel.add(temp);
				updated = true;
			}
		} catch (Exception e) {
			updated = false;
			throw e;
		}
		return;
	}

	/**
	 * Show if the conversion have been done
	 */
	public boolean isConverted() {

		return (converted);
	}

	/**
	 * Tell if the model have been saved
	 * 
	 * @return
	 */
	public boolean isSaved() {

		return (saved);
	}

	/**
	 * Tell if the model have been updated
	 */
	public boolean isUpdated() {

		return (updated);
	}

	/**
	 * Saved the model in the fileDestination with the Data Format format
	 * 
	 * @param fileDestination
	 * @param format
	 * @return
	 */
	public boolean saveModelAs(String DestinationUrl, String format) {

		File desFile = new File(URI.create(DestinationUrl));

		try {
			desFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			converted = false;
		}

		try {
			dataModel.write(new PrintStream(desFile), format);
			saved = true;
			return true;
		} catch (Exception e) {
			System.out.println("ConvertToModelError:" + desFile.getAbsolutePath());
			saved = false;
			return false;
		}

	}

	/**
	 * return the list containing all atom which are in the current model
	 * 
	 * @return
	 */
	public List<Atome> getListAtome() {

		extractAtoms();
		return (allAtomes);
	}

	public void seeAtom() {

		Atome currentAtome;
		int i = 0;

		extractAtoms();
		for (i = 0; i < allAtomes.size(); i++) {
			currentAtome = allAtomes.get(i);
			System.out.println(
					currentAtome.getSubject() + "\t" + currentAtome.getPredicat() + "\t" + currentAtome.getObject());
		}
	}
}
