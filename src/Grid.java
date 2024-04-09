import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid {
    private final int maxX;
    private final int maxY;
    private final Set<List<Integer>> scents = new HashSet<>();

    public Grid(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void addScent(int x, int y) {
        scents.add(List.of(x, y));
    }

    public boolean hasScent(int x, int y) {
        return scents.contains(List.of(x,y));
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x <= maxX && y >= 0 && y <= maxY;
    }
}
