package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.IPwGenConstants;
import de.rrze.jpwgen.flags.AbstractPwFlag;

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
		int maskedFlag = super.mask(flags);
		return new PwSymbolsFlag().unmask(maskedFlag);
	}

	public int unmask(int flags)
	{
		int tmp = flags;

		if (new PwSymbolsFlag().isMasked(tmp))
		{
			tmp = super.unmask(tmp);
		} else
		{
			tmp = new AtLeast2SymbolsFlag().unmask(new Only1SymbolFlag()
					.unmask(super.unmask(tmp)));
		}

		return tmp;
	}

}
