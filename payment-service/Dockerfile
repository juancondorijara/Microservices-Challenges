FROM alpine

RUN apk update \
	&& apk add openjdk8-jre

COPY build/libs/payment-service-0.0.1-SNAPSHOT.jar payment-service.jar

EXPOSE 9191

CMD ["java", "-jar", "payment-service.jar"]