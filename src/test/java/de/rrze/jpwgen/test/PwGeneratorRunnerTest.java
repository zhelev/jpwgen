package de.rrze.jpwgen.test;

import java.util.List;

import org.apache.maven.surefire.shade.org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.jpwgen.options.PwGeneratorOptionBuilder;
import de.rrze.jpwgen.utils.PwHelper;

public class PwGeneratorRunnerTest extends PwGeneratorTest
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
	{ "builder" }, invocationCount = 20)
	public void testRunner()
	{
		int passLength = 11;
		int numPasswords = 25;

		System.out.println("RUNNER TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		PwGeneratorOptionBuilder options = new PwGeneratorOptionBuilder()
				.setNumberOfPasswords(numPasswords).setMaxAttempts(100)
				.setOnly1Digit().setPasswordLength(passLength)
				.setDoNotEndWithSmallLetter().setIncludeAmbiguous(false)
				.setIncludeSymbols(false).setUseRandom()
				.setDoNotStartWithDigit();

		List<String> passwords = PwHelper.process(options.build());
		assertLengthCount(this.getClass().getSimpleName(), passLength,
				numPasswords, passwords);

		stopWatch.stop();
		System.out.println("\nRUNNER TEST FINISHED:" + stopWatch.toString()
				+ "\n");
	}
}
