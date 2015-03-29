package com.cjhawley.personal.persistence.local.dao.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.cjhawley.personal.model.PersonalEvent;
import com.cjhawley.personal.persistence.dao.PersonalEventDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * This class will load (and eventually save) personal events from disk. 
 * 
 * @author cjhawley
 *
 */
public class LocalPersonalEventDaoImpl implements PersonalEventDao {
	private static final PersonalEventDao INSTANCE = new LocalPersonalEventDaoImpl();
		
	public static PersonalEventDao getInstance() {
		return INSTANCE;
	}
	
	private LocalPersonalEventDaoImpl() {}
	
	@Override
	public List<PersonalEvent> getPersonalEvents() {
		return loadPersonalEventsFromDisk();
	}
	
	private List<PersonalEvent> loadPersonalEventsFromDisk() {
		
		ObjectMapper mapper = new ObjectMapper();
		List<PersonalEvent> personalEvents = new ArrayList<PersonalEvent>();
		try {
			InputStream stream = this.getClass().getResourceAsStream("/../../resources/content/personalevents.json");
			byte[] jsonBytes = IOUtils.toByteArray(stream);
			personalEvents = mapper.readValue(jsonBytes, TypeFactory.defaultInstance().constructCollectionType(List.class, PersonalEvent.class));
		} catch (Exception e) {
			// TODO - propper logging...
			e.printStackTrace();
		}
		return personalEvents;
	}

}
