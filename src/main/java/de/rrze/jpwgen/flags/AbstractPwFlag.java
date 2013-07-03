package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.IDefaultFilter;
import de.rrze.jpwgen.IPwFlag;

public abstract class AbstractPwFlag implements IPwFlag, IDefaultFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Long mask = 0l;

	public Long mask(Long flags) {
		return flags | mask;
	}

	public Long unmask(Long flags) {
		return flags & (~mask);
	}

	public Long getMask() {
		return mask;
	}

	public boolean isMasked(Long flags) {
		return (flags & mask) != 0;
	}

	public String getId() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return "AbstractPwFlag [mask=" + mask + "]";
	}

}
