package org.anc.lapps.oauth.database;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Keith Suderman
 */
public interface SequenceDatabase extends Repository<Sequence,Long>
{
	List<Sequence> findAll();
	Sequence findById(Long id);
	Sequence findByName(String name);
	void delete(Sequence sequence);
	void save(Sequence sequence);
//	void update(Sequence sequence);
}
