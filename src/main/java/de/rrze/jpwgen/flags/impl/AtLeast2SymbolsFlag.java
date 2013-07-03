package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractCliFlag;
import de.rrze.jpwgen.utils.Messages;

public class AtLeast2SymbolsFlag extends AbstractCliFlag {

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "p";

	public static final String CLI_LONG = "two-symbol";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_REGEX_AT_LEAST_2_SYMBOLS_DESC");

	public AtLeast2SymbolsFlag() {
		mask = REGEX_AT_LEAST_2_SYMBOLS_FLAG;

		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;
	}

	public Long mask(Long flags) {

		Long tmp = new SingleSymbolFlag().unmask(flags);

		if (new PwReducedSymbolsFlag().isMasked(tmp)
				|| new PwSymbolsFlag().isMasked(tmp))
			return super.mask(tmp);

		return super.mask(new PwReducedSymbolsFlag().mask(tmp));
	}

	@Override
	public String toString() {
		return "AtLeast2SymbolsFlag [cliShort=" + cliShort + ", cliLong="
				+ cliLong + ", cliDescription=" + cliDescription
				+ ", cliShortDisable=" + cliShortDisable + ", cliLongDisable="
				+ cliLongDisable + ", cliDescriptionDisable="
				+ cliDescriptionDisable + ", mask=" + mask + "]";
	}

}
