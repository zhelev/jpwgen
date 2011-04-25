package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class Only1DigitFlag implements IPwFlag
{
	public int mask(int flags)
	{
		return flags | IPwGenRegEx.REGEX_ONLY_1_DIGIT_FLAG;
	}
}
