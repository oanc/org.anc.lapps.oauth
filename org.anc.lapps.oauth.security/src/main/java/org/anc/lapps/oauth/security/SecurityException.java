package org.anc.lapps.oauth.security;

/**
 * @author Keith Suderman
 */
public class SecurityException extends Exception
{
	public SecurityException()
	{
	}

	public SecurityException(String message)
	{
		super(message);
	}

	public SecurityException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public SecurityException(Throwable cause)
	{
		super(cause);
	}

	public SecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
