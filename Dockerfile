FROM openjdk:8-jre
MAINTAINER TBME Labs <info@tbmelabs.ch>

ENTRYPOINT ["/usr/bin/java", "-jar", "/home/configurationserver/configuration-server.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /home/configurationserver/configuration-server.jar

RUN useradd -ms /bin/bash configurationserver
RUN chown configurationserver /home/configurationserver/configuration-server.jar

USER configurationserver
WORKDIR /home/configurationserver
