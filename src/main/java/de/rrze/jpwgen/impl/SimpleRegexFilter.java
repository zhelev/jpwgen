package de.rrze.jpwgen.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class SimpleRegexFilter extends AbstractPasswordFilter {

	private Pattern regexPattern;

	private String regex;

	private boolean useFind = false;

	private String id;

	boolean negate = false;

	public SimpleRegexFilter(String id, String regex, boolean useFind,
			boolean negate) throws PatternSyntaxException {
		super();
		this.id = id;
		this.regex = regex;
		regexPattern = Pattern.compile(regex);
		this.useFind = useFind;
		this.negate = negate;

	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public List<String> filter(Long passwordFlags, String password) {

		List<String> failReasons = new ArrayList<String>();

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

		if (negate)
			matches = !matches;

		if (!matches)
			failReasons.add(toString());

		return failReasons;
	}

	@Override
	public String toString() {
		return "SimpleRegexFilter [id=" + id + ", regex=" + regex
				+ ", useFind=" + useFind + ", negate=" + negate + "]";
	}

}
