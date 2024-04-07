package main;

import lombok.*;
import main.Grid;

@Data
public class Robot {
    @NonNull
    private Integer x;
    @NonNull
    private Integer y;
    @NonNull
    private String orientation;
    @NonNull
    private String instructions;
    @NonNull
    private Grid grid;
    private boolean isLost = false;

    public void turnLeft() {
        orientation = switch (orientation) {
            case "N" -> "W";
            case "W" -> "S";
            case "S" -> "E";
            default -> "N";
        };
    }

    public void turnRight() {
        orientation = switch (orientation) {
            case "N" -> "E";
            case "E" -> "S";
            case "S" -> "W";
            default -> "N";
        };
    }

    public void moveForward() {
        int newX = x, newY = y;

        switch (orientation) {
            case "N" -> newY++;
            case "E" -> newX++;
            case "S" -> newY--;
            case "W" -> newX--;
        }

        if (!grid.isRobotStillOnGrid(newX, newY)) {
            if (!grid.hasScent(x, y)) {
                isLost = true;
                grid.addScent(x, y);
            }
            return;
        }

        x = newX;
        y = newY;
    }


    public String processInstructions() {
        for (char instruction : instructions.toCharArray()) {
            if (isLost) break;

            switch (instruction) {
                case 'L' -> turnLeft();
                case 'R' -> turnRight();
                case 'F' -> moveForward();
            }
        }

        return String.format("%d %d %s %s", x, y, orientation, isLost ? "LOST" : "").trim();
    }
}
