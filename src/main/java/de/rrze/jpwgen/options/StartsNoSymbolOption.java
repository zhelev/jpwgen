package de.rrze.jpwgen.options;

import de.rrze.jpwgen.IPwGenCommandLineOptions;

/**
 * @autor Sheldon Fuchs
 * */
public class StartsNoSymbolOption implements IPwOption {
    public String getOptionName() {
	return IPwGenCommandLineOptions.CL_REGEX_STARTS_NO_SYMBOL;
    }
}
