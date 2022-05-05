package edu.ntnu.stud.idatt2001.sojohans.wargames;

import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class WarGamesApplication extends Application {
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new Pane());
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.START);
        stage.setTitle("Start screen");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}