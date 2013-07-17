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

import de.rrze.jpwgen.ICliFlag;
import de.rrze.jpwgen.IDefaultFilter;
import de.rrze.jpwgen.IPasswordFilter;
import de.rrze.jpwgen.IPasswordPolicy;
import de.rrze.jpwgen.IPwDefConstants;
import de.rrze.jpwgen.IPwGenCommandLineOptions;
import de.rrze.jpwgen.IPwGenerator;
import de.rrze.jpwgen.IRandomFactory;
import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;
import de.rrze.jpwgen.impl.PasswordPolicy;
import de.rrze.jpwgen.impl.PwGenerator;

public class PwHelper implements IPwGenCommandLineOptions, IPwDefConstants,
		IDefaultFilter {
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
	public static void main(String[] args) {
		List<String> passwords = process(args);
		if (doColumns) {
			printColumns(passwords, termWidth);
		} else {
			print(passwords);
		}
	}

	/**
	 * Initializes the CLI (Command Line Interface) options of the PwGenerator.
	 * 
	 * @return the CLI options
	 */
	private synchronized static Options createOptions() {

		options = new Options();

		Option option = createOption(CL_NUMBER_PASSWORD,
				CL_NUMBER_PASSWORD_LONG, CL_NUMBER_PASSWORD_DESC, true, false);
		options.addOption(option);

		option = createOption(CL_PASSWORD_LENGTH, CL_PASSWORD_LENGTH_LONG,
				CL_PASSWORD_LENGTH_DESC, true, false);
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

		return createCliOptionsFromFlags(options);
	}

	private static Options createCliOptionsFromFlags(Options options) {

		List<ICliFlag> clis = PwGeneratorFlagBuilder.getCliFlags();
		for (ICliFlag cli : clis) {
			// System.out.println(cli);

			Option option = createOption(cli.cliShort(), cli.cliLong(),
					cli.cliDescription(), false, false);

			// System.out.println("============== " + option);
			options.addOption(option);
			if (cli.cliShortDisable() != null) {
				option = createOption(cli.cliShortDisable(),
						cli.cliLongDisable(), cli.cliDescriptionDisable(),
						false, false);
				options.addOption(option);
			}
		}

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
			String longOption, String description, boolean arg, boolean required) {

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
	private static synchronized void printUsage(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(Messages.getString("PwGenerator.USAGE"), //$NON-NLS-1$
				options);
		LOGGER.fine(Messages.getString("PwGenerator.EXAMPLE") //$NON-NLS-1$
		);
	}

	public static synchronized List<String> process(String[] args,
			List<String> blackList) {
		return process(args, blackList, null);
	}

	public static synchronized List<String> process(String[] args) {
		return process(args, null, null);
	}

	public static synchronized IPasswordPolicy buildPasswordPolicy(
			String[] args, List<String> blackList, List<IPasswordFilter> filters) {

		IPasswordPolicy passwordPolicy = null;

		// The length of the password to be generated
		int passwordLength = DEFAULT_PASSWORD_LENGTH;

		int maxAttempts = DEFAULT_MAX_ATTEMPTS;

		BasicParser parser = new BasicParser();
		try {
			CommandLine commandLine = parser.parse(options, args);

			Random random = null;
			/* random */
			if (commandLine.hasOption(CL_RANDOM)) {
				random = RandomFactory.getInstance().getRandom();
				LOGGER.fine(Messages.getString("PwGenerator.NORMAL_RANDOM")); //$NON-NLS-1$
			} else {
				try {
					random = RandomFactory.getInstance().getSecureRandom();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					random = RandomFactory.getInstance().getRandom();
				} catch (NoSuchProviderException e) {
					e.printStackTrace();
					random = RandomFactory.getInstance().getRandom();
				}
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_SR_ALGORITHM)) {
				String[] data = commandLine.getOptionValues(CL_SR_ALGORITHM);
				if (data.length == 2) {
					try {
						random = RandomFactory.getInstance().getSecureRandom(
								data[0], data[1]);

						LOGGER.fine(Messages.getString("PwGenerator.SEC_ALG") + data[0] //$NON-NLS-1$
								+ Messages.getString("PwGenerator.PROV") + data[1] + Messages.getString("PwGenerator.DOR")); //$NON-NLS-1$ //$NON-NLS-2$
					} catch (NoSuchAlgorithmException e) {
						LOGGER.fine(Messages.getString("PwGenerator.ERROR") + e.getMessage() + Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$ //$NON-NLS-2$
						LOGGER.fine(Messages
								.getString("PwGenerator.DEFAUL_RANDOM")); //$NON-NLS-1$
					} catch (NoSuchProviderException e) {
						LOGGER.fine(Messages.getString("PwGenerator.ERROR") + e.getMessage() + Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$ //$NON-NLS-2$
						LOGGER.fine(Messages
								.getString("PwGenerator.DEFAUL_RANDOM")); //$NON-NLS-1$
					}
				}
			}
			/* ~random~ */

			// max attempts
			if (commandLine.hasOption(CL_MAX_ATTEMPTS)) {
				String sMaxAttempts = commandLine
						.getOptionValue(CL_MAX_ATTEMPTS);
				if (sMaxAttempts != null)
					maxAttempts = Integer.parseInt(sMaxAttempts);
				LOGGER.fine(Messages.getString("PwGenerator.MAX_ATTEMPTS") + maxAttempts); //$NON-NLS-1$
			}

			commandLine = parser.parse(options, args);
			if (commandLine.hasOption(CL_PASSWORD_LENGTH)) {
				String sPasswordLength = commandLine
						.getOptionValue(CL_PASSWORD_LENGTH);
				if (sPasswordLength != null)
					passwordLength = Integer.parseInt(sPasswordLength);
				LOGGER.fine(Messages.getString("PwGenerator.PASSWORD_LENGTH") + passwordLength); //$NON-NLS-1$
			}

			Long passwordFlags = DEFAULT_FLAGS;

			passwordFlags = parseCLIFlags(passwordFlags, commandLine);

			passwordPolicy = new PasswordPolicy(passwordLength, passwordLength,maxAttempts,
					passwordFlags, random);

			if (blackList != null)
				passwordPolicy.getBlackList().addAll(blackList);

			if (filters != null)
				passwordPolicy.getFilters().addAll(filters);

		} catch (ParseException e) {
			e.printStackTrace();
			LOGGER.fine(Messages.getString("PwGenerator.PARAM_ERROR") + e.getLocalizedMessage()); //$NON-NLS-1$
			printUsage(options);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOGGER.fine(Messages.getString("PwGenerator.NUM_FORM_ERROR") + e.getLocalizedMessage()); //$NON-NLS-1$
			printUsage(options);
		}

		return passwordPolicy;
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
	public static synchronized List<String> process(String[] args,
			List<String> blackList, List<IPasswordFilter> filters) {

		int numberOfPasswords = DEFAULT_NUMBER_OF_PASSWORDS;

		LOGGER.fine(Messages.getString("PwGenerator.PASSWORD_GENERATOR") //$NON-NLS-1$
		);

		List<String> passwords = new ArrayList<String>();
		BasicParser parser = new BasicParser();
		try {
			CommandLine commandLine = parser.parse(options, args);

			parser.parse(options, args);
			if (commandLine.hasOption(CL_HELP)) {
				printUsage(options);
				LOGGER.fine(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
				);
				return passwords;
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_SR_PROVIDERS)) {
				Set<String> serviceProviders = RandomFactory.getInstance()
						.getServiceProviderFor(
								IRandomFactory.TYPE_SECURE_RANDOM);
				LOGGER.fine(Messages
						.getString("PwGenerator.SERVICES_PROVIDERS_FOR") //$NON-NLS-1$
						+ IRandomFactory.TYPE_SECURE_RANDOM
						+ Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$
				for (Iterator<String> iter = serviceProviders.iterator(); iter
						.hasNext();) {
					String element = (String) iter.next();
					LOGGER.fine(Messages
							.getString("PwGenerator.SERVICE_PROVIDERS") + element + Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$ //$NON-NLS-2$
					LOGGER.fine(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
					);
					return passwords;
				}
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_PROVIDERS)) {
				LOGGER.fine(Messages.getString("PwGenerator.ALL_SEC_PROVIDERS") //$NON-NLS-1$
						+ IRandomFactory.TYPE_SECURE_RANDOM
						+ Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$
				Provider[] serviceProviders = RandomFactory.getInstance()
						.getProviders();
				for (int i = 0; i < serviceProviders.length; i++) {
					LOGGER.fine(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
					);
					LOGGER.fine(Messages.getString("PwGenerator.PROVIDER") + serviceProviders[i].getName() + Messages.getString("PwGenerator.NEW_LINE") //$NON-NLS-1$ //$NON-NLS-2$
					);
					Set<Provider.Service> services = serviceProviders[i]
							.getServices();
					LOGGER.fine(Messages.getString("PwGenerator.SERVICES") + Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$ //$NON-NLS-2$
					LOGGER.fine(services.toString());
					LOGGER.fine(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
					);
				}
				LOGGER.fine(Messages.getString("PwGenerator.SEPARATOR") //$NON-NLS-1$
				);
				return passwords;
			}

			if (commandLine.hasOption(CL_NUMBER_PASSWORD)) {
				String sNumberOfPasswords = commandLine
						.getOptionValue(CL_NUMBER_PASSWORD);
				if (sNumberOfPasswords != null)
					numberOfPasswords = Integer.parseInt(sNumberOfPasswords);
				LOGGER.fine(Messages.getString("PwGenerator.NUM_PASSWORDS") + numberOfPasswords); //$NON-NLS-1$
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_COLUMN)) {
				doColumns = true;
				LOGGER.fine(Messages.getString("PwGenerator.COLUMNS_ENABLED")); //$NON-NLS-1$
			}

			parser.parse(options, args);
			if (commandLine.hasOption(CL_TERM_WIDTH)) {
				String sTermWidth = commandLine.getOptionValue(CL_TERM_WIDTH);
				if (sTermWidth != null)
					termWidth = Integer.parseInt(sTermWidth);
				LOGGER.fine(Messages.getString("PwGenerator.TERMINAL_LENGTH") + termWidth); //$NON-NLS-1$
			}

			IPasswordPolicy passwordPolicy = buildPasswordPolicy(args,
					blackList, filters);

			List<String> flags = PwGeneratorFlagBuilder
					.evalFlags(passwordPolicy.getFlags());
			for (String flag : flags) {
				LOGGER.fine(flag);
			}

			if (blackList != null)
				passwordPolicy.getBlackList().addAll(blackList);

			if (filters != null)
				passwordPolicy.getFilters().addAll(filters);

			IPwGenerator pg = new PwGenerator(passwordPolicy);

			passwords = pg.generate(numberOfPasswords, 0, null);

		} catch (ParseException e) {
			LOGGER.fine(Messages.getString("PwGenerator.PARAM_ERROR") + e.getLocalizedMessage()); //$NON-NLS-1$
			printUsage(options);
		} catch (NumberFormatException e) {
			LOGGER.fine(Messages.getString("PwGenerator.NUM_FORM_ERROR") + e.getLocalizedMessage()); //$NON-NLS-1$
			printUsage(options);
		}

		return passwords;
	}

	private static Long parseCLIFlags(Long passwordFlags,
			CommandLine commandLine) {

		List<ICliFlag> clis = PwGeneratorFlagBuilder.getCliFlags();
		for (ICliFlag cli : clis) {

			if (commandLine.hasOption(cli.cliShort())) {
				passwordFlags = cli.mask(passwordFlags);
				LOGGER.fine("Enabling flag: " + cli);
			}

			if (cli.cliShortDisable() != null
					&& commandLine.hasOption(cli.cliShortDisable())) {
				passwordFlags = cli.unmask(passwordFlags);
				LOGGER.fine("Disabling flag: " + cli);
			}
		}

		return passwordFlags;
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
	public static void printColumns(List<String> passwords, int termWidth) {

		System.out.println(Messages.getString("PwGenerator.N_SEPARATOR") //$NON-NLS-1$
				);

		for (int i = 0; i < passwords.size(); i++) {
			String password = (String) passwords.get(i);

			int numberOfColumns = termWidth / (password.length() + 1);
			if (numberOfColumns == 0)
				numberOfColumns = 1;

			if (((i % numberOfColumns) == (numberOfColumns - 1))) {

				System.out.print(password
						+ Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$
			} else {
				System.out.print(password + ' ');
			}
		}
		System.out.println("\n" + Messages.getString("PwGenerator.N_SEPARATOR") //$NON-NLS-1$
		);
	}

	/**
	 * Prints a list of passwords to a terminal(System.out)
	 * 
	 * @param passwords
	 *            a list of passwords to be printed
	 */

	public static void print(List<String> passwords) {
		System.out.println(Messages.getString("PwGenerator.N_SEPARATOR") //$NON-NLS-1$
				);

		for (int i = 0; i < passwords.size(); i++) {
			String password = (String) passwords.get(i);

			System.out.print(password
					+ Messages.getString("PwGenerator.NEW_LINE")); //$NON-NLS-1$
		}
		System.out.println(Messages.getString("PwGenerator.N_SEPARATOR") //$NON-NLS-1$
				);
	}

}
