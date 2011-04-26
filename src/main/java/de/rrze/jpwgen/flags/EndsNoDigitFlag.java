package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class EndsNoDigitFlag extends AbstractPwFlag
{
	public EndsNoDigitFlag()
	{
		mask = IPwGenRegEx.REGEX_ENDS_NO_DIGIT_FLAG;
	}
}
