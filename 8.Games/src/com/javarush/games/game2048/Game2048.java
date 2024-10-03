package com.javarush.games.game2048;

import com.javarush.engine.cell.*;

import java.util.Random;

public class Game2048 extends Game {
    private static final int SIDE = 4;

    Random random = new Random();
    private int[][] gameField = new int[SIDE][SIDE];

    public void update() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {;
                setCellNumber(x, y, gameField[y][x]);
            }
        }
    }

    public void moveUp() {
        for (int y = 1; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int yx = 0; yx <= y; yx++) {
                    if (gameField[yx][x] == 0 & gameField[y][x] != 0) {
                        gameField[yx][x] = gameField[y][x];
                        gameField[y][x] = 0;
                    } else if (gameField[yx][x] == gameField[y][x] & gameField[y][x] != 0) {
                        gameField[yx][x] *= 2;
                        gameField[y][x] = 0;
                    }
                }
            }
        }

        update();
    }

    public void moveLeft() {

    }

    public void moveRight() {

    }

    public void moveDown() {
        for (int y = 2; y >= 0; y--) {
            for (int x = 0; x < 4; x++) {
                for (int yx = 3; yx >= y; yx--) {
                    if (gameField[yx][x] == 0 & gameField[y][x] != 0) {
                        gameField[yx][x] = gameField[y][x];
                        gameField[y][x] = 0;
                    } else if (gameField[yx][x] == gameField[y][x] & gameField[y][x] != 0) {
                        gameField[yx][x] *= 2;
                        gameField[y][x] = 0;
                    }
                }
            }

            for (int x = 0; x < 3; x++) {
                if (gameField[y][x] == gameField[y][x+1] & gameField[y][x] != 0) {
                    gameField[y][x + 1] *= 2;
                    gameField[y][x] = 0;
                }
            }
        }

        update();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.DOWN) {
            moveDown();
        } else if (key == Key.UP) {
            moveUp();
        }else if (key == Key.RIGHT) {
            moveRight();
        }else if (key == Key.LEFT) {
            moveLeft();
        }
    }

    private void createGame(){

    }

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);

        update();

        createGame();
    }
}
