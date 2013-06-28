package de.rrze.jpwgen.test;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.jpwgen.impl.PwGenerator;
import de.rrze.jpwgen.utils.BlankRemover;

public class PwGeneratorUpperTest extends PwGeneratorTest
{

	@BeforeClass
	public void setUp()
	{
		System.out.println("======================== "
				+ this.getClass().getSimpleName()
				+ " ================================");
	}

	@AfterClass
	public void finish()
	{
		System.out.println("======================== "
				+ this.getClass().getSimpleName()
				+ " ================================");
	}

	@Test(groups =
	{ "default" }, invocationCount = 50)
	public void upperSymbolsTest()
	{

		int numPasswords = 30;
		int passLength = 8;
		
		System.out
				.println("UPPER TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "-N " + numPasswords + " -M 500 -m 1 -s " + passLength
				+ " -z -r -c";

		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		List<String> passwords = process(this.getClass().getSimpleName(), ar, numPasswords, passLength, null);

		for (String password : passwords)
		{
			char[] cs = password.toCharArray();
			boolean found = false;
			for (int i = 0; i < cs.length; i++)
			{
				if(Character.isUpperCase(cs[i]))
					found = true;
			}
			if(!found)
				throw new RuntimeException("No upper case letters");
		}
		
		stopWatch.stop();
		System.out.println("\nUPPER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");
	}
}
