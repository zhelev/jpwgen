package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractCliFlag;
import de.rrze.jpwgen.utils.Messages;

public class PwSymbolsFlag extends AbstractCliFlag {

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "y";

	public static final String CLI_LONG = "symbols";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_SYMBOLS_DESC");

	public static final String CLI_SHORT_DISABLE = "Y";

	public static final String CLI_LONG_DISABLE = "no-symbols";

	public static final String CLI_DESCRIPTION_DISABLE = Messages
			.getString("IPwGenCommandLineOptions.CL_NO_SYMBOLS_DESC");

	public PwSymbolsFlag() {
		mask = PW_SYMBOLS;

		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;

		this.cliShortDisable = CLI_SHORT_DISABLE;
		this.cliLongDisable = CLI_LONG_DISABLE;
		this.cliDescriptionDisable = CLI_DESCRIPTION_DISABLE;
	}

	public Long mask(Long flags) {
		Long maskedFlag = super.mask(flags);
		return new PwReducedSymbolsFlag().unmask(maskedFlag);
	}

	public Long unmask(Long flags) {
		Long tmp = flags;

		if (new PwReducedSymbolsFlag().isMasked(tmp)) {
			tmp = super.unmask(tmp);
		} else {
			tmp = new AtLeast2SymbolsFlag().unmask(new SingleSymbolFlag()
					.unmask(super.unmask(tmp)));
		}

		return tmp;
	}

	@Override
	public String toString() {
		return "PwSymbolsFlag [cliShort=" + cliShort + ", cliLong=" + cliLong
				+ ", cliDescription=" + cliDescription + ", cliShortDisable="
				+ cliShortDisable + ", cliLongDisable=" + cliLongDisable
				+ ", cliDescriptionDisable=" + cliDescriptionDisable
				+ ", mask=" + mask + "]";
	}

}
