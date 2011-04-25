package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwNumeralsFlag implements IPwFlag
{
	private boolean enabled = false;

	public PwNumeralsFlag(boolean enabled)
	{
		this.enabled = enabled;
	}

	public int mask(int flags)
	{
		if (enabled)
		{
			return flags | IPwGenConstants.PW_DIGITS;
		} else
		{
			return flags & (~IPwGenConstants.PW_DIGITS);
		}
	}
}
