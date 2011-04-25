package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class AtLeast2SymbolsFlag implements IPwFlag
{
	public int mask(int flags)
	{
		return flags | IPwGenRegEx.REGEX_AT_LEAST_2_SYMBOLS_FLAG;
	}
}
