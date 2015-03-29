package com.cjhawley.personal.persistence.local;

import com.cjhawley.personal.persistence.PersistenceManager;
import com.cjhawley.personal.persistence.dao.PersonalEventDao;
import com.cjhawley.personal.persistence.local.dao.impl.LocalPersonalEventDaoImpl;

/**
 * This class will manage all of the DAO which interact with the file system.
 * @author cjhawley
 *
 */
public class LocalPersistenceManager implements PersistenceManager {
	private static final PersistenceManager INSTANCE = new LocalPersistenceManager();
	
	public static PersistenceManager getInstance() {
		return INSTANCE;
	}
	
	private LocalPersistenceManager() {}

	@Override
	public PersonalEventDao getPersonalEventDao() {
		return LocalPersonalEventDaoImpl.getInstance();
	}
}
