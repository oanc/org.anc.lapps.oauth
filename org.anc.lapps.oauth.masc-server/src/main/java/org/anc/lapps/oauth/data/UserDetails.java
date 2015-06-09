package org.anc.lapps.oauth.data;

import javax.persistence.*;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @author Keith Suderman
 */
@Entity
@Table(name="user_details")
public class UserDetails
{
	@Id
	protected String username;
	@Column
	protected String email;
	@Column
	protected Date createdDate;
	@Column
	protected Date lastUpdated;

	public UserDetails()
	{

	}
}
