From openjdk:8
copy ./target/currency-rate-exchange-service-0.0.1-SNAPSHOT.jar currency-rate-exchange-service-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","currency-rate-exchange-service-0.0.1-SNAPSHOT.jar"]