package de.rrze.idmone.utils.jpwgen.options;

import de.rrze.idmone.utils.jpwgen.IPwGenCommandLineOptions;
import de.rrze.idmone.utils.jpwgen.IPwGenConstants;

/**
 * The number of attempts to generate a suitable password, before giving up
 * 
 * @autor Sheldon Fuchs, unrz205
 * */
public class MaxAttempts extends PwOptionFlag
{
	public MaxAttempts()
	{
		setValue(Integer.toString(IPwGenConstants.DEFAULT_MAX_ATTEMPTS));
	}

	public String getOptionName()
	{
		return IPwGenCommandLineOptions.CL_MAX_ATTEMPTS;
	}
}
