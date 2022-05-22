package edu.ntnu.stud.idatt2001.sojohans.wargames;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Battle;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class responsible for launching the application and setting the stage.
 */
public class WarGamesApplication extends Application {

    /**
     * Starts the application and launches GUI.
     * @param stage stage
     */
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new Pane());
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.MENU);
        stage.setTitle("War Games: The Game of Wars");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                "wargames/images/spear.png"));
        /*
        Method says to Battle to shut down simulate, so that it does not
        keep running after the window is closed.
         */
        stage.setOnCloseRequest(windowEvent -> Battle.shutdownSimulate());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}