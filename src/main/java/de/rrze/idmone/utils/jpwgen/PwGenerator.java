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
package de.rrze.idmone.utils.jpwgen;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The program is started from this class. It performs the actual password
 * generation. The idea of this class is to generate passwords that are
 * relatively easily to remember but at the same time complex enough. It
 * utilizes a predefined set of vowels and consonants that are brought together
 * by an iterative algorithm. The concept of dipthong is also used which can be
 * used as an additional flag for a consonant or a vowel. Typical examples of
 * dipthongs are <em>ei</em> for vowels and <em>th</em> for consonants.
 * 
 * Various algorithms for random generation can be used for feeding the process
 * of password generation. This ensures unique password creation.
 * 
 * By default two filters are registered in the PwGenerator. The first one is an
 * empty black list filter. It can be used to filter out forbidden predefined
 * passwords. The second one is based on regular expressions and uses frequently
 * utilized rules for filtering passwords such as the number of contained
 * symbols, digits and so on. See the help for a detailed description.
 * 
 * 
 * <table border="1px">
 * <caption>CLI options comparison to original pwgen</caption> <tbody>
 * <tr>
 * <th colspan="3">pwgen</th>
 * <th colspan="3">jpwgen</th>
 * </tr>
 * <tr>
 * <th>short</th>
 * <th>long</th>
 * <th>description</th>
 * <th>short</th>
 * <th>long</th>
 * <th>description</th>
 * </tr>
 * 
 * <tr>
 * <td>-0</td>
 * <td>--no-numerals</td>
 * <td>Don't include numbers in the generated passwords.</td>
 * <td>-O</td>
 * <td>--no-numerals</td>
 * <td>Don't include numbers in the generated passwords.</td>
 * </tr>
 * 
 * <tr>
 * <td>-n</td>
 * <td>--numerals</td>
 * <td>Include at least one number in the password</td>
 * <td>-n</td>
 * <td>--numerals</td>
 * <td>Include at least one number in the password</td>
 * </tr>
 * 
 * <tr>
 * <td>-y</td>
 * <td>--symbols</td>
 * <td>Include at least one special symbol in the password</td>
 * <td>-y</td>
 * <td>--symbols</td>
 * <td>Include at least one special symbol in the password</td>
 * </tr>
 * 
 * <tr>
 * <td>-z</td>
 * <td>--reduced-symbols</td>
 * <td>Include at least one special symbol from the reduced set in the password</td>
 * <td></td>
 * <td></td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td>-1</td>
 * <td>-</td>
 * <td>Print the generated passwords one per line.</td>
 * <td></td>
 * <td></td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td>-a</td>
 * <td>--alt-phonic</td>
 * <td>This option doesn't do anything special; it is present only for backwards
 * compatibility.</td>
 * <td></td>
 * <td></td>
 * <td></td>
 * </tr>
 * 
 * 
 * <tr>
 * <td>-B</td>
 * <td>--ambiguous</td>
 * <td>Don't include ambiguous characters in the password</td>
 * <td>-B</td>
 * <td>--ambiguous</td>
 * <td>Don't include ambiguous characters in the password</td>
 * </tr>
 * 
 * 
 * <tr>
 * <td>-c</td>
 * <td>--capitalize</td>
 * <td>Include at least one capital letter in the password. This is the default
 * if the standard output is a tty device.</td>
 * <td>-c</td>
 * <td>--capitalize</td>
 * <td>Include at least one capital letter in the password. This is the default
 * if the standard output is a tty device.</td>
 * </tr>
 * 
 * <tr>
 * <td>-s</td>
 * <td>--secure</td>
 * <td>Generate completely random passwords</td>
 * <td></td>
 * <td></td>
 * <td></td>
 * </tr>
 * 
 * 
 * <tr>
 * <td>-h</td>
 * <td>--help</td>
 * <td>Print a help message</td>
 * <td>-h</td>
 * <td>--help</td>
 * <td>Print a help message</td>
 * </tr>
 * 
 * <tr>
 * <td>-C</td>
 * <td></td>
 * <td>Print the generated passwords in columns</td>
 * <td>-C</td>
 * <td>--columns</td>
 * <td>Print the generated passwords in columns</td>
 * </tr>
 * 
 * <tr>
 * <td>-v</td>
 * <td>--no-vowels</td>
 * <td>Use sha1 hash of given file as a (not so) random generator</td>
 * <td></td>
 * <td></td>
 * <td></td>
 * </tr>
 * 
 * 
 * <tr>
 * <td>-H</td>
 * <td>--sha1=path/to/file[#seed]</td>
 * <td>Do not use any vowels so as to avoid accidental nasty words</td>
 * <td></td>
 * <td></td>
 * <td></td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-b</td>
 * <td>--start-no-small-letter</td>
 * <td>Generates password starting with a character different than a small
 * letter</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-d</td>
 * <td>--end-no-small-letter</td>
 * <td>Generates password ending with a character different than a small letter</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-D</td>
 * <td>--allow-ambiguous</td>
 * <td>Allow ambiguous characters in the password</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-e</td>
 * <td>--start-no-uppercase-letter</td>
 * <td>Generates password starting with a character different than a uppercase
 * letter</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-f</td>
 * <td>--end-no-uppercase-letter</td>
 * <td>Generates password ending with a character different than a uppercase
 * letter</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-g</td>
 * <td>--end-no-digit</td>
 * <td>Generates password ending with a character different than a digit</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-i</td>
 * <td>--start-no-digit-letter</td>
 * <td>Generates password starting with a character different than a digit</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-j</td>
 * <td>--start-no-symbol-letter</td>
 * <td>Generates password starting with a character different than a symbol</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-k</td>
 * <td>--end-no-symbol</td>
 * <td>Generates password ending with a character different than a symbol</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-l</td>
 * <td>--list-sr-providers</td>
 * <td>Lists the available security service providers for SecureRandom and exits
 * </td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-L</td>
 * <td>--list-providers</td>
 * <td>Lists all available security providers and algorithms</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-m</td>
 * <td>--one-upercase</td>
 * <td>Generates password containing exactly one uppercase letter</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-M</td>
 * <td>--max-attempts &lt;arg&gt;</td>
 * <td>Sets the maximum number of attempts for generating a password with the
 * provided policies</td>
 * </tr>
 * 
 * <tr>
 * <td>second parameter</td>
 * <td>second parameter</td>
 * <td></td>
 * <td>-N</td>
 * <td>--number &lt;arg&gt;</td>
 * <td>The number of passwords to be generated</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-o</td>
 * <td>--one-symbol</td>
 * <td>Generates password containing exactly one symbol</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-p</td>
 * <td>--two-symbol</td>
 * <td>Generates password containing at least two symbols</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-q</td>
 * <td>--one-digit</td>
 * <td>Generates password containing exactly one digit</td>
 * </tr>
 * 
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-r</td>
 * <td>--random</td>
 * <td>Use simple random for password generation</td>
 * </tr>
 * 
 * <tr>
 * <td>first parameter</td>
 * <td>first parameter</td>
 * <td>The length of the generated password</td>
 * <td>-s</td>
 * <td>--size &lt;arg&gt;</td>
 * <td>The length of the generated password</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-S</td>
 * <td>--set-algorithm &lt;arg&gt;</td>
 * <td>set-algorithm</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-t</td>
 * <td>--term-width &lt;arg&gt;</td>
 * <td>Sets the character width of the jpwgen terminal</td>
 * </tr>
 * 
 * <tr>
 * <td></td>
 * <td></td>
 * <td></td>
 * <td>-u</td>
 * <td>--two-digits</td>
 * <td>Generates password containing at least two digits</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * <h3>
 * Example:</h3>
 * <ul>
 * <br>
 * String flags = "-N 50 -M 100 -y -s 16 -m -o -q ";<br>
 * <br>
 * flags = BlankRemover.itrim(flags); <br>
 * String[] ar = flags.split(" "); <br>
 * 
 * PwGenerator generator = new PwGenerator();<br>
 * 
 * generator.getDefaultBlacklistFilter().addToBlacklist("badpassword");
 * List<String> passwords = generator.process(ar); <br>
 * int count = 0;<br>
 * System.out.printf("\n");<br>
 * for (Iterator iter = passwords.iterator(); iter.hasNext();) <br>
 * {<br>
 * <br>
 * String element = (String) iter.next();<br>
 * System.out.printf("%3d Password: * %s\n", ++count, element); <br>
 * <br>
 * 
 * 
 * @author unrz205
 */
public class PwGenerator implements IPwGenConstants, IPwGenCommandLineOptions,
		IPwGenRegEx
{
	// The class logger
	private static final Log logger = LogFactory.getLog(PwGenerator.class);

	// A static list of predefined vowels and consonants dipthongs. Suitable for
	// English speaking people.
	// This can be exchanged or extended with a different one if needed.
	private static final PwElement[] PW_ELEMENTS =
	{ new PwElement("a", VOWEL), new PwElement("ae", VOWEL | DIPTHONG), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("ah", VOWEL | DIPTHONG), //$NON-NLS-1$
			new PwElement("ai", VOWEL | DIPTHONG), //$NON-NLS-1$
			new PwElement("b", CONSONANT), new PwElement("c", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("ch", CONSONANT | DIPTHONG), //$NON-NLS-1$
			new PwElement("d", CONSONANT), new PwElement("e", VOWEL), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("ee", VOWEL | DIPTHONG), //$NON-NLS-1$
			new PwElement("ei", VOWEL | DIPTHONG), //$NON-NLS-1$
			new PwElement("f", CONSONANT), new PwElement("g", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("gh", CONSONANT | DIPTHONG | NOT_FIRST), //$NON-NLS-1$
			new PwElement("h", CONSONANT), new PwElement("i", VOWEL), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("ie", VOWEL | DIPTHONG), //$NON-NLS-1$
			new PwElement("j", CONSONANT), new PwElement("k", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("l", CONSONANT), new PwElement("m", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("n", CONSONANT), //$NON-NLS-1$
			new PwElement("ng", CONSONANT | DIPTHONG | NOT_FIRST), //$NON-NLS-1$
			new PwElement("o", VOWEL), new PwElement("oh", VOWEL | DIPTHONG), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("oo", VOWEL | DIPTHONG), //$NON-NLS-1$
			new PwElement("p", CONSONANT), //$NON-NLS-1$
			new PwElement("ph", CONSONANT | DIPTHONG), //$NON-NLS-1$
			new PwElement("qu", CONSONANT | DIPTHONG), //$NON-NLS-1$
			new PwElement("r", CONSONANT), new PwElement("s", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("sh", CONSONANT | DIPTHONG),
			new PwElement("t", CONSONANT), //$NON-NLS-1$
			new PwElement("th", CONSONANT | DIPTHONG), //$NON-NLS-1$
			new PwElement("u", VOWEL), new PwElement("v", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("w", CONSONANT), new PwElement("x", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
			new PwElement("y", CONSONANT), new PwElement("z", CONSONANT) }; //$NON-NLS-1$ //$NON-NLS-2$

	// An instance of the Random number that would be used during the generation
	// process
	// private Random random;

	// The singleton instance of the PwGenertor
	// private static PwGenerator instance;

	// The command options with which the program was started
	private Options options;

	// Flags used during the process of password generation
	private int passwordFlags = 0;

	// The length of the password to be generated
	private int passwordLength = DEFAULT_PASSWORD_LENGTH;

	private int numberOfPasswords = DEFAULT_NUMBER_OF_PASSWORDS;

	private int termWidth = DEFAULT_TERM_WIDTH;

	private boolean doColumns = DEFAULT_DO_COLUMNS;

	private Map<String, IPasswordFilter> filters;

	private static int MAX_ATTEMPTS = 10000;

	private IPasswordFilter defaultRegexFilter;

	private IPasswordFilter defaultBlacklistFilter;

	private IRandomFactory randomFactory;

	/**
	 * Adds a password filter to the registry
	 * 
	 * @param filter
	 *            the filter instance to be registered
	 * @return the registered instance
	 */
	public IPasswordFilter addFilter(IPasswordFilter filter)
	{
		return filters.put(filter.getID(), filter);
	}

	/**
	 * Removes a filter from the registry by instance search
	 * 
	 * @param filter
	 *            the instance of the filter
	 * @return the removed instance
	 */
	public IPasswordFilter removeFilter(IPasswordFilter filter)
	{
		return filters.remove(filter.getID());
	}

	/**
	 * Removes a filter from the registry by identifier search
	 * 
	 * @param id
	 *            the identifier of the filter
	 * @return the removed instance
	 */
	public IPasswordFilter removeFilter(String id)
	{
		return filters.remove(id);
	}

	/**
	 * Logs a message as info or error depending on a flag
	 * 
	 * @param message
	 *            the message to be logged
	 * @param error
	 *            indicates whether the message is an error or not
	 */
	private void log(String message, boolean error)
	{
		if (error)
			logger.error(message);
		logger.debug(message);
	}

	/**
	 * Constructor of the PwGenerator
	 */
	public PwGenerator()
	{
		passwordFlags |= PW_UPPERS;
		logger.debug(Messages.getString("PwGenerator.TRACE_UPPERCASE_ON")); //$NON-NLS-1$
		passwordFlags |= PW_DIGITS;
		logger.debug(Messages.getString("PwGenerator.TRACE_DIGITS_ON")); //$NON-NLS-1$
		// passwordFlags |= PW_SYMBOLS;
		// passwordFlags |= PW_AMBIGUOUS;

		options = createOptions();

		filters = new HashMap<String, IPasswordFilter>();

		defaultRegexFilter = new DefaultRegExFilter();
		filters.put(defaultRegexFilter.getID(), defaultRegexFilter);

		defaultBlacklistFilter = new DefaultBlacklistFilter();
		filters.put(defaultBlacklistFilter.getID(), defaultBlacklistFilter);

		randomFactory = RandomFactory.getInstance();
	}

	/**
	 * This method logs some general info about the given settings and tries to
	 * generate passwords with the given flags and given length. The method
	 * return <em>null</em> if it does not manage to create a suitable password
	 * within the <em>MAX_ATTEMPTS</em>.
	 * 
	 * @param passwordLength
	 *            the length of the password to be generated
	 * @param passwordFlags
	 *            the settings for the particular password
	 * @return a suitable password or <em>null</em> if such could not be
	 *         generated
	 */
	public String generatePassword(int passwordLength, int passwordFlags, Random random)
	{
		if (passwordLength <= 2)
		{
			passwordFlags &= ~PW_UPPERS;
			logger.warn(Messages.getString("PwGenerator.WARN_PL_UPERCASE_OFF")); //$NON-NLS-1$
		}
		if (passwordLength <= 1)
		{
			passwordFlags &= ~PW_DIGITS;
			logger.warn(Messages.getString("PwGenerator.WARN_PL_DIGITS_OFF")); //$NON-NLS-1$
		}
		if (passwordLength <= 2)
		{
			passwordFlags &= ~PW_SYMBOLS;
			logger.warn(Messages.getString("PwGenerator.WARN_PL_SYMBOLS_OFF")); //$NON-NLS-1$
		}
		if (passwordLength <= 2)
		{
			passwordFlags &= ~PW_SYMBOLS_REDUCED;
			logger.warn(Messages
					.getString("PwGenerator.WARN_PL_SYMBOLS_REDUCED_OFF")); //$NON-NLS-1$
		}

		String password = null;
		for (int i = 0; i < MAX_ATTEMPTS; i++)
		{
			password = phonemes(passwordLength, passwordFlags, random);
			Set<String> filterIDs = filters.keySet();

			for (Iterator<String> iter = filterIDs.iterator(); iter.hasNext();)
			{
				String element = (String) iter.next();
				IPasswordFilter filter = filters.get(element);
				password = filter.filter(passwordFlags, password);
				if (password == null)
					break;
			}

			if (password != null)
				break;

			logger
					.debug(Messages.getString("PwGenerator.TRACE_ATTEMPT") + i + Messages.getString("PwGenerator.TRACE_ATTEMPT_GENERATE") //$NON-NLS-1$ //$NON-NLS-2$
							+ passwordFlags);
		}

		return password;
	}

	/**
	 * Entry point of the program
	 * 
	 * @param args
	 *            the program arguments
	 */
	public static void main(String[] args)
	{
		PwGenerator generator = new PwGenerator();
		final List<String> passwords = generator.process(args);
		if (generator.doColumns)
		{
			generator.printColumns(passwords, generator.termWidth);
		} else
		{
			generator.print(passwords);
		}
	}

	/**
	 * This method parses the command line options, initializes the needed
	 * objects and generates the required number of passwords by calling
	 * <em>generatePassword()</em>. When not used as a stand-alone program this
	 * method is to be preferred instead of the main(String[]).
	 * 
	 * @param args
	 *            the arguments used to initialize the generation process
	 * @return a list of passwords or <em>null</em> if no suitable passwords
	 *         could be generated.
	 */
	public List<String> process(String[] args)
	{

		log(Messages.getString("PwGenerator.PASSWORD_GENERATOR"), //$NON-NLS-1$
				false);

		ArrayList<String> passwords = new ArrayList<String>();
		BasicParser parser = new BasicParser();
		try
		{
			CommandLine commandLine = parser.parse(options, args);

			parser.parse(options, args);
			if (commandLine.hasOption(CL_HELP))
			{
				printUsage();
				log(Messages.getString("PwGenerator.SEPARATOR"), //$NON-NLS-1$
						false);
				System.exit(0);
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_SR_PROVIDERS))
			{
				Set<String> serviceProviders = randomFactory
						.getServiceProviderFor(IRandomFactory.TYPE_SECURE_RANDOM);
				log(Messages.getString("PwGenerator.SERVICES_PROVIDERS_FOR") //$NON-NLS-1$
						+ IRandomFactory.TYPE_SECURE_RANDOM
						+ Messages.getString("PwGenerator.NEW_LINE"), false); //$NON-NLS-1$
				for (Iterator<String> iter = serviceProviders.iterator(); iter
						.hasNext();)
				{
					String element = (String) iter.next();
					log(
							Messages.getString("PwGenerator.SERVICE_PROVIDERS") + element + Messages.getString("PwGenerator.NEW_LINE"), false); //$NON-NLS-1$ //$NON-NLS-2$
					log(Messages.getString("PwGenerator.SEPARATOR"), //$NON-NLS-1$
							false);
					System.exit(0);
				}
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_PROVIDERS))
			{
				log(Messages.getString("PwGenerator.ALL_SEC_PROVIDERS") //$NON-NLS-1$
						+ IRandomFactory.TYPE_SECURE_RANDOM
						+ Messages.getString("PwGenerator.NEW_LINE"), false); //$NON-NLS-1$
				Provider[] serviceProviders = randomFactory.getProviders();
				for (int i = 0; i < serviceProviders.length; i++)
				{
					log(Messages.getString("PwGenerator.SEPARATOR"), //$NON-NLS-1$
							false);
					log(
							Messages.getString("PwGenerator.PROVIDER") + serviceProviders[i].getName() + Messages.getString("PwGenerator.NEW_LINE"), //$NON-NLS-1$ //$NON-NLS-2$
							false);
					Set<Provider.Service> services = serviceProviders[i]
							.getServices();
					log(
							Messages.getString("PwGenerator.SERVICES") + Messages.getString("PwGenerator.NEW_LINE"), false); //$NON-NLS-1$ //$NON-NLS-2$
					log(services.toString(), false);
					log(Messages.getString("PwGenerator.SEPARATOR"), //$NON-NLS-1$
							false);
				}
				log(Messages.getString("PwGenerator.SEPARATOR"), //$NON-NLS-1$
						false);
				System.exit(0);
			}

			if (commandLine.hasOption(CL_NUMBER_PASSWORD))
			{
				String sNumberOfPasswords = commandLine
						.getOptionValue(CL_NUMBER_PASSWORD);
				if (sNumberOfPasswords != null)
					numberOfPasswords = Integer.parseInt(sNumberOfPasswords);
				log(
						Messages.getString("PwGenerator.NUM_PASSWORDS") + numberOfPasswords, false); //$NON-NLS-1$
			}

			commandLine = parser.parse(options, args);
			if (commandLine.hasOption(CL_PASSWORD_LENGTH))
			{
				String sPasswordLength = commandLine
						.getOptionValue(CL_PASSWORD_LENGTH);
				if (sPasswordLength != null)
					passwordLength = Integer.parseInt(sPasswordLength);
				log(
						Messages.getString("PwGenerator.PASSWORD_LENGTH") + passwordLength, false); //$NON-NLS-1$
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_COLUMN))
			{
				doColumns = true;
				log(Messages.getString("PwGenerator.COLUMNS_ENABLED"), false); //$NON-NLS-1$
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_TERM_WIDTH))
			{
				String sTermWidth = commandLine.getOptionValue(CL_TERM_WIDTH);
				if (sTermWidth != null)
					termWidth = Integer.parseInt(sTermWidth);
				log(
						Messages.getString("PwGenerator.TERMINAL_LENGTH") + termWidth, false); //$NON-NLS-1$
			}

			Random random = null;
			parser.parse(options, args);
			if (commandLine.hasOption(CL_RANDOM))
			{
				random = randomFactory.getRandom();
				log(Messages.getString("PwGenerator.NORMAL_RANDOM"), false); //$NON-NLS-1$
			} else
			{
				try
				{
					random = randomFactory.getSecureRandom();
				} catch (NoSuchAlgorithmException e)
				{
					e.printStackTrace();
					random = randomFactory.getRandom();
				} catch (NoSuchProviderException e)
				{
					e.printStackTrace();
					random = randomFactory.getRandom();
				}
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_SR_ALGORITHM))
			{
				String[] data = commandLine.getOptionValues(CL_SR_ALGORITHM);
				if (data.length == 2)
				{
					try
					{
						random = randomFactory
								.getSecureRandom(data[0], data[1]);
						log(
								Messages.getString("PwGenerator.SEC_ALG") + data[0] //$NON-NLS-1$
										+ Messages
												.getString("PwGenerator.PROV") + data[1] + Messages.getString("PwGenerator.DOR"), false); //$NON-NLS-1$ //$NON-NLS-2$
					} catch (NoSuchAlgorithmException e)
					{
						log(
								Messages.getString("PwGenerator.ERROR") + e.getMessage() + Messages.getString("PwGenerator.NEW_LINE"), true); //$NON-NLS-1$ //$NON-NLS-2$
						log(
								Messages.getString("PwGenerator.DEFAUL_RANDOM"), true); //$NON-NLS-1$
					} catch (NoSuchProviderException e)
					{
						log(
								Messages.getString("PwGenerator.ERROR") + e.getMessage() + Messages.getString("PwGenerator.NEW_LINE"), true); //$NON-NLS-1$ //$NON-NLS-2$
						log(
								Messages.getString("PwGenerator.DEFAUL_RANDOM"), true); //$NON-NLS-1$
					}
				}
			}

			if (commandLine.hasOption(CL_NUMERALS))
			{
				passwordFlags |= PW_DIGITS;
				log(Messages.getString("PwGenerator.DIGITS_ON"), false); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_NO_NUMERALS))
			{
				passwordFlags &= ~PW_DIGITS;
				log(Messages.getString("PwGenerator.DIGITS_OFF"), false); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_CAPITALIZE))
			{
				passwordFlags |= PW_UPPERS;
				log(Messages.getString("PwGenerator.UPPERCASE_ON"), false); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_NO_CAPITALIZE))
			{
				passwordFlags &= ~PW_UPPERS;
				log(Messages.getString("PwGenerator.UPPERCASE_OFF"), false); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_AMBIGOUS))
			{
				passwordFlags |= PW_AMBIGUOUS;
				log(Messages.getString("PwGenerator.AMBIGOUS_ON"), false); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_NO_AMBIGOUS))
			{
				passwordFlags &= ~PW_AMBIGUOUS;
				log(Messages.getString("PwGenerator.AMBIGOUS_OFF"), false); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_SYMBOLS))
			{
				passwordFlags |= PW_SYMBOLS;
				log(Messages.getString("PwGenerator.SYMBOLS_ON"), false); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_SYMBOLS_REDUCED))
			{
				passwordFlags |= PW_SYMBOLS_REDUCED;
				log(Messages.getString("PwGenerator.SYMBOLS_REDUCED_ON"), false); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_NO_SYMBOLS))
			{
				passwordFlags &= ~PW_SYMBOLS;
				passwordFlags &= ~PW_SYMBOLS_REDUCED;
				log(Messages.getString("PwGenerator.SYMBOLS_OFF"), false); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_MAX_ATTEMPTS))
			{
				String maxAttempts = commandLine
						.getOptionValue(CL_MAX_ATTEMPTS);
				if (maxAttempts != null)
					MAX_ATTEMPTS = Integer.parseInt(maxAttempts);
				log(
						Messages.getString("PwGenerator.MAX_ATTEMPTS") + MAX_ATTEMPTS, false); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_REGEX_STARTS_NO_SMALL_LETTER))
				passwordFlags |= REGEX_STARTS_NO_SMALL_LETTER_FLAG;

			if (commandLine.hasOption(CL_REGEX_ENDS_NO_SMALL_LETTER))
				passwordFlags |= REGEX_STARTS_NO_SMALL_LETTER_FLAG;

			if (commandLine.hasOption(CL_REGEX_STARTS_NO_UPPER_LETTER))
				passwordFlags |= REGEX_STARTS_NO_UPPER_LETTER_FLAG;

			if (commandLine.hasOption(CL_REGEX_ENDS_NO_UPPER_LETTER))
				passwordFlags |= REGEX_ENDS_NO_UPPER_LETTER_FLAG;

			if (commandLine.hasOption(CL_REGEX_ENDS_NO_DIGIT))
				passwordFlags |= REGEX_ENDS_NO_DIGIT_FLAG;

			if (commandLine.hasOption(CL_REGEX_STARTS_NO_DIGIT))
				passwordFlags |= REGEX_STARTS_NO_DIGIT_FLAG;

			if (commandLine.hasOption(CL_REGEX_STARTS_NO_SYMBOL))
				passwordFlags |= REGEX_STARTS_NO_SYMBOL_FLAG;

			if (commandLine.hasOption(CL_REGEX_ENDS_NO_SYMBOL))
				passwordFlags |= REGEX_ENDS_NO_SYMBOL_FLAG;

			if (commandLine.hasOption(CL_REGEX_ONLY_1_CAPITAL))
				passwordFlags |= REGEX_ONLY_1_CAPITAL_FLAG;

			if (commandLine.hasOption(CL_REGEX_ONLY_1_SYMBOL))
				passwordFlags |= REGEX_ONLY_1_SYMBOL_FLAG;

			if (commandLine.hasOption(CL_REGEX_AT_LEAST_2_SYMBOLS))
				passwordFlags |= REGEX_AT_LEAST_2_SYMBOLS_FLAG;

			if (commandLine.hasOption(CL_REGEX_ONLY_1_DIGIT))
				passwordFlags |= REGEX_ONLY_1_DIGIT_FLAG;

			if (commandLine.hasOption(CL_REGEX_AT_LEAST_2_DIGITS))
				passwordFlags |= REGEX_AT_LEAST_2_DIGITS_FLAG;
			// -------------------------------------------------------------------

			log(Messages.getString("PwGenerator.GENRIC_FLAGS"), //$NON-NLS-1$
					false);

			int res = passwordFlags & PW_DIGITS;
			log(Messages.getString("PwGenerator.DIGITS") + (res != 0), false); //$NON-NLS-1$
			res = passwordFlags & PW_AMBIGUOUS;
			log(Messages.getString("PwGenerator.AMBIGOUS") + (res != 0), false); //$NON-NLS-1$
			res = passwordFlags & PW_SYMBOLS;
			log(Messages.getString("PwGenerator.SYMBOLS") + (res != 0), false); //$NON-NLS-1$
			res = passwordFlags & PW_SYMBOLS_REDUCED;
			log(
					Messages.getString("PwGenerator.SYMBOLS_REDUCED") + (res != 0), false); //$NON-NLS-1$
			res = passwordFlags & PW_UPPERS;
			log(Messages.getString("PwGenerator.UPPERS") + (res != 0), false); //$NON-NLS-1$
			log(Messages.getString("PwGenerator.SEPARATOR"), //$NON-NLS-1$
					false);

			log(
					Messages.getString("PwGenerator.GENERATING") + numberOfPasswords + Messages.getString("PwGenerator.PW_LENGTH") //$NON-NLS-1$ //$NON-NLS-2$
							+ passwordLength, false);
			log(Messages.getString("PwGenerator.PW"), //$NON-NLS-1$
					false);

			int i;
			for (i = 0; i < numberOfPasswords; i++)
			{
				String password = generatePassword(passwordLength,
						passwordFlags, random);
				if (password != null)
					passwords.add(password);
			}
		} catch (ParseException e)
		{
			log(
					Messages.getString("PwGenerator.PARAM_ERROR") + e.getLocalizedMessage(), true); //$NON-NLS-1$
			printUsage();
		} catch (NumberFormatException e)
		{
			log(
					Messages.getString("PwGenerator.NUM_FORM_ERROR") + e.getLocalizedMessage(), true); //$NON-NLS-1$
			printUsage();
		}

		return passwords;
	}

	/**
	 * Prints passwords into columns with a predefined terminal width(to
	 * System.out). The number of columns is calculated from the terminal width.
	 * 
	 * @param passwords
	 *            a list of passwords to be printed
	 * @param termWidth
	 *            the width in characters of the terminal
	 */
	public void printColumns(List<String> passwords, int termWidth)
	{

		int numberOfColumns = termWidth / (passwordLength + 1);
		if (numberOfColumns == 0)
			numberOfColumns = 1;

		log(Messages.getString("PwGenerator.N_SEPARATOR"), //$NON-NLS-1$
				false);

		for (int i = 0; i < passwords.size(); i++)
		{
			String password = (String) passwords.get(i);
			if (logger.isInfoEnabled())
			{
				if (((i % numberOfColumns) == (numberOfColumns - 1)))
				{
					System.out.print(password
							+ Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$
				} else
				{
					System.out.print(password + ' ');
				}
			}
		}
		log(Messages.getString("PwGenerator.N_SEPARATOR"), //$NON-NLS-1$
				false);
	}

	/**
	 * Prints a list of passwords to a terminal(System.out)
	 * 
	 * @param passwords
	 *            a list of passwords to be printed
	 */
	public void print(List<String> passwords)
	{
		log(Messages.getString("PwGenerator.N_SEPARATOR"), //$NON-NLS-1$
				false);

		for (int i = 0; i < passwords.size(); i++)
		{
			String password = (String) passwords.get(i);
			if (logger.isInfoEnabled())
			{
				System.out.print(password
						+ Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$
			}
		}
		log(Messages.getString("PwGenerator.N_SEPARATOR"), //$NON-NLS-1$
				false);
	}

	/**
	 * The real password generation is performed in this method
	 * 
	 * @param size
	 *            the length of the password
	 * @param pw_flags
	 *            the settings for the password
	 * @return the newly created password
	 */
	private String phonemes(int size, int pw_flags, Random random)
	{
		int c, i, len, flags, feature_flags;
		int prev, should_be;
		boolean first;
		String str;
		char ch;
		StringBuffer buf = new StringBuffer();

		do
		{
			buf.delete(0, buf.length());
			feature_flags = pw_flags;
			c = 0;
			prev = 0;
			should_be = 0;
			first = true;
			should_be = random.nextBoolean() ? VOWEL : CONSONANT;

			while (c < size)
			{

				i = random.nextInt(PW_ELEMENTS.length);
				str = PW_ELEMENTS[i].getValue();
				len = str.length();
				flags = PW_ELEMENTS[i].getType();
				/* Filter on the basic type of the next element */
				if ((flags & should_be) == 0)
				{
					continue;
				}
				/* Handle the NOT_FIRST flag */
				if (first && ((flags & NOT_FIRST) != 0))
					continue;
				/* Don't allow VOWEL followed a Vowel/Dipthong pair */
				if (((prev & VOWEL) != 0) && ((flags & VOWEL) != 0)
						&& ((flags & DIPTHONG) != 0))
					continue;
				/* Don't allow us to overflow the buffer */
				if (len > size - c)
					continue;
				/*
				 * OK, we found an element which matches our criteria, let's do
				 * it!
				 */
				buf.append(str);

				/* Handle PW_SYMBOLS */
				if ((pw_flags & PW_SYMBOLS) != 0)
				{
					if (!first && (random.nextInt(10) < 2))
					{
						do
						{
							ch = PW_SPECIAL_SYMBOLS.charAt(random
									.nextInt(PW_SPECIAL_SYMBOLS.length()));
						} while (((pw_flags & PW_AMBIGUOUS) != 0)
								&& (PW_AMBIGUOUS_SYMBOLS.indexOf(ch) != -1));
						c++;
						buf = buf.append(ch);
						feature_flags &= ~PW_SYMBOLS;
					}
				} else if ((pw_flags & PW_SYMBOLS_REDUCED) != 0)
				{
					if (!first && (random.nextInt(10) < 2))
					{
						do
						{
							ch = PW_SPECIAL_SYMBOLS_REDUCED.charAt(random
									.nextInt(PW_SPECIAL_SYMBOLS_REDUCED
											.length()));
						} while (((pw_flags & PW_AMBIGUOUS) != 0)
								&& (PW_AMBIGUOUS_SYMBOLS.indexOf(ch) != -1));
						c++;
						buf = buf.append(ch);
						feature_flags &= ~PW_SYMBOLS_REDUCED;
					}
				}

				/* Handle PW_UPPERS */
				if ((pw_flags & PW_UPPERS) != 0)
				{
					if ((first || ((flags & CONSONANT) != 0))
							&& (random.nextInt(10) < 2))
					{
						int lastChar = buf.length() - 1;
						buf.setCharAt(lastChar, Character.toUpperCase(buf
								.charAt(lastChar)));
						feature_flags &= ~PW_UPPERS;
					}
				}

				c += len;
				/* Handle the AMBIGUOUS flag */
				if ((pw_flags & PW_AMBIGUOUS) != 0)
				{
					int k = -1;
					for (int j = 0; j < PW_AMBIGUOUS_SYMBOLS.length(); j++)
					{
						k = buf.indexOf(String.valueOf(PW_AMBIGUOUS_SYMBOLS
								.charAt(j)));
						if (k != -1)
							break;
					}
					if (k != -1)
					{
						buf.delete(k, buf.length());
						c = buf.length();
					}
				}

				/* Time to stop? */
				if (c >= size)
					break;

				/*
				 * Handle PW_DIGITS
				 */
				if ((pw_flags & PW_DIGITS) != 0)
				{
					if (!first && (random.nextInt(10) < 3))
					{
						do
						{
							ch = (new Integer(random.nextInt(10))).toString()
									.charAt(0);
						} while (((pw_flags & PW_AMBIGUOUS) != 0)
								&& (PW_AMBIGUOUS_SYMBOLS.indexOf(ch) != -1));
						c++;
						buf = buf.append(ch);
						feature_flags &= ~PW_DIGITS;

						first = true;
						prev = 0;
						should_be = random.nextBoolean() ? VOWEL : CONSONANT;
						continue;
					}
				}

				/*
				 * OK, figure out what the next element should be
				 */
				if (should_be == CONSONANT)
				{
					should_be = VOWEL;
				} else
				{ /* should_be == VOWEL */
					if (((prev & VOWEL) != 0) || ((flags & DIPTHONG) != 0)
							|| (random.nextInt(10) > 3))
						should_be = CONSONANT;
					else
						should_be = VOWEL;
				}
				prev = flags;
				first = false;

			}
		} while ((feature_flags & (PW_UPPERS | PW_DIGITS | PW_SYMBOLS)) != 0);

		return buf.toString();
	}

	/**
	 * Initializes the CLI (Command Line Interface) options of the PwGenerator.
	 * 
	 * @return the CLI options
	 */
	private Options createOptions()
	{
		options = new Options();

		Option option = createOption(CL_NUMBER_PASSWORD,
				CL_NUMBER_PASSWORD_LONG, CL_NUMBER_PASSWORD_DESC, true, false);
		options.addOption(option);

		option = createOption(CL_PASSWORD_LENGTH, CL_PASSWORD_LENGTH_LONG,
				CL_PASSWORD_LENGTH_DESC, true, false);
		options.addOption(option);

		option = createOption(CL_CAPITALIZE, CL_CAPITALIZE_LONG,
				CL_CAPITALIZE_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_NO_CAPITALIZE, CL_NO_CAPITALIZE_LONG,
				CL_NO_CAPITALIZE_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_NUMERALS, CL_NUMERALS_LONG, CL_NUMERALS_DESC,
				false, false);
		options.addOption(option);

		option = createOption(CL_NO_NUMERALS, CL_NO_NUMERALS_LONG,
				CL_NO_NUMERALS_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_SYMBOLS, CL_SYMBOLS_LONG, CL_SYMBOLS_DESC,
				false, false);
		options.addOption(option);

		option = createOption(CL_SYMBOLS_REDUCED, CL_SYMBOLS_REDUCED_LONG,
				CL_SYMBOLS_REDUCED_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_NO_SYMBOLS, CL_NO_SYMBOLS_LONG,
				CL_NO_SYMBOLS_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_AMBIGOUS, CL_AMBIGOUS_LONG, CL_AMBIGOUS_DESC,
				false, false);
		options.addOption(option);

		option = createOption(CL_NO_AMBIGOUS, CL_NO_AMBIGOUS_LONG,
				CL_NO_AMBIGOUS_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_HELP, CL_HELP_LONG, CL_HELP_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_RANDOM, CL_RANDOM_LONG, CL_RANDOM_DESC, false,
				false);
		options.addOption(option);

		option = createOption(CL_COLUMN, CL_COLUMN_LONG, CL_COLUMN_DESC, false,
				false);
		options.addOption(option);

		option = createOption(CL_SR_PROVIDERS, CL_SR_PROVIDERS_LONG,
				CL_SR_PROVIDERS_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_PROVIDERS, CL_PROVIDERS_LONG,
				CL_PROVIDERS_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_SR_ALGORITHM, CL_SR_ALGORITHM_LONG,
				CL_SR_ALGORITHM_LONG, true, false);
		option.hasArgs();
		option.setArgs(2);
		options.addOption(option);

		option = createOption(CL_TERM_WIDTH, CL_TERM_WIDTH_LONG,
				CL_TERM_WIDTH_DESC, true, false);
		options.addOption(option);

		option = createOption(CL_MAX_ATTEMPTS, CL_MAX_ATTEMPTS_LONG,
				CL_MAX_ATTEMPTS_DESC, true, false);
		options.addOption(option);

		// regex
		option = createOption(CL_REGEX_STARTS_NO_SMALL_LETTER,
				CL_REGEX_STARTS_NO_SMALL_LETTER_LONG,
				CL_REGEX_STARTS_NO_SMALL_LETTER_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_ENDS_NO_SMALL_LETTER,
				CL_REGEX_ENDS_NO_SMALL_LETTER_LONG,
				CL_REGEX_ENDS_NO_SMALL_LETTER_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_STARTS_NO_UPPER_LETTER,
				CL_REGEX_STARTS_NO_UPPER_LETTER_LONG,
				CL_REGEX_STARTS_NO_UPPER_LETTER_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_ENDS_NO_UPPER_LETTER,
				CL_REGEX_ENDS_NO_UPPER_LETTER_LONG,
				CL_REGEX_ENDS_NO_UPPER_LETTER_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_ENDS_NO_DIGIT,
				CL_REGEX_ENDS_NO_DIGIT_LONG, CL_REGEX_ENDS_NO_DIGIT_DESC,
				false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_STARTS_NO_DIGIT,
				CL_REGEX_STARTS_NO_DIGIT_LONG, CL_REGEX_STARTS_NO_DIGIT_DESC,
				false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_STARTS_NO_SYMBOL,
				CL_REGEX_STARTS_NO_SYMBOL_LONG, CL_REGEX_STARTS_NO_SYMBOL_DESC,
				false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_ENDS_NO_SYMBOL,
				CL_REGEX_ENDS_NO_SYMBOL_LONG, CL_REGEX_ENDS_NO_SYMBOL_DESC,
				false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_ONLY_1_CAPITAL,
				CL_REGEX_ONLY_1_CAPITAL_LONG, CL_REGEX_ONLY_1_CAPITAL_DESC,
				false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_ONLY_1_SYMBOL,
				CL_REGEX_ONLY_1_SYMBOL_LONG, CL_REGEX_ONLY_1_SYMBOL_DESC,
				false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_AT_LEAST_2_SYMBOLS,
				CL_REGEX_AT_LEAST_2_SYMBOLS_LONG,
				CL_REGEX_AT_LEAST_2_SYMBOLS_DESC, false, false);
		options.addOption(option);

		option = createOption(CL_REGEX_ONLY_1_DIGIT,
				CL_REGEX_ONLY_1_DIGIT_LONG, CL_REGEX_ONLY_1_DIGIT_DESC, false,
				false);
		options.addOption(option);

		option = createOption(CL_REGEX_AT_LEAST_2_DIGITS,
				CL_REGEX_AT_LEAST_2_DIGITS_LONG,
				CL_REGEX_AT_LEAST_2_DIGITS_DESC, false, false);
		options.addOption(option);

		return options;
	}

	/**
	 * Creates a CLI entry.
	 * 
	 * @param shortOption
	 *            a one letter flag
	 * @param longOption
	 *            long flag
	 * @param description
	 *            the description of the cCLI option
	 * @param arg
	 *            specifies whether the option has arguments
	 * @param required
	 *            specifies whether the option is required
	 * @return a new instance of a CLI option with the predefined properties
	 */
	private Option createOption(String shortOption, String longOption,
			String description, boolean arg, boolean required)
	{
		OptionBuilder.withLongOpt(longOption);
		OptionBuilder.withDescription(description);
		OptionBuilder.isRequired(required);
		if (arg)
			OptionBuilder.hasArg();
		Option option = OptionBuilder.create(shortOption);

		return option;
	}

	/**
	 * Prints the usage info
	 */
	private void printUsage()
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(Messages.getString("PwGenerator.USAGE"), //$NON-NLS-1$
				options);
		log(Messages.getString("PwGenerator.EXAMPLE"), //$NON-NLS-1$
				false);
	}

	/**
	 * Returns the instance of the default blacklist filter registered with the
	 * PwGenerator
	 * 
	 * @return the default blacklist filter
	 */
	public IPasswordFilter getDefaultBlacklistFilter()
	{
		return defaultBlacklistFilter;
	}

	/**
	 * Sets the instance of default blacklist filter
	 * 
	 * @param defaultBlacklistFilter
	 *            a new instance for the default blacklist filter
	 */
	public void setDefaultBlacklistFilter(IPasswordFilter defaultBlacklistFilter)
	{
		this.defaultBlacklistFilter = defaultBlacklistFilter;
	}

	/**
	 * Returns the instance of the default regular expression filter registered
	 * with the PwGenerator
	 * 
	 * @return the default regular expression filter
	 */
	public IPasswordFilter getDefaultRegexFilter()
	{
		return defaultRegexFilter;
	}

	/**
	 * Sets the instance of default regular expression filter
	 * 
	 * @param defaultRegexFilter
	 */
	public void setDefaultRegexFilter(IPasswordFilter defaultRegexFilter)
	{
		this.defaultRegexFilter = defaultRegexFilter;
	}
}