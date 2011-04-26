package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class EndsNoUpperLetterFlag extends AbstractPwFlag
{
	public EndsNoUpperLetterFlag()
	{
		mask = IPwGenRegEx.REGEX_ENDS_NO_UPPER_LETTER_FLAG;
	}
}
