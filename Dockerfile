FROM openjdk:8-jdk-alpine

ENV LYRE_PATH=/lyre

RUN mkdir $LYRE_PATH && mkdir $LYRE_PATH/jar/ && mkdir $LYRE_PATH/shared/

ADD target/*.jar $LYRE_PATH/

RUN mv $LYRE_PATH/*-standalone.jar $LYRE_PATH/jar/lyre.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/lyre/jar/lyre.jar", "--lyre.enable-remote-connections=true", "--lyre.scan-path=/lyre/shared", "--spring.profiles.active=docker"]
