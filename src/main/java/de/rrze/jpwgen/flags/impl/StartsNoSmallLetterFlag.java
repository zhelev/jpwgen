package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.IPwGenRegEx;
import de.rrze.jpwgen.flags.AbstractPwFlag;

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
