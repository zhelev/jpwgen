package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwNumeralsFlag extends AbstractPwFlag
{
	public PwNumeralsFlag()
	{
		mask = IPwGenConstants.PW_DIGITS;
	}
}
