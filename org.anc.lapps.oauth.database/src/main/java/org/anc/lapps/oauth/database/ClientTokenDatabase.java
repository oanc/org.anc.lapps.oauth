package org.anc.lapps.oauth.database;


import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Keith Suderman
 */
public interface ClientTokenDatabase extends Repository<ClientToken,Long>
{
	List<ClientToken> findAll();
	ClientToken findById(Long id);
	ClientToken findByToken(Long tokenId);
	ClientToken findByClient(Long clientId);
	void save(ClientToken clientToken);
	void delete(ClientToken clientToken);
	void deleteAll();
	void deleteById(Long id);
	void deleteByToken(Long token);
	void deleteByClient(Long client);
}
