FROM openjdk:11
COPY build/libs/domaintransactions-0.0.1-SNAPSHOT.jar domaintransactions-0.0.1.jar
ENTRYPOINT ["java","-jar","/domaintransactions-0.0.1.jar"]