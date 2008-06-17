package de.rrze.idmone.utils.jpwgen.test;

import org.apache.maven.surefire.shade.org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.idmone.utils.jpwgen.BlankRemover;

public class BlankRemoverTest
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
	public void test()
	{

		System.out.println("Start: " + this.getClass().getSimpleName());

		String oldStr = " >     <1-2-1-2-1-2-1-2-1-2-1-----2-1-2-1-2-1-2-1-2-1-2-1-2>   < ";
		String newStr = oldStr.replaceAll("-", " ");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		System.out.println("Modifing: " + newStr);
		System.out.println(BlankRemover.ltrim(newStr));
		System.out.println(BlankRemover.rtrim(newStr));
		System.out.println(BlankRemover.itrim(newStr));
		System.out.println(BlankRemover.lrtrim(newStr));

		stopWatch.stop();
		System.out.println("\n" + this.getClass().getSimpleName() + " Runtime:"
				+ stopWatch.toString() + "\n");
	}

}
