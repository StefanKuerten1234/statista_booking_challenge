**Intro**
The Booking Service enables various business departments to maintain and process customer bookings. It offers a RESTful
CRUD API via HTTP and also exposes some utility functions. It is build in an extensible manner, meaning adding new
business departments is supported by a plugin architecture.

**Limitations**

- Only temporary persistence: Bookings are stored in memory only and will get lost on application restart. Especially
  consider this point when deploying in a non-permanent hosting environment (i.e. AWS ECS Fargate or GCP CloudRun)
- No Transport Layer encryption: By design, the application has no Transport Layer encryption. This shouldn't be much
  of a problem when hosting in a public cloud, but don't forget to add a loadbalancer and application firewall to your
  setup
- No authentication: For the sake of making demonstration on this PoC easier, there is no authentication build in.
  Unless you plan to rely on cloud provider IAM to authenticate access, it is strongly recommended to add this feature

**Prerequisites**

- JDK >= 17

**How to test / build / run**

- run all tests:

```shell
./mvnw test
```

- build an executable

```shell
./mvnw clean package spring-boot:repackage
```

- run locally

```shell
./mvnw spring-boot:run
```

**API Documentation**
Once started, the application offers an OpenApi specification. For a locally run instance the URL is:

http://localhost:8080/swagger-ui.html

**Original specification below**

**Preamble**

Your mission would you decide to accept it:

As part of the multi-disciplinary development elite team **PIT** in Statista you are creating software to alleviate the
issues from our Sales team. For this, a new requirement has been raised to implement a RESTful web service that stores
_booking_ objects in memory and return information about them.

Note: A _booking_ is a request from any of our beloved customers to acquire one of our products.

**Challenge**

The module **code-challenge** already contains the basic structure of a Spring Boot application for you.

The bookings to be stored have the following fields:

- booking_id
- description
- price
- currency
- subscription_start_date
- email
- department

Please define as many departments as you will like, and create a unique method `doBusiness()` for each department. This 
is your time to shine, so the method implementations can be as simple, elegant, or complicated as you want.

Feel free to select the best data type that, in your opinion, would define those fields the best.

You should not use a database or some other sort of persistence but come up with an own data structure to store the 
transactions.

In any moment where the implementation implies the usage of an external server (i.e. sending e-mail's) feel free to mock
creatively these external resources, keeping in mind that all real-world use-cases must be covered.

In general, we are looking for a good implementation, code quality, code resilience, code extensibility, code 
maintainability and how the implementation is tested.
Basically something you wouldn't be too embarrassed to push to production.

**API Specification:**

**POST /bookingsservice/bookings**

Sample Body:
```
{"description": "Cool description!", "price": 50.00, "currency": "USD", "subscription_start_date": 683124845000, "email": "valid@email.ok", "department": "cool department"}
```

Creates a new booking and sends an e-mail with the details.

**PUT /bookingservice/bookings/{booking_id}**

Sample Body:
```
{"description": "Cool description!", "price": 50.00, "currency": "USD", "subscription_start_date": 683124845000, "email": "valid@email.ok", "department": "another_department" }
```

Insert, replace if already exists a booking.

**GET /bookingservice/bookings/{booking_id}**

Returns the specified booking as JSON.

**GET /bookingservice/bookings/department/{department}**

Returns a JSON list of all bookings ids with the given department.

**GET /bookingservice/bookings/currencies**

Returns a JSON list with all used currencies in the existing bookings.

**GET /bookingservice/sum/{currency}**

Returns the sum of all bookings prices with the given currency.

**GET /bookingservice/bookings/dobusiness/{booking_id}**

Returns the result of `doBusiness()` for the given booking corresponding department.

**Input / Output Examples:**

