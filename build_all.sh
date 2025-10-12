#!/bin/bash

# Exit script if any command fails
set -e

echo "Building Project a01..."
(cd a01-get-started-with-spring-boot && mvn clean install)

echo "Building Project a02..."
(cd a02-serve-web-content && mvn clean install)

echo "Building Project a03..."
(cd a03-validate-form-input && mvn clean install)

echo "Building Project a04..."
(cd a04-connect-to-database-using-jpa && mvn clean install)

echo "Building Project a05..."
(cd a05-expose-rest-service && mvn clean install)

echo "Building Project a06..."
(cd a06-consume-restful-web-service && mvn clean install)

echo "Building Project a07..."
(cd a07-cover-code-with-tests && mvn clean install)

echo "Building Project a08..."
(cd a08-secure-the-application && mvn clean install)

echo "Building Project a09..."
(cd a09-package-the-application && mvn clean install)

echo "Building Project a10..."
(cd a10-use-shared-library && mvn clean install)

echo "Building Project solution-a01..."
(cd solution-a01 && mvn clean install)

echo "Building Project solution-a02..."
(cd solution-a02 && mvn clean install)

echo "Building Project solution-a03..."
(cd solution-a03 && mvn clean install)

echo "Building Project solution-a04..."
(cd solution-a04 && mvn clean install)

echo "Building Project solution-a05..."
(cd solution-a05 && mvn clean install)

echo "Building Project solution-a06..."
(cd solution-a06 && mvn clean install)

echo "Building Project solution-a07..."
(cd solution-a07 && mvn clean install)

echo "All projects built successfully! 👍"





















