# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Add Maintainer Info
LABEL maintainer="david.borgeest@gmail.com"

WORKDIR /app

# Make port 5500 available to the world outside this container
EXPOSE 5500

# The application's jar file
ARG JAR_FILE=target/mag-striker-profiles-0.0.1.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]