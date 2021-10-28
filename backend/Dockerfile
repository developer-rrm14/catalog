FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/catalog-0.0.1-SNAPSHOT.jar catalog-app.jar
ENTRYPOINT ["java","-jar","/catalog-app.jar"]