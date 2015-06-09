package org.anc.lapps.oauth.security;

import static org.junit.Assert.*;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Keith Suderman
 */
public class PasswordHashTests
{
	@Test
	public void testHash() throws SecurityException
	{
		String password = "password";
		String hash1 = PasswordHash.hash(password);
		String hash2 = PasswordHash.hash(password);
		assertNotEquals(hash1, hash2);
		assertTrue(PasswordHash.validate(password, hash1));
	}
}
