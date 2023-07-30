FROM openjdk:11
ARG user=admin
ARG group=paymentchain
ARG uid=1000
ARG gid=1000
RUN groupadd -g ${gid} ${group} && useradd -u ${uid} -G ${group} -s /bin/sh ${user}
USER admin:paymentchain
VOLUME /tmp
ARG JAR_FILE=target/*.jar
ADD target/${JAR_FILE} app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]