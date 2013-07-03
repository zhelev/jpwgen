package de.rrze.jpwgen.test;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.Test;

import de.rrze.jpwgen.IDefaultFilter;
import de.rrze.jpwgen.IPasswordPolicy;
import de.rrze.jpwgen.IPwGenerator;
import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;
import de.rrze.jpwgen.flags.impl.PwDigitsFlag;
import de.rrze.jpwgen.flags.impl.PwReducedSymbolsFlag;
import de.rrze.jpwgen.flags.impl.PwSymbolsFlag;
import de.rrze.jpwgen.flags.impl.SingleSymbolFlag;
import de.rrze.jpwgen.impl.PasswordPolicy;
import de.rrze.jpwgen.impl.PwGenerator;

public class BuildersTest extends PwGeneratorTest {

	@Test(groups = { "default" }, invocationCount = 30)
	public void flagsBuilderTest() {
		int numPasswords = 10;
		int passLength = 8;

		System.out.println("FLAG BUILDER TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags.setIncludeDigits().setIncludeCapitals()
				.setIncludeReducedSymbols().setFilterAmbiguous();

		IPasswordPolicy passwordPolicy = new PasswordPolicy(passLength, 0,
				flags.build(), null);

		IPwGenerator pw = new PwGenerator(passwordPolicy);

		List<String> passwords = pw.generate(numPasswords, 0, null);

		assertLengthCount(getClass().getSimpleName(), passLength, numPasswords,
				passwords);

		stopWatch.stop();
		System.out.println("\nFLAG BUILDER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");

	}

	@Test(groups = { "default" }, invocationCount = 20)
	public void dependencyTest() {
		PwGeneratorFlagBuilder flagBuilder = new PwGeneratorFlagBuilder(
				IDefaultFilter.DEFAULT_FLAGS);
		Long flags = flagBuilder.build();

		flags = new SingleSymbolFlag().mask(flags);

		Assert.assertFalse(new PwSymbolsFlag().isMasked(flags));
		System.out.println("Symbols set: "
				+ new PwSymbolsFlag().isMasked(flags));

		Assert.assertTrue(new PwReducedSymbolsFlag().isMasked(flags));
		System.out.println("Reduced Symbols set: "
				+ new PwReducedSymbolsFlag().isMasked(flags));

		flags = new PwSymbolsFlag().mask(flags);
		System.out.println("Symbols set: " + flags);

		Assert.assertTrue(new PwSymbolsFlag().isMasked(flags));
		Assert.assertFalse(new PwReducedSymbolsFlag().isMasked(flags));
		Assert.assertTrue(new SingleSymbolFlag().isMasked(flags));

		System.out.println("Only 1 Symbol set: "
				+ new SingleSymbolFlag().isMasked(flags));

		flags = new PwSymbolsFlag().mask(flags);
		System.out.println("Reduced Symbols unset: " + flags);

		Assert.assertFalse(new PwReducedSymbolsFlag().isMasked(flags));
		Assert.assertTrue(new SingleSymbolFlag().isMasked(flags));

		Assert.assertTrue(new PwSymbolsFlag().isMasked(flags));
		flags = new PwSymbolsFlag().unmask(flags);

		System.out.println("Only 1 Symbol set: "
				+ new SingleSymbolFlag().isMasked(flags));

		Assert.assertFalse(new SingleSymbolFlag().isMasked(flags));

		System.out.println("Digits set: " + new PwDigitsFlag().isMasked(flags));
	}

}