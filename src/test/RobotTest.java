package test;

import main.Grid;
import main.Robot;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    Grid grid = new Grid("src/resources/input.txt");

    @ParameterizedTest
    @MethodSource("buildProcessInstructionsArguments")
    void shouldProcessInstructions(int initialX, int initialY, String initialOrientation, String instructions, String expectedResult) {
        Robot robot = new Robot(initialX, initialY, initialOrientation, instructions, grid);

        String result = robot.processInstructions();

        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("buildInvalidProcessInstructionsArguments")
    void shouldThrowExceptionInvalidOperation(int initialX, int initialY, String initialOrientation, String instructions) {
        Robot robot = new Robot(initialX, initialY, initialOrientation, instructions, grid);

        assertThrows(IllegalArgumentException.class, robot::processInstructions);
    }

    @ParameterizedTest
    @MethodSource("buildTurnLeftArguments")
    void shouldTestTurnLeft(String initialOrientation, String expectedOrientation) {
        Robot robot = new Robot(0, 0, initialOrientation, "", grid);

        robot.turnLeft();

        assertEquals(expectedOrientation, robot.getOrientation(),
                String.format("Expected orientation to be %s after turning left from %s", expectedOrientation, initialOrientation));
    }

    @ParameterizedTest
    @MethodSource("buildTurnRightArguments")
    void shouldTestTurnRight(String initialOrientation, String expectedOrientation) {
        Robot robot = new Robot(0, 0, initialOrientation, "", grid);

        robot.turnRight();

        assertEquals(expectedOrientation, robot.getOrientation(),
                String.format("Expected orientation to be %s after turning left from %s", expectedOrientation, initialOrientation));
    }

    @ParameterizedTest
    @MethodSource("buildMoveForwardArguments")
    void shouldTestMoveForward(int initialX, int initialY, String initialOrientation, int expectedX, int expectedY, boolean expectedIsLost) {
        Robot robot = new Robot(initialX, initialY, initialOrientation, "", grid);

        robot.moveForward();

        assertEquals(expectedX, robot.getX(), "X coordinate mismatch");
        assertEquals(expectedY, robot.getY(), "Y coordinate mismatch");
        assertEquals(expectedIsLost, robot.isLost(), "Lost status mismatch");
    }

    private static Stream<Arguments> buildTurnLeftArguments() {
        return Stream.of(
                Arguments.of("N", "W"),
                Arguments.of("W", "S"),
                Arguments.of("S", "E"),
                Arguments.of("E", "N")
        );
    }

    private static Stream<Arguments> buildTurnRightArguments() {
        return Stream.of(
                Arguments.of("N", "E"),
                Arguments.of("E", "S"),
                Arguments.of("S", "W"),
                Arguments.of("W", "N")
        );
    }

    private static Stream<Arguments> buildMoveForwardArguments() {
        return Stream.of(
                Arguments.of(2, 2, "N", 2, 3, false),
                Arguments.of(2, 2, "S", 2, 1, false),
                Arguments.of(5, 5, "N", 5, 5, true),
                Arguments.of(5, 5, "E", 5, 5, true)
        );
    }

    private static Stream<Arguments> buildProcessInstructionsArguments() {
        return Stream.of(
                Arguments.of(1, 1, "E", "RFRFRFRF", "1 1 E"),
                Arguments.of(3, 2, "N", "FRRFLLFFRRFLL", "3 3 N LOST"),
                Arguments.of(0, 3, "W", "LLFFFLFLFL", "3 3 N LOST")
        );
    }

    private static Stream<Arguments> buildInvalidProcessInstructionsArguments() {
        return Stream.of(
                Arguments.of(1, 1, "E", "RFRFRAFRF", "1 1 E"),
                Arguments.of(3, 2, "N", "FVRRFLLFFRRFLL", "3 3 N LOST"),
                Arguments.of(0, 3, "W", "LLFFCFLFLFL", "3 3 N LOST")
        );
    }
}