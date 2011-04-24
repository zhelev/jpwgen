package de.rrze.jpwgen.options;

import de.rrze.jpwgen.IPwGenCommandLineOptions;
import de.rrze.jpwgen.IPwGenConstants;

/**
 * @autor Sheldon Fuchs, unrz205
 * */
public class PwPasswordLength extends PwOptionFlag
{
	public PwPasswordLength()
	{
		setValue(Integer.toString(IPwGenConstants.DEFAULT_PASSWORD_LENGTH));
	}

	public String getOptionName()
	{
		return IPwGenCommandLineOptions.CL_PASSWORD_LENGTH;
	}
}
