package test;

import main.Grid;
import main.Robot;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    Grid grid = new Grid(5, 3);

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

    private static Stream<Arguments> buildProcessInstructionsArguments() {
        return Stream.of(
                Arguments.of(1, 1, "E", "RFRFRFRF", "1 1 E"),
                Arguments.of(3, 2, "N", "FRRFLLFFRRFLL", "3 3 N LOST"),
                Arguments.of(0, 3, "W", "LLFFFLFLFL", "3 3 N LOST"),
                Arguments.of(0, 3, "S", "FFF", "0 0 S")
        );
    }

    private static Stream<Arguments> buildInvalidProcessInstructionsArguments() {
        return Stream.of(
                Arguments.of(1, 1, "E", "RFRFRAFRF"),
                Arguments.of(3, 2, "N", "FVRRFLLFFRRFLL"),
                Arguments.of(0, 3, "W", "LLFFCFLFLFL")
        );
    }
}