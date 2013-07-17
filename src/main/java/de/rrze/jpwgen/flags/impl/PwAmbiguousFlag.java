package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.IGeneratorOption;
import de.rrze.jpwgen.flags.AbstractCliFlag;
import de.rrze.jpwgen.utils.Messages;

public class PwAmbiguousFlag extends AbstractCliFlag  implements IGeneratorOption {

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "B";

	public static final String CLI_LONG = "ambiguous";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_AMBIGOUS_DESC");

	public static final String CLI_SHORT_DISABLE = "D";

	public static final String CLI_LONG_DISABLE = "allow-ambiguous";

	public static final String CLI_DESCRIPTION_DISABLE = Messages
			.getString("IPwGenCommandLineOptions.CL_NO_AMBIGOUS_DESC");

	public PwAmbiguousFlag() {
		mask = PW_AMBIGUOUS;

		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;

		this.cliShortDisable = CLI_SHORT_DISABLE;
		this.cliLongDisable = CLI_LONG_DISABLE;
		this.cliDescriptionDisable = CLI_DESCRIPTION_DISABLE;
	}

	@Override
	public String toString() {
		return "PwAmbiguousFlag [cliShort=" + cliShort + ", cliLong=" + cliLong
				+ ", cliDescription=" + cliDescription + ", cliShortDisable="
				+ cliShortDisable + ", cliLongDisable=" + cliLongDisable
				+ ", cliDescriptionDisable=" + cliDescriptionDisable
				+ ", mask=" + mask + "]";
	}

}
