FROM alpine

RUN apk update \
	&& apk add openjdk8-jre

COPY build/libs/order-service-0.0.1-SNAPSHOT.jar order-service.jar

EXPOSE 9192

CMD ["java", "-jar", "order-service.jar"]