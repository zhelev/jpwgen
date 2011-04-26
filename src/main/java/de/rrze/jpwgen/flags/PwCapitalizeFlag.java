package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwCapitalizeFlag extends AbstractPwFlag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PwCapitalizeFlag()
	{
		mask = IPwGenConstants.PW_UPPERS;
	}

	public int unmask(int flags)
	{
		return new Only1CapitalFlag().unmask(super.unmask(flags));
	}
}
