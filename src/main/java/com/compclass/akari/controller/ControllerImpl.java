package com.compclass.akari.controller;

import com.compclass.akari.model.CellType;
import com.compclass.akari.model.Model;
import com.compclass.akari.model.Puzzle;

import java.util.Random;

public class ControllerImpl implements AlternateMvcController {

  private Model model;

  public ControllerImpl(Model model) {
    if (model != null) {
      this.model = model;
    }
  }

  @Override
  public void clickNextPuzzle() {
    int current = model.getActivePuzzleIndex();
    if (current < 4) {
      model.setActivePuzzleIndex(current + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int current = model.getActivePuzzleIndex();
    if (current > 0) {
      model.setActivePuzzleIndex(current - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    Random random = new Random();
    int upper = 5;
    int set = random.nextInt(upper);
    while (set == model.getActivePuzzleIndex()) {
      set = random.nextInt(upper);
    }
    model.setActivePuzzleIndex(set);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.isLamp(r, c)) {
      model.removeLamp(r, c);
    } else {
      model.addLamp(r, c);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  @Override
  public int getPuzzleHeight() {
    return model.getActivePuzzle().getHeight();
  }

  @Override
  public int getPuzzleWidth() {
    return model.getActivePuzzle().getWidth();
  }

  @Override
  public int getClue(int r, int c) {
    return model.getActivePuzzle().getClue(r, c);
  }

  @Override
  public CellType getCellType(int r, int c) {
    return model.getActivePuzzle().getCellType(r, c);
  }

  public String getName() {
    int index = model.getActivePuzzleIndex();
    switch (index) {
      case 0:
        return "Puzzle 1";
      case 1:
        return "Puzzle 2";
      case 2:
        return "Puzzle 3";
      case 3:
        return "Puzzle 4";
      case 4:
        return "Puzzle 5";
    }
    return "Puzzles!";
  }
}
