package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IPwGenRegEx;

public class Only1CapitalFlag implements IPwFlag
{
	public int mask(int flags)
	{
		return flags | IPwGenRegEx.REGEX_ONLY_1_CAPITAL_FLAG;
	}
}
