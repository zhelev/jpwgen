package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractPwFlag;

public class AtLeast2DigitsFlag extends AbstractPwFlag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtLeast2DigitsFlag() {
		mask = REGEX_AT_LEAST_2_DIGITS_FLAG;
	}

	public int mask(int flags) {
		int tmp = new Only1DigitFlag().unmask(flags);
		
		return super.mask(new PwNumeralsFlag().mask(tmp));
	}

}
