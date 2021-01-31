FROM tomcat:latest

RUN rm -rf /usr/local/tomcat/webapps/*

COPY ./target/lawn-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/Root.war

CMD [ "catalina.sh", "run" ]


