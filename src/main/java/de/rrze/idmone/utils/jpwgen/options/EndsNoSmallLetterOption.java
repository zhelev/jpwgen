package de.rrze.idmone.utils.jpwgen.options;

import de.rrze.idmone.utils.jpwgen.IPwGenCommandLineOptions;

/**
 *  @autor Sheldon Fuchs
 * */
public class EndsNoSmallLetterOption implements IPwOption {
    public String getOptionName() {
	return IPwGenCommandLineOptions.CL_REGEX_ENDS_NO_SMALL_LETTER;
    }
}
