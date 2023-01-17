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
package fr.ensma.lias.ontodbench.mapper.b_mapper;

import fr.ensma.lias.ontodbench.databasedao.databasesession.IOntologySession;

/**
 * @author Géraud FOKOU
 */
public abstract class A_BMapper implements I_BMapper {

	protected IOntologySession connecter;

	@Override
	public abstract void createShema();

	@Override
	public abstract void uploadData();

	@Override
	public abstract long query1();

	@Override
	public abstract long query2();

	@Override
	public abstract long query3();

	@Override
	public abstract long query4();

	@Override
	public abstract long query5();

	@Override
	public abstract long query6();

	@Override
	public abstract long query7();

	@Override
	public abstract long query8();

	@Override
	public abstract long query9();

	@Override
	public abstract long query10();

	@Override
	public abstract long query11();

	@Override
	public abstract long query12();

	@Override
	public abstract long query13();

	/**
	 * Instantiate connecter
	 */
	public void setConnecter(IOntologySession p) {
		connecter = p;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return connecter.getHost();
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		connecter.setnameHost(host);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return connecter.getName();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		connecter.setName(name);
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return connecter.getUser();
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		connecter.setUser(user);
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return connecter.getPasswd();
	}

	/**
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		connecter.setPasswd(passwd);
	}

}
