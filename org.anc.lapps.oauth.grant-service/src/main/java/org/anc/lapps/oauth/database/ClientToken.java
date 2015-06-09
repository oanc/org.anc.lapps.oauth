package org.anc.lapps.oauth.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Records Tokens assigned to clients.
 *
 * @author Keith Suderman
 */
@Entity
public class ClientToken
{
	@Id
	@GeneratedValue
	Long id;

	@Column(nullable = false)
	Long token;

	@Column(nullable = false)
	Long client;

	public ClientToken()
	{

	}

	public ClientToken(Long token, Long client)
	{
		this.token = token;
		this.client = client;
	}

	public Long getId()
	{
		return id;
	}

	public Long getToken()
	{
		return token;
	}

	public Long getClient()
	{
		return client;
	}
}
