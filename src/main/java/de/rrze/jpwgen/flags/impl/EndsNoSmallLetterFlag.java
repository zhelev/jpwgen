package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractPwFlag;

public class EndsNoSmallLetterFlag extends AbstractPwFlag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EndsNoSmallLetterFlag() {
		mask = REGEX_ENDS_NO_SMALL_LETTER_FLAG;
	}
}