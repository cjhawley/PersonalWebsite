package com.cjhawley.personal.persistence.manager.s3;

import com.cjhawley.personal.persistence.dao.PersonalEventDao;
import com.cjhawley.personal.persistence.dao.impl.s3.S3PersonalEventDaoImpl;
import com.cjhawley.personal.persistence.manager.PersistenceManager;

public class S3PersistenceManager implements PersistenceManager {

	private static final PersistenceManager INSTANCE = new S3PersistenceManager();

	public static PersistenceManager getInstance() {
		return INSTANCE;
	}

	private S3PersistenceManager() {
	}

	@Override
	public PersonalEventDao getPersonalEventDao() {
		return S3PersonalEventDaoImpl.getInstance();
	}
}
