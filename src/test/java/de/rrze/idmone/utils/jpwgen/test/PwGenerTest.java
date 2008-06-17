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
		int iterations = 1;

		System.out.println("TEST1: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "-N " + numPasswords
				+ " -M 200 -m 1 -y  -q 1 -k  -s 10   -i  -j -r";

		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		PwGenerator generator = new PwGenerator();

		List<String> passwords = null;
		generator.getDefaultBlacklistFilter().addToBlacklist("badpassword");
		for (int i = 0; i < iterations; i++)
		{
			passwords = generator.process(ar);
			Assert.assertEquals(passwords.size(), numPasswords);
		}

		stopWatch.stop();
		System.out.println("\nTEST1 Runtime:" + stopWatch.toString() + "\n");

		int count = 0;
		System.out.printf("\n");
		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();)
		{
			String element = (String) iter.next();
			System.out.printf("%3d Password: %s\n", ++count, element);
		}

	}

	@Test(groups =
	{ "secure" })
	public void secureTest()
	{

		int numPasswords = 20;
		int iterations = 1;

		System.out.println("TEST2: Generating password with secure random:");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String flags = "-N " + numPasswords + " -s 10 -C -Y -S SHA1PRNG SUN";

		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		PwGenerator generator = new PwGenerator();

		List<String> passwords = null;
		generator.getDefaultBlacklistFilter().addToBlacklist("qwerty");
		for (int i = 0; i < iterations; i++)
		{
			passwords = generator.process(ar);
			Assert.assertEquals(passwords.size(), numPasswords);
		}

		stopWatch.stop();
		System.out.println("\nTEST2 Runtime:" + stopWatch.toString() + "\n");

		int count = 0;
		System.out.printf("\n");
		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();)
		{

			String element = (String) iter.next();
			System.out.printf("%3d Password: %s\n", ++count, element);
		}

	}
}