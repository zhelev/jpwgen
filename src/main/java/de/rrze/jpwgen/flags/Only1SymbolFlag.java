package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class Only1SymbolFlag extends AbstractPwFlag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Only1SymbolFlag()
	{
		mask = IPwGenRegEx.REGEX_ONLY_1_SYMBOL_FLAG;
	}

	public int mask(int flags)
	{
		int tmp = new AtLeast2SymbolsFlag().unmask(flags);
		if (new PwReducedSymbolsFlag().isMasked(tmp)
				|| new PwSymbolsFlag().isMasked(tmp))
			return super.mask(tmp);

		return super.mask(new PwReducedSymbolsFlag().mask(tmp));
	}
}
