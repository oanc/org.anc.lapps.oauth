package org.anc.lapps.oauth.config;

/**
 * @author Keith Suderman
 */
public class ConfigurationFactory
{
	private ConfigurationFactory()
	{

	}

	public static Configuration getConfiguration()
	{
		return new LdcConfiguration();
//		return new LocalHostConfiguration();
//		String property = System.getProperty("OAUTH_CONFIG");
//		if (property == null)
//		{
//			property = System.getenv("HOSTNAME");
//			if (property == null)
//			{
//				return new LocalHostConfiguration();
//			}
//		}
//		if ("picard".equals(property))
//		{
//			System.out.println("Using localhost configuration.");
//			return new LocalHostConfiguration();
//		}
//		if ("grid".equals(property))
//		{
//			System.out.println("Using Grid configuration.");
//			return new GridConfiguration();
//		}
//		System.out.println("Using LDC configuration.");
//		return new LdcConfiguration();
	}
}
