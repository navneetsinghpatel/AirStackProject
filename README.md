# AirStackProject

This is GraphQL API Automation Test Project which uses following Tech Stack:
1. Java
2. Rest Assured
3. TestNG
4. Maven

Project Folder Structure:
-------------------------
1. main/java directory contains all the functionality part of the project like utils classes, reporters, data models, data providers etc.
2. main/resources directory contains files used for test data and reporter config.
3. test/java directory conatins all the test cases divided into multiple packages and classes.
4. test/resources directory contains json files which are used to make GraphQL API requests.

Run the test:
-------------
1. Java 11 and Maven should be installed in your system and path should be set.
2. Github Actions Worflow is also setup so you can run the project from their manually. Reports and logs are generated as part of artifacts.
3. Alternatively after downloading/cloing the project you navigate to the Project directory and execute mvn test or mvn clean test to run the test.
4. After the run reports and logs can be found in the result directory in the same project folder.
5. Some test will fail as there are bugs in the API.
 

