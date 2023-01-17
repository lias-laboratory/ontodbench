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

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;
import fr.ensma.lias.ontodbench.databasedao.querymetric.ITripletDAO;

/**
 * @author Géraud FOKOU
 */
public class InsertThread extends Thread {

	private static int numero = 0;

	private String source;

	private String sourceExt;

	private String destination = null;

	private String destExt = null;

	private IOntologySession refSession;

	private ITripletDAO accesDB;

	private boolean saveMode;

	/**
	 * 
	 */
	public InsertThread() {
		numero = numero + 1;
	}

	/**
	 * @param name
	 */
	public InsertThread(String name) {
		super(name + Integer.toString(numero + 1));
		numero = numero + 1;
	}

	/**
	 * @param group
	 * @param name
	 */
	public InsertThread(ThreadGroup group, String name) {
		super(group, name + Integer.toString(numero + 1));
		numero = numero + 1;
	}

	/**
	 * Setting the parameter for the execution of the thread
	 * 
	 * @param src
	 * @param dest
	 * @param fsrc
	 * @param fdest
	 * @param ref
	 * @param mode
	 */
	public void setParameters(String src, String fsrc, IOntologySession ref, ITripletDAO T, boolean mode) {

		source = src;
		sourceExt = fsrc;
		refSession = ref;
		saveMode = mode;
		accesDB = T;

	}

	public void setParameters(String src, String fsrc, IOntologySession ref, ITripletDAO T) {

		source = src;
		sourceExt = fsrc;
		refSession = ref;
		saveMode = false;
		accesDB = T;

	}

	public void setParameters(String src, String fsrc, String destFolder, String destFormat, IOntologySession ref,
			ITripletDAO T, boolean mode) {

		source = src;
		sourceExt = fsrc;
		destination = destFolder;
		destExt = destFormat;
		refSession = ref;
		saveMode = mode;
		accesDB = T;

	}

	public void setParameters(String src, String fsrc, String destFolder, String destFormat, IOntologySession ref,
			ITripletDAO T) {

		source = src;
		sourceExt = fsrc;
		destination = destFolder;
		destExt = destFormat;
		refSession = ref;
		saveMode = false;
		accesDB = T;

	}

	/**
	 * This is the entry point for the thread.
	 */
	public void run() {

		NTripleModel converteur = new NTripleModel();

		if (source == null)
			return;
		if (this.destination == null) {
			converteur.setParameter(source, source, sourceExt, sourceExt);
			converteur.multipleFile(saveMode);
			converteur.convertAfile(refSession, accesDB, false);
		} else {
			converteur.setParameter(source, destination, sourceExt, destExt);
			converteur.multipleFile(saveMode);
			converteur.convertAfile(refSession, accesDB, true);
		}
		converteur = null;
		refSession.closeSession();
		refSession = null;
		return;
	}
}
