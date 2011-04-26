package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;
import de.rrze.jpwgen.IPwGenRegEx;

public class AtLeast2DigitsFlag extends AbstractPwFlag
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AtLeast2DigitsFlag()
	{
		mask = IPwGenRegEx.REGEX_AT_LEAST_2_DIGITS_FLAG;
	}

	public int mask(int flags)
	{
		return super.mask(flags) | IPwGenConstants.PW_DIGITS;
	}

}
