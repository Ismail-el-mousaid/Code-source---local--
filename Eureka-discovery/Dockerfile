FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/Eureka-discovery-0.0.1-SNAPSHOT.jar /eureka.jar
CMD ["java", "-jar", "/eureka.jar", "--spring.profiles.active=prod"]
EXPOSE 8761

