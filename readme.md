## Technologies used
- Java 8
- Spring Boot
- Spring Security
- HttpClients
- OAuth
- JWT [JSON Web Token]
- JUnit
- Spring Data JPA [Hibernate]
- MYSQL
- Logback
- Maven
- Docker


## System configuration prerequisites to run the application
### What should be installed in your machine -
1. Docker


### Step 1: Clone the project
Open terminal and run
````
git clone https://github.com/hnjaman/currency-rate-exchange-service.git
````
In your current directory ``currency-rate-exchange-service`` directory will be created.


### Step 2: Run the application in docker
Move to ``currency-rate-exchange-service`` directory and enter below command from your terminal 
````
docker-compose up
````
#### What will happen now ?
1. Pull ```openjdk:8``` docker image, build ```currency-rate-exchange-service``` spring boot application locally and make a docker image.
2. Pull ```mysql:8``` docker image.
3. Deploy above two docker image in two separate docker container in your docker environment.

##### Congratulations! Now the application is ready to use.


### API details

|No.| API              | REST Method   | API endpoints                                                |
|---|:-----------------|:--------------|:-------------------------------------------------------------|
|1  |User Registration |POST           |``http://localhost:8080/register``                            |
|2  |Login             |POST           |``http://localhost:8080/login``                               |
|3  |IDR Exchange Rate |GET            |``http://localhost:8080/api/idr/rate/exchange/{countryName}`` |


#### 1. ``http://localhost:8080/register``

Request:
````
{
    "email": "hnjs@gmail.com",
    "pass": "1234"
}
````

Response:
````
{
    "status": true,
    "email": "hnjs@gmail.com",
    "message": "Success"
}
````


#### 2. ``http://localhost:8080/login``

Request:
````
{
    "email": "hnjs@gmail.com",
    "pass": "1234"
}
````

Response:
````
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJobmpzQGdtYWlsLmNvbSIsImV4cCI6MTY0Mjk3NjUzOSwiaWF0IjoxNjQyOTc1OTM5fQ.jwR-6oono-h9wwbs3jjDojeuCQbnWAIL8M235FzCepWyIbmeducHJMcFCVp6y1wT9_HsbjZvbct8vcnjo5rrow"
}
````

#### 3. ``http://localhost:8080/api/idr/rate/exchange/{countryName}``

Request:
````
curl --location --request GET 'http://localhost:8080/api/idr/rate/exchange/bangladesh' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJobmpzQGdtYWlsLmNvbSIsImV4cCI6MTY0Mjk3NjUzOSwiaWF0IjoxNjQyOTc1OTM5fQ.jwR-6oono-h9wwbs3jjDojeuCQbnWAIL8M235FzCepWyIbmeducHJMcFCVp6y1wT9_HsbjZvbct8vcnjo5rrow' \
--header 'Cookie: JSESSIONID=0B7D237A4EACFD20233E39BB34E5E0C5'
````

Response:
````
{
  "countryName": "Bangladesh",
  "population": 164689383,
  "exchangeRatesInIDR": [
    {
      "currency": "BDT",
      "currencyFullName": "Bangladeshi taka",
      "exchangeRateInIDR": 166.69723260890584
    }
  ],
  "message": "Success"
}
````

### The End
