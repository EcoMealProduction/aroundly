FROM maven:3.9.9-eclipse-temurin-21 AS dependencies

WORKDIR /opt/aroundly
COPY pom.xml .
COPY code-coverage/pom.xml code-coverage/pom.xml
COPY domain/pom.xml domain/pom.xml
COPY port/pom.xml port/pom.xml
COPY application/pom.xml application/pom.xml
COPY adapter/pom.xml adapter/pom.xml
COPY infra/pom.xml infra/pom.xml

# -B: Stands for batch mode (non-interactive)
#     It makes Maven run without asking any interactive questions
# -e: Tells Maven to show full stack traces for errors
# -f: Force the use of an alternate POM. In this case is parent pom.xml

RUN mvn -B -e -f pom.xml dependency:go-offline

FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /opt/aroundly
COPY --from=dependencies /root/.m2 /root/.m2
COPY . .
# -pl: list of specified reactor projects to build instead of all projects.
#      Here we build infra module, which contains all spring related startup options
# -am: also make” those dependent modules from source
RUN mvn -B -pl infra -am -DskipTests clean package

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /opt/aroundly
COPY --from=builder /opt/aroundly/infra/target/infra-*.jar aroundly.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=heroku", "-jar", "aroundly.jar"]

