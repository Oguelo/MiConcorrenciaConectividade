FROM maven:3.8.5-openjdk-17 as buid
RUN	mkdir -p /MiConcorrenciaConectividade/src/server
WORKDIR /MiConcorrenciaConectividade/src/server
ADD . /MiConcorrenciaConectividade/src/server
EXPOSE 8922
Expose 8923
RUN mvn package

FROM eclipse-temurin:17-jdk-jammy
RUN	mkdir -p /MiConcorrenciaConectividade/src/server
WORKDIR /MiConcorrenciaConectividade/src/server
COPY --from=build /MiConcorrenciaConectividade/src/server/target/MiConcorrenciaConectividade-0.0.1-SNAPSHOT.jar server.jar
CMD ["java", "-jar", "server.jar"]