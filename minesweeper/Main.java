package minesweeper;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Cell[][] field = new Cell[9][9];
        fillField(field);
        boolean fail = false;
        while (!win(field) && !fail) {
            printField(field);
            fail = action(field);
        }
        if (!fail) {
            printField(field);
            System.out.println("Congratulations! You found all the mines!");
        } else {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (field[i][j].getType() == Type.MINE)
                        field[i][j].setOpened();
                }
            }
            printField(field);
            System.out.println("You stepped on a mine and failed!");
        }
    }

    public static void open(Cell[][] field, int x, int y) {
        if (field[y][x].getType() != Type.MINE && !field[y][x].isOpened()) {
            if (field[y][x].getType() == Type.NUMBER) {
                field[y][x].setOpened();
            } else {
                field[y][x].setOpened();
                for (int i = y - 1; i <= y + 1; i++) {
                    for (int j = x - 1; j <= x + 1; j++) {
                        if (i >= 0 && i <= 8 && j >= 0 && j <= 8) {
                            open(field, j, i);
                        }
                    }
                }
            }
        }
    }

    public static boolean action(Cell[][] field) {
        System.out.print("Set/unset mines marks or claim a cell as free: ");
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        String action = scanner.next();
        if (action.equals("mine") && !field[y][x].isOpened()) {
            field[y][x].setFlag(!field[y][x].getFlag());
        }
        if (action.equals("free")) {
            if (field[y][x].getType() == Type.MINE) {
                return true;
            } else {
                open(field, x, y);
            }
        }
        return false;
    }

    public static boolean win(Cell[][] field) {
        int mines = 0;
        int flaggedMines = 0;
        int flags = 0;
        int emptys = 0;
        int opened = 0;
        for (Cell[] cells : field) {
            for (Cell cell : cells) {
                if (cell.getType() == Type.MINE) {
                    mines++;
                    if (cell.getFlag()) {
                        flaggedMines++;
                    }
                }
                if (cell.getFlag()) {
                    flags++;
                }
                if (cell.getType() == Type.EMPTY || cell.getType() == Type.NUMBER) {
                    emptys++;
                }
                if (cell.isOpened()) {
                    opened++;
                }
            }
        }
        if (flags == flaggedMines && flags == mines) {
            return true;
        }
        if (emptys == opened) {
            return true;
        }
        return false;
    }

    public static void printField(Cell[][] field) {
        System.out.println(" |123456789|\n-|---------|");
        for (int i = 0; i < field.length; i++) {
            System.out.print((i + 1) + "|");
            for (Cell cell : field[i]) {
                System.out.print(cell);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|\n");
    }

    public static void fillField(Cell[][] field) {
        System.out.print("How many mines do you want on the field? ");
        int mines = scanner.nextInt();
        System.out.println();
        Random random = new Random();
        for (int i = 0; i < mines; i++) {
            int x = 0;
            int y = 0;
            do {
                x = random.nextInt(9);
                y = random.nextInt(9);
            } while (field[y][x] != null && field[y][x].getType() == Type.MINE);
            field[y][x] = new Cell(Type.MINE);
            for (int j = y - 1; j <= y + 1; j++) {
                for (int k = x - 1; k <= x + 1; k++) {
                    if (j >= 0 && j <= 8 && k >= 0 && k <= 8) {
                        if (field[j][k] == null) {
                            field[j][k] = new Cell(Type.NUMBER);
                        } else if (field[j][k].getType() == Type.NUMBER) {
                            field[j][k].addNum();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (field[i][j] == null) {
                    field[i][j] = new Cell(Type.EMPTY);
                }
            }
        }
    }
}
