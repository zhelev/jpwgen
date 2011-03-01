package de.rrze.idmone.utils.jpwgen.options;

import de.rrze.idmone.utils.jpwgen.IPwGenCommandLineOptions;

/**
 * @autor Sheldon Fuchs
 * */
public class Only1SymbolOption implements IPwOption {
    public String getOptionName() {
	return IPwGenCommandLineOptions.CL_REGEX_ONLY_1_SYMBOL;
    }
}
