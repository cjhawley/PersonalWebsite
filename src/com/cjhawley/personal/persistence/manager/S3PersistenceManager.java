package com.cjhawley.personal.persistence.manager;

import com.cjhawley.personal.persistence.dao.PersonalEventDao;
import com.cjhawley.personal.persistence.dao.S3PersonalEventDaoImpl;

/**
 * Use S3 as a persistence layer
 * 
 * @author cjhawley
 *
 */
public class S3PersistenceManager implements PersistenceManager {
	private static final PersistenceManager INSTANCE = new S3PersistenceManager();

	public static PersistenceManager getInstance() {
		return INSTANCE;
	}

	private S3PersistenceManager() {}

	@Override
	public PersonalEventDao getPersonalEventDao() {
		return S3PersonalEventDaoImpl.getInstance();
	}
}
