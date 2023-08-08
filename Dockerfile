FROM eclipse-temurin:11 as build
WORKDIR /workspace/app
ARG SPRING_PROFILE
ARG DB_URL
ARG DB_PORT
ARG MYSQLDB_DATABASE
ARG MYSQLDB_USER
ARG MYSQLDB_ROOT_PASSWORD
ARG TEST
ARG APPLICATION_PORT
# only for account mgr
ARG JWT_SECRET
ARG JWT_EXPIRY_DURATION

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN echo SPRING_PROFILE = $SPRING_PROFILE
RUN echo DB_URL = $DB_URL
RUN echo DB_PORT = $DB_PORT

# RUN ./mvnw install -DskipTests -e

RUN ./mvnw install -Dspring.profiles.active=$SPRING_PROFILE \
    -Dspring.datasource.url="jdbc:mysql://${DB_URL}:$DB_PORT/$MYSQLDB_DATABASE" \
    -Dspring.datasource.password=$MYSQLDB_ROOT_PASSWORD \
    -Dspring.datasource.username=$MYSQLDB_USER  \
    -Dserver.port=$APPLICATION_PORT -e

#RUN ./mvnw install -DskipTests -e


RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:11
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java" ,"-cp","app:app/lib/*","com.peertutor.AccountMgr.AccountMgrApplication"]

