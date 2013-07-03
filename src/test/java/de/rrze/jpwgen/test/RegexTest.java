package de.rrze.jpwgen.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.Test;

public class RegexTest {

	@Test(groups = { "instance" }, invocationCount = 30)
	public void instanceTest() throws Exception {

		System.out.println("INSTANCE TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Long a = 1l;
		for (int i = 0; i < 100; i++) {

			System.out.println("Flags: " + a + " -> 0x"
					+ String.format("%X", a));
			a = a << 1;
		}

		String regex = ".*\\W\\W.*";
		String password = "as43da!B";

		Pattern regexPattern = Pattern.compile(regex);
		Matcher matcher = regexPattern.matcher(password);

		System.out.println(matcher.matches());
	}

}