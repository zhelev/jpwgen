package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class EndsNoSymbolFlag implements IPwFlag
{
	public int mask(int flags)
	{
		return flags | IPwGenRegEx.REGEX_ENDS_NO_SYMBOL_FLAG;
	}
}
