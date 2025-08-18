package Tower_Defence;

import java.util.ArrayList;

public class Grid {
    private Block[][] blocks = new Block[6][5]; // 6 rows, 5 columns

    public Grid() {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                blocks[y][x] = new Block("Block_" + y + "_" + x, y, x);
            }
        }
    }


    public Block getBlock(int row, int col) {
        if (row >= 0 && row < 6 && col >= 0 && col < 5) {
            return blocks[row][col];
        }
        return null;
    }

    public ArrayList<Block> getBlocksInRange(int centerY, int centerX, int range) {
        ArrayList<Block> blocksInRange = new ArrayList<>();
        for (int y = Math.max(0, centerY - range); y <= Math.min(5, centerY + range); y++) {
            for (int x = Math.max(0, centerX - range); x <= Math.min(4, centerX + range); x++) {
                if (Math.abs(centerY - y) + Math.abs(centerX - x) <= range) {
                    blocksInRange.add(blocks[y][x]);
                }
            }
        }
        return blocksInRange;
    }

    public ArrayList<Entity> getAllEntities() {
        ArrayList<Entity> allEntities = new ArrayList<>();
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                allEntities.addAll(blocks[y][x].getEntities());
            }
        }
        return allEntities;
    }

    public void moveEntity(Entity e, int newY, int newX) {
        Block oldBlock = getBlock(e.getPositionY(), e.getPositionX());
        Block newBlock = getBlock(newY, newX);
        if (oldBlock != null) oldBlock.removeEntity(e);
        if (newBlock != null) newBlock.addEntity(e);
        e.setPositionY(newY);
        e.setPositionX(newX);
    }


    public void printGrid() {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                System.out.print("[" + blocks[y][x].getName() + "] ");
            }
            System.out.println();
        }
    }
}



