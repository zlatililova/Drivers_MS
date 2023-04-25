FROM openjdk:17
EXPOSE 8089
ADD /target/drivers-ms.jar drivers-ms.jar
ENTRYPOINT ["java", "-jar", "/drivers-ms.jar"]