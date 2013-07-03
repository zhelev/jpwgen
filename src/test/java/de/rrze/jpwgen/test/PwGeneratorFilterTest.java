package de.rrze.jpwgen.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.jpwgen.IPasswordFilter;
import de.rrze.jpwgen.IPasswordPolicy;
import de.rrze.jpwgen.impl.SimpleRegexFilter;
import de.rrze.jpwgen.utils.BlankRemover;
import de.rrze.jpwgen.utils.PwHelper;

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
		filters.add(new SimpleRegexFilter("anyLetter", "[\\w]+", true, false));

		IPasswordPolicy passwordPolicy = PwHelper.buildPasswordPolicy(ar, null,
				filters);

		process(this.getClass().getSimpleName(), passwordPolicy, numPasswords);

		stopWatch.stop();
		System.out.println("\nUSER REGEX FILTER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");
	}
}
