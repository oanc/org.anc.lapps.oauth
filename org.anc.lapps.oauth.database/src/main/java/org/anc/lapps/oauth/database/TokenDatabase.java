package org.anc.lapps.oauth.database;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Keith Suderman
 */
public interface TokenDatabase extends Repository<Token, Long>
{
	List<Token> findAll();
	Token findById(Long id);
	Token findByToken(String token);
//	Iterable<Token> findByTimeLessThan(Long time);
	void save(Token token);
	void delete(Token token);
	void deleteAll();
	void deleteById(Long id);
	void deleteByToken(String token);
}
