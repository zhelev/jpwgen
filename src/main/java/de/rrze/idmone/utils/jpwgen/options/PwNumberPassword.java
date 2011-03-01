package de.rrze.idmone.utils.jpwgen.options;

import de.rrze.idmone.utils.jpwgen.IPwGenCommandLineOptions;
import de.rrze.idmone.utils.jpwgen.IPwGenConstants;

/**
 * @autor Sheldon Fuchs, unrz205
 * */
public class PwNumberPassword extends PwOptionFlag {
    public PwNumberPassword() {
	setValue(Integer.toString(IPwGenConstants.DEFAULT_NUMBER_OF_PASSWORDS));
    }

    public String getOptionName() {
	return IPwGenCommandLineOptions.CL_NUMBER_PASSWORD;
    }
}
