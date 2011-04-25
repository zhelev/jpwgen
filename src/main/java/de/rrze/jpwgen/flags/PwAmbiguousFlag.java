package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenConstants;

public class PwAmbiguousFlag implements IPwFlag
{
	private boolean enabled = false;

	public PwAmbiguousFlag(boolean enabled)
	{
		this.enabled = enabled;
	}

	public int mask(int flags)
	{
		if (enabled)
		{
			return flags | IPwGenConstants.PW_AMBIGUOUS;
		}
		else
		{
			return flags & (~IPwGenConstants.PW_AMBIGUOUS);
		}
	}
}
