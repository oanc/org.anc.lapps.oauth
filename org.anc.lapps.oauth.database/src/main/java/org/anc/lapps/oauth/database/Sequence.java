package org.anc.lapps.oauth.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Keith Suderman
 */
@Entity
public class Sequence
{
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Long value;

	public Sequence()
	{
		this.name = "Unknown";
		this.value = 0L;
	}

	public Sequence(String name)
	{
		this.name = name;
		this.value = 0L;
	}

	public Sequence(String name, Long value)
	{
		this.name = name;
		this.value = value;
	}

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public Long getValue()
	{
		return value;
	}

	public void setValue(Long value)
	{
		this.value = value;
	}

	public Long increment()
	{
		long result = value;
		value += 1;
		return result;
	}
}
