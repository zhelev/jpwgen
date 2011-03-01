package de.rrze.idmone.utils.jpwgen.options;

import de.rrze.idmone.utils.jpwgen.IPwGenCommandLineOptions;

/**
 * @autor Sheldon Fuchs
 * */
public class PwCapitalizeOption implements IPwOption{

    public String getOptionName() {
	return IPwGenCommandLineOptions.CL_CAPITALIZE;
    }
}
