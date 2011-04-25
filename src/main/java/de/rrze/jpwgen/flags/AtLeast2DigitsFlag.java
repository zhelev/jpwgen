package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class AtLeast2DigitsFlag implements IPwFlag
{
	public int mask(int flags)
	{
		return flags | IPwGenRegEx.REGEX_AT_LEAST_2_DIGITS_FLAG;
	}

}
