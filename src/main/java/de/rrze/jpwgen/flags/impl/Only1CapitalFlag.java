package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractPwFlag;

public class Only1CapitalFlag extends AbstractPwFlag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Only1CapitalFlag() {
		mask = REGEX_ONLY_1_CAPITAL_FLAG;
	}

	public int mask(int flags) {
		return super.mask(flags) | PW_UPPERS;
	}

}
