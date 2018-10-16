FROM openjdk:11-slim
MAINTAINER TBME Labs <info@tbmelabs.ch>

ENTRYPOINT ["/usr/bin/java", "-jar", "/home/springbootadmin/spring-boot-admin.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /home/springbootadmin/spring-boot-admin.jar

RUN useradd -ms /bin/bash springbootadmin
RUN chown springbootadmin /home/springbootadmin/spring-boot-admin.jar

USER springbootadmin
WORKDIR /home/springbootadmin
