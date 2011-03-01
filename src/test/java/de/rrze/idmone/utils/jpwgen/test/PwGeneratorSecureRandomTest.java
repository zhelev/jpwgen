package de.rrze.idmone.utils.jpwgen.test;

import org.apache.maven.surefire.shade.org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.idmone.utils.jpwgen.BlankRemover;
import de.rrze.idmone.utils.jpwgen.PwGenerator;

public class PwGeneratorSecureRandomTest extends PwGeneratorTest
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
	{ "secure" }, invocationCount = 20)
	public void secureTest()
	{

		int numPasswords = 20;
		int passLength = 12;

		System.out
				.println("SECURE TEST STARTED: Generating password with secure random:");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "-N " + numPasswords + " -s " + passLength
				+ " -C -Y -S SHA1PRNG SUN";

		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		PwGenerator.getDefaultBlacklistFilter().addToBlacklist("qwerty");

		process(this.getClass().getSimpleName(), ar, numPasswords, passLength);

		stopWatch.stop();
		System.out.println("\nSECURE TEST FINISHED:" + stopWatch.toString()
				+ "\n");
	}

}
