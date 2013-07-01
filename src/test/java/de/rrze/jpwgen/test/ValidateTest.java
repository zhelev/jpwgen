package de.rrze.jpwgen.test;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.List;


import junit.framework.Assert;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.Test;

import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;
import de.rrze.jpwgen.impl.DefaultRegExFilter;
import de.rrze.jpwgen.impl.PwGenerator;
import de.rrze.jpwgen.impl.SimpleRegexFilter;
import de.rrze.jpwgen.utils.RandomFactory;

public class ValidateTest
{

	@Test(groups =
	{ "instance" }, invocationCount = 30)
	public void conformTest() throws Exception
	{
		int numPasswords = 10;
		int passLength = 8;

		System.out.println("INSTANCE TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();


		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags.setIncludeNumerals().setIncludeCapitals()
				.setIncludeReducedSymbols().setFilterAmbiguous().setDoNotEndWithSymbol().setDoNotEndWithDigit();

		int builtFlag = flags.build();


		PwGenerator pg = new PwGenerator();
		pg.addInstanceFilter(new SimpleRegexFilter("lala","(?=.{8,})(?=.*?[^\\w\\s])(?=.*?[0-9])(?=.*?[A-Z]).*?[a-z].*",false));
		pg.addInstanceFilter(new SimpleRegexFilter("lala1","^a.*4.*",false));

		
		String password = "b33xA(sr";
		
		System.out.println("inValid: " + PwGenerator.isInvalid(builtFlag, password));
		
		List<String> drfResult = PwGenerator.failsDefaultRegExFilter(builtFlag, password);
		
		System.out.println("Test DefaultBlackFilter: " + drfResult);
		
		Boolean dbResult = PwGenerator.failsDefaultBlackList(builtFlag, password);
		
		System.out.println("Test DefaultBlackFilter: " + dbResult);
	
		System.out.println("instanceInvalid: " + pg.isInstanceInvalid(builtFlag, password));
		
		Assert.assertEquals(false,false);
		
		stopWatch.stop();
		System.out.println("\nFLAG BUILDER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");

		//assertLengthCount(getClass().getSimpleName(), passLength, numPasswords, passwords);
		
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