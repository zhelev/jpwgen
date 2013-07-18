package de.rrze.jpwgen.test;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.Test;

import de.rrze.jpwgen.IPasswordPolicy;
import de.rrze.jpwgen.IPwGenerator;
import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;
import de.rrze.jpwgen.impl.PasswordPolicy;
import de.rrze.jpwgen.impl.PwGenerator;

public class PasswordLengthTest {

	@Test(groups = { "instance" })
	public void conformTest() throws Exception {
		int numPasswords = 20;
		int minPwdLength = 8;
		int maxPwdLength = 12;

		System.out
				.println("PasswordLengthTest TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags.setIncludeDigits().setIncludeCapitals()
				.setIncludeReducedSymbols().setFilterAmbiguous()
				.setDoNotEndWithSymbol().setDoNotEndWithDigit()
				.setDisableConsecSymbols();

		IPasswordPolicy passwordPolicy = new PasswordPolicy(minPwdLength,
				maxPwdLength, 0, flags.build(), null);

		IPwGenerator pw = new PwGenerator(passwordPolicy);

		List<String> passwords = pw.generate(numPasswords, 0, null);

		assertLengthCount(this.getClass().getSimpleName(), minPwdLength,
				maxPwdLength, numPasswords, passwords);

		String password = "na3,";

		/** part two */

		Map<String, Map<String, String>> validationResult = pw
				.validate(password);

		for (String filterId : validationResult.keySet()) {
			System.out.println(filterId);
			System.out.println(validationResult.get(filterId));
		}

		Assert.assertTrue(validationResult.size() > 0);

		stopWatch.stop();
		System.out.println("\nPasswordLengthTest TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");

	}

	protected void assertLengthCount(String test, int minPwdLength,
			int maxPwdLength, int numPasswords, List<String> passwords) {

		int count = 0;
		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();) {
			++count;
			String password = (String) iter.next();
			if (password.length() < minPwdLength) {
				System.out
						.printf("#####========>%s %s %s %d Wrong number of characters - too short: %d vs. %d for %s\n",
								ManagementFactory.getRuntimeMXBean().getName(),
								Thread.currentThread().getName(), test, count,
								password.length(), minPwdLength, password);
				Assert.assertEquals(password.length(), minPwdLength);
			}
			if (password.length() > maxPwdLength) {
				System.out
						.printf("#####========>%s %s %s %d Wrong number of characters too long: %d vs. %d for %s\n",
								ManagementFactory.getRuntimeMXBean().getName(),
								Thread.currentThread().getName(), test, count,
								password.length(), minPwdLength, password);
				Assert.assertEquals(password.length(), minPwdLength);
			}

			System.out.printf("%s %s %s, %3d  Password: %s -> %d\n",
					ManagementFactory.getRuntimeMXBean().getName(), Thread
							.currentThread().getName(), test, count, password,
					password.length());

		}
	}

}