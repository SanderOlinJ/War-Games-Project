package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartScreenController {

    @FXML
    public void onBattleSimulationButtonClicked() {
        ViewSwitcher.switchTo(View.BATTLE_SIMULATION);
    }

    @FXML
    public void onCreateArmyButtonClicked(){
        ViewSwitcher.switchTo(View.CREATE_ARMY);
    }

    @FXML
    public void onViewLocalArmiesButtonClicked(){
        ViewSwitcher.switchTo(View.VIEW_OR_EDIT_LOCAL_ARMIES);
    }

}
