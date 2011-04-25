package de.rrze.jpwgen.test;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;

import de.rrze.jpwgen.utils.PwHelper;

public class PwGeneratorTest
{
	protected List<String> process(String test, String[] ar, int numPasswords,
			int passLength, List<String> blacklist)
	{

		System.out.println(ManagementFactory.getRuntimeMXBean().getName()
				+ " --> " + Thread.currentThread().getName() + " FLAGS: "
				+ test + " --> " + Arrays.deepToString(ar) + " -> "
				+ passLength);

		List<String> passwords = PwHelper.process(ar, blacklist);

		Assert.assertEquals(passwords.size(), numPasswords);

		assertLengthCount(test, passLength, numPasswords, passwords);

		System.out.println(ManagementFactory.getRuntimeMXBean().getName()
				+ " --> " + Thread.currentThread().getName()
				+ " #############################");

		return passwords;
	}

	protected void assertLengthCount(String test, int passLength,
			int numPasswords, List<String> passwords)
	{

		int count = 0;
		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();)
		{
			++count;
			String password = (String) iter.next();
			if (password.length() != passLength)
			{
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
