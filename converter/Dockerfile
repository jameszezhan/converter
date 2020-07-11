FROM maven:3.6.3-openjdk-11-slim as BUILD
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM adoptopenjdk/openjdk11
RUN mkdir /opt/app
COPY --from=build /usr/src/app/target/*.jar /opt/app
CMD ["java", "-jar", "/opt/app/converter-0.0.1-SNAPSHOT.jar" ]



