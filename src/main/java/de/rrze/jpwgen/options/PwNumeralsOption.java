package de.rrze.jpwgen.options;

import de.rrze.jpwgen.IPwGenCommandLineOptions;

/**
 * @autor Sheldon Fuchs
 * */
public class PwNumeralsOption extends PwOptionSwitch {
    public String getOptionName() {
	return isEnabled() ? IPwGenCommandLineOptions.CL_NUMERALS : IPwGenCommandLineOptions.CL_NO_NUMERALS;
    }
}
