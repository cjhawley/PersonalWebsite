package com.cjhawley.personal.persistence;

import com.cjhawley.personal.model.PersonalEvent;
import java.util.List;

public interface PersonalEventsLoader {
  List<PersonalEvent> getPersonalEvents();
}
