# AirStackProject

This is GraphQL API Automation Test Project which uses following Tech Stack:
1. Java
2. Rest Assured
3. TestNG
4. Maven

Project Folder Structure:
-------------------------
1. src/main/java contains multipple packages and classes:
  1.1 commons: This package has 2 classes BaseTest and Constants
    1.1.1 BaseTest Class: contains code for initial setup and being extended by all Test Classes.
    1.1.2 Constants Class: contains Global Constants being used through out the project.
  1.2 dataproviders: This package contains 1 class called TestDataProviders which contains code for testng data provider which is used for data driven testing using different data set.
  1.3 models: This package contains mutiple POJO Classes to store payload or response of the API
  1.4. reporters: This class contains Test Listener classes which are used for Reporting. Extent Report is being used for HTML Report generation.
    1.4.1 
  1.5. 
  

