# simply but bad version

FROM eclipse-temurin:17.0.9_9-jdk
ADD . /src
WORKDIR /src
RUN chmod +x mvnw
RUN ./mvnw package -DskipTests
EXPOSE 8080
COPY target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


#FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine as builder
#ADD . /src
#WORKDIR /src
#RUN ./mvnw package -DskipTests
#
#FROM alpine:3.10.3 as packager
#RUN apk --no-cache add openjdk11-jdk openjdk11-jmods
#ENV JAVA_MINIMAL="/opt/java-minimal"
#RUN /usr/lib/jvm/java-11-openjdk/bin/jlink \
#    --verbose \
#    --add-modules \
#        java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument \
#    --compress 2 --strip-debug --no-header-files --no-man-pages \
#    --release-info="add:IMPLEMENTOR=radistao:IMPLEMENTOR_VERSION=radistao_JRE" \
#    --output "$JAVA_MINIMAL"
#
#FROM alpine:3.10.3
#LABEL maintainer="Nikita Hahua 2gagua121@gmail.com"
#ENV JAVA_HOME=/opt/java-minimal
#ENV PATH="$PATH:$JAVA_HOME/bin"
#COPY --from=packager "$JAVA_HOME" "$JAVA_HOME"
#COPY --from=builder /src/target/microservices-backend-*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/app.jar"]