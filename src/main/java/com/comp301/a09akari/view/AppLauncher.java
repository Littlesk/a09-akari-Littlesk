package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    @Override
    public void start(Stage stage) {
        // TODO: Create your Model, View, and Controller instances and launch your GUI

        //Model
        Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
        Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
        Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
        Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
        Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

        PuzzleLibrary puzzleLibrary = new PuzzleLibraryImpl();
        puzzleLibrary.addPuzzle(puzzle1);
        puzzleLibrary.addPuzzle(puzzle2);
        puzzleLibrary.addPuzzle(puzzle3);
        puzzleLibrary.addPuzzle(puzzle4);
        puzzleLibrary.addPuzzle(puzzle5);

        Model model = new ModelImpl(puzzleLibrary);

        //controller
        AlternateMvcController controller = new ControllerImpl(model);

        //View
        View view = new View(controller);


        //make scene
        Scene scene = new Scene(view.render());
        scene.getStylesheets().add("main.css");
        stage.setScene(scene);

        //refresh view when model changes
        model.addObserver((Model m) -> {
            scene.setRoot(view.render());
            stage.sizeToScene();
        });

        //show the stage
        stage.setTitle("Akari Game");
        stage.show();
    }
}
