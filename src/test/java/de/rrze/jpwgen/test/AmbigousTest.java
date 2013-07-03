package de.rrze.jpwgen.test;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.jpwgen.IPasswordPolicy;
import de.rrze.jpwgen.utils.BlankRemover;
import de.rrze.jpwgen.utils.PwHelper;

// Error reported for:
//--digits --ambiguous
//--capitalize --max-attempts 100 --number 10 --size 8 
public class AmbigousTest extends PwGeneratorTest {

	@BeforeClass
	public void setUp() {
		System.out.println("======================== "
				+ this.getClass().getSimpleName()
				+ " ================================");
	}

	@AfterClass
	public void finish() {
		System.out.println("======================== "
				+ this.getClass().getSimpleName()
				+ " ================================");
	}

	@Test(groups = { "default" }, invocationCount = 100)
	public void ambigousTest() {

		int numPasswords = 10;

		System.out.println("AMBIGOUS TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "--digits --ambiguous --capitalize --max-attempts 100 --size 8 --number "
				+ numPasswords;

		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		IPasswordPolicy passwordPolicy = PwHelper.buildPasswordPolicy(ar, null,
				null);

		process(this.getClass().getSimpleName(), passwordPolicy, numPasswords);

		stopWatch.stop();
		System.out.println("\nAMBIGOUS TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");
	}

}
