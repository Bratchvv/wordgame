FROM openjdk:16-alpine3.13

MAINTAINER Wordgame Team
VOLUME /tmp
ADD ./nvpsv-oir-structure.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
EXPOSE 8080
