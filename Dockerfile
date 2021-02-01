FROM tomcat:latest

RUN rm -rf /usr/local/tomcat/webapps/*

COPY ./target/lawn-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps

CMD [ "catalina.sh", "run" ]




#FROM openjdk:11
#ADD out/artifacts/LawnCare_jar/LawnCare.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar" , "app.jar"]
