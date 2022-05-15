package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitFactory;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.Unit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateArmyController {

    @FXML private Text numberOfGold;
    @FXML private Text numberOfGold1;
    @FXML private TextField textFieldArmyName;
    @FXML private TextField textFieldSpearFighter;
    @FXML private TextField textFieldSwordsman;
    @FXML private TextField textFieldAxeman;
    @FXML private TextField textFieldArcher;
    @FXML private TextField textFieldLightCavalry;
    @FXML private TextField textFieldPaladin;
    @FXML private Text costOfSpearFighter;
    @FXML private Text costOfSwordsman;
    @FXML private Text costOfAxeman;
    @FXML private Text costOfArcher;
    @FXML private Text costOfLightCavalry;
    @FXML private Text costOfPaladin;
    @FXML private Text spearFighterNumber;
    @FXML private Text swordsmanNumber;
    @FXML private Text axemanNumber;
    @FXML private Text archerNumber;
    @FXML private Text lightCavalryNumber;
    @FXML private Text paladinNumber;
    @FXML private Text warningText;
    @FXML private Button instantiateArmy;
    @FXML private Text armyNameText;
    @FXML private Text armyName;
    @FXML private Button setArmyNameButton;
    @FXML private Button editArmyNameButton;
    @FXML private Button addSpearFighterButton;
    @FXML private Button removeSpearFighterButton;
    @FXML private Button addSwordsmanButton;
    @FXML private Button removeSwordsmanButton;
    @FXML private Button addAxemanButton;
    @FXML private Button removeAxemanButton;
    @FXML private Button addArcherButton;
    @FXML private Button removeArcherButton;
    @FXML private Button addLightCavalryButton;
    @FXML private Button removeLightCavalryButton;
    @FXML private Button addPaladinButton;
    @FXML private Button removePaladinButton;

    private List<TextField> nrOfUnitsToBeAddedOrRemovedTextFields;
    private List<Text> nrOfUnitsAddedTexts;
    private List<Button> addUnitButtons;
    private List<Button> removeUnitButtons;
    private int[] costPerUnit;
    private int[] nrOfSpecificUnits;
    private int gold;
    private Army army;

    @FXML
    public void initialize(){
        gold = 1000;
        numberOfGold.setText(String.valueOf(gold));
        numberOfGold1.setVisible(true);
        nrOfSpecificUnits = new int[6];
        costPerUnit = new int[6];

        nrOfUnitsAddedTexts = new ArrayList<>(Arrays.asList(spearFighterNumber, swordsmanNumber, axemanNumber,
                archerNumber, lightCavalryNumber, paladinNumber));
        nrOfUnitsToBeAddedOrRemovedTextFields = new ArrayList<>(Arrays.asList(textFieldSpearFighter, textFieldSwordsman,
                textFieldAxeman, textFieldArcher, textFieldLightCavalry, textFieldPaladin));

        addUnitButtons = new ArrayList<>(Arrays.asList(addSpearFighterButton, addSwordsmanButton, addAxemanButton,
                addArcherButton, addLightCavalryButton, addPaladinButton));
        removeUnitButtons = new ArrayList<>(Arrays.asList(removeSpearFighterButton, removeSwordsmanButton,
                removeAxemanButton, removeArcherButton, removeLightCavalryButton, removePaladinButton));

        for (int i = 0; i < addUnitButtons.size() && i < removeUnitButtons.size(); i++){
            int index = i;
            addUnitButtons.get(index).setOnAction(event -> onAddUnitButtonClicked(index));
            removeUnitButtons.get(index).setOnAction(event -> onRemoveUnitButtonClicked(index));
        }

        fillCostsPerUnit();
        showCostOfUnitInGUI();
        disableButtons();
    }

    @FXML
    void onSetArmyNameButtonClicked(){
        if (textFieldArmyName.getText() == null || textFieldArmyName.getText().trim().isEmpty()){
            printTextToErrorMessage("Error: Name of Army is empty!");

        }
        if (Utilities.stringDoesNotContainAnyNonAlphaNumericSymbols(textFieldArmyName.getText())){
            printTextToErrorMessage("Error: Army name can only contain alpha-numeric symbols!");

        }
        if (Utilities.doesArmyFileExist(textFieldArmyName.getText().trim())){
            printTextToErrorMessage("Error: There already exists an army under this name!");
        }
        else {
            army = new Army(textFieldArmyName.getText());
            editNameTextSwap();
            removeWarningLabel();
            enableButtons();
        }
    }

    @FXML
    void onEditArmyNameButtonClicked(){
        setNameSwap();
        army = null;
        removeWarningLabel();
        disableButtons();
    }

    private void setNameSwap(){
        armyName.setVisible(false);
        armyName.setDisable(true);
        armyName.setText("");

        editArmyNameButton.setVisible(false);
        editArmyNameButton.setDisable(true);
        editArmyNameButton.setPrefWidth(0);

        armyNameText.setText("Army name:");
        armyNameText.setVisible(true);
        armyNameText.setDisable(false);

        textFieldArmyName.setVisible(true);
        textFieldArmyName.setDisable(false);
        textFieldArmyName.setPrefWidth(325);

        setArmyNameButton.setVisible(true);
        setArmyNameButton.setDisable(false);
        setArmyNameButton.setPrefWidth(173);
    }

    private void editNameTextSwap(){
        armyNameText.setVisible(false);
        armyNameText.setDisable(true);
        armyNameText.setText("");

        textFieldArmyName.setText("");
        textFieldArmyName.setPrefWidth(0);
        textFieldArmyName.setVisible(false);
        textFieldArmyName.setDisable(true);

        setArmyNameButton.setVisible(false);
        setArmyNameButton.setDisable(true);
        setArmyNameButton.setPrefWidth(0);

        armyName.setVisible(true);
        armyName.setDisable(false);
        armyName.setText(army.getName());

        editArmyNameButton.setVisible(true);
        editArmyNameButton.setDisable(false);
        editArmyNameButton.setPrefWidth(173);

    }

    private void onAddUnitButtonClicked(int indexOfUnit){

        if (checkIfTextFieldValuesAreValidBoolean(nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit))
                && checkIfEnoughCurrencyForMoreUnitsBoolean(indexOfUnit)) {

            int numberOfUnitsToBeAdded = Integer.parseInt(nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit).getText().trim());
            nrOfSpecificUnits[indexOfUnit] += numberOfUnitsToBeAdded;

            nrOfUnitsAddedTexts.get(indexOfUnit).setText("" + nrOfSpecificUnits[indexOfUnit]);
            nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit).setText("");

            updateGold(numberOfUnitsToBeAdded, indexOfUnit);
            removeWarningLabel();
        }
    }

    private void onRemoveUnitButtonClicked(int indexOfUnit){
        if (checkIfTextFieldValuesAreValidBoolean(nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit))
                && checkIfMoreUnitsAreNotRemovedThanAlreadyAddedBoolean(indexOfUnit)){

            int numberOfUnitsToBeRemoved = Integer
                    .parseInt(nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit).getText().trim());
            nrOfSpecificUnits[indexOfUnit] -= numberOfUnitsToBeRemoved;

            nrOfUnitsAddedTexts.get(indexOfUnit).setText("" + nrOfSpecificUnits[indexOfUnit]);
            nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit).setText("");

            updateGold(-numberOfUnitsToBeRemoved, indexOfUnit);
            removeWarningLabel();
        }
    }

    private void updateGold(int amountOfUnits, int unitIndex){
        int costOfUnit = costPerUnit[unitIndex];
        int totalAmount = amountOfUnits * costOfUnit;
        gold -= totalAmount;
        numberOfGold.setText(String.valueOf(gold));
    }

    private boolean checkIfTextFieldValuesAreValidBoolean(TextField textField){
        boolean bool = true;
        if (army == null){
            printTextToErrorMessage("Error: Name of Army needs to be set first!");
            bool = false;
        }
        if (textField.getText().trim().isEmpty()){
            printTextToErrorMessage("Error: Number of units is empty!");
            bool = false;
        }
        if (Utilities.stringDoesNotContainSymbolsOtherThanNumbers(textField.getText().trim())){
            printTextToErrorMessage("Error: Number of units has to be a positive integer!");
            bool = false;
        }
        return bool;
    }

    private boolean checkIfEnoughCurrencyForMoreUnitsBoolean(int indexOfUnits){
        boolean bool = true;
        int numberOfUnits = Integer.parseInt(nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnits).getText().trim());
        int cost = costPerUnit[indexOfUnits];
        int totalSum = numberOfUnits*cost;

        if ((gold - totalSum) < 0){
            printTextToErrorMessage("Error: Cannot add " + numberOfUnits + " units, not enough Gold!");
            bool = false;
        }

        return bool;
    }

    private boolean checkIfMoreUnitsAreNotRemovedThanAlreadyAddedBoolean(int indexOfUnit){
        boolean bool = true;
        int numberOfUnits = Integer.parseInt(nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit).getText().trim());
        if (nrOfSpecificUnits[indexOfUnit] - numberOfUnits < 0){
            printTextToErrorMessage("Error: Cannot remove " + numberOfUnits +
                    " units of this type, more than registered!");
            bool = false;
        }
        return bool;
    }

    private void fillCostsPerUnit(){
        costPerUnit[0] = 10;
        costPerUnit[1] = 15;
        costPerUnit[2] = 20;
        costPerUnit[3] = 15;
        costPerUnit[4] = 30;
        costPerUnit[5] = 100;
    }


    private void showCostOfUnitInGUI(){
        costOfSpearFighter.setText(costPerUnit[0] + "");
        costOfSwordsman.setText(costPerUnit[1] + "");
        costOfAxeman.setText(costPerUnit[2] + "");
        costOfArcher.setText(costPerUnit[3] + "");
        costOfLightCavalry.setText(costPerUnit[4] + "");
        costOfPaladin.setText(costPerUnit[5] + "");
    }


    @FXML
    void onInstantiateArmyButtonClicked(){
        List<Unit> units = new ArrayList<>();
        if (army == null){
            printTextToErrorMessage("Error: Name of Army needs to be set first!");
        }
        else {
            if (nrOfSpecificUnits[0] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountUnits(UnitType.INFANTRY_UNIT, "Infantry Units", nrOfSpecificUnits[0]));
            }
            if (nrOfSpecificUnits[1] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountUnits(UnitType.RANGED_UNIT, "Ranged Units", nrOfSpecificUnits[1]));
            }
            if (nrOfSpecificUnits[2] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountUnits(UnitType.CAVALRY_UNIT, "Cavalry Units", nrOfSpecificUnits[2]));
            }
            if (nrOfSpecificUnits[3] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountUnits(UnitType.COMMANDER_UNIT, "Commander Units", nrOfSpecificUnits[4]));
            }
        }

        if (units.isEmpty()){
            printTextToErrorMessage("Error: Army needs units before instantiation!");
        } else {
            army.addAllUnits(units);
            ViewSwitcher.switchTo(View.START);
        }
    }

    @FXML
    void onMenuButtonClicked(){
        ViewSwitcher.switchTo(View.MAIN_PAGE);
    }

    private void removeWarningLabel(){
        warningText.setText("");
    }

    private void printTextToErrorMessage(String errorMessage){
        warningText.setText(errorMessage);
    }

    private void enableButtons(){
        addUnitButtons.forEach(button -> button.setDisable(false));
        removeUnitButtons.forEach(button -> button.setDisable(false));
        instantiateArmy.setDisable(false);
    }

    private void disableButtons(){
        addUnitButtons.forEach(button -> button.setDisable(true));
        removeUnitButtons.forEach(button -> button.setDisable(true));
        instantiateArmy.setDisable(true);
    }

}
