FROM openjdk:latest
WORKDIR /src/server
COPY HttpSoc.java /src/server/HttpSoc.java
EXPOSE 8922
EXPOSE 8923
RUN javac HttpSoc.java
CMD [ "java", "HttpSoc ]