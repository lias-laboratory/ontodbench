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
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;
import fr.ensma.lias.ontodbench.databasedao.databasesession.OntologySessionFactory;
import fr.ensma.lias.ontodbench.databasedao.querymetric.ITripletDAO;

/**
 * @author Géraud FOKOU
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ControlThread extends SwingWorker<List<Integer>, Integer> {

	public static double evolutioninsert = 0;

	public static double evolutionconv = 0;

	private ThreadGroup child;

	private int nbreChild;

	private IOntologySession refSession;

	private ITripletDAO accesDB;

	private String pathDirectory;

	private String srcExt;

	private String destpath = null;

	private String destExt = null;

	private List listFile = new ArrayList();

	private boolean fin = false;

	/**
	 * Constructor
	 */
	public static void setEvolution(double nbr) {
		evolutioninsert = evolutioninsert + nbr;
		return;
	}

	public ControlThread() {
		child = new ThreadGroup("convertThread");
	}

	private void selection() {

		listFile.clear(); // clear the old pathname

		File dataFolder = new File(pathDirectory);
		FileNameExtensionFilter rdfExt = new FileNameExtensionFilter(srcExt + " Files", srcExt);

		File allFiles[] = dataFolder.listFiles();
		for (int i = 0; i < allFiles.length; i++) {
			if ((allFiles[i].isFile()) && (rdfExt.accept(allFiles[i]))) {
				try {
					listFile.add(allFiles[i].getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String takePath() {

		String temp = null;
		try {
			temp = (String) listFile.get(listFile.size() - 1);
			listFile.remove(listFile.size() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (temp);
	}

	private void initConvertThread() {
		accesDB.initialisation(refSession);
		InsertThread temp;
		int cmp;

		if (listFile.size() < nbreChild) {
			cmp = listFile.size();
		} else {
			cmp = nbreChild;
		}

		for (int i = 0; i < cmp; i++) {
			temp = new InsertThread(child, "Convert");
			if (this.destpath == null) {
				temp.setParameters(this.takePath(), this.srcExt,
						OntologySessionFactory.createOntologySession(refSession.getName(), refSession.getUser(),
								refSession.getPasswd(), refSession.getnameHost(), refSession.getPort(),
								refSession.getType()),
						accesDB);
			} else {
				temp.setParameters(this.takePath(), srcExt, this.destpath, this.destExt,
						OntologySessionFactory.createOntologySession(refSession.getName(), refSession.getUser(),
								refSession.getPasswd(), refSession.getnameHost(), refSession.getPort(),
								refSession.getType()),
						accesDB);
			}
			temp.start();
			evolutionconv = evolutionconv + (1.00 / this.listFile.size()) * 100;
			evolutioninsert = evolutioninsert + (1.00 / this.listFile.size()) * 50;
		}
		return;
	}

	private void addThread() {

		InsertThread temp;
		temp = new InsertThread(child, "Convert");

		if (this.destpath == null) {
			temp.setParameters(this.takePath(), this.srcExt,
					OntologySessionFactory.createOntologySession(refSession.getName(), refSession.getUser(),
							refSession.getPasswd(), refSession.getnameHost(), refSession.getPort(),
							refSession.getType()),
					accesDB);
		} else {
			temp.setParameters(this.takePath(), srcExt, this.destpath, this.destExt,
					OntologySessionFactory.createOntologySession(refSession.getName(), refSession.getUser(),
							refSession.getPasswd(), refSession.getnameHost(), refSession.getPort(),
							refSession.getType()),
					accesDB);
		}
		temp.start();
		return;
	}

	public void setParameter(String path, String srcExt, IOntologySession p, ITripletDAO T, int nbre) {

		pathDirectory = path;
		this.srcExt = srcExt;
		refSession = p;
		accesDB = T;
		nbreChild = nbre;
		this.selection();
		return;

	}

	public void setParameter(String path, String srcExt, IOntologySession p, ITripletDAO T) {

		pathDirectory = path;
		this.srcExt = srcExt;
		refSession = p;
		accesDB = T;
		nbreChild = 4;
		this.selection();
		return;

	}

	public void setParameter(String path, String srcExt, String dest, String fDest, IOntologySession p, ITripletDAO T) {

		this.pathDirectory = path;
		this.srcExt = srcExt;
		this.destpath = dest;
		this.destExt = fDest;
		refSession = p;
		accesDB = T;
		nbreChild = 4;
		this.selection();
		return;

	}

	public void setParameter(String path, String srcExt, String dest, String fDest, IOntologySession p, ITripletDAO T,
			int nbre) {

		this.pathDirectory = path;
		this.srcExt = srcExt;
		this.destpath = dest;
		this.destExt = fDest;
		refSession = p;
		accesDB = T;
		nbreChild = nbre;
		this.selection();
		return;

	}

	/**
	 * tell if the worker have finished
	 * 
	 * @return
	 */
	public boolean isFinish() {

		return fin;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	protected List<Integer> doInBackground() throws Exception {
		fin = false;
		this.initConvertThread();
		while (this.listFile.size() != 0) {
			if (child.activeCount() < nbreChild) {
				publish(1);
			} else {
				System.out.println("Groupe plein");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (child.activeCount() != 0) {
			try {
				System.out.println("Attente des derniers threads");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			accesDB.updateDB(refSession);
			evolutioninsert = 100;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void process(List<Integer> chunk) {

		for (int number : chunk) {

			switch (number) {

			case 1:
				while (child.activeCount() < nbreChild) {
					this.addThread();
					evolutionconv = evolutionconv + (1.00 / this.listFile.size()) * 100;
					evolutioninsert = evolutioninsert + (1.00 / this.listFile.size()) * 50;
				}
				break;
			}
		}
	}

	@Override
	protected void done() {
		fin = true;

	}
}
