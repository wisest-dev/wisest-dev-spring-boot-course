FROM eclipse-temurin:21.0.6_7-jre as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract
RUN find . -exec touch -t 202301010000.00 {} +

FROM eclipse-temurin:21.0.6_7-jre
WORKDIR application

COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

USER www-data

ENTRYPOINT ["/bin/bash", "-c", "java $ADDITIONAL_JVM_PARAMS org.springframework.boot.loader.launch.JarLauncher application.jar"]
