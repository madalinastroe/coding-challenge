# Martian Robots

### Prerequisites
- Install Maven
- Install JDK (Java 17)
- Ensure that you have the JDK and Maven properly set up in your PATH.

### Running the program
Note that development was made on Windows. While not expected, please make adjustments for other OSs if needed.
1. Open terminal/command prompt
2. Navigate to the root of the project
3. Compile program using `mvn clean install` - this command run tests as well
4. Run program using the following commands:
   * on Windows: `mvn exec:java -D"exec.args"="resources/input.txt"` 
   * on Unix systems: `mvn exec:java -Dexec.args="resources/input.txt"`

### Running the tests
1. Use command `mvn test`

### Justification of the solution
I have decided to structure my solution in three classes:
* `Grid` - represents the game grid on which the Robots move. 
  * Has maxX and maxY coordinates to represent the dimensions of the grid.
  * Has a list of scents that are marked by fallen robots.
    * The `addScent` method is used to add scent to the list.
    * The `hasScent` method verifies if a specific pair of coordinates appear in the scent list.
  * The `isWithinBounds` method verifies if the given pair of coordinates is between the minimum [0, 0] and maximum [maxX, maxY] limits.
* `Robot` - the object that is moving on the grid and can receive instructions.
  * When constructed, the robot receives the starting coordinates and orientation, its instructions set, and the grid it moves on.
  * The robot has a public method `processInstructions` which processes the robot's movement on the grid when called.
  * For each instruction type we have a corresponding method which is called by `processInstructions` to execute it.
  * The robot has an `isLost` property for the case when it falls off the grid.
* `Main` - contains static methods for parsing the input, creating the game objects, running the game, and printing the output.
  * Also validates the instructions' lengths (maximum 100) and the coordinates' (maximum 50).

### Notes
* The coordinates have been implemented in a simple way by defining the `turnLeft` and `turnRight` methods, as well as the updates to the robots' coordinates, using switch statements.
A different solution would utilize arrays to define the coordinates and deltas applied to the position when taking a step in a particular direction.
This solution would allow introducing new orientations (example NW, NE, SW, SE) easily and without needing to expand the switch statements.
However, adhering to the KISS rule, I decided not to go for this solution as it would make the code more difficult to understand for a new developer.
* As required, the implementation allows for easy extension of the instruction set, as the only required changes are adding a case to the switch statement and implementing a corresponding method
in the Robot class to process the instruction.

### Possible extension
With more time for the assignment, it would be interesting to introduce some new functionalities to our application, such as **Collision avoidance for the robots**.

Right now, it is possible for two robots to overlap on a grid position.
Suppose that we want to avoid such collisions when executing the robots' movements. The game rules could be expanded to avoid these situations.