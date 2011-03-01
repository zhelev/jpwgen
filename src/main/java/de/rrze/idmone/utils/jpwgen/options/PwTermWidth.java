package de.rrze.idmone.utils.jpwgen.options;

import de.rrze.idmone.utils.jpwgen.IPwGenCommandLineOptions;
import de.rrze.idmone.utils.jpwgen.IPwGenConstants;

/**
 * @autor Sheldon Fuchs, unrz205
 * */
public class PwTermWidth extends PwOptionFlag
{
	public PwTermWidth()
	{
		setValue(Integer.toString(IPwGenConstants.DEFAULT_TERM_WIDTH));
	}

	public String getOptionName()
	{
		return IPwGenCommandLineOptions.CL_TERM_WIDTH;
	}
}
