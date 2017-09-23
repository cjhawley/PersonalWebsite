FROM openjdk:8u131-jdk-alpine

RUN apk --update add maven

RUN mkdir -p /cjhawleywebsite

COPY . /cjhawleywebsite/

WORKDIR /cjhawleywebsite

RUN rm -rf target

RUN mvn clean package


CMD ["java", "-jar", "./target/CjhawleyPersonalWebsite-0.0.18-SNAPSHOT.jar", ">", "/dev/null", "2>", "/dev/null", "<", "/dev/null", "&"]


