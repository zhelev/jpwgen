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
package de.rrze.jpwgen.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import de.rrze.jpwgen.IPasswordFilter;
import de.rrze.jpwgen.utils.Messages;

/**
 * This class is used for filtering passwords from a blacklist. If the proposed
 * password is contained within the blacklist, <em>null</em> is returned to
 * indicate the password is not suitable. Otherwise the password itself is
 * returned.
 * 
 * @author unrz205
 */
public class DefaultBlacklistFilter implements IPasswordFilter
{
	// A list that stores the forbidden words
	private List<String> blacklist = new ArrayList<String>();

	private final static Logger LOGGER = Logger
			.getLogger(DefaultBlacklistFilter.class.getName());

	/**
	 * Default constructor.
	 */
	public DefaultBlacklistFilter()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#filter(int,
	 * java.lang.String)
	 */
	public synchronized String filter(int passwordFlags, String password)
	{
		// Iterate over the list and check whether it contains the word
		for (Iterator<String> iter = blacklist.iterator(); iter.hasNext();)
		{
			String blackword = (String) iter.next();
			LOGGER.fine(Messages
					.getString("DefaultBlacklistFilter.CHECK_PASSWD") + password //$NON-NLS-1$
					+ Messages
							.getString("DefaultBlacklistFilter.BLACKLIST_ENTRY") + blackword + "\""); //$NON-NLS-1$ //$NON-NLS-2$
			if (password.contains(blackword))
				return null;
		}

		return password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#filter(int,
	 * java.util.List)
	 */
	public synchronized List<String> filter(int passwordFlags,
			List<String> password)
	{
		List<String> suitable = new ArrayList<String>();
		for (Iterator<String> iter = password.iterator(); iter.hasNext();)
		{
			String element = (String) iter.next();
			if (filter(passwordFlags, password) != null)
				suitable.add(element);
		}
		return suitable;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#getDescription()
	 */
	public String getDescription()
	{
		return Messages.getString("DefaultBlacklistFilter.DESCR"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#getID()
	 */
	public String getID()
	{
		return this.getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#setDescription(java.lang.String
	 * )
	 */
	public void setDescription(String description)
	{

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#setID(java.lang.String)
	 */
	public void setID(String id)
	{
		LOGGER.fine(Messages.getString("DefaultBlacklistFilter.ID_CHANGE")); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#getBlacklist()
	 */
	public List<String> getBlacklist()
	{
		return blacklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#setBlacklist(java.util.List)
	 */
	public void setBlacklist(List<String> blacklist)
	{
		this.blacklist = blacklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#addToBlacklist(java.lang.String
	 * )
	 */
	public synchronized boolean addToBlacklist(String blackWord)
	{
		return blacklist.add(blackWord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#removeFromBlacklist(java.lang
	 * .String)
	 */
	public synchronized boolean removeFromBlacklist(String blackWord)
	{
		return blacklist.remove(blackWord);
	}

}
