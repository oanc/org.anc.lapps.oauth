package org.anc.lapps.oauth.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Keith Suderman
 */
@Entity
public class Token
{
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private String refresh;

	@Column(nullable = false)
	private Long time;

//	@Column(nullable = false)
//	private String clientId;

	public Token()
	{
		time = System.currentTimeMillis();
	}

	public Token(Long time, String token)
	{
		this.token = token;
//		this.clientId = clientId;
		this.time = System.currentTimeMillis() + (time * 1000);
	}

	public Token(Long time, String token, String refresh)
	{
//		this.clientId = clientId;
		this.token = token;
		this.refresh = refresh;
		this.time = System.currentTimeMillis() + (time * 1000);
	}

	public Long getId() { return id; }
	public String getRefresh() { return refresh; }
	public String getToken()
	{
		return token;
	}
//	public String getClientId() { return clientId; }
	public Long getTime()
	{
		return time;
	}
}
