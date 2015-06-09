package org.anc.lapps.oauth.database;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Keith Suderman
 */
@Entity
public class Client
{
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String clientId;

	@Column(nullable = false)
	private String clientSecret;

	public Client()
	{

	}

	public Client(String clientId, String clientSecret)
	{
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public Client(String name, String clientId, String clientSecret)
	{
		this.name = name;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public Long getId() { return id; }

	public String getName()
	{
		return name;
	}

	public String getClientId() { return clientId; }
	public String getClientSecret() { return clientSecret; }
}
