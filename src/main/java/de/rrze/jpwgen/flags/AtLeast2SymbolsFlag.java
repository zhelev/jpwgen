package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;
import de.rrze.jpwgen.IPwGenRegEx;

public class AtLeast2SymbolsFlag extends AbstractPwFlag
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AtLeast2SymbolsFlag()
	{
		mask = IPwGenRegEx.REGEX_AT_LEAST_2_SYMBOLS_FLAG;
	}

	public int mask(int flags)
	{
		return super.mask(flags) | IPwGenConstants.PW_SYMBOLS;
	}

}
