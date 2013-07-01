package de.rrze.jpwgen.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.rrze.jpwgen.IPasswordFilter;
import de.rrze.jpwgen.IPwDefConstants;
import de.rrze.jpwgen.IPwProcessing;

public abstract class AbstractPasswordFilter implements IPasswordFilter,
		IPwDefConstants, IPwProcessing {

	// A list that stores the forbidden words
	protected List<String> blacklist = new ArrayList<String>();

	protected String id = AbstractPasswordFilter.class.getSimpleName();
	protected String description = AbstractPasswordFilter.class.getSimpleName();

	AbstractPasswordFilter() {
		id = this.getClass().getSimpleName();
		description = this.getClass().getSimpleName();
	}

	AbstractPasswordFilter(List<String> blacklist) {
		this.blacklist = blacklist;
		id = this.getClass().getSimpleName();
		description = this.getClass().getSimpleName();
	}
	
	public String getID() {
		return id;
	}

	public String getDescription() {
		return description;
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
	public synchronized List<String> getBlacklist() {
		return blacklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#setBlacklist(java.util.List)
	 */
	public synchronized void setBlacklist(List<String> blacklist) {
		this.blacklist = blacklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#addToBlacklist(java.lang.String
	 * )
	 */
	public synchronized boolean addToBlacklist(String blackWord) {
		return blacklist.add(blackWord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#removeFromBlacklist(java.lang
	 * .String)
	 */
	public synchronized boolean removeFromBlacklist(String blackWord) {
		return blacklist.remove(blackWord);
	}

}
