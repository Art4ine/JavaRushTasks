package com.javarush.games.game2048;

import com.javarush.engine.cell.*;

import java.util.Random;

public class Game2048 extends Game {
    Random random = new Random();
    int[][] cells = new int[4][4];

    public void update() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {;
                setCellNumber(x, y, cells[y][x]);
            }
        }
    }

    public void moveUp() {
        for (int y = 1; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int yx = 0; yx <= y; yx++) {
                    if (cells[yx][x] == 0 & cells[y][x] != 0) {
                        cells[yx][x] = cells[y][x];
                        cells[y][x] = 0;
                    } else if (cells[yx][x] == cells[y][x] & cells[y][x] != 0) {
                        cells[yx][x] *= 2;
                        cells[y][x] = 0;
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
                    if (cells[yx][x] == 0 & cells[y][x] != 0) {
                        cells[yx][x] = cells[y][x];
                        cells[y][x] = 0;
                    } else if (cells[yx][x] == cells[y][x] & cells[y][x] != 0) {
                        cells[yx][x] *= 2;
                        cells[y][x] = 0;
                    }
                }
            }

            for (int x = 0; x < 3; x++) {
                if (cells[y][x] == cells[y][x+1] & cells[y][x] != 0) {
                    cells[y][x + 1] *= 2;
                    cells[y][x] = 0;
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

    @Override
    public void initialize() {
        setScreenSize(4, 4);

        update();
    }
}
