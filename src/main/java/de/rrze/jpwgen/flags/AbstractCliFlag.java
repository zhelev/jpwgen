package de.rrze.jpwgen.flags;

import de.rrze.jpwgen.ICliFlag;

public class AbstractCliFlag extends AbstractPwFlag implements ICliFlag {

	private static final long serialVersionUID = 1L;

	protected String cliShort;

	protected String cliLong;

	protected String cliDescription;

	protected String cliShortDisable;

	protected String cliLongDisable;

	protected String cliDescriptionDisable;

	@Override
	public String cliShort() {
		return cliShort;
	}

	@Override
	public String cliLong() {
		return cliLong;
	}

	@Override
	public String cliDescription() {
		return cliDescription;
	}

	@Override
	public String cliShortDisable() {
		return cliShortDisable;
	}

	@Override
	public String cliLongDisable() {
		return cliLongDisable;
	}

	@Override
	public String cliDescriptionDisable() {
		return cliDescriptionDisable;
	}

	@Override
	public String toString() {
		return "AbstractCliFlag [cliShort=" + cliShort + ", cliLong=" + cliLong
				+ ", cliDescription=" + cliDescription + ", cliShortDisable="
				+ cliShortDisable + ", cliLongDisable=" + cliLongDisable
				+ ", cliDescriptionDisable=" + cliDescriptionDisable
				+ ", mask=" + mask + "]";
	}

}
