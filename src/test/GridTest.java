package test;

import main.Grid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    Grid grid = new Grid(5, 3);

    @ParameterizedTest
    @MethodSource("buildIsWithinBoundsArguments")
    void shouldVerifyIfRobotIsStillOnGrid(int initialX, int initialY, boolean expectedResponse) {
        boolean result = grid.isWithinBounds(initialX, initialY);

        assertEquals(expectedResponse, result);
    }

    @Test
    void shouldVerifyScents() {
        grid.addScent(1, 2);
        assertTrue(grid.hasScent(1, 2));

        grid.addScent(3, 4);
        assertTrue(grid.hasScent(3, 4));
    }

    private static Stream<Arguments> buildIsWithinBoundsArguments() {
        return Stream.of(
                Arguments.of(5, 3, true),
                Arguments.of(0, 0, true),
                Arguments.of(1, 5, false)
        );
    }
}