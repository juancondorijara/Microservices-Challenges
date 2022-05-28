FROM alpine:latest

ENV JAVA_HOME="/usr/lib/jvm/default-jvm/"
RUN apk add openjdk11

ENV PATH=$PATH:${JAVA_HOME}/bin

CMD ["jshell"]

COPY target/Ms-User-0.0.1-SNAPSHOT.jar /appuser.jar

ENTRYPOINT ["java", "-jar", "/appuser.jar"]

EXPOSE 8084