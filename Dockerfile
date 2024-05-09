# Usar uma imagem base oficial do OpenJDK
FROM openjdk:8-jdk-alpine

# Argumento de versão para flexibilidade
ARG JAR_FILE=target/api-java-sales-1.0.0-SNAPSHOT.jar

# Adiciona o arquivo .jar da aplicação para o contêiner
ADD ${JAR_FILE} app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
