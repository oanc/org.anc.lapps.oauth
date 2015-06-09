package org.anc.lapps.oauth.data;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Keith Suderman
 */
public interface UserRoleDatabase extends Repository<UserRole, String>
{
	List<UserRole> findAll();
	List<UserRole> findByUsername(String username);
	List<UserRole> findByAuthority(String authority);
	void save(UserRole userRole);
	void delete(UserRole userRole);
	void deleteByUsername(String username);
	void deleteByAuthority(String authority);

}
