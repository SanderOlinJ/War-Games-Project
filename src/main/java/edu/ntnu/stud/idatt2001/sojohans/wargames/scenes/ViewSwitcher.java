package edu.ntnu.stud.idatt2001.sojohans.wargames.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class ViewSwitcher {
    private static Scene scene;

    public static void setScene(Scene scene) {ViewSwitcher.scene = scene;}

    public static void switchTo(View view){

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(ViewSwitcher.class.getResource(view.getFileName())));
            scene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
