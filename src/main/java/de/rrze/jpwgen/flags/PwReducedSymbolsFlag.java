package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwReducedSymbolsFlag extends AbstractPwFlag
{

	public PwReducedSymbolsFlag()
	{
		mask = IPwGenConstants.PW_SYMBOLS_REDUCED;
	}

	public int mask(int flags)
	{
		return super.mask(flags) & (~IPwGenConstants.PW_SYMBOLS);
	}

}
