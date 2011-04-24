package de.rrze.jpwgen;

/**
 * Keeps track of the password generation process
 * */
public interface IProgressListener
{

	/**
	 * @param current - index of the currently generated password
	 * @param goal - how many passwords are supposed to be generated
	 * @return - should the generation process be aborted
	 */
	public boolean progress(int current, int goal);
}