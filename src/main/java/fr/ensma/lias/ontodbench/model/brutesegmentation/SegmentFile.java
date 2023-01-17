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
package fr.ensma.lias.ontodbench.model.brutesegmentation;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Géraud FOKOU
 */
public class SegmentFile {

	/**
	 * the string which indicate the path of the file which will be segmented
	 */
	private String sourceUrl;

	/**
	 * The format of the file which will be segmented
	 */
	private String sourceExtension;

	/**
	 * Given the offset of the result file
	 */
	private Long offset;

	/**
	 * List of files in the data folder
	 */
	private List<File> rdfFilespath = new ArrayList<File>();

	/**
	 * This method take the quantity "offset" of information beginning at "base" in
	 * the file "source" and copy it in the end of the file "dest"
	 * 
	 * @param source
	 * @param dest
	 * @param base
	 * @param offset
	 * @return
	 */
	private long createSegment(File source, File dest, long base, long offset1) {

		FileChannel in = null;
		FileChannel out = null;
		long nbase;
		try {
			in = new FileInputStream(source).getChannel();
			out = new FileOutputStream(dest).getChannel();
			in.transferTo(base, offset1, out);
			nbase = base + offset1;
		} catch (Exception e) {
			e.printStackTrace();
			nbase = base;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return nbase;
	}

	/**
	 * Extract all the pathname of the Rdf Data file in the data folder and put it
	 * in the list
	 * 
	 * @return
	 */
	private void selection() {

		rdfFilespath.clear();
		File dataFolder = new File(sourceUrl);
		FileNameExtensionFilter rdfExt = new FileNameExtensionFilter(sourceExtension + " Files", sourceExtension);

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
		;
		return (temp);
	}

	/**
	 * The constructor of the class "SegmentFile"
	 * 
	 * @param url
	 * @param ext
	 */
	public SegmentFile(String url, String ext, Long length) {

		sourceUrl = url;
		sourceExtension = ext;
		offset = length;
	}

	/**
	 * Set the necessary parameter for the computation of the Segmentation
	 * 
	 * @param url
	 * @param ext
	 */
	public void setParemter(String url, String ext, Long length) {

		sourceUrl = url;
		sourceExtension = ext;
		offset = length;
	}

	/**
	 * Compute the segmentation of one file in many file
	 * 
	 * @param offset
	 * @return
	 */
	public String segmentation() {

		File sourceFolder = new File(sourceUrl);
		File destfile, destfolder, currentFolder, sourcefile;
		FileChannel in = null;
		long sizeSource;
		long base;
		int num = 0;

		if ((!sourceFolder.exists()) || (!sourceFolder.isDirectory())) {
			return null;
		}
		destfolder = new File(sourceFolder.getPath() + "\\SegFiles");
		destfolder.mkdirs();
		this.selection();
		while (rdfFilespath.size() != 0) {

			sourcefile = this.takeFile();
			try {
				in = new FileInputStream(sourcefile).getChannel();
				sizeSource = in.size();
				base = 0;
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				return destfolder.getPath();
			}
			while (base + offset < sizeSource) {
				currentFolder = new File(destfolder.getPath() + "\\SegFile" + Integer.toString(num));
				currentFolder.mkdirs();
				destfile = new File(
						currentFolder.getPath() + "\\file " + Integer.toString(num) + "." + sourceExtension);
				try {
					destfile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return destfolder.getPath();
				}
				base = this.createSegment(sourcefile, destfile, base, offset);
				num = num + 1;
			}

			if (base != sizeSource) {
				currentFolder = new File(destfolder.getPath() + "\\SegFile" + Integer.toString(num));
				currentFolder.mkdirs();
				destfile = new File(
						currentFolder.getPath() + "\\file " + Integer.toString(num) + "." + sourceExtension);
				try {
					destfile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return destfolder.getPath();
				}
				base = this.createSegment(sourcefile, destfile, base, sizeSource - base);
				num = num + 1;
			}
		}
		return destfolder.getPath();
	}
}
