package com.cjhawley.personal.persistence.dao;

import java.util.List;

import com.cjhawley.personal.model.PersonalEvent;

/**
 * interface for working with personal events
 * 
 * @author cjhawley
 *
 */
public interface PersonalEventDao {
	List<PersonalEvent> getPersonalEvents();
}
