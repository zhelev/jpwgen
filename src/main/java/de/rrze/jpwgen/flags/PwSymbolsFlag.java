package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwSymbolsFlag implements IPwFlag
{

	private IPwGenConstants.SYMBOL_OPTIONS type = IPwGenConstants.SYMBOL_OPTIONS.NONE;

	public PwSymbolsFlag(IPwGenConstants.SYMBOL_OPTIONS type)
	{
		this.type = type;
	}

	public int mask(int flags)
	{
		int tmp = flags;

		if (type == IPwGenConstants.SYMBOL_OPTIONS.NONE)
		{
			tmp = flags & (~IPwGenConstants.PW_SYMBOLS);
			tmp = flags & (~IPwGenConstants.PW_SYMBOLS_REDUCED);
		} else if (type == IPwGenConstants.SYMBOL_OPTIONS.ALL)
		{
			tmp = flags & (~IPwGenConstants.PW_SYMBOLS_REDUCED);
			tmp = flags | IPwGenConstants.PW_SYMBOLS;
		} else if (type == IPwGenConstants.SYMBOL_OPTIONS.REDUCED)
		{
			tmp = flags & (~IPwGenConstants.PW_SYMBOLS);
			tmp = flags | IPwGenConstants.PW_SYMBOLS_REDUCED;
		}

		return tmp;
	}
}
