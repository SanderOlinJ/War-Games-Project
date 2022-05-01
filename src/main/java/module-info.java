module edu.ntnu.stud.idatt2001.sojohans.wargames {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens edu.ntnu.stud.idatt2001.sojohans.wargames to javafx.fxml;
    exports edu.ntnu.stud.idatt2001.sojohans.wargames;
}