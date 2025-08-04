package Entity;

import Entity.*;
import Entity.Enemy.*;

import java.util.ArrayList;

public class Grid {
    private Block[][] blocks = new Block[6][5]; // 6 rows, 5 columns
    private ArrayList<Entity> entities;

    public Grid() {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                blocks[y][x] = new Block("Block_" + y + "_" + x, y, x);
            }
        }
    }

    public Block getBlockAt(int y, int x) {
        if (y >= 0 && y < 6 && x >= 0 && x < 5) {
            return blocks[y][x];
        }
        return null;
    }

    public void printGrid() {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                System.out.print("[" + blocks[y][x].getName() + "] ");
            }
            System.out.println();
        }
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
    public boolean moveEnemyRight(Enemy enemy) {
        int currentY = enemy.getPositionY();
        int currentX = enemy.getPositionX();

        Block currentBlock = getBlockAt(currentY, currentX);
        if (currentBlock != null) {
            currentBlock.removeEntity(enemy);
        }

        int newX = currentX + 1;
        if (newX >= 5) {
            // Enemy reached the right edge, handle accordingly (remove or stop)
            return false;
        }

        enemy.setPositionX(newX);

        Block newBlock = getBlockAt(currentY, newX);
        if (newBlock != null) {
            newBlock.addEntity(enemy);
            return true;
        }

        return false;
    }


}

