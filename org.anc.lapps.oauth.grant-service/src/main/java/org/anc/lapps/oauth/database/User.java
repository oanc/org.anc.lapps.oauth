package org.anc.lapps.oauth.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Keith Suderman
 */
@Entity
public class User
{
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String password;

	public User()
	{
	}

	public User(String name, String password)
	{
		this.name = name;
		this.password = password;
	}

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}
	public String getPassword()
	{
		return password;
	}
}
