package org.anc.lapps.oauth.database;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Keith Suderman
 */
public interface UserDatabase extends Repository<User,Long>
{
	List<User> findAll();
	User findById(Long id);
	User findByName(String name);
	void save(User user);
	void delete(User user);
	void deleteAll();
	void deleteById(Long id);
	void deleteByName(String nane);
}
