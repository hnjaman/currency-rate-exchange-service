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


### API details

| API              | REST Method   | API endpoints                                                |
|------------------|:--------------|:-------------------------------------------------------------|
|User Registration |POST           |``http://localhost:8080/register``                            |
|Login             |POST           |``http://localhost:8080/login``                               |
|IDR Exchange Rate |GET            |``http://localhost:9000/api/idr/rate/exchange/{countryName}`` |


#### ``http://localhost:8080/register``

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


#### ``http://localhost:8080/login``

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

#### ``http://localhost:9000/api/idr/rate/exchange/{countryName}``

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