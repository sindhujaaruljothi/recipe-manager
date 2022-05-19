# Receipe Manager

### _Author: Sindhuja Aruljothi_

## Requirement:
~~~
Objective
Create a web application which allows users to manage your favourite recipes.
Create API’s to show all available recipes and the actions to create, update and delete a recipe.
API’s could be able to retrieve recipes with following attributes:
1. Date and time of creation (formatted as dd‐MM‐yyyy HH:mm).
2. Indicator if the dish is vegetarian.
3. Indicator displaying the number of people the dish is suitable for.
4. Display ingredients as a list.
5. Cooking instructions.
   Requirements
   Please ensure that we have some documentation about the architectural choices and also how to
   run the application.
   The project is expected to be delivered as a GitHub repository URL or a zip file.
   All these requirements needs to be satisfied:
1. Application must be production ready.
2. REST application must be implemented using Java.
3. Data must be persisted in a database.
4. Use any frameworks of your choice for REST.
5. Unit testing must be taken in due consideration.
6. Describe at least 10 testing scenarios using GivenWhenThen style.
7. The API's must be built ensuring that it is secured from security attacks.
   Bonus
1. REST application should be secured by implementing authentication process (please provide
   credentials).
2. Application should have an API documentation.
3. Write automation tests for the described testing scenarios.
4. Use of container based solutions is an added advantage.
5. Creating a single-page application illustrating the use of API.
~~~

## Implementation details

### Approach & Key tech stack used in this project

> * Java 11
> * SpringBoot 2.6.4
> * Contract First Approach
>    * I have used contract first approach for this use case as requirement and expected outputs are very clear
> * Containerized
>    * This Application is containerized and invokes mysql image from docker hub and added as dependant service to main recipe-manager application  
> * Security
>    * This application is secured using spring Security framework

## How to RUN

Since I have used Contract First Approach, in-order to convert the contract to a java sources, very first we have to execute below command which will generate all necessary auto-magic codes required in the main service

```sh
cd <parent_directory>

mvn clean install
```


### To run as a docker container follow the below step
```sh
mvn clean install -Pdocker

docker compose up

To Stop/remove the containers: 

docker compose down
```

### To run the application locally
```sh
mvn clean install
Set active profile to local. "spring.profiles.active=local"

- local run uses "in-memory h2" database 
```

## API Playground:

I have used swagger for API documentation which generates playground where user can examine the enabled APIs. 

To access the swagger documentation please go to: http://localhost:8090/swagger-ui/index.html

* Below are the list of APIs which are whitelisted and does not require any authentication, rest all fully secured and without proper token the application will reject the request
  * ✨[/recipe-management/client-api/v1/createaccount](http://localhost:8090/recipe-management/client-api/v1/createaccount) 
  * ✨[/recipe-management/client-api/v1/authenticate](http://localhost:8090/recipe-management/client-api/v1/authenticate) 
  * ✨[/recipe-management/client-api/v1/*](http://localhost:8090/recipe-management/client-api/v1/*)  (only GET methods)

In-order to invoke the authorized APIs used must register/sign-in to the application, only then user can add/update/delete recipes. 

Also, user can not perform any action (except GET) on recipes created by other user.

### The work flow goes like below
* Sign-in
  * ✨[/recipe-management/client-api/v1/createaccount](http://localhost:8090/recipe-management/client-api/v1/createaccount) 
* login
  * ✨[/recipe-management/client-api/v1/authenticate](http://localhost:8090/recipe-management/client-api/v1/authenticate) 
  * Once authenticated, please copy the JWT token returned by the API and attach it in the header for authorized requests
  * ```
    Ex: "authorization": "Bearer JWT_TOKEN"
    Replace JWT_TOKEN with actual value while passing in the header
    ```
* create recipe (make sure to attach JWT token in header)
  * ✨[/recipe-management/client-api/v1/recipe](http://localhost:8090/recipe-management/client-api/v1/recipe)  POST (refer swagger for input payload)
* Update recipe (make sure to attach JWT token in header)
  * ✨[/recipe-management/client-api/v1/recipe?](http://localhost:8090/recipe-management/client-api/v1/recipe?)  PUT (refer swagger for input payload)
* Delete recipe (make sure to attach JWT token in header)
  * ✨[recipe-management/client-api/v1/recipe?recipeName=SomeThing](http://localhost:8090/recipe-management/client-api/v1/recipe?recipeId=SomeThing) DEL (refer swagger for input payload) 
* retrieve all recipe
  * ✨[recipe-management/client-api/v1/recipe](http://localhost:8090/recipe-management/client-api/v1/recipe)

### Additional recipe APIs

There are few more additional APIs I build to make the application more robust and meaningful
* ingredient
  * ✨[recipe-management/client-api/v1/ingredient](http://localhost:8090/recipe-management/client-api/v1/ingredient)
  * All ingredients are maintained in separate table for better re-usability therefore enabled a separate API to handle data in it
* unit
  * ✨[recipe-management/client-api/v1/unit](http://localhost:8090/recipe-management/client-api/v1/unit)
  * When user add a recipe and tags an ingredients, it is wise to get the measurement along with quantity ex: `chicken 1 kg` therefore added unit to separate table for better normalization


## Postman
To test the application locally I have also committed the postman collections under `./recipe-manager/postman/recipe-management.postman_collection.json` which can be used to test it locally



