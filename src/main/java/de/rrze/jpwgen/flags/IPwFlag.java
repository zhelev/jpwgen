package de.rrze.jpwgen.flags;

public interface IPwFlag
{
	int mask(int flags);
	
	int unmask(int flags);
}
