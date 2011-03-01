package de.rrze.idmone.utils.jpwgen.test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.idmone.utils.jpwgen.IPwGenCommandLineOptions;
import de.rrze.idmone.utils.jpwgen.PwGeneratorOptionBuilder;

public class PwGeneratorOptionBuilderTest
{
	@BeforeClass
	public void setUp()
	{
		System.out.println("======================== "
				+ this.getClass().getSimpleName()
				+ " ================================");
	}

	@AfterClass
	public void finish()
	{
		System.out.println("======================== "
				+ this.getClass().getSimpleName()
				+ " ================================");
	}

	@Test(groups =
	{ "builder" })
	public void noOptionsSpecifiedShouldGenerateABlankSwitchLine()
	{
		PwGeneratorOptionBuilder _options = new PwGeneratorOptionBuilder();
		Assert.assertEquals(_options.build(), new String[] {});
	}

	@Test(groups =
	{ "builder" })
	public void specifyingAFlagAddsOptionAndValue()
	{
		PwGeneratorOptionBuilder _options = new PwGeneratorOptionBuilder();
		_options.setNumberOfPasswords(100);
		Assert.assertEquals(_options.build(), new String[]
		{ "-N", "100" });
	}

	@Test(groups =
	{ "builder" })
	public void specifyingASwitchAsTrueSetsAppropriateValueForOption()
	{
		PwGeneratorOptionBuilder _options = new PwGeneratorOptionBuilder();
		_options.setIncludeNumerals(true);
		Assert.assertEquals(_options.build(), new String[]
		{ "-" + IPwGenCommandLineOptions.CL_NUMERALS });
	}

	@Test(groups =
	{ "builder" })
	public void specifyingASwitchAsFalseSetsAppropriateValueForOption()
	{
		PwGeneratorOptionBuilder _options = new PwGeneratorOptionBuilder();
		_options.setIncludeNumerals(false);
		Assert.assertEquals(_options.build(), new String[]
		{ "-" + IPwGenCommandLineOptions.CL_NO_NUMERALS });
	}

	@Test(groups =
	{ "builder" })
	public void specifyingOptionSetsCorrectOption()
	{
		PwGeneratorOptionBuilder _options = new PwGeneratorOptionBuilder();
		_options.setUseRandom();
		Assert.assertEquals(_options.build(), new String[]
		{ "-" + IPwGenCommandLineOptions.CL_RANDOM });
	}
}
