FROM openjdk:8-jdk-alpine

ENV LYRE_PATH=/lyre

RUN mkdir $LYRE_PATH && mkdir $LYRE_PATH/jar/ && mkdir $LYRE_PATH/configs/

ADD target/*.jar $LYRE_PATH/

RUN mv $LYRE_PATH/*.jar $LYRE_PATH/jar/lyre.jar

EXPOSE $LYRE_PORT

ENTRYPOINT ["java", "-jar", "/lyre/jar/lyre.jar --lyre.enable-remote-connections=true"]
