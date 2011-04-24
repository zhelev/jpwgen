package de.rrze.jpwgen.options;

import de.rrze.jpwgen.IPwGenCommandLineOptions;

/**
 * @autor Sheldon Fuchs
 * */
public class PwSymbolsOption extends PwOptionSwitch {
    public String getOptionName() {
	return isEnabled() ? IPwGenCommandLineOptions.CL_SYMBOLS: IPwGenCommandLineOptions.CL_NO_SYMBOLS;
    }
}
