package de.rrze.idmone.utils.jpwgen.test;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.surefire.shade.org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.idmone.utils.jpwgen.BlankRemover;
import de.rrze.idmone.utils.jpwgen.PwGenerator;

public class SimpleTest
{

	@BeforeClass
	public void setUp()
	{
		System.out
				.println("=============================================================");
	}

	@Test(groups =
	{ "normal" })
	public void aNormalTest()
	{

		System.out.println("TEST1");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		// String flags = "-N 50 -M 100 -y -s 16 -m -o -q -r";
		String flags = "-N 5 -M 20 -m 1 -y  -q 1 -k  -s 8   -i  -j -r";

		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		PwGenerator generator = new PwGenerator();

		List<String> passwords = null;
		generator.getDefaultBlacklistFilter().addToBlacklist("badpassword");
		for (int i = 0; i < 1000; i++)
		{
			passwords = generator.process(ar);
		}

		stopWatch.stop();
		System.out.println("\nTEST1 Runtime:" + stopWatch.toString() + "\n");

		int count = 0;
		System.out.printf("\n");
		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();)
		{

			String element = (String) iter.next();
			//System.out.printf("%3d Password: %s\n", ++count, element);
		}

	}

	@Test(groups =
	{ "secure" })
	public void aSecureTest()
	{
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		System.out.println("TEST2");

		String flags = "-N 60 -s 10 -C -Y -S SHA1PRNG SUN";

		
		flags = BlankRemover.itrim(flags);
		String[] ar = flags.split(" ");

		PwGenerator generator = new PwGenerator();

		generator.getDefaultBlacklistFilter().addToBlacklist("badpassword");
		List<String> passwords = generator.process(ar);

		stopWatch.stop();
		System.out.println("\nRuntime:" + stopWatch.toString() + "\n");

		int count = 0;
		System.out.printf("\n");
		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();)
		{

			String element = (String) iter.next();
			//System.out.printf("%3d Password: %s\n", ++count, element);
		}

	}

}