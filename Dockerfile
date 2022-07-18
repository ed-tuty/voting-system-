FROM openjdk:11.0.3-jdk
RUN apt-get update && apt-get install bash
RUN mkdir -p /usr/app/
ENV PROJECT_HOME /usr/app/
COPY build/ $PROJECT_HOME/
WORKDIR $PROJECT_HOME
CMD ["java", "-jar", "./libs/test-0.0.1-SNAPSHOT.jar"]