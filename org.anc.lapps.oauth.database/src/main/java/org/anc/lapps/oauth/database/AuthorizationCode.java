package org.anc.lapps.oauth.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Keith Suderman
 */
@Entity
public class AuthorizationCode
{
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String code;

	@Column(nullable=false)
	private String issuedTo;

	public AuthorizationCode()
	{

	}

	public AuthorizationCode(String code)
	{
		this.code = code;
		this.issuedTo = "Unknown";
	}

	public AuthorizationCode(String code, String issuedTo)
	{
		this.code = code;
		this.issuedTo = issuedTo;
	}
	public Long getId()
	{
		return id;
	}

	public String getCode()
	{
		return code;
	}
	public String getIssuedTo() { return issuedTo; }
}
