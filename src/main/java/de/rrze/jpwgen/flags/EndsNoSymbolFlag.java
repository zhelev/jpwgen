package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class EndsNoSymbolFlag extends AbstractPwFlag
{
	public EndsNoSymbolFlag()
	{
		mask = IPwGenRegEx.REGEX_ENDS_NO_SYMBOL_FLAG;
	}
}
