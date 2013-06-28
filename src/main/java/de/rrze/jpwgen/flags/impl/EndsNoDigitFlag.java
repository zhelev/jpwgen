package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.IPwGenRegEx;
import de.rrze.jpwgen.flags.AbstractPwFlag;

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
