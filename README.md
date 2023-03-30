# Food-Delivery-Application

This is a full-stack simple food delivery application, which calculates the delivery fee for given inputs (city and vehicle type) based on the latest weather data from the weather portal of Estonian Environment Agency (https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php). There are three possible inputs for city: Tallinn, Tartu and Pärnu, and three possible inputs for vehicle: car, scooter and bike. Weather data is based on stations named Tallinn-Harku, Tartu-Tõravere and Pärnu.

Project is written using Java, Spring framework, H2 database and React.

Application has a H2 database, which permanently holds weather data. Database has one table called "Weather". There is Cronjob implemented to update the weather info 15 minutes after each hour. In REST interface you have two input fields to choose city and vehicle, and a button "Calculate", by which you can request for the delivery fee. The delivery fee will then show up under the button.

I used IntelliJ for developing and Oracle OpenJDK version 19 for the project SDK. Frontend implementation is stored in rest/src/frontend folder, backend implementation is stored in rest/src/main folder and tests are implemented in rest/src/test folder.

## Business rules for calculations
### Business rules to calculate regional base fee (RBF):
• In case City = Tallinn and:

    • Vehicle type = Car, then RBF = 4 €
  
    • Vehicle type = Scooter, then RBF = 3,5 €
  
    • Vehicle type = Bike, then RBF = 3 €
  
• In case City = Tartu and:

    • Vehicle type = Car, then RBF = 3,5 €
  
    • Vehicle type = Scooter, then RBF = 3 €
  
    • Vehicle type = Bike, then RBF = 2,5 €
  
• In case City = Pärnu and:

    • Vehicle type = Car, then RBF = 3 €
  
    • Vehicle type = Scooter, then RBF = 2,5 €
  
    • Vehicle type = Bike, then RBF = 2 €
    
### Business rules to calculate extra fees for weather conditions:
• Extra fee based on air temperature (ATEF) in a specific city is paid in case Vehicle type = Scooter or Bike and:
 
    • Air temperature is less than -10̊ C, then ATEF = 1 €

    • Air temperature is between -10̊ C and 0̊ C, then ATEF = 0,5 €

• Extra fee based on wind speed (WSEF) in a specific city is paid in case Vehicle type = Bike and:

    • Wind speed is between 10 m/s and 20 m/s, then WSEF = 0,5 €

    • In case of wind speed is greater than 20 m/s, then the error message “Usage of selected vehicle type is forbidden” has to be given

• Extra fee based on weather phenomenon (WPEF) in a specific city is paid in case Vehicle type = Scooter or Bike and:

    • Weather phenomenon is related to snow or sleet, then WPEF = 1 €
    
    • Weather phenomenon is related to rain, then WPEF = 0,5 €

    • In case the weather phenomenon is glaze, hail, or thunder, then the error message “Usage of selected vehicle type is forbidden” has to be given
    
### Example calculation:
• Input parameters: TARTU and BIKE -> RBF = 2,5 €

• Latest weather data for Tartu (Tartu-Tõravere):

    • Air temperature = -2,1̊ C -> ATEF = 0,5 €
    
    • Wind speed = 4,7 m/s -> WSEF = 0 €

    • Weather phenomenon = Light snow shower -> WPEF = 1 €

• Total delivery fee = RBF + ATEF + WSEF + WPEF = 2,5 + 0,5 + 0 + 1 = 4 €

## How to install and run the project
1) Download and unzip the project
2) Open the project in IntelliJ (open folder Food-Delivery-Application-main)
3) "Maven build script found" -> Choose "Load"
4) use Oracle openjdk 19 as SDK (recommended, works with openjdk 20 as well)
5) Start the backend application by running class called RestApiApplication (rest/src/main/java/com/raiki/app/rest/RestApiApplication.java). You can open the database console from address http://localhost:8080/h2-console and connect to the database -> JDBC URL has to be jdbc:h2:file:./rest/data/fileDb (change it if it's not) -> username is "sa" and password is empty -> Connect. 
6) Start the frontend -> Navigate to the rest/src/frontend folder from the terminal -> write "npm install" and wait for the installation to be finished -> write "npm start" to start the application (application should start in the browser at http://localhost:3000)

NB! It is vital that the Database url is correct. The correct database files are in rest -> data folder. In case of any errors regarding the database while running the RestApiApplication (for example: Table "WEATHER" not found), check the rest/src/main/resources/application.properties file and make sure you have the correct path in spring.datasource.url=jdbc:h2:file:./rest/data/fileDb.
