module edu.ntnu.stud.idatt2001.sojohans.wargames {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    exports edu.ntnu.stud.idatt2001.sojohans.wargames;
    opens edu.ntnu.stud.idatt2001.sojohans.wargames to javafx.fxml;
    opens edu.ntnu.stud.idatt2001.sojohans.wargames.view to javafx.fxml;
    exports edu.ntnu.stud.idatt2001.sojohans.wargames.view;
    opens edu.ntnu.stud.idatt2001.sojohans.wargames.scenes to javafx.fxml;
    exports edu.ntnu.stud.idatt2001.sojohans.wargames.scenes;

}