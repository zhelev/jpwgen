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
		return super.mask(new PwReducedSymbolsFlag().unmask(flags));
	}

	public int unmask(int flags)
	{
		return new AtLeast2SymbolsFlag().unmask(new Only1SymbolFlag()
				.unmask(super.unmask(flags)));
	}

}
