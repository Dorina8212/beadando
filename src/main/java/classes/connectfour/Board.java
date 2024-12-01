package classes.connectfour;

import classes.connectfour.Disc;
import classes.connectfour.Position;

public class Board {
    private final int rows;
    private final int cols;
    private final Disc[][] grid;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Disc[rows][cols];
    }

    public Disc getDisc(Position position) {
        return grid[position.getRow()][position.getCol()];
    }

    public boolean isColumnFull(int col) {
        return grid[0][col] != null;
    }

    public Position dropDisc(int col, Disc disc) {
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][col] == null) {
                grid[row][col] = disc;
                return new Position(row, col);
            }
        }
        throw new IllegalArgumentException("Column is full!");
    }

    public boolean checkWin(Position position, Disc disc) {
        return checkLine(position, disc, 0, 1) ||
                checkLine(position, disc, 1, 0) ||
                checkLine(position, disc, 1, 1) ||
                checkLine(position, disc, 1, -1);
    }

    private boolean checkLine(Position start, Disc disc, int rowDir, int colDir) {
        return (countDiscsInDirection(start, disc, rowDir, colDir) +
                countDiscsInDirection(start, disc, -rowDir, -colDir) - 1) >= 4;
    }

    private int countDiscsInDirection(Position start, Disc disc, int rowDir, int colDir) {
        int count = 0;
        int row = start.getRow();
        int col = start.getCol();

        while (row >= 0 && row < rows && col >= 0 && col < cols && grid[row][col] == disc) {
            count++;
            row += rowDir;
            col += colDir;
        }
        return count;
    }

    public Disc[][] getGrid() {
        return grid;
    }

    public int getColumns() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}
