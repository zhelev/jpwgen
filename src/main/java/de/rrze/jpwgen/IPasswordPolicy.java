package de.rrze.jpwgen;

import java.util.List;
import java.util.Random;

public interface IPasswordPolicy {

	public abstract int getPwLength();

	public abstract void setPwLength(int length);

	public abstract int getMaxAttempts();

	public abstract void setMaxAttempts(int maxAttempts);

	public abstract Long getFlags();

	public abstract void setFlags(Long flags);

	public abstract Random getRandom();

	public abstract void setRandom(Random randomType);

	public abstract boolean addFilter(IPasswordFilter filter);

	public abstract boolean removeFilter(IPasswordFilter filter);

	public abstract boolean removeFilter(String id);

	public abstract List<String> getBlackList();

	public abstract List<IPasswordFilter> getFilters();

}