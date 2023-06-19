# FROM maven:3.9-eclipse-temurin-17-alpine
FROM maven:3.8.3-openjdk-17

ENV TZ=Asia/Ho_Chi_Minh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime
RUN echo $TZ > /etc/timezone

WORKDIR /app
COPY ./be-src/target/restful-api-app.jar ./restful-api-app.jar

RUN useradd -m rest-app
RUN groupadd webapp
RUN usermod -a -G webapp rest-app

RUN chown -R rest-app:webapp /app
RUN chmod 550 /app
RUN find . -type f -exec chmod 640 {} \;
RUN find . -type d -exec chmod 550 {} \;
RUN chmod +t -R /app

USER rest-app

EXPOSE 9595
CMD ["java", "-jar", "./restful-api-app.jar"]
