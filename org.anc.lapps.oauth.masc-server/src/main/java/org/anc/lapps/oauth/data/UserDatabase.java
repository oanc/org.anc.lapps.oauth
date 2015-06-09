package org.anc.lapps.oauth.data;

import org.springframework.data.repository.Repository;
//import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author Keith Suderman
 */
public interface UserDatabase extends Repository<User,String>
{
	List<User> findAll();
	User findByUsername(String username);
	User save(User user);
	void delete(User user);
	void deleteByUsername(String username);
}
