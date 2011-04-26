package de.rrze.jpwgen.flags;

public interface IPwFlag
{
	public int mask(int flags);
	
	public int unmask(int flags);
	
	public int getMask();
}
