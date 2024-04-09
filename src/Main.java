import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int MAX_COORDINATE_VALUE = 50;
    private static final int MAX_INSTRUCTION_LENGTH = 100;

    private static GameObjects loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            Grid grid = createGrid(line);
            List<Robot> robots = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String instructions = reader.readLine();
                validateInstructionLength(instructions);

                String[] position = line.split(" ");
                Robot robot = createRobotFromInput(position, instructions, grid);
                robots.add(robot);
            }

            return new GameObjects(grid, robots);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }

        return null;
    }

    private static Grid createGrid(String firstLine) {
        if (firstLine == null) {
            throw new IllegalArgumentException("Invalid input format.");
        }

        String[] parts = firstLine.split(" ");
        return new Grid(parseCoordinate(parts[0]), parseCoordinate(parts[1]));
    }

    private static void validateInstructionLength(String instructions) {
        if (instructions.length() > MAX_INSTRUCTION_LENGTH) {
            throw new IllegalArgumentException("Instruction length cannot exceed " + MAX_INSTRUCTION_LENGTH + " characters.");
        }
    }

    private static int parseCoordinate(String value) {
        int coordinate = Integer.parseInt(value);
        if (coordinate > MAX_COORDINATE_VALUE) {
            throw new IllegalArgumentException("Coordinate value cannot exceed " + MAX_COORDINATE_VALUE + ".");
        }

        return coordinate;
    }

    private static Robot createRobotFromInput(String[] position, String instructions, Grid grid) {
        int robotX = parseCoordinate(position[0]);
        int robotY = parseCoordinate(position[1]);
        String orientation = position[2];

        return new Robot(robotX, robotY, orientation, instructions, grid);
    }

    private static void runGame(List<Robot> robots) {
        for (Robot robot: robots) {
            System.out.println(robot.processInstructions());
        }
    }

    public static void main(String[] args) {
        GameObjects gameObjects = loadFromFile(args[0]);

        if (gameObjects != null) {
            runGame(gameObjects.robots);
        }
    }

    private static class GameObjects {
        final Grid grid;
        final List<Robot> robots;

        public GameObjects(Grid grid, List<Robot> robots) {
            this.grid = grid;
            this.robots = robots;
        }
    }
}