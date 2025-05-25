# Karate API Automation Framework

This project provides a basic framework for API automation using Karate.

## Prerequisites

*   Java Development Kit (JDK) 8 or higher
*   Apache Maven

## Setup

1.  Clone the repository:
    ```bash
    git clone <repository_url>
    cd karate-api-automation
    ```
2.  Build the project and download dependencies:
    ```bash
    mvn clean install
    ```

## Running Tests

To run all tests, execute the following Maven command:

```bash
mvn test
```

This will trigger the `KarateTestRunner` class, which runs all feature files found in the `src/test/java/com/example/features` directory.

## Project Structure

*   `pom.xml`: Maven project configuration, including dependencies.
*   `src/test/java/com/example/features/`: Contains Karate feature files.
    *   `sample.feature`: An example feature file.
*   `src/test/java/com/example/utils/`: Can be used for Java utility classes.
*   `src/test/java/com/example/KarateTestRunner.java`: JUnit runner for executing Karate tests.
*   `src/test/resources/karate-config.js`: Karate configuration file for managing environment-specific settings.
*   `src/test/resources/logback-test.xml`: Configuration for logging (currently empty, can be customized).

## Writing Tests

Create new `.feature` files in the `src/test/java/com/example/features/` directory. Refer to the [Karate documentation](https://github.com/intuit/karate) for detailed information on how to write Karate tests.

## Configuration

The `karate-config.js` file can be used to define environment-specific variables. By default, it sets a `appBaseUrl`. You can switch environments by passing the `karate.env` system property:

```bash
mvn test -Dkarate.env=staging
```

## Reporting

Karate generates HTML reports by default in the `target/karate-reports` directory after a test run. Open the `karate-summary.html` file in a web browser to view the results.
