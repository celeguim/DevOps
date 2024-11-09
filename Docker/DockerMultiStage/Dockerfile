###############################
# Build Stage
FROM maven:3.8.3-openjdk-17 AS build_image
RUN git clone -b jvminfo-v3 https://github.com/celeguim/jvminfo.git
RUN cd jvminfo && mvn clean install -DskipTests

###############################
# Runtime Stage
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build_image ./jvminfo/target/jvminfo*.jar /app.jar
EXPOSE 8080

ENV JAVA_OPTS="-Dpar1:val1"
ENV JAR_ARGS="arg1 arg2"
ENV SYS_PROPS="-Dsysprop1=val1 -Dsysprop2=val2"

# ENTRYPOINT java -jar ${SYS_PROPS} HelloWorld.jar ${JAR_ARGS}
ENTRYPOINT java -jar ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom /app.jar ${JAR_ARGS}

# ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]
# ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

# docker run --rm -p 8080:8080 -e JAVA_OPTS="-DParam1=Val1" -e JAR_ARGS="par1 par2" multi:v2
# -XX:+UseG1GC
