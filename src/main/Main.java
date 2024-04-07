package main;

import main.Grid;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid("src/resources/input.txt");
        grid.moveRobots();
    }
}