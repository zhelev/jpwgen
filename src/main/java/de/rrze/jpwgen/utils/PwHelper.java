package de.rrze.jpwgen.utils;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.rrze.jpwgen.IPwGenCommandLineOptions;
import de.rrze.jpwgen.IPwGenConstants;
import de.rrze.jpwgen.IPwGenRegEx;
import de.rrze.jpwgen.IRandomFactory;
import de.rrze.jpwgen.impl.PwGenerator;

public class PwHelper implements IPwGenCommandLineOptions, IPwGenConstants,
		IPwGenRegEx
{
	private final static Logger LOGGER = Logger.getLogger(PwHelper.class
			.getName());

	// The command options with which the program was started
	private static Options options = createOptions();

	private static int termWidth = DEFAULT_TERM_WIDTH;

	private static boolean doColumns = DEFAULT_DO_COLUMNS;

	/**
	 * Entry point of the program
	 * 
	 * @param args
	 *            the program arguments
	 */
	public static void main(String[] args)
	{
		List<String> passwords = process(args, null);
		if (doColumns)
		{
			printColumns(passwords, termWidth);
		} else
		{
			print(passwords);
		}
	}

	/**
	 * Initializes the CLI (Command Line Interface) options of the PwGenerator.
	 * 
	 * @return the CLI options
	 */
	private synchronized static Options createOptions()
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
	 *            the description of the CLI option
	 * @param arg
	 *            specifies whether the option has arguments
	 * @param required
	 *            specifies whether the option is required
	 * @return a new instance of a CLI option with the predefined properties
	 */
	private synchronized static Option createOption(String shortOption,
			String longOption, String description, boolean arg, boolean required)
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
	private static synchronized void printUsage()
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(Messages.getString("PwGenerator.USAGE"), //$NON-NLS-1$
				options);
		LOGGER.info(Messages.getString("PwGenerator.EXAMPLE") //$NON-NLS-1$
		);
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
	public static synchronized List<String> process(String[] args, List<String> blackList)
	{
		int passwordFlags = DEFAULT_FLAGS;

		// The length of the password to be generated
		int passwordLength = DEFAULT_PASSWORD_LENGTH;

		int numberOfPasswords = DEFAULT_NUMBER_OF_PASSWORDS;

		int maxAttempts = DEFAULT_MAX_ATTEMPTS;

		LOGGER.info(Messages.getString("PwGenerator.PASSWORD_GENERATOR") //$NON-NLS-1$
		);

		ArrayList<String> passwords = new ArrayList<String>();
		BasicParser parser = new BasicParser();
		try
		{
			CommandLine commandLine = parser.parse(options, args);

			parser.parse(options, args);
			if (commandLine.hasOption(CL_HELP))
			{
				printUsage();
				LOGGER.info(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
				);
				return passwords;
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_SR_PROVIDERS))
			{
				Set<String> serviceProviders = RandomFactory.getInstance()
						.getServiceProviderFor(
								IRandomFactory.TYPE_SECURE_RANDOM);
				LOGGER.info(Messages
						.getString("PwGenerator.SERVICES_PROVIDERS_FOR") //$NON-NLS-1$
						+ IRandomFactory.TYPE_SECURE_RANDOM
						+ Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$
				for (Iterator<String> iter = serviceProviders.iterator(); iter
						.hasNext();)
				{
					String element = (String) iter.next();
					LOGGER.info(Messages
							.getString("PwGenerator.SERVICE_PROVIDERS") + element + Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$ //$NON-NLS-2$
					LOGGER.info(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
					);
					return passwords;
				}
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_PROVIDERS))
			{
				LOGGER.info(Messages.getString("PwGenerator.ALL_SEC_PROVIDERS") //$NON-NLS-1$
						+ IRandomFactory.TYPE_SECURE_RANDOM
						+ Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$
				Provider[] serviceProviders = RandomFactory.getInstance()
						.getProviders();
				for (int i = 0; i < serviceProviders.length; i++)
				{
					LOGGER.info(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
					);
					LOGGER.info(Messages.getString("PwGenerator.PROVIDER") + serviceProviders[i].getName() + Messages.getString("PwGenerator.NEW_LINE") //$NON-NLS-1$ //$NON-NLS-2$
					);
					Set<Provider.Service> services = serviceProviders[i]
							.getServices();
					LOGGER.info(Messages.getString("PwGenerator.SERVICES") + Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$ //$NON-NLS-2$
					LOGGER.info(services.toString());
					LOGGER.info(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
					);
				}
				LOGGER.info(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
				);
				return passwords;
			}

			if (commandLine.hasOption(CL_NUMBER_PASSWORD))
			{
				String sNumberOfPasswords = commandLine
						.getOptionValue(CL_NUMBER_PASSWORD);
				if (sNumberOfPasswords != null)
					numberOfPasswords = Integer.parseInt(sNumberOfPasswords);
				LOGGER.info(Messages.getString("PwGenerator.NUM_PASSWORDS") + numberOfPasswords); //$NON-NLS-1$
			}

			commandLine = parser.parse(options, args);
			if (commandLine.hasOption(CL_PASSWORD_LENGTH))
			{
				String sPasswordLength = commandLine
						.getOptionValue(CL_PASSWORD_LENGTH);
				if (sPasswordLength != null)
					passwordLength = Integer.parseInt(sPasswordLength);
				LOGGER.info(Messages.getString("PwGenerator.PASSWORD_LENGTH") + passwordLength); //$NON-NLS-1$
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_COLUMN))
			{
				doColumns = true;
				LOGGER.info(Messages.getString("PwGenerator.COLUMNS_ENABLED")); //$NON-NLS-1$
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_TERM_WIDTH))
			{
				String sTermWidth = commandLine.getOptionValue(CL_TERM_WIDTH);
				if (sTermWidth != null)
					termWidth = Integer.parseInt(sTermWidth);
				LOGGER.info(Messages.getString("PwGenerator.TERMINAL_LENGTH") + termWidth); //$NON-NLS-1$
			}

			Random random = null;
			parser.parse(options, args);
			if (commandLine.hasOption(CL_RANDOM))
			{
				random = RandomFactory.getInstance().getRandom();
				LOGGER.info(Messages.getString("PwGenerator.NORMAL_RANDOM")); //$NON-NLS-1$
			} else
			{
				try
				{
					random = RandomFactory.getInstance().getSecureRandom();
				} catch (NoSuchAlgorithmException e)
				{
					e.printStackTrace();
					random = RandomFactory.getInstance().getRandom();
				} catch (NoSuchProviderException e)
				{
					e.printStackTrace();
					random = RandomFactory.getInstance().getRandom();
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
						random = RandomFactory.getInstance().getSecureRandom(
								data[0], data[1]);

						LOGGER.info(Messages.getString("PwGenerator.SEC_ALG") + data[0] //$NON-NLS-1$
								+ Messages.getString("PwGenerator.PROV") + data[1] + Messages.getString("PwGenerator.DOR")); //$NON-NLS-1$ //$NON-NLS-2$
					} catch (NoSuchAlgorithmException e)
					{
						LOGGER.info(Messages.getString("PwGenerator.ERROR") + e.getMessage() + Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$ //$NON-NLS-2$
						LOGGER.info(Messages
								.getString("PwGenerator.DEFAUL_RANDOM")); //$NON-NLS-1$
					} catch (NoSuchProviderException e)
					{
						LOGGER.info(Messages.getString("PwGenerator.ERROR") + e.getMessage() + Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$ //$NON-NLS-2$
						LOGGER.info(Messages
								.getString("PwGenerator.DEFAUL_RANDOM")); //$NON-NLS-1$
					}
				}
			}

			if (commandLine.hasOption(CL_NUMERALS))
			{
				passwordFlags |= PW_DIGITS;
				LOGGER.info(Messages.getString("PwGenerator.DIGITS_ON")); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_NO_NUMERALS))
			{
				passwordFlags &= ~PW_DIGITS;
				LOGGER.info(Messages.getString("PwGenerator.DIGITS_OFF")); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_CAPITALIZE))
			{
				passwordFlags |= PW_UPPERS;
				LOGGER.info(Messages.getString("PwGenerator.UPPERCASE_ON")); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_NO_CAPITALIZE))
			{
				passwordFlags &= ~PW_UPPERS;
				LOGGER.info(Messages.getString("PwGenerator.UPPERCASE_OFF")); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_AMBIGOUS))
			{
				passwordFlags |= PW_AMBIGUOUS;
				LOGGER.info(Messages.getString("PwGenerator.AMBIGOUS_ON")); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_NO_AMBIGOUS))
			{
				passwordFlags &= ~PW_AMBIGUOUS;
				LOGGER.info(Messages.getString("PwGenerator.AMBIGOUS_OFF")); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_SYMBOLS))
			{
				passwordFlags |= PW_SYMBOLS;
				LOGGER.info(Messages.getString("PwGenerator.SYMBOLS_ON")); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_SYMBOLS_REDUCED))
			{
				passwordFlags |= PW_SYMBOLS_REDUCED;
				LOGGER.info(Messages
						.getString("PwGenerator.SYMBOLS_REDUCED_ON")); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_NO_SYMBOLS))
			{
				passwordFlags &= ~PW_SYMBOLS;
				passwordFlags &= ~PW_SYMBOLS_REDUCED;
				LOGGER.info(Messages.getString("PwGenerator.SYMBOLS_OFF")); //$NON-NLS-1$
			}

			if (commandLine.hasOption(CL_MAX_ATTEMPTS))
			{
				String sMaxAttempts = commandLine
						.getOptionValue(CL_MAX_ATTEMPTS);
				if (sMaxAttempts != null)
					maxAttempts = Integer.parseInt(sMaxAttempts);
				LOGGER.info(Messages.getString("PwGenerator.MAX_ATTEMPTS") + maxAttempts); //$NON-NLS-1$
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

			LOGGER.info(Messages.getString("PwGenerator.GENRIC_FLAGS") //$NON-NLS-1$
			);

			int res = passwordFlags & PW_DIGITS;
			LOGGER.info(Messages.getString("PwGenerator.DIGITS") + (res != 0)); //$NON-NLS-1$
			res = passwordFlags & PW_AMBIGUOUS;
			LOGGER.info(Messages.getString("PwGenerator.AMBIGOUS") + (res != 0)); //$NON-NLS-1$
			res = passwordFlags & PW_SYMBOLS;
			LOGGER.info(Messages.getString("PwGenerator.SYMBOLS") + (res != 0)); //$NON-NLS-1$
			res = passwordFlags & PW_SYMBOLS_REDUCED;
			LOGGER.info(Messages.getString("PwGenerator.SYMBOLS_REDUCED") + (res != 0)); //$NON-NLS-1$
			res = passwordFlags & PW_UPPERS;
			LOGGER.info(Messages.getString("PwGenerator.UPPERS") + (res != 0)); //$NON-NLS-1$
			LOGGER.info(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
			);

			LOGGER.info(Messages.getString("PwGenerator.GENERATING") + numberOfPasswords + Messages.getString("PwGenerator.PW_LENGTH") //$NON-NLS-1$ //$NON-NLS-2$
					+ passwordLength);
			LOGGER.info(Messages.getString("PwGenerator.PW") //$NON-NLS-1$
			);

			if(blackList!=null)
				PwGenerator.getDefaultBlacklistFilter().getBlacklist().addAll(blackList);
			
			int i;
			for (i = 0; i < numberOfPasswords; i++)
			{
				String password = PwGenerator.generatePassword(passwordLength,
						passwordFlags, maxAttempts, random);
				if (password != null)
					passwords.add(password);
			}
		} catch (ParseException e)
		{
			LOGGER.info(Messages.getString("PwGenerator.PARAM_ERROR") + e.getLocalizedMessage()); //$NON-NLS-1$
			printUsage();
		} catch (NumberFormatException e)
		{
			LOGGER.info(Messages.getString("PwGenerator.NUM_FORM_ERROR") + e.getLocalizedMessage()); //$NON-NLS-1$
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
	public static void printColumns(List<String> passwords, int termWidth)
	{

		LOGGER.info(Messages.getString("PwGenerator.N_SEPARATOR") //$NON-NLS-1$
		);

		for (int i = 0; i < passwords.size(); i++)
		{
			String password = (String) passwords.get(i);

			int numberOfColumns = termWidth / (password.length() + 1);
			if (numberOfColumns == 0)
				numberOfColumns = 1;

			if (((i % numberOfColumns) == (numberOfColumns - 1)))
			{

				System.out.print(password
						+ Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$
			} else
			{
				System.out.print(password + ' ');
			}
		}
		LOGGER.info(Messages.getString("PwGenerator.N_SEPARATOR") //$NON-NLS-1$
		);
	}

	/**
	 * Prints a list of passwords to a terminal(System.out)
	 * 
	 * @param passwords
	 *            a list of passwords to be printed
	 */

	public static void print(List<String> passwords)
	{
		LOGGER.info(Messages.getString("PwGenerator.N_SEPARATOR") //$NON-NLS-1$
		);

		for (int i = 0; i < passwords.size(); i++)
		{
			String password = (String) passwords.get(i);

			System.out.print(password
					+ Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$
		}
		LOGGER.info(Messages.getString("PwGenerator.N_SEPARATOR") //$NON-NLS-1$
		);
	}

}
