package de.rrze.jpwgen.options;

import de.rrze.jpwgen.IPwGenCommandLineOptions;

/**
 * @autor Sheldon Fuchs
 * */
public class Only1DigitOption implements IPwOption {
    public String getOptionName() {
	return IPwGenCommandLineOptions.CL_REGEX_ONLY_1_DIGIT;
    }
}
