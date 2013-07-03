package de.rrze.jpwgen.test;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.Test;

import de.rrze.jpwgen.IPwGenCommandLineOptions;
import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;

public class HelperTest {

	@Test(groups = { "helper" })
	public void maxFlagTest() throws Exception {

		System.out.println("INSTANCE TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Long flag = 1l;
		for (int i = 0; i < 100; i++) {

			System.out.println("Flags: " + (i + 1) + " " + flag + " ->\t\t 0x"
					+ String.format("%X", flag));
			flag = flag << 1;
			if (flag < 0)
				break;
		}

	}

	@Test(groups = { "helper" })
	public void freeFlagsTest() throws Exception {

		String consoleFlagsLower = "shrtl";
		String consoleFlagsUpper = "NCLSM";
		
		List<String> shorts = PwGeneratorFlagBuilder.getCliShorts();

		for (int i = 0; i < IPwGenCommandLineOptions.ALPHA_LOW.length(); i++) {
			char ch = IPwGenCommandLineOptions.ALPHA_LOW.charAt(i);
			if (!shorts.contains(new String(new char[] { ch })) && consoleFlagsLower.indexOf(ch)==-1)
				System.out.println("Free Lower Case flags: " + ch);
		}

		for (int i = 0; i < IPwGenCommandLineOptions.ALPHA_UP.length(); i++) {
			char ch = IPwGenCommandLineOptions.ALPHA_UP.charAt(i);
			if (!shorts.contains(new String(new char[] { ch }))&& consoleFlagsUpper.indexOf(ch)==-1)
				System.out.println("Free uper Case flags: " + ch);
		}
	}

}