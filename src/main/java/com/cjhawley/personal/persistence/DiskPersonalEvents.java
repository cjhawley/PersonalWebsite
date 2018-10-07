package com.cjhawley.personal.persistence;

import com.cjhawley.personal.model.PersonalEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiskPersonalEvents implements PersonalEventsLoader {

  private final List<PersonalEvent> personalEvents;

  public DiskPersonalEvents(ObjectMapper objectMapper, File personalEventsFile) throws IOException {
    personalEvents = sort(objectMapper.readValue(personalEventsFile, new TypeReference<List<PersonalEvent>>() {}));
  }

  public List<PersonalEvent> getPersonalEvents() {
    return new ArrayList<>(personalEvents);
  }

  private List<PersonalEvent> sort(List<PersonalEvent> personalEvents) {
    return personalEvents.stream().sorted((p1, p2) -> p2.date().compareTo(p1.date()))
        .collect(Collectors.toList());
  }
}
