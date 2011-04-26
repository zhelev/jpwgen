package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class StartsNoUpperLetterFlag extends AbstractPwFlag
{
	public StartsNoUpperLetterFlag()
	{
		mask = IPwGenRegEx.REGEX_STARTS_NO_UPPER_LETTER_FLAG;
	}
}
