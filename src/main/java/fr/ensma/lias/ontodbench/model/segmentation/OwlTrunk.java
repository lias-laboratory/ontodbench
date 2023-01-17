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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Géraud FOKOU
 */
public class OwlTrunk extends AOntoTrunk {

	private static int num = 0;

	static final String EXTENSION = ".owl";
	
	static final String MARK_END = "</owl:Ontology>";
	
	static final String END_FILE = "</rdf:RDF>";

	/**
	 * Access to the File which will be truncate
	 */
	RandomAccessFile inputfile;

	/**
	 * Cursor position in the file
	 */
	long position;

	/**
	 * file to save header
	 */
	File tempheader;

	/**
	 * file for saving the current instance
	 */
	File tempCurrentInstance;

	/**
	 * Folder of segment File
	 */
	File segFolder;

	/**
	 * List of files in the data folder
	 */
	private List<File> rdfFilespath = new ArrayList<File>();

	/**
	 * Constructor visible only in the package
	 */
	protected OwlTrunk() {

	}

	/**
	 * Extract all the pathname of the Rdf Data file in the data folder and put it
	 * in the list
	 * 
	 * @return
	 */
	private void selection(String sourceUrl) {

		rdfFilespath.clear();
		File dataFolder = new File(sourceUrl);
		FileNameExtensionFilter rdfExt = new FileNameExtensionFilter(EXTENSION.substring(1) + " Files",
				EXTENSION.substring(1));

		if (dataFolder.exists() && dataFolder.isDirectory()) {
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
		return (temp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.ensma.lisi.ontologymetric.model.segmentation.IOntoTrunk#writeHeaders()
	 */
	@Override
	public long writeHeaders() {

		String ligne;
		boolean find = false;
		long savePos = position;

		try {
			tempheader = File.createTempFile("tempHeader", EXTENSION);
			PrintStream tempAccess = new PrintStream(tempheader);

			while ((!find) && (inputfile.getFilePointer() != inputfile.length())) {

				ligne = inputfile.readLine();
				if ((ligne.compareToIgnoreCase(MARK_END) == 0) || (ligne.endsWith(MARK_END))) {
					find = true;
				}
				tempAccess.println(ligne);
			}
			tempAccess.close();
			position = inputfile.getFilePointer();
		} catch (IOException e) {
			position = savePos;
			e.printStackTrace();
		}
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ensma.lisi.ontologymetric.model.segmentation.IOntoTrunk#writeInstance
	 * (long)
	 */
	@Override
	public long writeInstance(long size) {

		String firstline;
		String currentline;
		boolean find = false;
		long savePos = position;

		try {
			tempCurrentInstance = File.createTempFile("tempinstance", EXTENSION);
			PrintStream tempAccess = new PrintStream(tempCurrentInstance);

			while ((tempCurrentInstance.length() < size) && (!isTheEnd())) {

				firstline = inputfile.readLine();

				while ((firstline.compareToIgnoreCase("\r") == 0) || (firstline.compareToIgnoreCase("\n") == 0)
						|| (firstline.length() == 0)) {

					tempAccess.println();
					firstline = inputfile.readLine();
				}
				currentline = firstline.toString();

				find = firstline.startsWith((currentline.replace("/", "")).replace(">", " "));
				find = find && currentline.startsWith("</") && currentline.endsWith(">");

				while ((!find) && (inputfile.getFilePointer() != inputfile.length())) {

					tempAccess.println(currentline);
					currentline = inputfile.readLine();

					while ((currentline.compareToIgnoreCase("\r") == 0) || (currentline.compareToIgnoreCase("\n") == 0)
							|| (currentline.length() == 0)) {
						tempAccess.println();
						currentline = inputfile.readLine();
					}
					find = firstline.startsWith((currentline.replace("/", "")).replace(">", " "));
					find = find && currentline.startsWith("</") && currentline.endsWith(">");
				}

				tempAccess.println(currentline);
				position = inputfile.getFilePointer();
			}
			tempAccess.close();
		} catch (IOException e) {
			position = savePos;
			e.printStackTrace();
		}

		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ensma.lisi.ontologymetric.model.segmentation.IOntoTrunk#writeFile(
	 * java.io.File)
	 */
	@Override
	public long writeFile(File segmentfile) {

		String elt;

		try {
			RandomAccessFile tempAccess = new RandomAccessFile(tempheader, "rw");
			RandomAccessFile tempAccess1 = new RandomAccessFile(tempCurrentInstance, "rw");
			PrintStream outputfile = new PrintStream(segmentfile);

			while (tempAccess.getFilePointer() != tempAccess.length()) {
				elt = tempAccess.readLine();
				outputfile.println(elt);
			}
			while (tempAccess1.getFilePointer() != tempAccess1.length()) {
				elt = tempAccess1.readLine();
				outputfile.println(elt);
			}
			outputfile.println(END_FILE);
			tempAccess.close();
			tempAccess1.close();
			outputfile.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ensma.lisi.ontologymetric.model.segmentation.IOntoTrunk#isTheEnd()
	 */
	@Override
	public boolean isTheEnd() {

		String lastline;
		long SavePos;

		try {
			if (inputfile.getFilePointer() == inputfile.length()) {
				return true;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			SavePos = inputfile.getFilePointer();
			lastline = inputfile.readLine();
			inputfile.seek(SavePos);
			if (lastline.compareToIgnoreCase(END_FILE) == 0) {
				return true;
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ensma.lisi.ontologymetric.model.segmentation.IOntoTrunk#segmentedFile
	 * (java.io.File, long)
	 */
	@Override
	public String segmentedFile(File sourcefile, long size) {

		File destfile;

		int i = (int) (sourcefile.length() / size);
		int j = 0;

		try {
			inputfile = new RandomAccessFile(sourcefile, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (segFolder == null) {
			segFolder = new File(
					(sourcefile.getPath()).substring(0, sourcefile.getPath().length() - EXTENSION.length()));
			segFolder.mkdirs();
		}
		writeHeaders();

		while (!isTheEnd()) {
			writeInstance(size);
			destfile = new File(segFolder.getPath() + "\\"
					+ sourcefile.getName().substring(0, sourcefile.getName().length() - EXTENSION.length())
					+ Integer.toString(num) + EXTENSION);
			writeFile(destfile);
			tempCurrentInstance.delete();
			num = num + 1;

			j = j + 1;
			evolutionfile = ((double) j / (double) i) * 100;
		}
		tempheader.delete();
		evolutionfile = 100;
		return segFolder.getPath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.ensma.lisi.ontologymetric.model.segmentation.IOntoTrunk#segmentedFolder
	 * (java.io.File, long)
	 */
	public String segmentFolder(String folderPath, long size) {

		File srcFolder;

		int i = 0;

		srcFolder = new File(folderPath);
		segFolder = new File(srcFolder.getPath() + "\\" + srcFolder.getName() + "SegFile");
		segFolder.mkdirs();

		this.selection(folderPath);
		while (0 < rdfFilespath.size()) {
			this.segmentedFile(this.takeFile(), size);

			i = i + 1;
			evolutionfolder = ((double) i / (double) rdfFilespath.size()) * 100;
		}
		evolutionfolder = 100;
		return segFolder.getPath();
	}
}
