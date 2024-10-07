package com.javarush.games.game2048;

import com.javarush.engine.cell.*;

import java.util.Random;

public class Game2048 extends Game {
    private static final int SIDE = 4;

    private int[][] gameField = new int[SIDE][SIDE];

    private Color getColorByValue(int value) {
        if (value == 0) {
            return Color.WHITE;
        } else if (value == 2) {
            return Color.BLUE;
        } else if (value == 4) {
            return Color.RED;
        } else if (value == 8) {
            return Color.ORANGE;
        } else if (value == 16) {
            return Color.AQUA;
        } else if (value == 32) {
            return Color.CRIMSON;
        } else if (value == 64) {
            return Color.ANTIQUEWHITE;
        } else if (value == 128) {
            return Color.WHEAT;
        } else if (value == 256) {
            return Color.DARKGREEN;
        } else if (value == 512) {
            return Color.GREEN;
        } else if (value == 1024) {
            return Color.LAWNGREEN;
        } else if (value == 2048) {
            return Color.THISTLE;
        } else {
            return Color.NONE;
        }
    }

     private void setCellColoredNumber(int x, int y, int value) {
         Color color = getColorByValue(value);
         String string = value <= 0 ? "":String.valueOf(value);

         setCellValueEx(x, y, color, string);
     }

    private void drawScene() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                
                setCellColoredNumber(x, y, gameField[y][x]);
            }
        }
    }

    private void createNewNumber() {
        int x = getRandomNumber(SIDE);
        int y = getRandomNumber(SIDE);
        int number = getRandomNumber(10);

        if (gameField[y][x] == 0) {
            gameField[y][x] = number == 9 ? 4 : 2;
        } else {
            createNewNumber();
        }
    }

    private boolean mergeRow(int[] row) {
        boolean canMerge = false;

        for (int i = 1; i < row.length; i++) {
            if (row[i] != 0 & row[i-1] == row[i]) {
                row[i-1] *= 2;
                row[i] = 0;
                canMerge = true;
            }
        }

        return canMerge;
    }

    private boolean compressRow(int[] row) {
        boolean cellMoved = false;
        int temp = 0;

        for (int i = 0; i < row.length; i++) {
            if (row[i] != 0) {
                row[temp] = row[i];

                if (temp != i) {
                    row[i] = 0;
                    cellMoved = true;
                }

                temp += 1;
            }
        }

        return cellMoved;
    }

    private void moveLeft() {
        boolean moved = false;
        for (int i = 0; i < SIDE; i++) {
            if (compressRow(gameField[i])) {
                moved = true;
            }

            if (mergeRow(gameField[i])) {
                moved = true;
            }

            compressRow(gameField[i]);
        }

        if (moved) {
            createNewNumber();
        }

        drawScene();
    }

    private void moveRight() {
        drawScene();
    }

    private void moveUp() {
        drawScene();
    }

    private void moveDown() {
        drawScene();
    }

    private void createGame() {
        createNewNumber();
        createNewNumber();
    }
    private void rotateClockwise() {
        int[][] newGameField = new int[SIDE][SIDE];

        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                newGameField[x][3-y] = gameField[y][x];
            }
        }

        gameField = newGameField;
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.UP) {
            moveUp();
        } else  if (key == Key.DOWN) {
            moveDown();
        } else  if (key == Key.LEFT) {
            moveLeft();
        } else  if (key == Key.RIGHT) {
            moveRight();
        }
    }

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);

        createGame();

        drawScene();
    }
}
