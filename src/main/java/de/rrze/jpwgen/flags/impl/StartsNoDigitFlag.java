package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractPwFlag;

public class StartsNoDigitFlag extends AbstractPwFlag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StartsNoDigitFlag() {
		mask = REGEX_STARTS_NO_DIGIT_FLAG;
	}

}
