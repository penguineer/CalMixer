#
# This dockerfile expects a compiled artifact in the target folder.
# Call "mvn clean package" first!
#
FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY target/CalMixer-*.jar /usr/local/lib/CalMixer.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/CalMixer.jar"]
