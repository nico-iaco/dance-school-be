FROM openjdk:17-jdk-alpine

LABEL mantainer="nicolaiacovelli98@gmail.com"
EXPOSE 8080

RUN addgroup -S user && adduser -S local -G user
USER local:user

COPY target/*.jar /app.jar

ENTRYPOINT java -jar app.jar
HEALTHCHECK --interval=5m --timeout=3s CMD curl -f http://localhost:8080/swagger-ui.html || exit 1