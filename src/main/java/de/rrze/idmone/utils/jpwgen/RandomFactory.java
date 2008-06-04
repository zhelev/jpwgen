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
import java.security.SecureRandom;
import java.security.Security;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * A singleton that provides various
 */
public class RandomFactory implements IRandomFactory
{
	// The class instance
	private static RandomFactory instance;

	// The class logger
	private static Logger logger;

	/**
	 * Accessor to the instance
	 * 
	 * @return the singleton instance
	 */
	public static RandomFactory getInstance()
	{
		if (instance == null)
			instance = new RandomFactory();

		return instance;
	}

	/**
	 * Constructor
	 */
	private RandomFactory()
	{
		logger = Logger.getLogger(RandomFactory.class);
	}

	/**
	 * Entry point method, used for debugging mainly
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			RandomFactory factory = new RandomFactory();

			logger.debug(Messages.getString("RandomFactory.TEST_LAG")); //$NON-NLS-1$
			Set<String> algs = factory.getAlgorithms();
			for (Iterator<String> iter = algs.iterator(); iter.hasNext();)
			{
				String element = (String) iter.next();
				logger.debug(Messages.getString("RandomFactory.ALG") + element); //$NON-NLS-1$
			}

			logger.debug(Messages.getString("RandomFactory.TEST_PROV")); //$NON-NLS-1$
			Provider[] prov = factory.getProviders();
			for (int i = 0; i < prov.length; i++)
			{
				logger
						.debug(Messages.getString("RandomFactory.PROV") + prov[i].getName()); //$NON-NLS-1$
				Set<Provider.Service> services = prov[i].getServices();
				logger.debug(Messages.getString("RandomFactory.SERV")); //$NON-NLS-1$
				logger.debug(services);
			}

			logger.debug(Messages.getString("RandomFactory.TEST_SERV")); //$NON-NLS-1$
			Set<String> services = factory
					.getServiceProviderFor(TYPE_SECURE_RANDOM);
			logger
					.debug(Messages.getString("RandomFactory.POSSIBLE") + TYPE_SECURE_RANDOM + Messages.getString("RandomFactory.7") //$NON-NLS-1$ //$NON-NLS-2$
							+ services);

			logger.debug(Messages.getString("RandomFactory.TEST")); //$NON-NLS-1$
			Set<String> serviceProviders = factory.getServiceProviders();
			logger
					.debug(Messages.getString("RandomFactory.GET_SRV_PRV") + serviceProviders); //$NON-NLS-1$

			logger.debug(Messages.getString("RandomFactory.TEST_GET_RANDOM")); //$NON-NLS-1$
			Random random = factory.getRandom();
			logger
					.debug(Messages.getString("RandomFactory.GET_RANDOM") + random.nextDouble()); //$NON-NLS-1$

			logger.debug(Messages.getString("RandomFactory.TEST_SEED")); //$NON-NLS-1$
			random = factory.getRandom(1231l);
			logger
					.debug(Messages
							.getString("RandomFactory.TEST_GET_RANDOM_SEED") + random.nextDouble()); //$NON-NLS-1$

			logger.debug(Messages
					.getString("RandomFactory.TEST_GET_SECURE_RANDOM")); //$NON-NLS-1$
			random = factory.getSecureRandom();
			logger
					.debug(Messages
							.getString("RandomFactory.GET_SECURE_RANDOM") + random.nextDouble()); //$NON-NLS-1$

			logger.debug(Messages
					.getString("RandomFactory.TEST_GET_SECURE_RANDOM_ALG")); //$NON-NLS-1$
			random = factory.getSecureRandom(ALG_DEFAULT);
			logger
					.debug(Messages
							.getString("RandomFactory.GET_SECURE_RANDOM_ALG") + random.nextDouble()); //$NON-NLS-1$

			logger
					.debug(Messages
							.getString("RandomFactory.TEST_GET_SECURE_RANDOM_ALG_PROV")); //$NON-NLS-1$
			factory.getSecureRandom(ALG_DEFAULT, PROVIDER_DEFAULT);
			logger.debug(Messages
					.getString("RandomFactory.GET_SECURE_RANDOM_ALG_PROV") //$NON-NLS-1$
					+ random.nextDouble());

		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} catch (NoSuchProviderException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Create a two pseudo random generator by utilizing the
	 * <em>SecureRandom</em> class provided by SUN. Uses a two step procedure
	 * for feeding the generator seed with two separate SecureRandom instances.
	 * 
	 * @see http://java.sun.com/j2se/1.4.2/docs/api/java/security/SecureRandom.html
	 * 
	 * @param algorithm
	 *            The algorithm used for creating the pseudo random generator
	 * @param provider
	 *            the provider identifier
	 * @return a seeded <em>SecureRandom</em>
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	private SecureRandom initSecureRandom(String algorithm, String provider)
			throws NoSuchAlgorithmException, NoSuchProviderException
	{
		logger.debug("Initializing random with: " + algorithm + " : "
				+ provider);
		if (provider == null)
			provider = PROVIDER_DEFAULT;

		// Create a secure random number generator
		SecureRandom sr = SecureRandom.getInstance(algorithm, provider);

		// Get 1024 random bits
		byte[] bytes = new byte[1024 / 8];
		sr.nextBytes(bytes);

		// Create two secure number generators with the same seed
		int seedByteCount = 10;
		byte[] seed = sr.generateSeed(seedByteCount);

		sr = SecureRandom.getInstance(algorithm, provider);
		sr.setSeed(seed);

		SecureRandom sr2 = SecureRandom.getInstance(algorithm, provider);
		sr2.setSeed(seed);
		return sr2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getAlgorithms()
	 */
	public Set<String> getAlgorithms()
	{
		Set<String> result = new HashSet<String>();

		// All providers
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++)
		{
			// Get services provided by each provider
			Set keys = providers[i].keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();)
			{
				String key = (String) it.next();
				String value = (String) providers[i].get(key);
				result.add(value);
			}
		}
		return result;
	}

	/**
	 * Returns a cleaned up version of the service providers.
	 * 
	 * @return a set of service providers that can be used for SecureRandom
	 *         feed.
	 */
	public Set<String> getServiceProviders()
	{
		Set<String> result = new HashSet<String>();

		// All providers
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++)
		{
			// Get services provided by each provider
			Set keys = providers[i].keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();)
			{
				String key = (String) it.next();
				key = key.split(" ")[0]; //$NON-NLS-1$

				if (key.startsWith(ALG_PARSE_STRING))
				{
					// Strip the alias
					key = key.substring(10);
				}
				int ix = key.indexOf('.');
				result.add(key.substring(0, ix));
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getServiceProviderFor(java.lang.String)
	 */
	public Set<String> getServiceProviderFor(String type)
	{
		Set<String> result = new HashSet<String>();

		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++)
		{
			// Get services provided by each provider
			Set keys = providers[i].keySet();
			for (Iterator it = keys.iterator(); it.hasNext();)
			{
				String key = (String) it.next();
				key = key.split(" ")[0]; //$NON-NLS-1$

				if (key.startsWith(type + ".")) //$NON-NLS-1$
				{
					result.add(key.substring(type.length() + 1));
				} else if (key.startsWith(ALG_PARSE_STRING + type + ".")) //$NON-NLS-1$
				{
					// This is an alias
					result.add(key.substring(type.length() + 11));
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getProviders()
	 */
	public Provider[] getProviders()
	{
		return Security.getProviders();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getRandom()
	 */
	public Random getRandom()
	{
		return new Random(System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getRandom(long)
	 */
	public Random getRandom(long seed)
	{
		return new Random(seed);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getSecureRandom()
	 */
	public Random getSecureRandom() throws NoSuchAlgorithmException,
			NoSuchProviderException
	{
		return initSecureRandom(ALG_SHA1PRNG, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getSecureRandom(java.lang.String)
	 */
	public Random getSecureRandom(String algorithm)
			throws NoSuchAlgorithmException, NoSuchProviderException
	{
		return initSecureRandom(algorithm, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getSecureRandom(java.lang.String,
	 *      java.lang.String)
	 */
	public Random getSecureRandom(String algorithm, String provider)
			throws NoSuchAlgorithmException, NoSuchProviderException
	{
		return initSecureRandom(algorithm, provider);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getLogger()
	 */
	public Logger getLogger()
	{
		return logger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#setLogger(org.apache.log4j.Logger)
	 */
	public void setLogger(Logger logger1)
	{
		logger = logger1;
	}

}
