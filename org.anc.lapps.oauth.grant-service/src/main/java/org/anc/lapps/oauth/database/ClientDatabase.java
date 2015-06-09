package org.anc.lapps.oauth.database;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Keith Suderman
 */
public interface ClientDatabase extends Repository<Client,Long>
{
	List<Client> findAll();
	Client findById(Long id);
	Client findByClientId(String clientId);
	void save(Client client);
	void delete(Client client);
	Long deleteAll();
}
