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
import java.util.*;
import java.net.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;
import fr.ensma.lias.ontodbench.databasedao.querymetric.ITripletDAO;

/**
 * Convert a RDF file contains in a Folder DataRdf into N-TRIPLE file saved in
 * the same Folder.
 * 
 * @author Géraud FOKOU
 */
public class NTripleModel {

	public static double evolutionfile = 0;

	public static double evolutionfolder = 0;

	/**
	 * The converter file to model tools
	 */
	private ConvertToModel converter;

	/**
	 * Folder's Path of the sources files
	 */
	private String sourceUrl;

	/**
	 * Folder's Path of destinations files
	 */
	private String destUrl;

	/**
	 * Format of the source file
	 */
	private String sourceExtension;

	/**
	 * Format of the destination file
	 */
	private String destExtension;

	/**
	 * Convert to one or distinct NTriple File
	 */
	private boolean distinctFile = true;

	/**
	 * Determine the existence of the data's Folder
	 */
	private boolean isValidFolder = false;

	/**
	 * List of files in the data folder
	 */
	private List<File> rdfFilespath = new ArrayList<File>();

	/**
	 * Default constructor
	 */
	public NTripleModel() {
	}

	/**
	 * Initializer
	 */
	public void setParameter(String folderSource, String destFolder, String sExt, String dExt) {

		sourceUrl = folderSource;
		destUrl = destFolder;
		sourceExtension = sExt;
		destExtension = dExt;

		File dataFolder = new File(sourceUrl);
		if (dataFolder.exists() && dataFolder.isDirectory()) {
			isValidFolder = true;
		}

	}

	/**
	 * Extract all the pathname of the Rdf Data file in the data folder and put it
	 * in the list
	 * 
	 * @return
	 */
	private void selection() {

		rdfFilespath.clear(); // clear the old pathname
		File dataFolder = new File(sourceUrl);
		FileNameExtensionFilter rdfExt = new FileNameExtensionFilter(sourceExtension + " Files", sourceExtension);

		if (isValidFolder) {
			File allFiles[] = dataFolder.listFiles();
			for (int i = 0; i < allFiles.length; i++) {

				if ((allFiles[i].isFile()) && (rdfExt.accept(allFiles[i]))) {
					rdfFilespath.add(allFiles[i]);
				}
			}

		}
	}

	/**
	 * Remove the last file in the list
	 * 
	 * @return File if the list is not empty and null otherwise
	 */
	private File takeFile() {
		File temp = null;
		try {
			temp = rdfFilespath.get(rdfFilespath.size() - 1);
			rdfFilespath.remove(rdfFilespath.size() - 1);
		} catch (Exception e) {
		}
		;
		return (temp);
	}

	/**
	 * Show if the list containing rdf files or not
	 * 
	 * @return true if the file exist and false otherwise
	 */
	private boolean containsSelectedFiles() {

		return (rdfFilespath.size() != 0);
	}

	/**
	 * Give the format corresponding to the extension given
	 * 
	 * @param extension
	 * @return
	 */
	private String convertExtension(String extension) {
		String format = "";

		if ((extension.compareToIgnoreCase("owl") == 0) || (extension.compareToIgnoreCase("daml") == 0)) {
			format = "RDF/XML";
			return format;
		}

		if ((extension.compareToIgnoreCase("rdf") == 0) || (extension.compareToIgnoreCase("rdfs") == 0)) {
			format = "RDF/XML";
			return format;
		}

		if (extension.compareToIgnoreCase("nt") == 0) {
			format = "N-TRIPLE";
			return format;
		}

		if (extension.compareToIgnoreCase("ttl") == 0) {
			format = "TURTLE";
			return format;
		}

		if (extension.compareToIgnoreCase("n3") == 0) {
			format = "N3";
			return format;
		}
		return format;
	}

	/**
	 * Transform the given pathname into the appropriate Url
	 * 
	 * @param path
	 * @return
	 */
	private String convertPath(String path) {

		String currentUrl = null;
		File temp = new File(path);

		URI currentUri = temp.toURI();
		try {
			currentUrl = currentUri.toURL().toString();
		} catch (MalformedURLException e) {
			System.out.println(e.toString() + "\n" + e.fillInStackTrace().toString());
		}
		return currentUrl;

	}

	/**
	 * All The RDF/XML file are converter into one NTRIPLE file
	 */
	private void converter_one() {

		converter = new ConvertToModel();
		File temp;

		int i = 0;

		while (containsSelectedFiles()) {
			temp = takeFile();
			converter.setSourceParam(convertPath(temp.getPath()), convertExtension(sourceExtension));
			try {
				converter.updateModel();
			} catch (Exception e) {
				System.out.println("NTripleModel:Error in Model update");
				e.printStackTrace();
			}
			if (!converter.isUpdated()) {
				System.out.println("NTripleModel:File " + temp.getName() + " can't be converted");
			}

			i = i + 1;
			evolutionfolder = ((double) i / (double) rdfFilespath.size()) * 75;
		}
		converter.saveModelAs(convertPath(destUrl + "\\" + new File(sourceUrl).getName() + "." + destExtension),
				convertExtension(destExtension));
		evolutionfolder = 100;

		return;
	}

	/**
	 * Convert each RDF/XML file to his corresponding NTRIPLE file
	 */
	private void converter_Multi() {

		converter = new ConvertToModel();
		File temp;
		String name;

		int i = 0;

		while (containsSelectedFiles()) {
			temp = takeFile();
			name = temp.getName();
			name = name.substring(0, name.length() - this.sourceExtension.length());
			name = name + destExtension;
			converter.setSourceParam(convertPath(temp.getPath()), convertExtension(sourceExtension));
			try {
				converter.createModel();
			} catch (Exception e) {
				System.out.println(convertPath(sourceUrl + "\\" + temp.getName()));
				System.out.println("NTripleModel:Error in Model creation");
			}

			if (!converter.isConverted()) {
				System.out.println("NTripleModel:File " + temp.getName() + " can't be converted");
			} else {
				converter.saveModelAs(convertPath(destUrl + "\\" + name), convertExtension(destExtension));
			}

			i = i + 1;
			evolutionfolder = ((double) i / (double) rdfFilespath.size()) * 100;
		}

		return;
	}

	/**
	 * Saved the model in the database or in file
	 * 
	 * @return
	 */
	private boolean saveModel(IOntologySession p, ITripletDAO accesDB) {

		List<Atome> elements = converter.getListAtome();
		Atome elt;

		for (int i = 0; i < elements.size(); i++) {
			elt = elements.get(i);
			accesDB.addAtome(p, elt.getSubject(), elt.getPredicat(), elt.getObject());
		}
		return true;
	}

	/**
	 * Number of source folder's File add in the current Model
	 * 
	 * @param num
	 */
	public void multipleFile(boolean num) {
		distinctFile = num;
		return;
	}

	/**
	 * True if the data Folder exist and is a folder
	 * 
	 * @return
	 */
	public boolean isValidFolder() {
		return (isValidFolder);
	}

	/**
	 * verified parameter and convert data if these parameter are correct and
	 * convert a folder
	 */
	public boolean convert() {

		if ((sourceUrl == "") || (destUrl == "")) {
			System.out.println("NTripleModel:Set the parameter of the conversion");
			return false;
		}

		if (!isValidFolder()) {
			System.out.println("NTripleModel:Source folder not found");
			return false;
		}

		selection();

		if (!containsSelectedFiles()) {
			System.out.println("NTripleModel:File with Format ." + this.sourceExtension + " not present in the folder "
					+ sourceUrl);
			return false;
		}

		if (!distinctFile)
			converter_one();
		else {
			converter_Multi();
		}
		return true;

	}

	/**
	 * conversion of one file
	 * 
	 * @return
	 */
	public String convertAfile() {

		converter = new ConvertToModel();
		String name;
		File dataFile;
		FileNameExtensionFilter rdfExt = new FileNameExtensionFilter(sourceExtension + " Files", sourceExtension);

		rdfFilespath.clear();
		if ((sourceUrl == "") || (destUrl == "")) {
			System.out.println("NTripleModel:Set the parameter of the conversion");
			return null;
		}

		dataFile = new File(sourceUrl);
		if ((dataFile.isFile()) && (rdfExt.accept(dataFile))) {
			rdfFilespath.add(dataFile);
		} else {
			return null;
		}

		name = dataFile.getName();
		name = name.substring(0, name.length() - this.sourceExtension.length());
		name = name + destExtension;

		converter.setSourceParam(convertPath(dataFile.getPath()), convertExtension(sourceExtension));
		try {
			converter.createModel();
		} catch (Exception e) {
			System.out.println(convertPath(dataFile.getPath()));
			System.out.println("NTripleModel:Error in Model creation");
		}

		evolutionfile = 75;
		if (!converter.isConverted()) {
			System.out.println("NTripleModel:File " + dataFile.getPath() + " can't be converted");
		} else {
			converter.saveModelAs(convertPath(destUrl + "\\" + name), convertExtension(destExtension));
		}
		evolutionfile = 100;
		return destUrl + "\\" + name;
	}

	/**
	 * conversion of one file an save in BD
	 * 
	 * @return
	 */
	public String convertAfile(IOntologySession p, ITripletDAO accesDB, boolean savedfile) {

		converter = new ConvertToModel();
		String name;
		File dataFile;
		FileNameExtensionFilter rdfExt = new FileNameExtensionFilter(sourceExtension + " Files", sourceExtension);

		rdfFilespath.clear();
		if ((sourceUrl == "") || (destUrl == "")) {
			System.out.println("NTripleModel:Set the parameter of the conversion");
			return null;
		}

		dataFile = new File(sourceUrl);

		if ((dataFile.isFile()) && (rdfExt.accept(dataFile))) {
			rdfFilespath.add(dataFile);
		} else {
			return null;
		}

		name = dataFile.getName();
		name = name.substring(0, name.length() - this.sourceExtension.length());
		name = name + destExtension;

		converter.setSourceParam(convertPath(dataFile.getPath()), convertExtension(sourceExtension));
		try {
			converter.createModel();
		} catch (Exception e) {
			System.out.println(convertPath(dataFile.getPath()));
			System.out.println("NTripleModel:Error in Model creation");
		}

		evolutionfile = 75;
		if (!converter.isConverted()) {
			System.out.println("NTripleModel:File " + dataFile.getPath() + " can't be converted");
		} else {
			saveModel(p, accesDB);
			if (savedfile) {
				converter.saveModelAs(convertPath(destUrl + "\\" + name), convertExtension(destExtension));
			}
		}
		evolutionfile = 100;
		return destUrl + "\\" + name;
	}
}
