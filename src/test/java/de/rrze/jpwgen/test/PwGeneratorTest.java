package de.rrze.jpwgen.test;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;

import de.rrze.jpwgen.IPasswordPolicy;
import de.rrze.jpwgen.IPwGenerator;
import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;
import de.rrze.jpwgen.impl.PwGenerator;

public class PwGeneratorTest {
	protected List<String> process(String testName,
			IPasswordPolicy passwordPolicy, int numPasswords) {

		System.out.println(ManagementFactory.getRuntimeMXBean().getName()
				+ " --> " + Thread.currentThread().getName() + passwordPolicy);

		List<String> flags = PwGeneratorFlagBuilder.evalFlags(passwordPolicy.getFlags());
		for (String flag : flags) {
			System.out.println("Applied flag: " + flag);
		}
		
		IPwGenerator pw = new PwGenerator(passwordPolicy);
		List<String> passwords = pw.generate(numPasswords, 0, null);

		Assert.assertEquals(passwords.size(), numPasswords);

		assertLengthCount(testName, passwordPolicy.getMinPwLength(), numPasswords,
				passwords);

		System.out.println(ManagementFactory.getRuntimeMXBean().getName()
				+ " --> " + Thread.currentThread().getName()
				+ " #############################");

		return passwords;
	}

	protected void assertLengthCount(String test, int passLength,
			int numPasswords, List<String> passwords) {

		int count = 0;
		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();) {
			++count;
			String password = (String) iter.next();
			if (password.length() != passLength) {
				System.out
						.printf("#####========>%s %s %s %d Wrong number of characters: %d vs. %d for %s\n",
								ManagementFactory.getRuntimeMXBean().getName(),
								Thread.currentThread().getName(), test, count,
								password.length(), passLength, password);
				Assert.assertEquals(password.length(), passLength);
			}
			System.out.printf("%s %s %s, %3d  Password: %s -> %d\n",
					ManagementFactory.getRuntimeMXBean().getName(), Thread
							.currentThread().getName(), test, count, password,
					password.length());

		}
	}
}
