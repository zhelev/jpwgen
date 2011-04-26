package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwSymbolsFlag extends AbstractPwFlag
{

	public PwSymbolsFlag()
	{
		mask = IPwGenConstants.PW_SYMBOLS;
	}

	public int mask(int flags)
	{
		return super.mask(flags) & (~IPwGenConstants.PW_SYMBOLS_REDUCED);
	}

}
