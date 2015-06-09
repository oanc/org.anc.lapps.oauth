package org.anc.lapps.oauth.data;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Keith Suderman
 */
public interface UserDetailsDatabase extends Repository<UserDetails,String>
{
	List<UserDetails> findAll();
	UserDetails findByUsername(String username);
	void save(UserDetails details);
	void delete(UserDetails details);
	void deleteByUsername(String username);
}
