package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

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
