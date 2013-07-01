package de.rrze.jpwgen;

import java.util.List;
import java.util.Random;


public interface IPwGenerator {

	/**
	 * Adds a password filter to the registry
	 * 
	 * @param filter
	 *            the filter instance to be registered
	 * @return the registered instance
	 */

	public abstract IPasswordFilter addFilter(IPasswordFilter filter);

	/**
	 * Removes a filter from the registry by instance search
	 * 
	 * @param filter
	 *            the instance of the filter
	 * @return the removed instance
	 */

	public abstract IPasswordFilter removeFilter(IPasswordFilter filter);

	/**
	 * Removes a filter from the registry by identifier search
	 * 
	 * @param id
	 *            the identifier of the filter
	 * @return the removed instance
	 */

	public abstract IPasswordFilter removeFilter(String id);

	public abstract List<String> validate(int passwordFlags, String password);

	public abstract List<String> valiadteWithProcessingFilter(int flags,
			String password);

	public abstract Boolean valiadteWithBlacklistFilter(int flags,
			String password);

	public abstract List<String> generatePasswords(int length, int number,
			int maxAttempts, int flags, Random random,
			IProgressListener progressListener);

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

	public abstract String generate(int passwordLength, int passwordFlags,
			int maxAttempts, Random random);

	/**
	 * Returns the instance of the default blacklist filter registered with the
	 * PwGenerator
	 * 
	 * @return the default blacklist filter
	 */
	public abstract IPasswordFilter getDefaultBlacklistFilter();

	/**
	 * Returns the instance of the default regular expression filter registered
	 * with the PwGenerator
	 * 
	 * @return the default regular expression filter
	 */

	public abstract IPasswordFilter getDefaultProcessingFilter();

}