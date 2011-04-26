package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class StartsNoSmallLetterFlag extends AbstractPwFlag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StartsNoSmallLetterFlag()
	{
		mask = IPwGenRegEx.REGEX_STARTS_NO_SMALL_LETTER_FLAG;
	}

}
