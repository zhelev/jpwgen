package de.rrze.idmone.utils.jpwgen.options;

import de.rrze.idmone.utils.jpwgen.IPwGenCommandLineOptions;

/**
 * @autor Sheldon Fuchs
 * */
public class PwNumeralsOption extends PwOptionSwitch {
    public String getOptionName() {
	return isEnabled() ? IPwGenCommandLineOptions.CL_NUMERALS : IPwGenCommandLineOptions.CL_NO_NUMERALS;
    }
}
