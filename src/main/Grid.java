package main;

import lombok.Data;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Data
public class Grid {
    private Integer maxX;
    private Integer maxY;
    private final List<Robot> robots = new ArrayList<>();
    private Set<List<Integer>> scents = new HashSet<>();
    private final int MAX_COORDINATE_VALUE = 50;
    private final int MAX_INSTRUCTION_LENGTH = 100;

    public Grid(String filePath) {
        this.loadFromFile(filePath);
    }

    public void moveRobots() {
        for (Robot robot: robots) {
            System.out.println(robot.processInstructions());
        }
    }

    public void addScent(int x, int y) {
        scents.add(List.of(x, y));
    }

    public boolean hasScent(int x, int y) {
        return scents.contains(List.of(x,y));
    }

    public boolean isRobotStillOnGrid(int x, int y) {
        return x >= 0 && x <= maxX && y >= 0 && y <= maxY;
    }

    public void getGridSize(String firstLine) {
        if (firstLine != null) {
            String[] parts = firstLine.split(" ");
            maxX = parseCoordinate(parts[0]);
            maxY = parseCoordinate(parts[1]);
        }
    }

    public void validateInstructionLength(String instructions) {
        if (instructions.length() > MAX_INSTRUCTION_LENGTH) {
            throw new IllegalArgumentException("Instruction length cannot exceed " + MAX_INSTRUCTION_LENGTH + " characters.");
        }
    }

    public int parseCoordinate(String value) {
        int coordinate = Integer.parseInt(value);
        if (coordinate > MAX_COORDINATE_VALUE) {
            throw new IllegalArgumentException("Coordinate value cannot exceed " + MAX_COORDINATE_VALUE + ".");
        }
        return coordinate;
    }

    private Robot createRobotFromInput(String[] position, String instructions) {
        int robotX = parseCoordinate(position[0]);
        int robotY = parseCoordinate(position[1]);
        String orientation = position[2];

        return new Robot(robotX, robotY, orientation, instructions, this);
    }

    public void loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            getGridSize(line);

            while ((line = reader.readLine()) != null) {
                String instructions = reader.readLine();
                validateInstructionLength(instructions);

                String[] position = line.split(" ");
                Robot robot = createRobotFromInput(position, instructions);
                robots.add(robot);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }
}
