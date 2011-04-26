package de.rrze.jpwgen.flags;

public abstract class AbstractPwFlag implements IPwFlag
{
	protected int mask = 0;

	public int mask(int flags)
	{
		return flags | mask;
	}

	public int unmask(int flags)
	{
		return flags & (~mask);
	}

	public int getMask()
	{
		return mask;
	}
	
	public boolean isMasked(int flags)
	{
		return (flags & mask) != 0;
	}
}
