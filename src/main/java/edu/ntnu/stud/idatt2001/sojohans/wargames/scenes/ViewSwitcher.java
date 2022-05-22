package edu.ntnu.stud.idatt2001.sojohans.wargames.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Objects;

/**
 * Class ViewSwitcher for switching between scenes in Application.
 */
public class ViewSwitcher {
    private static Scene scene;

    /**
     * Method for setting the scene.
     * @param scene Scene to be set.
     */
    public static void setScene(Scene scene) {ViewSwitcher.scene = scene;}

    /**
     * Method for switching between different fxml files, while keeping same scene.
     * @param view View enum with fxml file, to be switched to.
     */
    public static void switchTo(View view){

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(ViewSwitcher.class.getResource(view.getFileName())));
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
