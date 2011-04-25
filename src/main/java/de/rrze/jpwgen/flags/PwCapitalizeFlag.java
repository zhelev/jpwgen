package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwCapitalizeFlag implements IPwFlag
{
	private boolean enabled = false;

	public PwCapitalizeFlag(boolean enabled)
	{
		this.enabled = enabled;
	}

	public int mask(int flags)
	{
		if (enabled)
		{
			return flags | IPwGenConstants.PW_UPPERS;
		} else
		{
			return flags & (~IPwGenConstants.PW_UPPERS);
		}
	}
}
