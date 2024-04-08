package test;

import main.Grid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    Grid grid = new Grid("src/resources/input.txt");

    @ParameterizedTest
    @MethodSource("buildParseCoordinatedArguments")
    void shouldProcessInstructions(String line, int expectedResult) {
        int result = grid.parseCoordinate(line);

        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInput")
    void testMethodThrowsExceptionForInvalidInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> grid.parseCoordinate(input));
    }

    @Test
    void shouldGetGridSize() {
        grid.getGridSize("6 5 N");

        assertEquals(6, grid.getMaxX());
        assertEquals(5, grid.getMaxY() );
    }

    @ParameterizedTest
    @MethodSource("buildIsRobotStillOnGridArguments")
    void shouldVerifyIfRobotIsStillOnGrid(int initialX, int initialY, boolean expectedResponse) {
        boolean result = grid.isRobotStillOnGrid(initialX, initialY);

        assertEquals(expectedResponse, result);
    }

    @ParameterizedTest
    @MethodSource("buildHasScentArguments")
    void shouldVerifyIfHasScent(int initialX, int initialY, boolean expectedResponse) {
        Set<List<Integer>> scents = Set.of(List.of(5, 3));
        grid.setScents(scents);

        boolean result = grid.hasScent(initialX, initialY);

        assertEquals(expectedResponse, result);
    }

    @Test
    void shouldProcessInstructions() {
        assertThrows(IllegalArgumentException.class, () -> grid.validateInstructionLength("LFT".repeat(101)));
    }

    @Test
    void shouldLoadFromFile() {
        assertNotNull(grid.getMaxX());
        assertNotNull(grid.getMaxY());
        assertNotNull(grid.getRobots());
    }

    private static Stream<Arguments> provideInvalidInput() {
        return Stream.of(
                Arguments.of("100"),
                Arguments.of("51")
        );
    }

    private static Stream<Arguments> buildParseCoordinatedArguments() {
        return Stream.of(
                Arguments.of("50", 50),
                Arguments.of("30", 30)
        );
    }

    private static Stream<Arguments> buildIsRobotStillOnGridArguments() {
        return Stream.of(
                Arguments.of(5, 3, true),
                Arguments.of(0, 0, true),
                Arguments.of(1, 5, false)
        );
    }

    private static Stream<Arguments> buildHasScentArguments() {
        return Stream.of(
                Arguments.of(5, 3, true),
                Arguments.of(0, 0, false),
                Arguments.of(1, 5, false)
        );
    }
}