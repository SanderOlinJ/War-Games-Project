package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.fxml.FXML;

/**
 * Controller class for the 'Main Menu' page.
 */
public class MenuController {

    /**
     * Redirects to the 'Battle Simulation' page.
     */
    @FXML
    public void onBattleSimulationButtonClicked() {
        ViewSwitcher.switchTo(View.BATTLE_SIMULATION);
    }

    /**
     * Redirects to the 'Create Army' page.
     */
    @FXML
    public void onCreateArmyButtonClicked(){
        ViewSwitcher.switchTo(View.CREATE_ARMY);
    }


    /**
     * Redirects to the 'View Armies' page.
     */
    @FXML
    public void onViewArmiesButtonClicked(){
        ViewSwitcher.switchTo(View.VIEW_ARMIES);
    }

}
