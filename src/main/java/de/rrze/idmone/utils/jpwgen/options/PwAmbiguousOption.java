package de.rrze.idmone.utils.jpwgen.options;

import de.rrze.idmone.utils.jpwgen.IPwGenCommandLineOptions;

/**
 * @autor Sheldon Fuchs
 * */
public class PwAmbiguousOption extends PwOptionSwitch {
    public String getOptionName() {
	return isEnabled() ? IPwGenCommandLineOptions.CL_AMBIGOUS : IPwGenCommandLineOptions.CL_NO_AMBIGOUS;
    }
}
