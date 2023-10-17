
# Change for a Bill

This software allows user to enter a valid bill and returns to the user the amount of coins that matches the bill value.

- The allowed bill values are: ONE, TWO, FIVE, TEN, TWENTY, FIFTY, ONE_HUNDRED.
- The coins available as return are: 1, 5, 10, 25 cents.
- There are two options to get the result: calculating for the LEAST amout of coins and for the MOST amount of coins.

Once the application starts the database will be filled with the amount of 100 coins of each type.






## Funcionalidades

- Change can be made by utilizing the LEAST amount of coins
- Change can be made by utilizing the MOST amount of coins
- API validates if there is enough coins to make the change and responds to the user accordingly
- API validates if the bill input is valid and responds to the user accordingly


## API Documentation

#### Endpoint documentation can be found at http://localhost:8080/swagger-ui/index.html#/change-bill/changeBill


## Run locally

#### To run this project you will need:
- Docker
- Maven
- Java 17

Start the cointaners
```bash
  docker-compose up
```

Run the project

```bash
  mvn spring-boot:run
```

To run the tests

```bash
  mvn clean install
```
#### Example Curls:

- Calculate for the MOST amount of coins for a ten dolar bill:

REQUEST

```json
curl --location 'localhost:8080/api/v1/change-bill' \
--header 'Content-Type: application/json' \
--data '{
    "billType":"TEN",
    "desiredAmount": "MOST_COINS"
}'
```



RESPONSE


```json
{
    "amountOfEachCoinsValue": [
        "The number of $10 coins for change is 40",
        "The number of $25 coins for change is 0",
        "The number of $5 coins for change is 100",
        "The number of $1 coins for change is 100"
    ]
}
```




- Calculate for the LEAST amount of coins for a ten dolar bill:

REQUEST

```json
curl --location 'localhost:8080/api/v1/change-bill' \
--header 'Content-Type: application/json' \
--data '{
    "billType":"TEN",
    "desiredAmount": "LEAST_COINS"
}'
```



RESPONSE
```json
{
    "amountOfEachCoinsValue": [
        "The number of $10 coins for change is 0",
        "The number of $25 coins for change is 40",
        "The number of $5 coins for change is 0",
        "The number of $1 coins for change is 0"
    ]
}
```


## Improvements

- Add the possibility to manage the coins via software (this can be done today by managing the database manually)
- Code improvements: decouple the layers more 

## Techonolgies

- Java 17
- SpringBoot
- Maven
- Docker
- Flyway
- Postgres