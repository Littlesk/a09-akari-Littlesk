package com.compclass.akari.view;

import com.compclass.akari.controller.AlternateMvcController;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class View implements FXComponent {
  private final AlternateMvcController controller;

  public View(AlternateMvcController controller) {
    if (controller != null) {
      this.controller = controller;
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();

    // title view
    TitleView titleView = new TitleView(controller);
    layout.getChildren().add(titleView.render());

    // Name view
    NameView nameView = new NameView(controller);
    layout.getChildren().add(nameView.render());

    // controls view
    ControlsView controlsView = new ControlsView(controller);
    layout.getChildren().add(controlsView.render());

    // Won View

    // puzzle
    PuzzleView puzzleView = new PuzzleView(controller);
    layout.getChildren().add(puzzleView.render());

    return layout;
  }
}
