***This work is mine unless otherwise cited - Nathan Loria***
## EzTravel Execution Steps
- Compile:
  - To compile submission files, type ./build into the terminal while in the project-src directory
- Run:
  - To run EzTravel (TravelMain.class), execute the comant ./run in a terminal in the project-src directory

## Classes
- TravelMain
  - Creates a distance matrix for the planned vacation locations
  - Public
  - Contains the main method
  - Orchestrates the program by using methods from other classes


- TripPlan
  - Custom data type to hold information about the trip
  - Contains methods to return and edit existing data


- WebScraper
  - Utilizes jsoup to connect and scrape data from airbnb.com
  - Return a hashmap of housing data


- DistanceFinder
  - Utilizes Google geocoding API
  - Returns the distance from one location to another


- TripPath
  - Contains the traveling salesman algorithm
  - Returns a list containing the optimal trip plan
