package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {

  private PuzzleLibrary library;
  private int puzzle;
  private boolean lamps[][];
  private List<ModelObserver> observerList;

  public ModelImpl(PuzzleLibrary library) {
    if (library != null) {
      this.library = library;
      puzzle = 0;
      lamps = new boolean[library.getPuzzle(0).getHeight()][library.getPuzzle(0).getWidth()];
      observerList = new ArrayList<>();
    }
  }

  @Override
  public void addLamp(int r, int c) {
    if (r > lamps.length || c > lamps[0].length) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(puzzle).getCellType(r, c) == CellType.CORRIDOR) {
      if (!isLamp(r, c)) {
        lamps[r][c] = true;
        notifyObservers();
      }
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r > lamps.length || c > lamps[0].length) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(puzzle).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      lamps[r][c] = false;
      notifyObservers();
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r > lamps.length || c > lamps[0].length) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(puzzle).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      int checker = 0;
      for (int row = 0; row < lamps.length; row++) {
        if (isLamp(row, c)) {
          checker++;
        } else if (library.getPuzzle(puzzle).getCellType(row, c) == CellType.CLUE
            || library.getPuzzle(puzzle).getCellType(row, c) == CellType.WALL) {
          if (row < r) {
            checker = 0;
          } else {
            row = lamps.length;
          }
        }
      }
      if (checker > 0) {
        return true;
      }
      checker = 0;
      for (int col = 0; col < lamps[0].length; col++) {
        if (isLamp(r, col)) {
          checker++;
        } else if (library.getPuzzle(puzzle).getCellType(r, col) == CellType.CLUE
            || library.getPuzzle(puzzle).getCellType(r, col) == CellType.WALL) {
          if (col < c) {
            checker = 0;
          } else {
            col = lamps[0].length;
          }
        }
      }
      if (checker > 0) {
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r > lamps.length || c > lamps[0].length) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(puzzle).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      return lamps[r][c];
    }
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r > lamps.length || c > lamps[0].length) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(puzzle).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      int checker = 0;
      for (int row = 0; row < lamps.length; row++) {
        if (isLamp(row, c)) {
          checker++;
        } else if (library.getPuzzle(puzzle).getCellType(row, c) == CellType.CLUE
            || library.getPuzzle(puzzle).getCellType(row, c) == CellType.WALL) {
          if (row < r) {
            checker = 0;
          } else {
            row = lamps.length;
          }
        }
      }
      if (checker > 1) {
        return true;
      }
      checker = 0;
      for (int col = 0; col < lamps[0].length; col++) {
        if (isLamp(r, col)) {
          checker++;
        } else if (library.getPuzzle(puzzle).getCellType(r, col) == CellType.CLUE
            || library.getPuzzle(puzzle).getCellType(r, col) == CellType.WALL) {
          if (col < c) {
            checker = 0;
          } else {
            col = lamps[0].length;
          }
        }
      }
      if (checker > 1) {
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(puzzle);
  }

  @Override
  public int getActivePuzzleIndex() {
    return puzzle;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index >= library.size() || index < 0) {
      throw new IndexOutOfBoundsException("Error Index given out of bounds: " + index);
    }
    puzzle = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    lamps =
        new boolean[library.getPuzzle(puzzle).getHeight()][library.getPuzzle(puzzle).getWidth()];
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    CellType type;
    for (int row = 0; row < lamps.length; row++) {
      for (int col = 0; col < lamps[0].length; col++) {
        if (isLamp(row, col)) {
          if (isLampIllegal(row, col)) {
            return false;
          }
        }
        type = library.getPuzzle(puzzle).getCellType(row, col);
        if (type == CellType.CLUE) {
          if (!isClueSatisfied(row, col)) {
            return false;
          }
        } else if (type == CellType.CORRIDOR) {
          if (!isLit(row, col)) {
            return false;
          }
        }
      }
    }
    notifyObservers();
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r > lamps.length || c > lamps[0].length) {
      throw new IndexOutOfBoundsException();
    } else {
      int temp = library.getPuzzle(puzzle).getClue(r, c);
      int check = 0;
      if (library.getPuzzle(puzzle).getCellType(r, c) == CellType.CLUE) {
        if (c > 0) {
          if (isLamp(r, c - 1)) {
            check++;
          }
        }
        if (c < library.getPuzzle(puzzle).getWidth() - 1) {
          if (isLamp(r, c + 1)) {
            check++;
          }
        }
        if (r > 0) {
          if (isLamp(r - 1, c)) {
            check++;
          }
        }
        if (r < library.getPuzzle(puzzle).getHeight() - 1) {
          if (isLamp(r + 1, c)) {
            check++;
          }
        }
      }
      return check == temp;
    }
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observerList.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observerList.remove(observer);
  }

  public int getClue(int r, int c) {
    return library.getPuzzle(puzzle).getClue(r, c);
  }

  public int getHeight() {
    return library.getPuzzle(puzzle).getHeight();
  }

  public int getWidth() {
    return library.getPuzzle(puzzle).getWidth();
  }

  private void notifyObservers() {
    for (ModelObserver observer : observerList) {
      observer.update(this);
    }
  }
}
