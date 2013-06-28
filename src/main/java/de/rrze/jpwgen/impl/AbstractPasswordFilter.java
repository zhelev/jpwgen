package de.rrze.jpwgen.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.rrze.jpwgen.IPasswordFilter;
import de.rrze.jpwgen.IPwGenConstants;
import de.rrze.jpwgen.IPwGenRegEx;

public abstract class AbstractPasswordFilter implements IPasswordFilter,
		IPwGenConstants, IPwGenRegEx {

	// A list that stores the forbidden words
	private List<String> blacklist = new ArrayList<String>();

	private String id = AbstractPasswordFilter.class.getName();
	private String description = AbstractPasswordFilter.class.getName();

	AbstractPasswordFilter() {
		id = this.getClass().getName();
		description = this.getClass().getName();
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public abstract String filter(int passwordFlags, String password);

	public List<String> filter(int passwordFlags, List<String> passwords) {
		List<String> suiatble = new ArrayList<String>();
		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			if (filter(passwordFlags, passwords) != null)
				suiatble.add(element);
		}
		return suiatble;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#getBlacklist()
	 */
	public List<String> getBlacklist() {
		return blacklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#setBlacklist(java.util.List)
	 */
	public void setBlacklist(List<String> blacklist) {
		this.blacklist = blacklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#addToBlacklist(java.lang.String
	 * )
	 */
	public boolean addToBlacklist(String blackWord) {
		return blacklist.add(blackWord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#removeFromBlacklist(java.lang
	 * .String)
	 */
	public boolean removeFromBlacklist(String blackWord) {
		return blacklist.remove(blackWord);
	}

}
