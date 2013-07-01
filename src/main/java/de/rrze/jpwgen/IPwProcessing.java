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
public interface IPwProcessing
{
	/** =========================================================================== */
	
	/**
	 * Flag that enables the inclusion of digits in the generated passwords.
	 */
	public static final int PW_DIGITS = 0x01;

	/**
	 * Regular expression that matches passwords containing at least one digit
	 */
	public static final String REGEX_AT_LEAST_1_DIGIT = "[\\d]+";
	
	/**
	 * Flag that enables the inclusion of upper case characters in the generated
	 * passwords.
	 */
	public static final int PW_UPPERS = 0x02;

	/**
	 * Regular expression that matches passwords containing at least one capital
	 */
	public static final String REGEX_AT_LEAST_1_CAPITAL = "[A-Z]+";
	
	/**
	 * Flag that enables the inclusion of symbols characters in the generated
	 * passwords.
	 */
	public static final int PW_SYMBOLS = 0x04;

	/**
	 * Regular expression that matches at least one symbol
	 */
	public static final String REGEX_AT_LEAST_1_SYMBOLS = "[\\W_]+";
	
	/**
	 * Flag that enables the inclusion of ambiguous characters in the generated
	 * passwords.
	 */
	public static final int PW_AMBIGUOUS = 0x08;

	/**
	 * Special characters that can be included in a password.
	 */
	public static final String PW_SPECIAL_SYMBOLS = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
	
	/**
	 * Flag that enables the inclusion of the reduced set of symbols characters
	 * in the generated passwords.
	 */
	public static final int PW_SYMBOLS_REDUCED = 0x10;

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
	public static final int REGEX_STARTS_NO_SMALL_LETTER_FLAG = 0x20;

	/**
	 * Regular expression that matches passwords starting with a lower letter
	 * character
	 */
	public static final String REGEX_STARTS_NO_SMALL_LETTER = "^[a-z]";

	/**
	 * Flag that disables passwords ending with a lower letter character
	 */
	public static final int REGEX_ENDS_NO_SMALL_LETTER_FLAG = 0x40;

	/**
	 * Regular expression that matches passwords ending with a lower letter
	 * character
	 */
	public static final String REGEX_ENDS_NO_SMALL_LETTER = "[a-z]$";

	/**
	 * Flag that disables passwords starting with a upper letter character
	 */
	public static final int REGEX_STARTS_NO_UPPER_LETTER_FLAG = 0x80;

	/**
	 * Regular expression that matches passwords starting with a upper letter
	 * character
	 */
	public static final String REGEX_STARTS_NO_UPPER_LETTER = "^[A-Z]";

	/**
	 * Flag that disables passwords ending with a upper letter character
	 */
	public static final int REGEX_ENDS_NO_UPPER_LETTER_FLAG = 0x100;

	/**
	 * Regular expression that matches passwords ending with a upper letter
	 * character
	 */
	public static final String REGEX_ENDS_NO_UPPER_LETTER = "[A-Z]$";

	/**
	 * Flag that disables passwords ending with a digit
	 */
	public static final int REGEX_ENDS_NO_DIGIT_FLAG = 0x200;

	/**
	 * Regular expression that matches passwords ending with a digit
	 */
	public static final String REGEX_ENDS_NO_DIGIT = "\\d$";

	/**
	 * Flag that disables passwords starting with a digit
	 */
	public static final int REGEX_STARTS_NO_DIGIT_FLAG = 0x400;

	/**
	 * Regular expression that matches passwords starting with a digit
	 */
	public static final String REGEX_STARTS_NO_DIGIT = "^\\d";

	/**
	 * Flag that disables passwords starting with a symbol
	 */
	public static final int REGEX_STARTS_NO_SYMBOL_FLAG = 0x800;

	/**
	 * Regular expression that matches passwords starting with a symbol
	 */
	public static final String REGEX_STARTS_NO_SYMBOL = "^\\W";

	/**
	 * Flag that disables passwords ending with a symbol
	 */
	public static final int REGEX_ENDS_NO_SYMBOL_FLAG = 0x1000;

	/**
	 * Regular expression that matches passwords ending with a symbol
	 */
	public static final String REGEX_ENDS_NO_SYMBOL = "[\\W_]$";

	/**
	 * Flag that disables passwords containing more than one upper case letter
	 */
	public static final int REGEX_ONLY_1_CAPITAL_FLAG = 0x2000;

	/**
	 * Regular expression that matches passwords containing exactly one upper
	 * case letter
	 */
	public static final String REGEX_ONLY_1_CAPITAL = "^[^A-Z]*[A-Z][^A-Z]*$";

	/**
	 * Flag that disables passwords containing more than one upper case letter
	 */
	public static final int REGEX_ONLY_1_SYMBOL_FLAG = 0x4000;

	/**
	 * Regular expression that matches passwords containing exactly one symbol
	 */
	public static final String REGEX_ONLY_1_SYMBOL = "^\\w*\\W\\w*$";

	/**
	 * Flag that disables passwords containing less than two upper case letter
	 */
	public static final int REGEX_AT_LEAST_2_SYMBOLS_FLAG = 0x8000;

	/**
	 * Regular expression that matches passwords containing at least 2 symbols
	 */
	public static final String REGEX_AT_LEAST_2_SYMBOLS = "\\w*[^\\w]\\w*[^\\w]\\w*";

	/**
	 * Flag that disables passwords containing more than one digit
	 */
	public static final int REGEX_ONLY_1_DIGIT_FLAG = 0x10000;

	/**
	 * Regular expression that matches passwords containing exactly one digit
	 */
	public static final String REGEX_ONLY_1_DIGIT = "^[\\D]*\\d[\\D]*$";;

	/**
	 * Flag that disables passwords containing less than two upper case letter
	 */
	public static final int REGEX_AT_LEAST_2_DIGITS_FLAG = 0x20000;

	/**
	 * Regular expression that matches passwords containing at least two digits
	 */
	public static final String REGEX_AT_LEAST_2_DIGITS = "\\w*[\\W]*[\\d]\\w*[\\W]*[\\d]\\w*[\\W]*";

	// max 4294967295 -> 0xffffffff
	// next 262144 -> 0x40000
	
	// Not needed any more

	// public static final int REGEX_AT_LEAST_2_CAPITALS_FLAG = 0x2000;

	// public static final String REGEX_AT_LEAST_2_CAPITALS =
	// "\\w*[^\\w]*[A-Z]\\w*[^\\w]*[A-Z]\\w*[^\\w]*";
	
	/**
	 * Represent no options set
	 * */
	public static final int NULL_FLAGS = 0;
	
	/**
	 * Default password flags(options) to be used in case none are provided
	 * */
	public static final int DEFAULT_FLAGS = NULL_FLAGS | PW_UPPERS | PW_DIGITS;

}
