package de.rrze.jpwgen.options;

import de.rrze.jpwgen.IPwGenCommandLineOptions;

/**
 * @autor Sheldon Fuchs
 * */
public class PwColumnOptions implements IPwOption {
    public String getOptionName() {
	return IPwGenCommandLineOptions.CL_COLUMN;
    }
}
