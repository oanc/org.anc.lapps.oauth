package org.anc.lapps.oauth.security;

import org.junit.*;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * @author Keith Suderman
 */
public class SecurityManagerTests
{
	@Test
	public void testSalt() throws SecurityException
	{
		String salt1 = SecurityManager.salt();
		String salt2 = SecurityManager.salt();
		assertFalse(salt1.equals(salt2));
		assertTrue("Length: " + salt1.length(), salt1.length() == 16);

		salt1 = SecurityManager.salt(16);
		salt2 = SecurityManager.salt(16);
		assertFalse(salt1.equals(salt2));
		assertTrue(salt1, salt1.length() == 32);
	}

	@Test
	public void testHash() throws SecurityException
	{
		String expected = "password";
		String encoded = SecurityManager.hash(expected);
		assertFalse(expected.equals(encoded));
		String again = SecurityManager.hash(expected);
		assertTrue(again.equals(encoded));
	}

	@Test
	public void testSaltedHash() throws SecurityException
	{
		String password = "password";
		String salt = SecurityManager.salt();
		String encoded = SecurityManager.hash(password);
		String salted = SecurityManager.hash(password, salt);
		assertFalse(encoded.equals(salted));
		encoded = SecurityManager.hash(salt + password);
		assertTrue(encoded.equals(salted));
	}

	@Test
	public void testToHex()
	{
		byte[] bytes = new byte[2];
		bytes[0] = 0;
		bytes[1] = 0;
		System.out.println(toHex(bytes));
	}
	public static String toHex(byte[] array)
	{
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if(paddingLength > 0)
		{
			System.out.println("Padding output " + paddingLength);
			return String.format("%0" + paddingLength + "d", 0) + hex;
		}
		else
			return hex;
	}
}
