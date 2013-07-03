package de.rrze.jpwgen;

public interface ICliFlag extends IPwFlag{

	public String cliShort();

	public String cliLong();

	public String cliDescription();

	public String cliShortDisable();

	public String cliLongDisable();

	public String cliDescriptionDisable();
}
