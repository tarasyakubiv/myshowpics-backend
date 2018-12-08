# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# The application's jar file
ARG JAR_FILE=target/my-show-pics-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} my-show-pics.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/my-show-pics.jar"]