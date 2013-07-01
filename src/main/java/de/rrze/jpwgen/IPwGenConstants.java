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
 * Interface containing default values and constants related to the password
 * generation process.
 * 
 * @author unrz205
 * 
 */
public interface IPwGenConstants
{		
	/**
	 * Default number of attempts to generate a password with specified flags, before giving up
	 */
	public static final int DEFAULT_MAX_ATTEMPTS = 50;
	
	/**
	 * Default number of passwords to be generated
	 */
	public static final int DEFAULT_NUMBER_OF_PASSWORDS = 1;

	/**
	 * Default password length, used if none is specified
	 */
	public static final int DEFAULT_PASSWORD_LENGTH = 8;

	/**
	 * Flag that indicates whether a password element should be treated as a
	 * consonant.
	 */
	public static final int CONSONANT = 0x01;

	/**
	 * Flag that indicates whether a password element should be treated as a
	 * vowel.
	 */
	public static final int VOWEL = 0x02;

	/**
	 * Flag that indicates whether a password element should be treated as a
	 * diphtong.
	 */
	public static final int DIPTHONG = 0x04;

	/**
	 * Flag that indicates that a password element should not be used as a
	 * starting one in a password.
	 */
	public static final int NOT_FIRST = 0x08;

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
	 * Regular expression that matches at least one symbol
	 */
	public static final String REGEX_AMBIGUOUS_SYMBOLS = "[B8G6I1l0OQDS5Z2]+";
	
	/**
	 * Represent no options set
	 * */
	public static final int NULL_FLAGS = 0;
	
	/**
	 * Default password flags(options) to be used in case none are provided
	 * */
	public static final int DEFAULT_FLAGS = NULL_FLAGS | PW_UPPERS | PW_DIGITS;

}
