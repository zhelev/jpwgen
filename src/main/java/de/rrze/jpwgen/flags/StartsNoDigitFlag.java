package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class StartsNoDigitFlag extends AbstractPwFlag
{

	public StartsNoDigitFlag()
	{
		mask = IPwGenRegEx.REGEX_STARTS_NO_DIGIT_FLAG;
	}

}
