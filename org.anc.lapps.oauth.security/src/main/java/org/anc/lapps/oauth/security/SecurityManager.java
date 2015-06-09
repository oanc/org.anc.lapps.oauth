package org.anc.lapps.oauth.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Keith Suderman
 */
public class SecurityManager
{
	private static final SecureRandom rng = new SecureRandom();

	private SecurityManager()
	{

	}
	private static final SecureRandom random = new SecureRandom();
	public static final int SALT_SIZE = 8;

	public static String salt()
	{
		return salt(SALT_SIZE);
	}

	public static String salt(int size)
	{
		byte[] salt = new byte[size];
		random.nextBytes(salt);
		return new BigInteger(1, salt).toString(16);
	}

	public static String hash(String password, String salt)
	{
		return hash(salt + password);
	}

	public static String hash(String password)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] digest = md.digest(password.getBytes("US-ASCII"));
			return String.format("%0128x", new BigInteger(1, digest));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return salt();
		}
	}

	public static boolean checkpw(String saved, String salt, String candidate)
	{
		String hashed = hash(salt + candidate);
		return constantCompare(saved, hashed);
	}

	/**
	 * A constant time string comparison.
	 *
	 * To prevent timing attacks the time to compare two strings CAN NOT
	 * depend on where the comparison failed, that is, the comparison can
	 * not short-circuit.
	 */
	protected static boolean constantCompare(String s1, String s2)
	{
		boolean result = s1.length() == s2.length();
		char[] a1 = s1.toCharArray();
		char[] a2 = s2.toCharArray();
		int length = Math.max(a1.length, a2.length);
		for (int i = 0; i < length; ++i)
		{
			// At least one of these will be set to "not negative one"
			char c1 = (char) -1;
			char c2 = (char) -1;

			if (i < a1.length)
			{
				c1 = a1[i];
			}
			if (i < a2.length)
			{
				c2 = a2[i];
			}

			// Now check if the characters are the same.
			if (c1 != c2)
			{
				result = false;
			}
		}
		return result;
	}


	/*
	public static String getSalt() throws SecurityException
	{
		return getSalt(8);
	}

	public static String getSalt(int size) throws SecurityException
	{
		byte[] bytes = new byte[size];
		rng.nextBytes(bytes);
		return encode(bytes);
	}

	public static String encode(byte[] input) throws SecurityException
	{
		try
		{
			int size = input.length + input.length;
			String format = "%0" + (size) + "x";
			System.out.println(format);
			MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
			return String.format(format, new BigInteger(1, sha512.digest(input)));
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new SecurityException(e);
		}
	}
	*/
}
