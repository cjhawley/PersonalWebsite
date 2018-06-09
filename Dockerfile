FROM openjdk:10-slim


RUN apt update && apt upgrade -y && \
    apt install maven -y && \
    mkdir -p /cjhawleywebsite

COPY . /cjhawleywebsite/

WORKDIR /cjhawleywebsite

RUN mvn clean package

CMD ["java", "-jar", "./target/CjhawleyPersonalWebsite-0.0.18-SNAPSHOT.jar", ">", "/dev/null", "2>", "/dev/null", "<", "/dev/null", "&"]


