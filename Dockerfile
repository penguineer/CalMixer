#
# Build stage
#
FROM maven:3.8-openjdk-17-slim AS build

COPY .mvn /home/app/.mvn
COPY pom.xml /home/app
COPY .git /home/app/.git

RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim

COPY --from=build /home/app/target/CalMixer-*.jar /usr/local/lib/CalMixer.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/CalMixer.jar"]
