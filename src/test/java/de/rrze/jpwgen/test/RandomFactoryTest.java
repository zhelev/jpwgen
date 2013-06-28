package de.rrze.jpwgen.test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.jpwgen.utils.Messages;
import de.rrze.jpwgen.utils.RandomFactory;

public class RandomFactoryTest
{

	@BeforeClass
	public void setUp()
	{
		System.out.println("======================== "
				+ this.getClass().getSimpleName()
				+ " ================================");
	}

	@AfterClass
	public void finish()
	{
		System.out.println("======================== "
				+ this.getClass().getSimpleName()
				+ " ================================");
	}

	@Test(groups =
	{ "default" })
	public void test()
	{
		try
		{
			System.out.println("Start: " + this.getClass().getSimpleName());
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			RandomFactory factory = RandomFactory.getInstance();

			System.out.println(Messages.getString("RandomFactory.TEST_LAG")); //$NON-NLS-1$
			Set<String> algs = factory.getAlgorithms();
			for (Iterator<String> iter = algs.iterator(); iter.hasNext();)
			{
				String element = (String) iter.next();
				System.out
						.println(Messages.getString("RandomFactory.ALG") + element); //$NON-NLS-1$
			}

			System.out.println("======================== "
					+ this.getClass().getSimpleName()
					+ " ================================");

			System.out.println(Messages.getString("RandomFactory.TEST_PROV")); //$NON-NLS-1$
			Provider[] prov = factory.getProviders();
			for (int i = 0; i < prov.length; i++)
			{
				System.out
						.println(Messages.getString("RandomFactory.PROV") + prov[i].getName()); //$NON-NLS-1$
				Set<Provider.Service> services = prov[i].getServices();
				System.out.println(Messages.getString("RandomFactory.SERV")); //$NON-NLS-1$
				System.out.println(services);
			}

			System.out.println("======================== "
					+ this.getClass().getSimpleName()
					+ " ================================");

			System.out.println(Messages.getString("RandomFactory.TEST_SERV")); //$NON-NLS-1$
			Set<String> services = factory
					.getServiceProviderFor(RandomFactory.TYPE_SECURE_RANDOM);
			System.out
					.println(Messages.getString("RandomFactory.POSSIBLE") + RandomFactory.TYPE_SECURE_RANDOM + Messages.getString("RandomFactory.7") //$NON-NLS-1$ //$NON-NLS-2$
							+ services);

			System.out.println("======================== "
					+ this.getClass().getSimpleName()
					+ " ================================");

			System.out.println(Messages.getString("RandomFactory.TEST")); //$NON-NLS-1$
			Set<String> serviceProviders = factory.getServiceProviders();
			System.out
					.println(Messages.getString("RandomFactory.GET_SRV_PRV") + serviceProviders); //$NON-NLS-1$

			System.out.println("======================== "
					+ this.getClass().getSimpleName()
					+ " ================================");

			System.out.println(Messages
					.getString("RandomFactory.TEST_GET_RANDOM")); //$NON-NLS-1$
			Random random = factory.getRandom();
			System.out
					.println(Messages.getString("RandomFactory.GET_RANDOM") + random.nextDouble()); //$NON-NLS-1$

			System.out.println("======================== "
					+ this.getClass().getSimpleName()
					+ " ================================");

			System.out.println(Messages.getString("RandomFactory.TEST_SEED")); //$NON-NLS-1$
			random = factory.getRandom(1231l);
			System.out
					.println(Messages
							.getString("RandomFactory.TEST_GET_RANDOM_SEED") + random.nextDouble()); //$NON-NLS-1$

			System.out.println("======================== "
					+ this.getClass().getSimpleName()
					+ " ================================");

			System.out.println(Messages
					.getString("RandomFactory.TEST_GET_SECURE_RANDOM")); //$NON-NLS-1$
			random = factory.getSecureRandom();
			System.out
					.println(Messages
							.getString("RandomFactory.GET_SECURE_RANDOM") + random.nextDouble()); //$NON-NLS-1$

			System.out.println("======================== "
					+ this.getClass().getSimpleName()
					+ " ================================");

			System.out.println(Messages
					.getString("RandomFactory.TEST_GET_SECURE_RANDOM_ALG")); //$NON-NLS-1$
			random = factory.getSecureRandom(RandomFactory.ALG_DEFAULT);
			System.out
					.println(Messages
							.getString("RandomFactory.GET_SECURE_RANDOM_ALG") + random.nextDouble()); //$NON-NLS-1$

			System.out.println("======================== "
					+ this.getClass().getSimpleName()
					+ " ================================");

			System.out
					.println(Messages
							.getString("RandomFactory.TEST_GET_SECURE_RANDOM_ALG_PROV")); //$NON-NLS-1$
			factory.getSecureRandom(RandomFactory.ALG_DEFAULT,
					RandomFactory.PROVIDER_DEFAULT);
			System.out.println(Messages
					.getString("RandomFactory.GET_SECURE_RANDOM_ALG_PROV") //$NON-NLS-1$
					+ random.nextDouble());

			System.out.println("======================== "
					+ this.getClass().getSimpleName()
					+ " ================================");

			stopWatch.stop();
			System.out.println("\n" + this.getClass().getSimpleName()
					+ " Runtime:" + stopWatch.toString() + "\n");

		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} catch (NoSuchProviderException e)
		{
			e.printStackTrace();
		}

	}

}
