FROM openjdk:8-jre
MAINTAINER TBME Labs <info@tbmelabs.ch>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/configuration-server/configuration-server.jar"]

# Java service
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/configuration-server/configuration-server.jar
