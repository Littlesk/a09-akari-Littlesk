package com.compclass.akari.view;

import com.compclass.akari.controller.AlternateMvcController;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TitleView implements FXComponent {
  private final AlternateMvcController controller;

  public TitleView(AlternateMvcController controller) {
    if (controller != null) {
      this.controller = controller;
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    // add style sheet here

    Text title = new Text("Akari - ");
    Text puzzle1 = new Text("By Shannon Little");
    title.setFont(Font.font("Verdana", 20));
    puzzle1.setFont(Font.font("Verdana", 18));

    layout.getChildren().add(title);
    layout.getChildren().add(puzzle1);

    return layout;
  }
}
