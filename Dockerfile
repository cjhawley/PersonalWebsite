# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.6.3-jdk-11-slim as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests


FROM openjdk:15-slim
MAINTAINER Chris Hawley <chris@chrishawley.io>

COPY --from=builder /app/target/lib                                  /usr/share/personalwebsite/lib
COPY --from=builder /app/target/personalwebsite.jar                  /usr/share/personalwebsite/personalwebsite.jar
COPY --from=builder /app/target/classes/content/personal_events.json /usr/share/personalwebsite/personal_events.json

CMD ["java", "-jar", "/usr/share/personalwebsite/personalwebsite.jar", "/usr/share/personalwebsite/personal_events.json"]
