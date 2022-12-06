
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/Gateway-service-0.0.1-SNAPSHOT.jar /gateway.jar
CMD ["java", "-jar", "/gateway.jar", "--spring.profiles.active=prod"]
EXPOSE 8888



