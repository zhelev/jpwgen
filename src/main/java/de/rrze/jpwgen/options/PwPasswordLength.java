package de.rrze.jpwgen.options;

import de.rrze.jpwgen.IPwGenCommandLineOptions;
import de.rrze.jpwgen.IPwDefConstants;

/**
 * @autor Sheldon Fuchs, unrz205
 * */
public class PwPasswordLength extends PwOptionFlag
{
	public PwPasswordLength()
	{
		setValue(Integer.toString(IPwDefConstants.DEFAULT_PASSWORD_LENGTH));
	}

	public String getOptionName()
	{
		return IPwGenCommandLineOptions.CL_PASSWORD_LENGTH;
	}
}
