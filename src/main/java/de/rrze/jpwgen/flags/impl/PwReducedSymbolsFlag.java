package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractCliFlag;
import de.rrze.jpwgen.utils.Messages;

public class PwReducedSymbolsFlag extends AbstractCliFlag {

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "z";

	public static final String CLI_LONG = "reduced-symbols";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_SYMBOLS_REDUCED_DESC");

	public static final String CLI_SHORT_DISABLE = "Z";

	public static final String CLI_LONG_DISABLE = "no-reduced-symbols";

	public static final String CLI_DESCRIPTION_DISABLE = Messages
			.getString("IPwGenCommandLineOptions.CL_NO_REDUCED_SYMBOLS_DESC");

	public PwReducedSymbolsFlag() {
		mask = PW_SYMBOLS_REDUCED;

		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;

		this.cliShortDisable = CLI_SHORT_DISABLE;
		this.cliLongDisable = CLI_LONG_DISABLE;
		this.cliDescriptionDisable = CLI_DESCRIPTION_DISABLE;
	}

	public Long mask(Long flags) {
		Long maskedFlag = super.mask(flags);
		return new PwSymbolsFlag().unmask(maskedFlag);
	}

	public Long unmask(Long flags) {
		Long tmp = flags;

		if (new PwSymbolsFlag().isMasked(tmp)) {
			tmp = super.unmask(tmp);
		} else {
			tmp = new AtLeast2SymbolsFlag().unmask(new SingleSymbolFlag()
					.unmask(super.unmask(tmp)));
		}

		return tmp;
	}

	@Override
	public String toString() {
		return "PwReducedSymbolsFlag [cliShort=" + cliShort + ", cliLong="
				+ cliLong + ", cliDescription=" + cliDescription
				+ ", cliShortDisable=" + cliShortDisable + ", cliLongDisable="
				+ cliLongDisable + ", cliDescriptionDisable="
				+ cliDescriptionDisable + ", mask=" + mask + "]";
	}

}
