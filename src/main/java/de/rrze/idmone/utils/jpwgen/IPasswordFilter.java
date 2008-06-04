/*
 * RRZEPwGen, developed as a part of the IDMOne project at RRZE.
 * Copyright 2007, RRZE, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors. This
 * product includes software developed by the Apache Software Foundation
 * http://www.apache.org/
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * This software uses code and ideas from:
 * 	http://sourceforge.net/projects/pwgen/
 * 	Copyright (C) 2001,2002 by Theodore Ts'o
 * 
 */
package de.rrze.idmone.utils.jpwgen;

import java.util.List;

/**
 * Interface that represents the basic functionality that should be supported by
 * a password filter class. Implementations of this class can be registered in
 * for usage by the PwGenerator class.
 * 
 * @author unrz205
 */
public interface IPasswordFilter
{

	/**
	 * This method must return the unique identifier of the filter. A unique
	 * identifier is needed for correct registration of the filter.
	 * 
	 * @return the filter identifier
	 */
	public String getID();

	/**
	 * Sets the identifier of this filter. A filter should have a predefined
	 * identifier. A good idea is to use the class.getName() method.
	 * 
	 * @param id
	 */
	public void setID(String id);

	/**
	 * This method returns a short description of what the filter is doing and
	 * how.
	 * 
	 * @return description
	 */
	public String getDescription();

	/**
	 * This method sets the description of the filter.
	 * 
	 * @param description
	 */
	public void setDescription(String description);

	/**
	 * This method does the actual filtering. It implements the main logic of
	 * the filter.
	 * 
	 * @param passwordFlags
	 *            the bitwise mask containing the password flags
	 * @param password
	 *            the password to be checked
	 * @return <em>null</em> if the password should be filtered and the
	 *         password if it satisfies the rules.
	 */
	public String filter(int passwordFlags, String password);

	/**
	 * This method checks a whole list of passwords. It should return a list of
	 * suitable passwords or an empty list if none of the passwords fits the
	 * rules.
	 * 
	 * @param passwordFlags
	 *            the bitwise mask containing the password flags
	 * @param passwords
	 *            a list of passwords to be checked
	 * @return the list with filtered passwords
	 */
	public List<String> filter(int passwordFlags, List<String> passwords);

	/**
	 * Returns a reference of the blacklist used by this filter and
	 * <em>null</em> if the filters is purely procedural and checks
	 * passwords against rule.
	 * 
	 * @return the blacklist of the filter or <em>null</em> if one is not
	 *         used.
	 */
	public List<String> getBlacklist();

	/**
	 * Sets the blacklist of the filter.
	 * 
	 * @param blacklist
	 */
	public void setBlacklist(List<String> blacklist);

	/**
	 * Adds a password to the list of forbidden passwords.
	 * 
	 * @param blackWord
	 *            the forbidden word
	 * @return <em>true</em> on successful inclusion, <em>false</me>
	 *         otherwise
	 */
	public boolean addToBlacklist(String blackWord);

	/**
	 * Removes a word from the blacklist.
	 * 
	 * @param blackWord
	 *            the word to be removed from the blacklist
	 * @return <em>true</em> on successful removal, <em>false</em>
	 *         otherwise
	 */
	public boolean removeFromBlacklist(String blackWord);
}
