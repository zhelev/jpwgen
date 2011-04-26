package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class StartsNoSymbolFlag extends AbstractPwFlag
{
	public StartsNoSymbolFlag()
	{
		mask = IPwGenRegEx.REGEX_STARTS_NO_SYMBOL_FLAG;
	}
}
