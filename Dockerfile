# build du WAR avec Maven
FROM maven:3.8.8-eclipse-temurin-8 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests


#  exécution sur Tomcat 9 (Java 8, comme en local)
FROM tomcat:9.0-jdk8-temurin

# On vide les webapps par défaut de Tomcat pour ne garder que la nôtre
RUN rm -rf /usr/local/tomcat/webapps/*

# Déploiement en "ROOT" donc ca doit etre accessible sur http://localhost:8080/
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]