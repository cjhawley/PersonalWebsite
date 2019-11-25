package com.cjhawley.personal.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.norberg.automatter.jackson.AutoMatterModule;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class DiskPersonalEventsTest {

  private static final ObjectMapper MAPPER = new ObjectMapper()
      .registerModule(new AutoMatterModule());


  @Test
  void testReadEvents() throws IOException {
    final File events = new File(this.getClass().getClassLoader().getResource("content/personal_events.json").getFile());

    DiskPersonalEvents diskPersonalEvents = new DiskPersonalEvents(MAPPER, events);

    assertThat(diskPersonalEvents.getPersonalEvents(), hasSize(5));
  }
}