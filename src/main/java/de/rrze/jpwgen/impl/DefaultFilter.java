/*
 * RRZEPwGen, developed as a part of the IDMOne project at RRZE.
 * Copyright 2007, RRZE, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors. This
 * product includes software developed by the Apache Software Foundation
 * http://www.apache.org/
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * 
 */
package de.rrze.jpwgen.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.rrze.jpwgen.IDefaultFilter;
import de.rrze.jpwgen.IPwFlag;
import de.rrze.jpwgen.utils.Messages;

/**
 * A password filter that uses to regular expressions to filter commonly
 * forbidden patterns in passwords. The class supports also a blacklist
 * filtering. By default the blacklist is empty.
 * 
 * @author unrz205
 */
public class DefaultFilter extends AbstractPasswordFilter implements
		IDefaultFilter {

	private final static Logger LOGGER = Logger.getLogger(DefaultFilter.class
			.getName());

	// A list that stores the forbidden words.
	private List<String> blacklist = new ArrayList<String>();

	private static final Pattern REGEX_AT_LEAST_1_CAPITAL_P = Pattern
			.compile(REGEX_CAPITALS);

	private static final Pattern REGEX_AT_LEAST_1_DIGIT_P = Pattern
			.compile(REGEX_DIGITS);

	// Filters passwords starting with small letter
	private static final Pattern REGEX_STARTS_NO_SMALL_LETTER_P = Pattern
			.compile(REGEX_STARTS_NO_LOWERCASE);

	// Filters passwords ending with small letter
	private static final Pattern REGEX_ENDS_NO_SMALL_LETTER_P = Pattern
			.compile(REGEX_ENDS_NO_LOWERCASE);

	// Filters passwords starting with a capital letter
	private static final Pattern REGEX_STARTS_NO_UPPER_LETTER_P = Pattern
			.compile(REGEX_STARTS_NO_UPPERCASE);

	// Filters passwords ending with a capital letter
	private static final Pattern REGEX_ENDS_NO_UPPER_LETTER_P = Pattern
			.compile(REGEX_ENDS_NO_UPPERCASE);

	// Filters passwords starting with a digit letter
	private static final Pattern REGEX_STARTS_NO_DIGIT_P = Pattern
			.compile(REGEX_STARTS_NO_DIGIT);

	// Filters passwords ending with a digit letter
	private static final Pattern REGEX_ENDS_NO_DIGIT_P = Pattern
			.compile(REGEX_ENDS_NO_DIGIT);

	// Filters passwords starting with a symbol letter
	private static final Pattern REGEX_STARTS_NO_SYMBOL_P = Pattern
			.compile(REGEX_STARTS_NO_SYMBOL);

	// Filters passwords ending with a symbol letter
	private static final Pattern REGEX_ENDS_NO_SYMBOL_P = Pattern
			.compile(REGEX_ENDS_NO_SYMBOL);

	// Filters passwords that do not contain exactly one capital letter
	private static final Pattern REGEX_ONLY_1_CAPITAL_P = Pattern
			.compile(REGEX_CAPITALS);

	// Filters passwords that do not contain exactly one symbol
	private static final Pattern REGEX_ONLY_1_SYMBOL_P = Pattern
			.compile(REGEX_SINGLE_SYMBOL);

	// Filters passwords that do not contain exactly one digit
	private static final Pattern REGEX_ONLY_1_DIGIT_P = Pattern
			.compile(REGEX_SINGLE_DIGIT);

	// Filters passwords that contain less than two symbols
	private static final Pattern REGEX_AT_LEAST_2_SYMBOLS_P = Pattern
			.compile(REGEX_AT_LEAST_2_SYMBOLS);

	// Filters passwords that contain less than two digits
	private static final Pattern REGEX_AT_LEAST_2_DIGITS_P = Pattern
			.compile(REGEX_AT_LEAST_2_DIGITS);

	private static final Pattern REGEX_CONSEC_CAPITALS_P = Pattern
			.compile(REGEX_CONSEC_CAPITALS);

	private static final Pattern REGEX_CONSEC_DIGITS_P = Pattern
			.compile(REGEX_CONSEC_DIGITS);

	private static final Pattern REGEX_CONSEC_SYMBOLS_P = Pattern
			.compile(REGEX_CONSEC_SYMBOLS);

	// Filters passwords that contain less than two capital letters
	// private static final Pattern REGEX_AT_LEAST_2_CAPITALS_P = Pattern
	// .compile(REGEX_AT_LEAST_2_CAPITALS);

	/**
	 * Default construct.
	 */
	public DefaultFilter() {
	}

	/**
	 * Default construct.
	 */
	public DefaultFilter(List<String> blacklist) {
		this.blacklist = blacklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#filter(int,
	 * java.lang.String)
	 */
	public Map<String, String> filter(Long passwordFlags, String password) {

		Map<String, String> failedChecks = new HashMap<String, String>();

		// Iterate over the list and check whether it contains the word
		for (Iterator<String> iter = blacklist.iterator(); iter.hasNext();) {
			String blackword = (String) iter.next();
			LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
					+ Messages.getString("DefaultFilter.BLACKLIST_ENTRY") + blackword + "\""); //$NON-NLS-1$ //$NON-NLS-2$
			if (password.toLowerCase().contains(blackword.toLowerCase()))
				failedChecks.put("blacklist", blackword);
		}

		// ----------- End character filters --------------------- //

		if (IPwFlag.PW_DIGITS_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_AT_LEAST_1_DIGIT_P.matcher(password);
			if (!matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.NO_DIGIT")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.PW_DIGITS_FLAG.getId(),
						REGEX_AT_LEAST_1_DIGIT_P.pattern());
			}
		}

		// not a regex but .... ;(
		if (IPwFlag.PW_SYMBOLS_FLAG.isMasked(passwordFlags)) {

			Boolean found = false;

			for (int i = 0; i < PW_SPECIAL_SYMBOLS.length(); i++) {
				if (password.indexOf(PW_SPECIAL_SYMBOLS.charAt(i)) != -1) {
					found = true;
					break;
				}
			}

			if (!found) {

				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.NO_SYMBOL")); //$NON-NLS-1$
				failedChecks.put(IPwFlag.PW_SYMBOLS_FLAG.getId(),
						PW_SPECIAL_SYMBOLS);
			}
		}

		// not a regex but .... ;(
		if (IPwFlag.PW_REDUCED_SYMBOLS_FLAG.isMasked(passwordFlags)) {

			Boolean found = false;

			for (int i = 0; i < PW_SPECIAL_SYMBOLS_REDUCED.length(); i++) {
				if (password.indexOf(PW_SPECIAL_SYMBOLS_REDUCED.charAt(i)) != -1) {
					found = true;
					break;
				}
			}

			if (!found) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.NO_REDUCED_SYMBOL")); //$NON-NLS-1$
				failedChecks.put(IPwFlag.PW_REDUCED_SYMBOLS_FLAG.getId(),
						PW_SPECIAL_SYMBOLS_REDUCED);
			}
		}

		if (IPwFlag.PW_AMBIGOUS_FLAG.isMasked(passwordFlags)) {

			Character found = null;

			for (int i = 0; i < PW_AMBIGUOUS_SYMBOLS.length(); i++) {
				if (password.indexOf(PW_AMBIGUOUS_SYMBOLS.charAt(i)) != -1) {
					found = PW_AMBIGUOUS_SYMBOLS.charAt(i);
					break;
				}
			}

			if (found!=null) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.NO_AMBIGUOUS")); //$NON-NLS-1$
				failedChecks.put(IPwFlag.PW_AMBIGOUS_FLAG.getId(),
						PW_AMBIGUOUS_SYMBOLS);
				failedChecks.put(IPwFlag.PW_AMBIGOUS_FLAG.getId()+".explain",
						found.toString());
			}
		}

		if (IPwFlag.PW_CAPITALS_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_AT_LEAST_1_CAPITAL_P.matcher(password);
			if (!matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.NO_CAPITAL")); //$NON-NLS-1$
				failedChecks.put(IPwFlag.PW_CAPITALS_FLAG.getId(),
						REGEX_AT_LEAST_1_CAPITAL_P.pattern());
			}
		}

		if (IPwFlag.ENDS_NO_LOWERCASE_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_ENDS_NO_SMALL_LETTER_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.ENDS_SMALL")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.ENDS_NO_LOWERCASE_FLAG.getId(),
						REGEX_ENDS_NO_SMALL_LETTER_P.pattern());
			}
		}

		if (IPwFlag.ENDS_NO_UPPERCASE_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_ENDS_NO_UPPER_LETTER_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.ENDS_UPPER")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.ENDS_NO_UPPERCASE_FLAG.getId(),
						REGEX_ENDS_NO_UPPER_LETTER_P.pattern());
			}
		}

		if (IPwFlag.ENDS_NO_DIGIT_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_ENDS_NO_DIGIT_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.ENDS_DIGIT")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.ENDS_NO_DIGIT_FLAG.getId(),
						REGEX_ENDS_NO_DIGIT_P.pattern());
			}
		}

		if (IPwFlag.ENDS_NO_SYMBOL_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_ENDS_NO_SYMBOL_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.TRACE_ENDS_SYMBOL")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.ENDS_NO_SYMBOL_FLAG.getId(),
						REGEX_ENDS_NO_SYMBOL_P.pattern());
			}
		}

		// -------- Starts character filters ----------------------- //
		if (IPwFlag.STARTS_NO_SYMBOL_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_STARTS_NO_SYMBOL_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages
								.getString("DefaultFilter.TRACE_STARTS_SYMBOL")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.STARTS_NO_SYMBOL_FLAG.getId(),
						REGEX_STARTS_NO_SYMBOL_P.pattern());
			}
		}

		if (IPwFlag.STARTS_NO_DIGIT_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_STARTS_NO_DIGIT_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages
								.getString("DefaultFilter.TRACE_STARTS_DIGIT")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.STARTS_NO_DIGIT_FLAG.getId(),
						REGEX_STARTS_NO_DIGIT_P.pattern());
			}
		}

		if (IPwFlag.STARTS_NO_UPPERCASE_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_STARTS_NO_UPPER_LETTER_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages
								.getString("DefaultFilter.TRACE_STARTS_UPERCASE")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.STARTS_NO_UPPERCASE_FLAG.getId(),
						REGEX_STARTS_NO_UPPER_LETTER_P.pattern());
			}
		}
		if (IPwFlag.STARTS_NO_LOWERCASE_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_STARTS_NO_SMALL_LETTER_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages
								.getString("DefaultFilter.TRACE_STARTS_SMALL")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.STARTS_NO_LOWERCASE_FLAG.getId(),
						REGEX_STARTS_NO_SMALL_LETTER_P.pattern());
			}
		}

		// ------- Count character filters ------------------- //
		if (IPwFlag.SINGLE_SYMBOL_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_ONLY_1_SYMBOL_P.matcher(password);
			if (!matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages
								.getString("DefaultFilter.TRACE_MORE_SYMBOLS")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.SINGLE_SYMBOL_FLAG.getId(),
						REGEX_ONLY_1_SYMBOL_P.pattern());
			}
		}

		if (IPwFlag.AT_LEAST_TWO_SYMBOLS_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_AT_LEAST_2_SYMBOLS_P.matcher(password);
			if (!matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.TRACE_NO_SYMBOLS")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.AT_LEAST_TWO_SYMBOLS_FLAG.getId(),
						REGEX_AT_LEAST_2_SYMBOLS_P.pattern());
			}
		}

		if (IPwFlag.SINGLE_DIGIT_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_ONLY_1_DIGIT_P.matcher(password);
			if (!matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.TRACE_MORE_DIGITS")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.SINGLE_DIGIT_FLAG.getId(),
						REGEX_ONLY_1_DIGIT_P.pattern());
			}
		}

		if (IPwFlag.AT_LEAST_TWO_DIGITS_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_AT_LEAST_2_DIGITS_P.matcher(password);
			if (!matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages.getString("DefaultFilter.TRACE_NO_DIGITS")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.AT_LEAST_TWO_DIGITS_FLAG.getId(),
						REGEX_AT_LEAST_2_DIGITS_P.pattern());
			}
		}

		if (IPwFlag.SINGLE_CAPITAL_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_ONLY_1_CAPITAL_P.matcher(password);
			if (!matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") //$NON-NLS-1$
						+ password
						+ Messages
								.getString("DefaultFilter.TRACE_MORE_UPPERCASE")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.SINGLE_CAPITAL_FLAG.getId(),
						REGEX_ONLY_1_CAPITAL_P.pattern());
			}
		}

		if (IPwFlag.CONSEC_CAPITALS_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_CONSEC_CAPITALS_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages
								.getString("DefaultFilter.TRACE_CONSEC_CAPITALS")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.CONSEC_CAPITALS_FLAG.getId(),
						REGEX_CONSEC_CAPITALS_P.pattern());
			}
		}

		if (IPwFlag.CONSEC_SYMBOLS_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_CONSEC_SYMBOLS_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages
								.getString("DefaultFilter.TRACE_CONSEC_SYMBOLS")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.CONSEC_SYMBOLS_FLAG.getId(),
						REGEX_CONSEC_SYMBOLS_P.pattern());
			}
		}

		if (IPwFlag.CONSEC_DIGITS_FLAG.isMasked(passwordFlags)) {
			Matcher matcher = REGEX_CONSEC_DIGITS_P.matcher(password);
			if (matcher.find()) {
				LOGGER.fine(Messages.getString("DefaultFilter.TRACE_PASSWD") + password //$NON-NLS-1$
						+ Messages
								.getString("DefaultFilter.TRACE_CONSEC_DIGITS")); //$NON-NLS-1$

				failedChecks.put(IPwFlag.CONSEC_DIGITS_FLAG.getId(),
						REGEX_CONSEC_DIGITS_P.pattern());
			}
		}

		return failedChecks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#getDescription()
	 */
	public String getDescription() {
		return Messages.getString("DefaultFilter.DESC"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#filter(int,
	 * java.util.List)
	 */
	public List<String> filter(int passwordFlags, List<String> password) {
		List<String> suiatble = new ArrayList<String>();
		for (Iterator<String> iter = password.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			if (filter(passwordFlags, password) != null)
				suiatble.add(element);
		}
		return suiatble;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#getID()
	 */
	public String getId() {
		return this.getClass().getSimpleName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#getBlacklist()
	 */
	public List<String> getBlacklist() {
		return blacklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#setBlacklist(java.util.List)
	 */
	public void setBlacklist(List<String> blacklist) {
		this.blacklist = blacklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#addToBlacklist(java.lang.String
	 * )
	 */
	public boolean addToBlacklist(String blackWord) {
		return blacklist.add(blackWord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#removeFromBlacklist(java.lang
	 * .String)
	 */
	public boolean removeFromBlacklist(String blackWord) {
		return blacklist.remove(blackWord);
	}

}
