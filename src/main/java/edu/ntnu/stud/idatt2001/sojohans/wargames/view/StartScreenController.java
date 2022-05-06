package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartScreenController {

    @FXML
    void onStartButtonClicked(ActionEvent event) {
        ViewSwitcher.switchTo(View.CREATE_ARMY);
    }

}
