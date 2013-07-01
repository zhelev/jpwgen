package de.rrze.jpwgen.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.jpwgen.IPasswordFilter;
import de.rrze.jpwgen.impl.PwGenerator;
import de.rrze.jpwgen.utils.BlankRemover;

public class PwGeneratorFilterTest extends PwGeneratorDefaultTest {
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

	@Test(groups = { "regex" }, invocationCount = 1)
	public void reducedSymbolsTest() {

		int numPasswords = 30;
		int passLength = 8;

		System.out
				.println("USER REGEX FILTER TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "-N " + numPasswords + " -M 500 -m 1 -s " + passLength
				+ " -z -r";

		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		List<IPasswordFilter> filters = new ArrayList<IPasswordFilter>();
		// filters.add(new
		// SimpleRegexFilter("ididid",SimpleRegexFilter.REGEX_STARTS_NO_SMALL_LETTER,true));

		process(new PwGenerator(), this.getClass().getSimpleName(), ar,
				numPasswords, passLength, null, filters);

		stopWatch.stop();
		System.out.println("\nUSER REGEX FILTER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");
	}
}
