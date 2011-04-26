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
		return super.mask(new PwSymbolsFlag().unmask(flags));
	}

	public int unmask(int flags)
	{
		return new AtLeast2SymbolsFlag().unmask(new Only1SymbolFlag()
				.unmask(super.unmask(flags)));
	}
	
}
