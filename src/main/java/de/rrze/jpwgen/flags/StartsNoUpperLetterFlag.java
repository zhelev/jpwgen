package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class StartsNoUpperLetterFlag extends AbstractPwFlag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StartsNoUpperLetterFlag()
	{
		mask = IPwGenRegEx.REGEX_STARTS_NO_UPPER_LETTER_FLAG;
	}
}
