package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Battle;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.fxml.FXML;

public class UnitInfoController {

    @FXML
    public void onMenuButtonClicked(){
        ViewSwitcher.switchTo(View.MENU);
    }

    @FXML
    public void onCreateArmyButtonClicked(){
        ViewSwitcher.switchTo(View.CREATE_ARMY);
    }

    @FXML
    public void onViewArmiesButtonClicked(){
        ViewSwitcher.switchTo(View.VIEW_ARMIES);
    }

    @FXML
    public void onSimulateButtonClicked(){
        Battle.startBattle();
        ViewSwitcher.switchTo(View.BATTLE_SIMULATION);
    }
}
