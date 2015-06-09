package org.anc.lapps.oauth.database;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Keith Suderman
 */
public interface AuthorizationCodeDatabase extends Repository<AuthorizationCode,Long>
{
	List<AuthorizationCode> findAll();
	AuthorizationCode findById(Long id);
	AuthorizationCode findByCode(String code);
	void save(AuthorizationCode code);
//	void update(AuthorizationCode code);
	void delete(AuthorizationCode code);
	void deleteAll();
	void deleteById(Long id);
	void deleteByCode(String code);
}
