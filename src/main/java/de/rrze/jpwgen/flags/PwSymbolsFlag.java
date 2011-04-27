package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwSymbolsFlag extends AbstractPwFlag
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PwSymbolsFlag()
	{
		mask = IPwGenConstants.PW_SYMBOLS;
	}

	public int mask(int flags)
	{
		return new PwReducedSymbolsFlag().unmask(super.mask(flags));
	}

	public int unmask(int flags)
	{
		int tmp = flags;

		if (new PwReducedSymbolsFlag().isMasked(flags))
		{
			tmp = super.unmask(flags);
		} else
		{
			tmp = new AtLeast2SymbolsFlag().unmask(new Only1SymbolFlag()
					.unmask(super.unmask(flags)));
		}

		return tmp;
	}

}
