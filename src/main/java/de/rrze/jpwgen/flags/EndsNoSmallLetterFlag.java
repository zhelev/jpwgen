package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class EndsNoSmallLetterFlag extends AbstractPwFlag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EndsNoSmallLetterFlag()
	{
		mask = IPwGenRegEx.REGEX_ENDS_NO_SMALL_LETTER_FLAG;
	}
}
