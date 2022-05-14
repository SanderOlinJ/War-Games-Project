package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitFactory;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.InfantryUnit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.Unit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @FXML private Text armyNameText;
    @FXML private Text armyName;
    @FXML private Button setArmyNameButton;
    @FXML private Button editArmyNameButton;

    private int[] costPerUnit;
    private Army army;
    private int[] nrOfSpecificUnits;

    private int gold;

    @FXML
    public void initialize(){
        gold = 1000;
        numberOfGold.setText(String.valueOf(gold));
        numberOfGold1.setVisible(true);
        nrOfSpecificUnits = new int[6];

        costPerUnit = new int[6];
        fillCostsPerUnit();
        showCostOfUnitInGUI();
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
    void onSetArmyNameButtonClicked() throws IOException{
        if (textFieldArmyName.getText() == null || textFieldArmyName.getText().trim().isEmpty()){
            printTextToErrorMessage("Error: Name of Army is empty!");
            throw new IOException("Error: Name of Army is empty!");
        }
        if (Utilities.stringDoesNotContainAnyNonAlphaNumericSymbols(textFieldArmyName.getText())){
            printTextToErrorMessage("Error: Army name can only contain alpha-numeric symbols");
            throw new IOException("Error: Army name can only contain alpha-numeric symbols");
        }
        army = new Army(textFieldArmyName.getText());
        editNameTextSwap();
        removeWarningLabel();
    }

    @FXML
    void onEditArmyNameButtonClicked(){
        setNameSwap();
        army = null;
        removeWarningLabel();
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

    private void checkIfTextFieldValuesAreValid(TextField textField) throws IOException {
        if (army == null){
            printTextToErrorMessage("Error: Name of Army needs to be set first!");
            throw new IOException("Error: Name of Army needs to be set first!");
        }
        if (textField.getText().trim().isEmpty()){
            printTextToErrorMessage("Error: Number of units is empty!");
            throw new IOException("Error: Number of units is empty!");
        }
        if (Utilities.stringDoesNotContainSymbolsOtherThanNumbers(textField.getText().trim())){
            printTextToErrorMessage("Error: Number of units has to be a positive integer!");
            throw new IOException("Error: Number of units has to be a positive integer!");
        }
    }

    private void checkIfEnoughCurrencyForMoreUnits(TextField textField, int indexOfUnitsInList) throws IOException{
        int numberOfUnits = Integer.parseInt(textField.getText().trim());
        int cost = costPerUnit[indexOfUnitsInList];
        int totalSum = numberOfUnits*cost;

        if ((gold - totalSum) < 0){
            printTextToErrorMessage("Error: Cannot add " + numberOfUnits + " units, not enough Gold!");
            throw new IOException("Error: Cannot add " + numberOfUnits + " units, not enough Gold!");
        }
    }

    private void checkIfMoreUnitsAreRemovedThanAlreadyAdded(TextField textField, int n) throws IOException{
        int numberOfUnits = Integer.parseInt(textField.getText().trim());
        if (nrOfSpecificUnits[n] - numberOfUnits < 0){
            printTextToErrorMessage("Error: Cannot remove " + numberOfUnits +
                    " units of this type, more than registered!");
            throw new IOException("Error: Cannot remove " + numberOfUnits +
                    " units of this type, more than registered!");
        }
    }


    private void onUnitAddButton(TextField unitsToBeAdded, Text unitsAdded, int indexOfUnitsInList)
            throws IOException{
        checkIfEnoughCurrencyForMoreUnits(unitsToBeAdded, indexOfUnitsInList);
        int numberOfUnitsToBeAdded = Integer.parseInt(unitsToBeAdded.getText().trim());
        nrOfSpecificUnits[indexOfUnitsInList] += numberOfUnitsToBeAdded;

        unitsAdded.setText("" + nrOfSpecificUnits[indexOfUnitsInList]);
        unitsToBeAdded.setText("");

        updateGold(numberOfUnitsToBeAdded, indexOfUnitsInList);
    }

    private void onUnitRemoveButton(TextField unitsToBeAdded, Text unitsAdded, int indexOfUnitsInList)
            throws IOException{
        checkIfMoreUnitsAreRemovedThanAlreadyAdded(unitsToBeAdded, indexOfUnitsInList);
        int numberOfUnitsToBeRemoved = Integer.parseInt(unitsToBeAdded.getText().trim());
        nrOfSpecificUnits[indexOfUnitsInList] -= numberOfUnitsToBeRemoved;

        unitsAdded.setText("" + nrOfSpecificUnits[indexOfUnitsInList]);
        unitsToBeAdded.setText("");

        updateGold(-numberOfUnitsToBeRemoved, indexOfUnitsInList);
    }

    @FXML
    void onAddSpearFighterButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldSpearFighter);
        onUnitAddButton(textFieldSpearFighter, spearFighterNumber, 0);
        removeWarningLabel();
    }

    @FXML
    void onRemoveSpearFighterButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldSpearFighter);
        onUnitRemoveButton(textFieldSpearFighter, spearFighterNumber, 0);
        removeWarningLabel();
    }

    @FXML
    void onAddSwordsmanButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldSwordsman);
        onUnitAddButton(textFieldSwordsman, swordsmanNumber, 1);
        removeWarningLabel();
    }

    @FXML
    void onRemoveSwordsmanButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldSwordsman);
        onUnitRemoveButton(textFieldSwordsman, swordsmanNumber, 1);
        removeWarningLabel();
    }

    @FXML
    void onAddAxemanButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldAxeman);
        onUnitAddButton(textFieldAxeman, axemanNumber, 2);
        removeWarningLabel();
    }

    @FXML
    void onRemoveAxemanButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldAxeman);
        onUnitRemoveButton(textFieldAxeman, axemanNumber, 2);
        removeWarningLabel();
    }

    @FXML
    void onAddArcherButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldArcher);
        onUnitAddButton(textFieldArcher, archerNumber, 3);
        removeWarningLabel();
    }

    @FXML
    void onRemoveArcherButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldArcher);
        onUnitRemoveButton(textFieldArcher, archerNumber, 3);
        removeWarningLabel();
    }

    @FXML
    void onAddLightCavalryButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldLightCavalry);
        onUnitAddButton(textFieldLightCavalry, lightCavalryNumber, 4);
        removeWarningLabel();
    }

    @FXML
    void onRemoveLightCavalryButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldLightCavalry);
        onUnitRemoveButton(textFieldLightCavalry, lightCavalryNumber, 4);
        removeWarningLabel();
    }

    @FXML
    void onAddPaladinButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldPaladin);
        onUnitAddButton(textFieldPaladin, paladinNumber, 5);
        removeWarningLabel();
    }

    @FXML
    void onRemovePaladinButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldPaladin);
        onUnitRemoveButton(textFieldPaladin, paladinNumber, 5);
        removeWarningLabel();
    }

    @FXML
    void onInstantiateArmyButtonClicked() throws IOException{
        if (army == null){
            printTextToErrorMessage("Error: Name of Army needs to be set first!");
            throw new IOException("Error: Name of Army needs to be set first!");
        }
        List<Unit> units = new ArrayList<>();
        if (nrOfSpecificUnits[0] > 0) {
            units.addAll(UnitFactory
                    .getCertainAmountUnits(UnitType.INFANTRY_UNIT, "Infantry Units", nrOfSpecificUnits[0]));
        } if (nrOfSpecificUnits[1] > 0) {
            units.addAll(UnitFactory
                    .getCertainAmountUnits(UnitType.RANGED_UNIT, "Ranged Units", nrOfSpecificUnits[1]));
        } if (nrOfSpecificUnits[2] > 0) {
            units.addAll(UnitFactory
                    .getCertainAmountUnits(UnitType.CAVALRY_UNIT, "Cavalry Units", nrOfSpecificUnits[2]));
        } if (nrOfSpecificUnits[3] > 0) {
            units.addAll(UnitFactory
                    .getCertainAmountUnits(UnitType.COMMANDER_UNIT, "Commander Units", nrOfSpecificUnits[4]));
        }

        if (units.isEmpty()){
            printTextToErrorMessage("Error: Army needs units before instantiation!");
            throw new IOException("Error: Army needs units before instantiation!");
        }
        army.addAllUnits(units);

        ViewSwitcher.switchTo(View.START);
    }

    @FXML
    void onMenuButtonClicked(MouseEvent event){
        ViewSwitcher.switchTo(View.MAIN_PAGE);
    }

    private void updateGold(int amountOfUnits, int unitIndex){
        int costOfUnit = costPerUnit[unitIndex];
        int totalAmount = amountOfUnits * costOfUnit;
        gold -= totalAmount;
        numberOfGold.setText(String.valueOf(gold));
    }

    private void removeWarningLabel(){
        warningText.setText("");
    }

    private void printTextToErrorMessage(String errorMessage){
        warningText.setText(errorMessage);
    }
}
