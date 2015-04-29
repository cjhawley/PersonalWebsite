package com.cjhawley.personal.persistence.manager;

import com.cjhawley.personal.persistence.dao.PersonalEventDao;

/**
 * Interface for working with any persistence manager that may be created.
 *
 * @author cjhawley
 *
 */
public interface PersistenceManager {
	public PersonalEventDao getPersonalEventDao();
}
