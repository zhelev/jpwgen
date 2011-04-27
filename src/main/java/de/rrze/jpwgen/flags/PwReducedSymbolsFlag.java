package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwReducedSymbolsFlag extends AbstractPwFlag
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PwReducedSymbolsFlag()
	{
		mask = IPwGenConstants.PW_SYMBOLS_REDUCED;
	}

	public int mask(int flags)
	{
		return new PwSymbolsFlag().unmask(super.mask(flags));
	}

	public int unmask(int flags)
	{
		int tmp = flags;

		if (new PwSymbolsFlag().isMasked(flags))
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
