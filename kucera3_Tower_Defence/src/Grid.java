import Entity.*;

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
}

