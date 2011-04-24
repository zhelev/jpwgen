package de.rrze.jpwgen.options;

import de.rrze.jpwgen.IPwGenCommandLineOptions;

/**
 *  @autor Sheldon Fuchs
 * */
public class EndsNoUpperLetterOption implements IPwOption {
    public String getOptionName() {
	return IPwGenCommandLineOptions.CL_REGEX_ENDS_NO_UPPER_LETTER;
    }
}
