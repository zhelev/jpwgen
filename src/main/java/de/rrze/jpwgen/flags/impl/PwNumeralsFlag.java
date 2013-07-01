package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractPwFlag;

public class PwNumeralsFlag extends AbstractPwFlag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PwNumeralsFlag() {
		mask = PW_DIGITS;
	}

	public int unmask(int flags) {
		return new AtLeast2DigitsFlag().unmask(new Only1DigitFlag()
				.unmask(super.unmask(flags)));
	}

}
