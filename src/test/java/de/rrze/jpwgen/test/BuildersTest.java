package de.rrze.jpwgen.test;

import java.util.List;

import org.apache.maven.surefire.shade.org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.Test;

import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;
import de.rrze.jpwgen.impl.PwGenerator;
import de.rrze.jpwgen.options.PwGeneratorOptionBuilder;
import de.rrze.jpwgen.utils.PwHelper;
import de.rrze.jpwgen.utils.RandomFactory;

public class BuildersTest extends PwGeneratorTest
{

	@Test(groups =
	{ "default" }, invocationCount = 30)
	public void flagsBuilderTest()
	{
		int numPasswords = 10;
		int passLength = 8;

		System.out.println("FLAG BUILDER TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags.setIncludeNumerals().setIncludeCapitals()
				.setIncludeReducedSymbols().setFilterAmbiguous();

		List<String> passwords = PwGenerator
				.generate(passLength, numPasswords, 0, flags.build(),
						RandomFactory.getInstance().getRandom(), null);

		assertLengthCount(getClass().getSimpleName(), passLength, numPasswords,
				passwords);

		stopWatch.stop();
		System.out.println("\nFLAG BUILDER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");

	}

	@Test(groups =
	{ "default" }, invocationCount = 20)
	public void optionsBuilderTest()
	{
		int numPasswords = 10;
		int passLength = 8;

		System.out
				.println("OPTIONS BUILDER TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		PwGeneratorOptionBuilder options = new PwGeneratorOptionBuilder();
		options.setIncludeNumerals(true).setNumberOfPasswords(10)
				.setIncludeSymbols(true).setIncludeOneCapital().setUseRandom()
				.setIncludeAmbiguous(true);

		List<String> passwords = PwHelper.process(options.build(), null);

		assertLengthCount(getClass().getSimpleName(), passLength, numPasswords,
				passwords);

		stopWatch.stop();
		System.out.println("\nOPTIONS BUILDER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");

	}

}