package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.IGeneratorOption;
import de.rrze.jpwgen.flags.AbstractCliFlag;
import de.rrze.jpwgen.utils.Messages;

public class PwCapitalizeFlag extends AbstractCliFlag implements IGeneratorOption{

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "c";

	public static final String CLI_LONG = "capitalize";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_CAPITALIZE_DESC");

	public static final String CLI_SHORT_DISABLE = "A";

	public static final String CLI_LONG_DISABLE = "no-capitalize";

	public static final String CLI_DESCRIPTION_DISABLE = Messages
			.getString("IPwGenCommandLineOptions.CL_NO_CAPITALIZE_DESC");

	public PwCapitalizeFlag() {
		mask = PW_CAPITALS;

		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;

		this.cliShortDisable = CLI_SHORT_DISABLE;
		this.cliLongDisable = CLI_LONG_DISABLE;
		this.cliDescriptionDisable = CLI_DESCRIPTION_DISABLE;
	}

	public Long unmask(Long flags) {
		return new SingleCapitalFlag().unmask(super.unmask(flags));
	}

	@Override
	public String toString() {
		return "PwCapitalizeFlag [cliShort=" + cliShort + ", cliLong="
				+ cliLong + ", cliDescription=" + cliDescription
				+ ", cliShortDisable=" + cliShortDisable + ", cliLongDisable="
				+ cliLongDisable + ", cliDescriptionDisable="
				+ cliDescriptionDisable + ", mask=" + mask + "]";
	}

}
