package de.rrze.jpwgen.test;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.jpwgen.impl.PwGenerator;
import de.rrze.jpwgen.utils.BlankRemover;

// Error reported for:
//--numerals --ambiguous
//--capitalize --max-attempts 100 --number 10 --size 8 
public class AmbigousTest extends PwGeneratorTest
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
	{ "default" }, invocationCount = 100)
	public void ambigousTest()
	{

		int numPasswords = 10;
		int passLength = 8;

		System.out.println("AMBIGOUS TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "--numerals --ambiguous --capitalize --max-attempts 100 --number 10 --size 8";

		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		process(new PwGenerator(), this.getClass().getSimpleName(), ar, numPasswords, passLength,
				null);

		stopWatch.stop();
		System.out.println("\nAMBIGOUS TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");
	}

}
