package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;
import de.rrze.jpwgen.IPwGenRegEx;

public class Only1SymbolFlag extends AbstractPwFlag
{
	public Only1SymbolFlag()
	{
		mask = IPwGenRegEx.REGEX_ONLY_1_SYMBOL_FLAG;
	}
	
	public int mask(int flags)
	{
		return super.mask(flags) | IPwGenConstants.PW_SYMBOLS;
	}
}
