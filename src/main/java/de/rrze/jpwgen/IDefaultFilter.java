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
 * This software uses code and ideas from:
 * 	http://sourceforge.net/projects/pwgen/
 * 	Copyright (C) 2001,2002 by Theodore Ts'o
 * 
 */
package de.rrze.jpwgen;

/**
 * Interface containing basic regular expressions used for matching and
 * filtering of passwords based on various criteria.
 * 
 * @author unrz205
 * 
 */
public interface IDefaultFilter {

	public static final Long ORIGIN = 0x01l;

	/**
	 * Flag that enables the inclusion of digits in the generated passwords.
	 */
	public static final Long PW_DIGITS = ORIGIN;

	/**
	 * Regular expression that matches passwords containing at least one digit
	 */
	public static final String REGEX_DIGITS = "[\\d]+";

	/**
	 * Flag that enables the inclusion of upper case characters in the generated
	 * passwords.
	 */
	public static final Long PW_CAPITALS = ORIGIN << 1;

	/**
	 * Regular expression that matches passwords containing at least one capital
	 */
	public static final String REGEX_CAPITALS = "[A-Z]+";

	/**
	 * Flag that enables the inclusion of symbols characters in the generated
	 * passwords.
	 */
	public static final Long PW_SYMBOLS = ORIGIN << 2;

	/**
	 * Regular expression that matches at least one symbol
	 */
	public static final String REGEX_SYMBOLS = "[\\W_]+";

	/**
	 * Flag that enables the inclusion of ambiguous characters in the generated
	 * passwords.
	 */
	public static final Long PW_AMBIGUOUS = ORIGIN << 3;

	/**
	 * Special characters that can be included in a password.
	 */
	public static final String PW_SPECIAL_SYMBOLS = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

	/**
	 * Flag that enables the inclusion of the reduced set of symbols characters
	 * in the generated passwords.
	 */
	public static final Long PW_SYMBOLS_REDUCED = ORIGIN << 4;

	/**
	 * A reduced set of special characters that can be included in a password.
	 */
	public static final String PW_SPECIAL_SYMBOLS_REDUCED = "*!#$%&+,-._<=>?@";

	/**
	 * Represents ambiguous characters that can look alike and can confuse
	 * users.
	 */
	public static final String PW_AMBIGUOUS_SYMBOLS = "B8G6I1l0OQDS5Z2";

	/**
	 * Flag that disables passwords starting with a lower letter character
	 */
	public static final Long REGEX_STARTS_NO_LOWERCASE_FLAG = ORIGIN << 5;

	/**
	 * Regular expression that matches passwords starting with a lower letter
	 * character
	 */
	public static final String REGEX_STARTS_NO_LOWERCASE = "^[a-z]";

	/**
	 * Flag that disables passwords ending with a lower letter character
	 */
	public static final Long REGEX_ENDS_NO_LOWERCASE_FLAG = ORIGIN << 6;

	/**
	 * Regular expression that matches passwords ending with a lower letter
	 * character
	 */
	public static final String REGEX_ENDS_NO_LOWERCASE = "[a-z]$";

	/**
	 * Flag that disables passwords starting with a upper letter character
	 */
	public static final Long REGEX_STARTS_NO_UPPERCASE_FLAG = ORIGIN << 7;

	/**
	 * Regular expression that matches passwords starting with a upper letter
	 * character
	 */
	public static final String REGEX_STARTS_NO_UPPERCASE = "^[A-Z]";

	/**
	 * Flag that disables passwords ending with a upper letter character
	 */
	public static final Long REGEX_ENDS_NO_UPPERCASE_FLAG = ORIGIN << 8;

	/**
	 * Regular expression that matches passwords ending with a upper letter
	 * character
	 */
	public static final String REGEX_ENDS_NO_UPPERCASE = "[A-Z]$";

	/**
	 * Flag that disables passwords ending with a digit
	 */
	public static final Long REGEX_ENDS_NO_DIGIT_FLAG = ORIGIN << 9;

	/**
	 * Regular expression that matches passwords ending with a digit
	 */
	public static final String REGEX_ENDS_NO_DIGIT = "\\d$";

	/**
	 * Flag that disables passwords starting with a digit
	 */
	public static final Long REGEX_STARTS_NO_DIGIT_FLAG = ORIGIN << 10;

	/**
	 * Regular expression that matches passwords starting with a digit
	 */
	public static final String REGEX_STARTS_NO_DIGIT = "^\\d";

	/**
	 * Flag that disables passwords starting with a symbol
	 */
	public static final Long REGEX_STARTS_NO_SYMBOL_FLAG = ORIGIN << 11;

	/**
	 * Regular expression that matches passwords starting with a symbol
	 */
	public static final String REGEX_STARTS_NO_SYMBOL = "^\\W";

	/**
	 * Flag that disables passwords ending with a symbol
	 */
	public static final Long REGEX_ENDS_NO_SYMBOL_FLAG = ORIGIN << 12;

	/**
	 * Regular expression that matches passwords ending with a symbol
	 */
	public static final String REGEX_ENDS_NO_SYMBOL = "[\\W_]$";

	/**
	 * Flag that disables passwords containing more than one upper case letter
	 */
	public static final Long REGEX_SINGLE_CAPITAL_FLAG = ORIGIN << 13;

	/**
	 * Regular expression that matches passwords containing exactly one upper
	 * case letter
	 */
	public static final String REGEX_SINGLE_CAPITAL = "^[^A-Z]*[A-Z][^A-Z]*$";

	/**
	 * Flag that disables passwords containing more than one upper case letter
	 */
	public static final Long REGEX_SINGLE_SYMBOL_FLAG = ORIGIN << 14;

	/**
	 * Regular expression that matches passwords containing exactly one symbol
	 */
	public static final String REGEX_SINGLE_SYMBOL = "^\\w*\\W\\w*$";

	/**
	 * Flag that disables passwords containing less than two upper case letter
	 */
	public static final Long REGEX_AT_LEAST_2_SYMBOLS_FLAG = ORIGIN << 15;

	/**
	 * Regular expression that matches passwords containing at least 2 symbols
	 */
	public static final String REGEX_AT_LEAST_2_SYMBOLS = "\\w*[^\\w]\\w*[^\\w]\\w*";

	/**
	 * Flag that disables passwords containing more than one digit
	 */
	public static final Long REGEX_SINGLE_DIGIT_FLAG = ORIGIN << 16;

	/**
	 * Regular expression that matches passwords containing exactly one digit
	 */
	public static final String REGEX_SINGLE_DIGIT = "^[\\D]*\\d[\\D]*$";;

	/**
	 * Flag that disables passwords containing less than two upper case letter
	 */
	public static final Long REGEX_AT_LEAST_2_DIGITS_FLAG = ORIGIN << 17;

	/**
	 * Regular expression that matches passwords containing at least two digits
	 */
	public static final String REGEX_AT_LEAST_2_DIGITS = "\\w*[\\W]*[\\d]\\w*[\\W]*[\\d]\\w*[\\W]*";

	public static final Long REGEX_CONSEQ_SYMBOLS_FLAG = ORIGIN << 18;

	/**
	 * Regular expression that matches passwords containing two consecutive
	 * symbols
	 */
	public static final String REGEX_CONSEC_SYMBOLS = ".*[\\W_][\\W_].*";

	public static final Long REGEX_CONSEQ_DIGITS_FLAG = ORIGIN << 19;
	/**
	 * Regular expression that matches passwords containing two consecutive
	 * digits
	 */
	public static final String REGEX_CONSEC_DIGITS = ".*\\d\\d.*";

	public static final Long REGEX_CONSEQ_CAPITALS_FLAG = ORIGIN << 20;
	/**
	 * Regular expression that matches passwords containing two consecutive
	 * capitals
	 */
	public static final String REGEX_CONSEC_CAPITALS = ".*[A-Z][A-Z].*";

	// max 4294967295 -> 0xffffffff
	// next 262144 -> 0x40000

	// Not needed any more

	// public static final Long REGEX_AT_LEAST_2_CAPITALS_FLAG = 0x2000;

	// public static final String REGEX_AT_LEAST_2_CAPITALS =
	// "\\w*[^\\w]*[A-Z]\\w*[^\\w]*[A-Z]\\w*[^\\w]*";

	/**
	 * Represent no options set
	 * */
	public static final Long NULL_FLAGS = 0l;

	/**
	 * Default password flags(options) to be used in case none are provided
	 * */
	public static final Long DEFAULT_FLAGS = NULL_FLAGS;
}
