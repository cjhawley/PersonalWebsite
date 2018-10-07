FROM openjdk:11-slim
MAINTAINER Chris Hawley <chris@chrishawley.io>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/personalwebsite/personalwebsite.jar", "/usr/share/personalwebsite/personal_events.json"]

ADD target/lib          /usr/share/personalwebsite/lib
ADD target/personalwebsite.jar /usr/share/personalwebsite/personalwebsite.jar
ADD target/classes/content/personal_events.json /usr/share/personalwebsite/personal_events.json
