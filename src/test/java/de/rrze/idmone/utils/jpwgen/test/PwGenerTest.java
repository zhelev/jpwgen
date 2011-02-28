package de.rrze.idmone.utils.jpwgen.test;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.surefire.shade.org.apache.commons.lang.time.StopWatch;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.idmone.utils.jpwgen.BlankRemover;
import de.rrze.idmone.utils.jpwgen.PwGenerator;

public class PwGenerTest
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
	{ "normal" })
	public void normalTest()
	{

		int numPasswords = 20;
		int iterations = 1010;
		int passLength = 10;

		System.out.println("TEST1: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "-N " + numPasswords
				+ " -M 200 -m 1 -y  -q 1 -k  -s "+passLength+" -i  -j -r";

		process(numPasswords, iterations, passLength, flags);

		stopWatch.stop();
		System.out.println("\nTEST1 Runtime:" + stopWatch.toString() + "\n");

	}

	@Test(groups =
	{ "normal" })
	public void reducedSymbolsTest()
	{

		int numPasswords = 20;
		int iterations = 1100;
		int passLength = 8;

		System.out.println("Reduced Symbols: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "-N " + numPasswords
				+ " -M 500 -m 1 -s "+passLength+" -z -r";

		process(numPasswords, iterations, passLength, flags);

		stopWatch.stop();
		System.out.println("\nReduced Symbols Runtime:" + stopWatch.toString() + "\n");
	}

	
	@Test(groups =
	{ "secure" })
	public void secureTest()
	{

		int numPasswords = 20;
		int iterations = 100;
		int passLength = 10;

		System.out.println("TEST2: Generating password with secure random:");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "-N " + numPasswords + " -s "+passLength+" -C -Y -S SHA1PRNG SUN";

		process(numPasswords, iterations, passLength, flags);

		stopWatch.stop();
		System.out.println("\nTEST2 Runtime:" + stopWatch.toString() + "\n");

	}

	private synchronized List<String>  process(int numPasswords, int iterations, int passLength,
			String flags)
	{
		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		PwGenerator generator = new PwGenerator();

		List<String> passwords = null;
		generator.getDefaultBlacklistFilter().addToBlacklist("qwerty");
		for (int i = 0; i < iterations; i++)
		{
			passwords = generator.process(ar);
			System.out.println("iteration:" + i);
			Assert.assertEquals(passwords.size(), numPasswords);
			printOut(passLength, passwords);
		}
		
		return passwords;
	}
	

	private void printOut(int passLength, List<String> passwords)
	{
		int count = 0;
		System.out.printf("\n");
		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();)
		{
			String element = (String) iter.next();
			if(element.length()!=passLength)
			{
				System.out.printf("---> Wrong number of characters: %d vs. %d for %s\n",element.length(),passLength, element);
				Assert.assertEquals(element.length(), passLength);
			}
			System.out.printf("%3d Password: %s\n", ++count, element);
		}
	}
}