package Tower_Defence;

import java.util.ArrayList;

public class Grid {

    private static Grid instance;

    public static final int DEFAULT_ROWS = 6;
    public static final int DEFAULT_COLS = 5;

    private int rows;
    private int cols;
    private Block[][] blocks;

    private Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        blocks = new Block[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                blocks[y][x] = new Block("Block_" + y + "_" + x, y, x);
            }
        }
    }


    public static Grid getInstance() {
        if (instance == null) {
            instance = new Grid(DEFAULT_ROWS, DEFAULT_COLS);
        }
        return instance;
    }

    public Block getBlock(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return blocks[row][col];
        }
        return null;
    }

    public ArrayList<Block> getBlocksInRange(int centerY, int centerX, int range) {
        ArrayList<Block> blocksInRange = new ArrayList<>();

        for (int y = Math.max(0, centerY - range); y <= Math.min(rows - 1, centerY + range); y++) {
            for (int x = Math.max(0, centerX - range); x <= Math.min(cols - 1, centerX + range); x++) {
                if (Math.abs(centerY - y) + Math.abs(centerX - x) <= range) {
                    blocksInRange.add(blocks[y][x]);
                }
            }
        }
        return blocksInRange;
    }

    public ArrayList<Entity> getAllEntities() {
        ArrayList<Entity> allEntities = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                allEntities.addAll(blocks[y][x].getEntities());
            }
        }
        return allEntities;
    }

    public void moveEntity(Entity e, int newY, int newX) {
        Block oldBlock = getBlock(e.getPositionY(), e.getPositionX());
        Block newBlock = getBlock(newY, newX);

        if (oldBlock != null) {
            oldBlock.removeEntity(e);
        }
        if (newBlock != null) {
            newBlock.addEntity(e);
            e.setPositionY(newY);
            e.setPositionX(newX);
        }
    }

    public void printGrid() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                System.out.print("[" + blocks[y][x].getName() + "] ");
            }
            System.out.println();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}



