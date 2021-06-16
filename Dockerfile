FROM openjdk:11-jdk as builder
WORKDIR application
ARG JAR_FILE=build/libs/homework3.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:11-jdk
ENV PORT=8080
WORKDIR application
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-Xmx512m", "-Xms512m", "org.springframework.boot.loader.JarLauncher"]