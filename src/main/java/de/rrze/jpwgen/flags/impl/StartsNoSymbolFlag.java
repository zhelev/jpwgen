package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.IPwGenRegEx;
import de.rrze.jpwgen.flags.AbstractPwFlag;

public class StartsNoSymbolFlag extends AbstractPwFlag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StartsNoSymbolFlag()
	{
		mask = IPwGenRegEx.REGEX_STARTS_NO_SYMBOL_FLAG;
	}
}
