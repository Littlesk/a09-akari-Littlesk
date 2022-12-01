package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {

    private int puzzleArray[][];
    private int height;
    private int width;

    public PuzzleImpl(int[][] board) {
        if (board != null) {
            this.puzzleArray = board.clone();
            this.height = board.length;
            this.width = board[0].length;
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public CellType getCellType(int r, int c) {
        if (!inPuzzle(r, c)) {
            throw new IndexOutOfBoundsException();
        } else {
            int temp = puzzleArray[r][c];
            if (temp <= 4) {
                return CellType.CLUE;
            } else if (temp == 5) {
                return CellType.WALL;
            } else {
                return CellType.CORRIDOR;
            }
        }
    }

    @Override
    public int getClue(int r, int c) {
        if (getCellType(r, c) == CellType.CLUE) {
            return puzzleArray[r][c];
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean inPuzzle(int r, int c) {
        if (r > width || c > height || r < 0 || c < 0) {
            return false;
        } else {
            return true;
        }
    }
}
