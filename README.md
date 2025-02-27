# API Automation Framework

## Overview
[APIAutomation](https://github.com/MuhamedTharwat/APIAutomation)
This project is a generic API automation framework built with Java, Rest Assured, TestNG, and Log4j2. It is designed to handle any API by abstracting common functionality into reusable components. The framework supports two approaches for sending request payloads:
- **Reading from an External JSON File:** JSON payloads can be stored in external files and read during test execution.

## Features

- **Generic API Client:**  
  A custom Request Builder encapsulates all aspects of request creation, making it easy to manage endpoints, HTTP methods, headers, query parameters, and bodies.

- **Flexible Payload Management:**  
reading JSON payloads from external files.

- **Comprehensive Logging:**  
  Integrated with Log4j2 to log detailed request/response information, errors, and exceptions. Logs are written to both the console and a file.

- **Robust Exception Handling:**  
  Built-in mechanisms to catch and log errors during request building and execution to simplify troubleshooting.





## Logging Configuration
The project uses Log4j2 for logging. The configuration file `log4j2.properties` (located in `src/main/resources`) is set up to output logs to both the console and a file.

## Test Example
The test examples in this framework demonstrate how to interact with a sample booking API using both approaches: POJO-based serialization and external JSON files. For instance, the `BookingTests` class contains a variety of test cases:

- **Positive Tests:**
  - **Valid Booking with Empty Additional Needs:** Similar to the first test but uses an empty string for the "additionalneeds" field.
  - **Valid Booking using External JSON:** Reads a JSON payload from an external file (`booking.json`) and uses it to create a booking.

- **Negative Tests:**
  - **Empty Payload and Malformed JSON:** Tests how the API handles an empty request body and improperly formatted JSON.

These tests utilize the custom `RequestBuilder` to construct API requests and Log4j2 to log each step, making it easier to trace execution and diagnose issues when tests fail.

## Extensibility
- **Adding New Endpoints:** Create additional API classes in the `pages` package following the structure of `BookingAPI.java`.
- **Data-Driven Testing:** Add more external JSON files in `src/test/resources` and use them with `JsonFileReader` for flexible test data.
- **Enhanced Logging:** Update the Log4j2 configuration as needed to adjust log levels or outputs.
- **Generic Requests:** Leverage the `RequestBuilder` to construct and execute API requests for any endpoint, HTTP method, or payload type.

## Conclusion
This framework provides a robust and flexible solution for API automation. With its generic design, comprehensive logging, and robust exception handling, it is easy to maintain and extend for any API testing needs.
