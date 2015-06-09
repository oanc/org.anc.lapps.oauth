package org.anc.lapps.oauth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Keith Suderman
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST,reason = "Unable to authroize.")
public class AuthorizationException
{
	public AuthorizationException()
	{

	}
}
