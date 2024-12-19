# Use OpenJDK 21 as the base image
FROM openjdk:21-slim

# Install necessary tools
RUN apt-get update && apt-get install -y unzip

# Set the working directory in the container
WORKDIR /app

# Create a directory for Gradle
RUN mkdir -p /gradle-cache

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src ./src

# Set Gradle home to the local directory
ENV GRADLE_USER_HOME=/gradle-cache

# Copy the local Gradle distribution to the Gradle cache
COPY gradle/wrapper/gradle-8.11-bin.zip /gradle-cache/

# Unzip the Gradle distribution in the Gradle cache directory
RUN unzip /gradle-cache/gradle-8.11-bin.zip -d /gradle-cache && \
rm /gradle-cache/gradle-8.11-bin.zip

# Make the Gradle wrapper executable
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew clean bootJar --no-daemon

# Print out the contents of the libs directory (for debugging)
RUN ls -l /app/build/libs

# Explicitly copy the JAR file with the specific name
RUN cp /app/build/libs/hotel-management-system-backend.jar /app/hotel-management-system-backend.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/hotel-management-system-backend.jar"]