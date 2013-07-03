package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractCliFlag;
import de.rrze.jpwgen.utils.Messages;

public class SingleSymbolFlag extends AbstractCliFlag {

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "o";

	public static final String CLI_LONG = "single-symbol";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_REGEX_SINGLE_SYMBOL_DESC");

	public SingleSymbolFlag() {
		mask = REGEX_SINGLE_SYMBOL_FLAG;

		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;
	}

	public Long mask(Long flags) {
		Long tmp = new AtLeast2SymbolsFlag().unmask(flags);

		if (new PwReducedSymbolsFlag().isMasked(tmp)
				|| new PwSymbolsFlag().isMasked(tmp))
			return super.mask(tmp);

		return super.mask(new PwReducedSymbolsFlag().mask(tmp));
	}

	@Override
	public String toString() {
		return "SingleSymbolFlag [cliShort=" + cliShort + ", cliLong="
				+ cliLong + ", cliDescription=" + cliDescription
				+ ", cliShortDisable=" + cliShortDisable + ", cliLongDisable="
				+ cliLongDisable + ", cliDescriptionDisable="
				+ cliDescriptionDisable + ", mask=" + mask + "]";
	}

}
