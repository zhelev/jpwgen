package de.rrze.jpwgen.test;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.jpwgen.IPasswordPolicy;
import de.rrze.jpwgen.IPwGenerator;
import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;
import de.rrze.jpwgen.impl.PasswordPolicy;
import de.rrze.jpwgen.impl.PwGenerator;

public class PwFlagsBuilderTest extends PwGeneratorTest {

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

	@Test(groups = { "default" }, invocationCount = 20)
	public void flagsBuilderTest() {
		int numPasswords = 10;
		int passLength = 8;

		System.out.println("FLAG BUILDER TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags.setIncludeDigits().setIncludeReducedSymbols()
				.setIncludeCapitals().setFilterAmbiguous();

		IPasswordPolicy passwordPolicy = new PasswordPolicy(passLength, passLength, 0,
				flags.build(), null);

		IPwGenerator pw = new PwGenerator(passwordPolicy);

		List<String> passwords = pw.generate(numPasswords, 0, null);

		assertLengthCount(getClass().getSimpleName(), passLength, numPasswords,
				passwords);

		stopWatch.stop();
		System.out.println("\nFLAG BUILDER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");

	}

}
