package de.rrze.idmone.utils.jpwgen.test;

import org.apache.maven.surefire.shade.org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.idmone.utils.jpwgen.BlankRemover;
import de.rrze.idmone.utils.jpwgen.PwGenerator;

public class PwGeneratorDefaultTest extends PwGeneratorTest
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
	{ "default" }, invocationCount = 20)
	public void defaultTest()
	{

		int numPasswords = 15;
		int passLength = 10;

		System.out.println("DEFAULT TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "-N " + numPasswords + " -M 200 -m 1 -y  -q 1 -k  -s "
				+ passLength + " -i  -j -r";

		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		PwGenerator.getDefaultBlacklistFilter().addToBlacklist("qwerty");

		process(this.getClass().getSimpleName(), ar, numPasswords, passLength);

		stopWatch.stop();
		System.out.println("\nDEFAULT TESTS FINISHED Runtime:"
				+ stopWatch.toString() + "\n");

	}

}