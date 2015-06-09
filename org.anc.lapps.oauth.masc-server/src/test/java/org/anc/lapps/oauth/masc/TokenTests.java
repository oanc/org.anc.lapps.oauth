package org.anc.lapps.oauth.masc;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Keith Suderman
 */
public class TokenTests
{
	public TokenTests()
	{

	}

	@Test
	public void testToken() throws OAuthSystemException
	{
		OAuthIssuer oauthIssuerImpl =
				  new OAuthIssuerImpl(new MD5Generator());
		String token = oauthIssuerImpl.accessToken();
		String refresh = oauthIssuerImpl.refreshToken();
		OAuthResponse response = OAuthASResponse
				  .tokenResponse(HttpServletResponse.SC_OK)
				  .setAccessToken(token)
				  .setRefreshToken(refresh)
				  .setExpiresIn("3600")
				  .buildJSONMessage();
		Map<String,String> headers = response.getHeaders();
		for (String key : headers.keySet())
		{
			System.out.println(key + "=" + headers.get(key));
		}
		System.out.println(response.getBody());
	}
}
