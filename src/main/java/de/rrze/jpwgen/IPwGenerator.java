package de.rrze.jpwgen;

import java.util.List;
import java.util.Map;

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

	public abstract Map<String, Map<String, String>> validate(String password);

	public abstract List<String> generate(int passwordCount,
			int iterationsCount, IProgressListener progressListener);

	/**
	 * This method logs some general info about the given settings and tries to
	 * generate passwords with the given flags and given length. The method
	 * return <em>null</em> if it does not manage to create a suitable password
	 * within the <em>MAX_ATTEMPTS</em>.
	 * 
	 * @return a suitable password or <em>null</em> if such could not be
	 *         generated
	 */

	public abstract String generate();

}