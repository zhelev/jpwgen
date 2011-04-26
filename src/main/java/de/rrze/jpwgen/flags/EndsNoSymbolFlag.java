package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class EndsNoSymbolFlag extends AbstractPwFlag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EndsNoSymbolFlag()
	{
		mask = IPwGenRegEx.REGEX_ENDS_NO_SYMBOL_FLAG;
	}
}
