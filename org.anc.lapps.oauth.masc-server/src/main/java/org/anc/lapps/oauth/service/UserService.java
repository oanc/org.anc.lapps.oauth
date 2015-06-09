package org.anc.lapps.oauth.service;

import org.anc.lapps.oauth.data.User;
import org.anc.lapps.oauth.data.UserDatabase;
import org.anc.lapps.oauth.data.UserRole;
import org.anc.lapps.oauth.data.UserRoleDatabase;
import org.anc.lapps.oauth.web.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Keith Suderman
 */
@Service
public class UserService
{
	@Autowired
	protected UserDatabase userDatabase;
	@Autowired
	protected UserRoleDatabase userRoleDatabase;

	public List<UserData> findAll()
	{
		List<User> users = userDatabase.findAll();
		List<UserData> result = new ArrayList<>(users.size());
		for (User user : users)
		{
			UserData data = new UserData();
			data.setUsername(user.getUsername());
			data.setPassword1(user.getPassword());
			List<UserRole> roles = userRoleDatabase.findByUsername(user.getUsername());
			for (UserRole role : roles)
			{
				data.addRole(role.getAuthority());
			}
			data.build();
			result.add(data);
		}
		return result;
	}
	/*
	@Autowired
	protected DataSource userDataSource;

	public UserService()
	{

	}

	public List<UserData> findAll() //throws SQLException
	{
		List<UserData> users = new ArrayList<>();
		try
		{
			ResultSet result = query("select * from users");
			while (result.next())
			{
				UserData user = new UserData();
				user.setUsername(result.getString("username"));
				user.setPassword1(result.getString("password"));
				users.add(user);
			}
			return users;
		}
		catch (SQLException e)
		{
			//TODO the exception should be logged.
		}
		return users;
	}

	public UserData findUser(String username) //throws SQLException
	{
		try
		{
			ResultSet result = query("select * from users where username='" + username + "'");
			if (result.next())
			{
				UserData data = new UserData();
				data.setUsername(result.getString("username"));
				data.setPassword1(result.getString("password"));
				return data;
			}
		}
		catch (SQLException e)
		{
			// TODO the exception should be logged.
		}
		return null;
	}

	public int updatePassword(String username, String password) //throws SQLException
	{
		String template = "update users set password='%s' where username='%s'";
		String sql = String.format(template, password, username);
		try
		{
			return update(sql);
		}
		catch (SQLException e)
		{
			// TODO the exception should be logged.
		}
		return 0;
	}

	public boolean checkPassword(String username, String password)
	{
		String sql = "select password from users where username = '" + username + "'";
		try
		{
			ResultSet result = query(sql);
			if (result.next())
			{
				String actualPassword = result.getString(0);
				return actualPassword.equals(password);
			}
		}
		catch (SQLException e)
		{
			// TODO this exception should be logged.
		}
		return false;
	}

	private ResultSet query(String sql)  throws SQLException
	{
		Connection connection = null;
		ResultSet result = null;
		try
		{
			connection = userDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			result = statement.executeQuery();
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
		}
		return result;
	}

	private int update(String sql) throws SQLException
	{
		Connection connection = null;
		int result = 0;
		try
		{
			connection = userDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			result = statement.executeUpdate();
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
		}
		return result;
	}
	*/
}
