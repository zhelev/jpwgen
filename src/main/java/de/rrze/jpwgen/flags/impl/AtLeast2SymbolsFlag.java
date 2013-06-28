package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.IPwGenRegEx;
import de.rrze.jpwgen.flags.AbstractPwFlag;

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
		int tmp = new Only1SymbolFlag().unmask(flags);
		if (new PwReducedSymbolsFlag().isMasked(tmp)
				|| new PwSymbolsFlag().isMasked(tmp))
			return super.mask(tmp);

		return super.mask(new PwReducedSymbolsFlag().mask(tmp));
	}

}
