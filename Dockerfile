FROM openjdk:11 as build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

FROM openjdk:11.0.10-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
CMD ["java","-jar","/app/app.jar"]