package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Puzzle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class PuzzleView implements FXComponent {
    private final AlternateMvcController controller;

    public PuzzleView(AlternateMvcController controller) {
        if (controller != null) {
            this.controller = controller;
        } else {
            throw new IllegalArgumentException();
        }
    }


    @Override
    public Parent render() {
        GridPane board = new GridPane();
        board.setHgap(5);
        board.setVgap(5);
        board.getStyleClass().add("board");
        for (int row = 0; row < controller.getPuzzleHeight(); row++) {
            for (int col = 0; col < controller.getPuzzleWidth(); col++) {
                board.add(makeTile(controller.getCellType(row, col), row, col), row, col);

            }
        }

        return board;
    }

    private Label makeTile(CellType type, int r, int c) {
        Label tile;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Won!");
        alert.setHeaderText("You Won!");
        alert.setContentText("Congratulations! Care to try another?");

        if (type == CellType.CLUE) {
            tile = new Label(String.valueOf(controller.getClue(r, c)));
            tile.getStyleClass().add("tile");
            if (controller.isClueSatisfied(r, c)) {
                tile.getStyleClass().add("clueSolved");
            } else {
                tile.getStyleClass().add("clue");
            }
        } else if (type == CellType.WALL) {
            tile = new Label();
            tile.getStyleClass().add("tile");
            tile.getStyleClass().add("wall");

        } else {
            if (controller.isLamp(r, c)) {
                tile = new Label();
                tile.getStyleClass().add("tile");
                tile.getStyleClass().add("lamp");
                tile.setOnMouseClicked((MouseEvent event) -> {
                    controller.clickCell(r, c);
                    if (controller.isSolved()) {
                        alert.showAndWait();
                    }
                });
            } else if (controller.isLit(r, c)) {
                tile = new Label();
                tile.getStyleClass().add("tile");
                tile.getStyleClass().add("lit");
                tile.setOnMouseClicked((MouseEvent event) -> {
                    controller.clickCell(r, c);
                    if (controller.isSolved()) {
                        alert.showAndWait();
                    }
                });
                //add light
            } else {
                tile = new Label();
                tile.getStyleClass().add("tile");
                tile.getStyleClass().add("white");
                tile.setOnMouseClicked((MouseEvent event) -> {
                    controller.clickCell(r, c);
                    if (controller.isSolved()) {
                        alert.showAndWait();
                    }
                });
                //make white
            }
        }
        //styling here

        return tile;
    }
}
