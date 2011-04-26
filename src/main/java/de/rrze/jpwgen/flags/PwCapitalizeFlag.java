package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwCapitalizeFlag extends AbstractPwFlag
{
	public PwCapitalizeFlag()
	{
		mask = IPwGenConstants.PW_UPPERS;
	}
}
