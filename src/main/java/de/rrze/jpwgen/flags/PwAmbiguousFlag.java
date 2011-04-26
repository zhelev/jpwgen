package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwAmbiguousFlag extends AbstractPwFlag
{
	public PwAmbiguousFlag()
	{
		mask = IPwGenConstants.PW_AMBIGUOUS;
	}
}
