call mvnw package -DskipTests
cd lawn_care_consumer
call mvnw package -DskipTests
cd ..
docker-compose up --build