package de.rrze.jpwgen.test;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.List;


import junit.framework.Assert;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.Test;

import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;
import de.rrze.jpwgen.impl.PwGenerator;
import de.rrze.jpwgen.impl.SimpleRegexFilter;
import de.rrze.jpwgen.utils.RandomFactory;

public class InstanceTest
{

	@Test(groups =
	{ "instance" }, invocationCount = 30)
	public void instanceTest() throws Exception
	{
		int numPasswords = 10;
		int passLength = 8;

		System.out.println("INSTANCE TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();


		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags.setIncludeNumerals().setOnly1Capital()
				.setIncludeReducedSymbols().setFilterAmbiguous().setDoNotEndWithSymbol().setDoNotEndWithDigit();

		int builtFlag = flags.build();

		System.out.println("Applied falgs: " +  PwGeneratorFlagBuilder.evalFlags(builtFlag));

		PwGenerator pg = new PwGenerator();
		pg.addInstanceFilter(new SimpleRegexFilter("lala","(?=.{9,})(?=.*?[^\\w\\s])(?=.*?[0-9])(?=.*?[A-Z]).*?[a-z].*",false));
		pg.addInstanceFilter(new SimpleRegexFilter("lala1","^a.*4.*",false));

		List<String> passwords = pg
				.gen(passLength, numPasswords, 0, builtFlag,
				RandomFactory.getInstance().getRandom(), null);
		
		assertLengthCount("InstanceTest", passLength, numPasswords, passwords);

		Assert.assertEquals(numPasswords,passwords.size());
		
		stopWatch.stop();
		System.out.println("\nFLAG BUILDER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");

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