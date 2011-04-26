package de.rrze.jpwgen.flags;

import java.io.Serializable;

public interface IPwFlag extends Serializable
{
	public int mask(int flags);
	
	public int unmask(int flags);
	
	public int getMask();
	
	public boolean isMasked(int flags);
}
