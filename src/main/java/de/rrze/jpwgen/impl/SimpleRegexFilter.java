package de.rrze.jpwgen.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class SimpleRegexFilter extends AbstractPasswordFilter {

	private Pattern regexPattern;

	private boolean useFind = false;

	private String id;

	boolean negate = false;

	public SimpleRegexFilter(String id, String regex, boolean useFind,
			boolean negate) throws PatternSyntaxException {
		super();
		this.id = id;
		regexPattern = Pattern.compile(regex);
		this.useFind = useFind;
		this.negate = negate;

	}

	@Override
	public String getID() {
		return this.id;
	}

	@Override
	public String filter(int passwordFlags, String password) {
		String pass = null;

		Matcher matcher = regexPattern.matcher(password);

		boolean matches = false;

		if (useFind) {
			if (matcher.find()) {

				matches = true;
			}
		} else {
			if (matcher.matches()) {
				matches = true;
			}

		}

		if(negate)
			matches = !matches;
		
		if(matches)
			pass = password;

		return pass;
	}

}
