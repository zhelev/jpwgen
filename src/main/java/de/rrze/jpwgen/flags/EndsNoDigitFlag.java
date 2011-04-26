package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class EndsNoDigitFlag extends AbstractPwFlag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EndsNoDigitFlag()
	{
		mask = IPwGenRegEx.REGEX_ENDS_NO_DIGIT_FLAG;
	}
}
