package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwAmbiguousFlag extends AbstractPwFlag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PwAmbiguousFlag()
	{
		mask = IPwGenConstants.PW_AMBIGUOUS;
	}
}
