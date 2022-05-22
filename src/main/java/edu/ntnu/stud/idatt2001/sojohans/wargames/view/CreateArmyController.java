package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitFactory;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.Unit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import edu.ntnu.stud.idatt2001.sojohans.wargames.io.readers.ArmyReader;
import edu.ntnu.stud.idatt2001.sojohans.wargames.io.writers.ArmyWriter;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 Controller class for the 'Create Army' page.
 */
public class CreateArmyController {

    @FXML private Text numberOfGold, costOfSpearFighter, costOfSwordsman, costOfAxeman, costOfArcher,
            costOfLightCavalry, costOfPaladin, spearFighterNumber, swordsmanNumber, axemanNumber,
            archerNumber, lightCavalryNumber, paladinNumber, warningText, armyNameText, armyName;
    @FXML private TextField textFieldArmyName, textFieldSpearFighter, textFieldSwordsman,textFieldAxeman,
            textFieldArcher, textFieldLightCavalry, textFieldPaladin;
    @FXML private Button instantiateArmy, setArmyNameButton, editArmyNameButton, addSpearFighterButton,
            removeSpearFighterButton, addSwordsmanButton, removeSwordsmanButton, addAxemanButton,
            removeAxemanButton, addArcherButton, removeArcherButton, addLightCavalryButton,
            removeLightCavalryButton, addPaladinButton, removePaladinButton;
    @FXML private ImageView spearFighterIcon, swordsmanIcon, axemanIcon, archerIcon, lightCavalryIcon,
            paladinIcon;

    private List<TextField> nrOfUnitsToBeAddedOrRemovedTextFields;
    private List<Text> nrOfUnitsAddedTexts;
    private List<Button> addUnitButtons, removeUnitButtons;
    private int[] costPerUnit, nrOfSpecificUnits;
    private int gold;
    private Army army, newArmy;
    private static File armyFile;

    /**
     Method for initializing the main page.
     Method sets the amount of gold the user has at disposal for purchasing units.
     Method also arranges list of unit information for later indexing,
     as well as setting action to each add and remove button.
     If an armyFile was set to the Controller, then the Army information
     will be set to the GUI, allowing for editing of the Army.
     */
    @FXML
    public void initialize(){
        addListenerToAddedUnitsAndUpdateGold();
        gold = 100000;  // Sets the amount of gold the user to 100000. In the future this will be changable.
        numberOfGold.setText(String.valueOf(gold));
        initializeLists();
        setActionsToButtons();
        fillCostsPerUnit();  // Sets cost to the array with the costs of each unit.
        showCostOfUnitInGUI();  // Shows cost of each unit in GUI
        disableButtons();  // Disables all add and remove buttons, as well as the "instantiate army" button.
        setToolTipToUnitLogos();
        if (armyFile != null){
            try {
                army = ArmyReader.readArmyFileWithPath(armyFile);
                fillWithInfoIfArmyIsToBeEdited();
            } catch (IOException exception){
                printErrorMessage(exception.getMessage());
            }
        }
    }

    /**
     * Method adds listener to every Text object that contains the number of each Unit type added to the Army.
     * Whenever the number of unit is changed (added or removed), it updates the array containing the number of
     * each unit with the current number and calculates and updates gold balance.
     */
    private void addListenerToAddedUnitsAndUpdateGold(){
        spearFighterNumber.textProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    nrOfSpecificUnits[0] = Integer.parseInt(spearFighterNumber.getText());
                    updateGold();
                }
        );
        swordsmanNumber.textProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    nrOfSpecificUnits[1] = Integer.parseInt(swordsmanNumber.getText());
                    updateGold();
                }
        );
        axemanNumber.textProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    nrOfSpecificUnits[2] = Integer.parseInt(axemanNumber.getText());
                    updateGold();
                }
        );
        archerNumber.textProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    nrOfSpecificUnits[3] = Integer.parseInt(archerNumber.getText());
                    updateGold();
                }
        );
        lightCavalryNumber.textProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    nrOfSpecificUnits[4] = Integer.parseInt(lightCavalryNumber.getText());
                    updateGold();
                }
        );
        paladinNumber.textProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    nrOfSpecificUnits[5] = Integer.parseInt(paladinNumber.getText());
                    updateGold();
                }
        );
    }

    /**
     * Method for initializing lists.
     */
    private void initializeLists(){
        nrOfSpecificUnits = new int[6];  // Array of the number of each unit.
        costPerUnit = new int[6];   // Array of the cost of each unit.

        nrOfUnitsAddedTexts = new ArrayList<>(Arrays.asList(spearFighterNumber, swordsmanNumber, axemanNumber,
                archerNumber, lightCavalryNumber, paladinNumber));
        nrOfUnitsToBeAddedOrRemovedTextFields = new ArrayList<>(Arrays.asList(textFieldSpearFighter,
                textFieldSwordsman, textFieldAxeman, textFieldArcher, textFieldLightCavalry, textFieldPaladin));

        // Arrays of all the add and remove unit buttons.
        addUnitButtons = new ArrayList<>(Arrays.asList(addSpearFighterButton, addSwordsmanButton, addAxemanButton,
                addArcherButton, addLightCavalryButton, addPaladinButton));
        removeUnitButtons = new ArrayList<>(Arrays.asList(removeSpearFighterButton, removeSwordsmanButton,
                removeAxemanButton, removeArcherButton, removeLightCavalryButton, removePaladinButton));
    }

    /**
     * Method sets actions to every Add and Remove Unit button.
     * Each button is given an action with the same index argument as the
     * corresponding Unit's index in 'costPerUnit' and 'nrOfSpecificUnits' arrays.
     */
    private void setActionsToButtons(){
        for (int i = 0; i < addUnitButtons.size() && i < removeUnitButtons.size(); i++){
            int index = i;
            addUnitButtons.get(index).setOnAction(event -> onAddUnitButtonClicked(index));
            removeUnitButtons.get(index).setOnAction(event -> onRemoveUnitButtonClicked(index));
        }
    }

    /**
     * Method for setting cost of each unit to an Array using the same index per unit type as the
     * other lists in the Controller.
     */
    private void fillCostsPerUnit(){
        costPerUnit[0] = 50; // Sets cost: 10 to SpearFighterUnit
        costPerUnit[1] = 50; // Sets cost: 15 to SwordsmanUnit
        costPerUnit[2] = 60; // Sets cost: 20 to AxemanUnit
        costPerUnit[3] = 120; // Sets cost: 15 to RangedUnit
        costPerUnit[4] = 300; // Sets cost: 30 to CavalryUnit
        costPerUnit[5] = 5000; // Sets cost: 100 to CommanderUnit
    }

    /**
     * Method for displaying cost of each unit in GUI.
     */
    private void showCostOfUnitInGUI(){
        costOfSpearFighter.setText(String.valueOf(costPerUnit[0]));
        costOfSwordsman.setText(String.valueOf(costPerUnit[1]));
        costOfAxeman.setText(String.valueOf(costPerUnit[2]));
        costOfArcher.setText(String.valueOf(costPerUnit[3]));
        costOfLightCavalry.setText(String.valueOf(costPerUnit[4]));
        costOfPaladin.setText(String.valueOf(costPerUnit[5]));
    }

    /**
     * Method for disabling all add and remove buttons, as well as the "instantiate army" button.
     */
    private void disableButtons(){
        addUnitButtons.forEach(button -> button.setDisable(true));
        removeUnitButtons.forEach(button -> button.setDisable(true));
        instantiateArmy.setDisable(true);
    }

    /**
     * Method for setting ToolTip to each Unit icon.
     */
    private void setToolTipToUnitLogos(){
        setToolTipToImageView(spearFighterIcon, "Spear fighter; Attack: 15, Armor: 10");
        setToolTipToImageView(swordsmanIcon, "Swordsman; Attack: 15, Armor: 10");
        setToolTipToImageView(axemanIcon, "Axeman; Attack: 15, Armor: 10");
        setToolTipToImageView(archerIcon, "Archer; Attack: 15, Armor: 8");
        setToolTipToImageView(lightCavalryIcon,"Light Cavalry; Attack: 20, Armor: 12");
        setToolTipToImageView(paladinIcon, "Paladin; Attack: 25, Armor: 15");

    }

    /**
     * Method for customizing a ToolTip, setting text and installing ToolTip to an ImageView.
     * @param imageView ImageView where ToolTip is to be installed (Unit icon).
     * @param str Information to be displayed.
     */
    private void setToolTipToImageView(ImageView imageView, String str){
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-background-color: #240000; -fx-text-fill: white; -fx-font: 16px Arial;" +
                "-fx-font-weight: Bold;");
        tooltip.setText(str);
        Tooltip.install(imageView, tooltip);
    }

    /**
     * Method for filling GUI with Army information if army file was sent to the controller.
     */
    private void fillWithInfoIfArmyIsToBeEdited(){
        newArmy = new Army(army.getName());
        editNameTextSwap();
        enableButtons();
        armyName.setText(army.getName());
        spearFighterNumber.setText(String.valueOf(army.getSpearFighterUnits().size()));
        swordsmanNumber.setText(String.valueOf(army.getSwordsmanUnits().size()));
        axemanNumber.setText(String.valueOf(army.getAxemanUnits().size()));
        archerNumber.setText(String.valueOf(army.getRangedUnits().size()));
        lightCavalryNumber.setText(String.valueOf(army.getCavalryUnits().size()));
        paladinNumber.setText(String.valueOf(army.getCommanderUnits().size()));
    }

    /**
     * Method updates the current balance the user has depending on number of units bought.
     */
    private void updateGold(){
        int totalCostOfSpearFighter = costPerUnit[0]*nrOfSpecificUnits[0];
        int totalCostOfSwordsman = costPerUnit[1]*nrOfSpecificUnits[1];
        int totalCostOfAxeman = costPerUnit[2]*nrOfSpecificUnits[2];
        int totalCostOfArcher = costPerUnit[3]*nrOfSpecificUnits[3];
        int totalCostOfLightCavalry = costPerUnit[4]*nrOfSpecificUnits[4];
        int totalCostOfPaladin = costPerUnit[5]*nrOfSpecificUnits[5];
        int totalCost = totalCostOfSpearFighter + totalCostOfSwordsman + totalCostOfAxeman + totalCostOfArcher
                + totalCostOfLightCavalry + totalCostOfPaladin;
        gold = 100000 - totalCost;
        numberOfGold.setText(String.valueOf(gold));
    }

    /**
     * Redirects to the 'Unit Information' page.
     */
    @FXML
    public void onHereTextClicked(){
        ViewSwitcher.switchTo(View.UNIT_INFO);
    }
    /**
     Method for setting army name in GUI.
     Checks if army name is valid and if there already is an army file under the name.
     The method then activates the add and remove buttons after the name is set,
     as well as the "instantiate army" button.
     */
    @FXML
    private void onSetArmyNameButtonClicked(){
        // Checks if anything was typed into the text field.
        if (textFieldArmyName.getText() == null || textFieldArmyName.getText().trim().isEmpty()){
            printErrorMessage("Error: Name of Army is empty!");
        }
        // Checks if any non-alphanumeric symbols where typed into the text field.
        else if (Utilities.checkIfStringContainsAnyNonAlphaNumericCharacters(textFieldArmyName.getText())){
            printErrorMessage("Error: Army name can only contain alpha-numeric symbols!");
        }
        // Checks if there already exists an army file under the name that was typed.
        else if (ArmyReader.doesArmyFileExist(textFieldArmyName.getText().trim())){
            printErrorMessage("Error: There already exists an army under this name!");
        }
        /*
        If none of the checks were positive, then army name is set to the Army object.
        Button and text field are then swapped out for the "Edit Army Name" format.
        Add and remove buttons, as well as the instantiate button is the activated for usage.
         */
        else {
            newArmy = new Army(textFieldArmyName.getText());
            editNameTextSwap();
            removeWarningLabel();
            enableButtons();
        }
    }

    /**
     Method for editing army name in GUI.
     Swaps back to the format of setting army name.
     Also disables the add and remove buttons.
     */
    @FXML
    public void onEditArmyNameButtonClicked(){
        setNameSwap();
        newArmy = null;
        removeWarningLabel();
        disableButtons();
    }

    /**
     Method for swapping to the format of setting army name.
     Method disables and sets invisible to button and text that is shown when an army name has been set.
     Method then enables button, text and text field used to set name of army.
     */
    private void setNameSwap(){
        // Sets the army name text to blank, disables it and makes it invisible.
        armyName.setVisible(false);
        armyName.setDisable(true);
        armyName.setText("");

        // Disables the "Edit Army Name" button and makes it invisible.
        editArmyNameButton.setVisible(false);
        editArmyNameButton.setDisable(true);
        editArmyNameButton.setPrefWidth(0);

        // Enables the "Army name: " text and makes it visible.
        armyNameText.setText("Army name:");
        armyNameText.setVisible(true);
        armyNameText.setDisable(false);

        // Enables the army name text field, makes it visible and sets a preferred width.
        textFieldArmyName.setVisible(true);
        textFieldArmyName.setDisable(false);
        textFieldArmyName.setPrefWidth(325);

        // Enables the "Set Army Name" button, makes it visible and sets a preferred width.
        setArmyNameButton.setVisible(true);
        setArmyNameButton.setDisable(false);
        setArmyNameButton.setPrefWidth(173);
    }

    /**
     Method for swapping to the format of army name set / editing army name.
     Method disables and sets invisible to button, text and text field that is shown when army name has not been set.
     Method then enables the text that shows the current army name, and the button that gives the possibility of
     setting a new army name.
     */
    private void editNameTextSwap(){
        // Disables the "Army name: " text and makes it invisible.
        armyNameText.setVisible(false);
        armyNameText.setDisable(true);
        armyNameText.setText("");

        // Disables the army name text field and makes it invisible.
        textFieldArmyName.setText("");
        textFieldArmyName.setPrefWidth(0);
        textFieldArmyName.setVisible(false);
        textFieldArmyName.setDisable(true);

        // Disables the "Set Army Name" button and makes it invisible.
        setArmyNameButton.setVisible(false);
        setArmyNameButton.setDisable(true);
        setArmyNameButton.setPrefWidth(0);

        // Shows the army name that has been set.
        armyName.setVisible(true);
        armyName.setDisable(false);
        armyName.setText(newArmy.getName());

        // Enables the "Edit Army Name" button and makes it visible.
        editArmyNameButton.setVisible(true);
        editArmyNameButton.setDisable(false);
        editArmyNameButton.setPrefWidth(173);

    }

    /**
     Method for adding number of units given in GUI, to the array with the total number of each respective unit.
     Method checks if input value is valid first (A number and enough gold),
     then adds the given number of the respective unit to the 'nrOfSpecificUnits' array.
     Gold is then updated by the listener set to the total number of the specific unit.
     * @param indexOfUnit index of the unit that is being added.
     */
    private void onAddUnitButtonClicked(int indexOfUnit){
        if (checkIfTextFieldValuesAreValidBoolean(nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit))
                && checkIfEnoughCurrencyForMoreUnitsBoolean(indexOfUnit)) {

            int numberOfUnitsToBeAdded = Integer.parseInt(nrOfUnitsToBeAddedOrRemovedTextFields
                    .get(indexOfUnit).getText().trim());
            int unitsAddedInTotalOfThisType = nrOfSpecificUnits[indexOfUnit] + numberOfUnitsToBeAdded;
            nrOfUnitsAddedTexts.get(indexOfUnit).setText(String.valueOf(unitsAddedInTotalOfThisType));
            nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit).setText("");
            removeWarningLabel();
        }
    }

    /**
     Method for removing number of units given in GUI, from the array with the total number of each respective unit.
     Method checks if input value is valid first (A number and if not removing more than added),
     then removes the given number of the respective unit from the 'nrOfSpecificUnits' array.
     Gold is then updated by the listener set to the total number of the specific unit.
     * @param indexOfUnit index of the unit in the arrays concerning unit data.
     */
    private void onRemoveUnitButtonClicked(int indexOfUnit){
        if (checkIfTextFieldValuesAreValidBoolean(nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit))
                && checkIfMoreUnitsAreNotRemovedThanAlreadyAddedBoolean(indexOfUnit)){

            int numberOfUnitsToBeRemoved = Integer.parseInt(nrOfUnitsToBeAddedOrRemovedTextFields
                    .get(indexOfUnit).getText().trim());
            int unitsAddedInTotalOfThisType = nrOfSpecificUnits[indexOfUnit] - numberOfUnitsToBeRemoved;
            nrOfUnitsAddedTexts.get(indexOfUnit).setText(String.valueOf(unitsAddedInTotalOfThisType));
            nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit).setText("");
            removeWarningLabel();
        }
    }


    /**
     Method for checking if the text fields regarding the adding and removing of units,
     have valid values.
     * @param textField text field that contains the data to be checked
     * @return true if no invalid values, false if not.
     */
    private boolean checkIfTextFieldValuesAreValidBoolean(TextField textField){
        boolean bool = true;
        if (textField.getText().trim().isEmpty()){
            printErrorMessage("Error: Number of units is empty!");
            bool = false;
        }
        else if (Utilities.checkIfStringContainsCharactersOtherThanNumbers(textField.getText().trim())){
            printErrorMessage("Error: Number of units has to be a positive integer!");
            bool = false;
        }
        return bool;
    }

    /**
     Method checking if user have enough gold to add more units.
     * @param indexOfUnits index of unit in the arrays concerning unit data.
     * @return true if user have enough gold, false if not.
     */
    private boolean checkIfEnoughCurrencyForMoreUnitsBoolean(int indexOfUnits){
        boolean bool = true;
        int numberOfUnits = Integer.parseInt
                (nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnits).getText().trim());
        int cost = costPerUnit[indexOfUnits];
        int totalSum = numberOfUnits*cost;

        if ((gold - totalSum) < 0){
            printErrorMessage("Error: Cannot add " + numberOfUnits + " units, not enough Gold!");
            bool = false;
        }

        return bool;
    }

    /**
     Method for checking if user is trying to remove more units than what has been added.
     * @param indexOfUnit index of unit in the arrays concerning unit data
     * @return true if user is not trying to remove more than added, false if not.
     */
    private boolean checkIfMoreUnitsAreNotRemovedThanAlreadyAddedBoolean(int indexOfUnit){
        boolean bool = true;
        int numberOfUnits = Integer.parseInt
                (nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit).getText().trim());
        if (nrOfSpecificUnits[indexOfUnit] - numberOfUnits < 0){
            printErrorMessage("Error: Cannot remove " + numberOfUnits +
                    " units of this type, more than registered!");
            bool = false;
        }
        return bool;
    }

    /**
     * Method for instantiating an Army and writing it to file.
     * Method uses the UnitFactory to create the amount of requested units before adding it to the Army.
     */
    @FXML
    void onInstantiateArmyButtonClicked(){
        List<Unit> units = new ArrayList<>();
        if (newArmy == null){
            printErrorMessage("Error: Name of Army needs to be set first!");
        }
        else {
            // Method uses UnitFactory to create units of requested type to be added to list.
            if (nrOfSpecificUnits[0] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountOfUnits(
                                UnitType.SPEAR_FIGHTER_UNIT, "Spear fighter", 100, nrOfSpecificUnits[0]));
            }
            if (nrOfSpecificUnits[1] > 0){
                units.addAll(UnitFactory
                        .getCertainAmountOfUnits(
                                UnitType.SWORDSMAN_UNIT, "Swordsman", 100, nrOfSpecificUnits[1]));
            }
            if (nrOfSpecificUnits[2] > 0){
                units.addAll(UnitFactory
                        .getCertainAmountOfUnits(
                                UnitType.AXEMAN_UNIT, "Axeman", 100, nrOfSpecificUnits[2]));
            }
            if (nrOfSpecificUnits[3] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountOfUnits(
                                UnitType.RANGED_UNIT, "Archer", 100, nrOfSpecificUnits[3]));
            }
            if (nrOfSpecificUnits[4] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountOfUnits(
                                UnitType.CAVALRY_UNIT, "Light Cavalry", 100, nrOfSpecificUnits[4]));
            }
            if (nrOfSpecificUnits[5] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountOfUnits(
                                UnitType.COMMANDER_UNIT, "Paladin", 100, nrOfSpecificUnits[5]));
            }
        }
        if (units.isEmpty()){
            printErrorMessage("Error: Army needs units before instantiation!");
        } else {
            newArmy.addAllUnits(units);
            try {
                //  If an Army was sent to be edited, then the old armyFile is deleted,
                //  file name removed from army overview file and both the 'army' and 'armyFile'
                //  properties are set to null so that they are not loaded in later
                //  if the user wants to access the page again.
                if (army != null){
                    if (armyFile.delete()) {
                        ArmyWriter.removeArmyFileNameFromOverviewFile
                                (Utilities.convertStringToFileName(army.getName()));
                        armyFile = null;
                        army = null;
                    }
                }
                // A new army file is then written
                ArmyWriter.writeArmyToFile(newArmy, newArmy.getName());
            } catch (IOException exception){
                exception.printStackTrace();
            } finally {
                // All info is then wiped
                wipeAllInfo();
                printErrorMessage("Army was saved to file");
            }
        }
    }

    /**
     * Method for wiping all info from GUI
     */
    private void wipeAllInfo(){
        armyFile = null;
        army = null;
        newArmy = null;
        armyName.setText("0");
        setNameSwap();
        spearFighterNumber.setText("0");
        swordsmanNumber.setText("0");
        axemanNumber.setText("0");
        archerNumber.setText("0");
        lightCavalryNumber.setText("0");
        paladinNumber.setText("0");
        disableButtons();
    }

    /**
     * Method for removing error message in GUI.
     */
    private void removeWarningLabel(){
        warningText.setText("");
    }

    /**
     * Method for printing error message to GUI.
     * @param errorMessage Error message to be displayed.
     */
    private void printErrorMessage(String errorMessage){
        warningText.setText(errorMessage);
    }

    /**
     * Method for enabling all Add and Remove buttons, as well as the "Instantiate Army" button.
     */
    private void enableButtons(){
        addUnitButtons.forEach(button -> button.setDisable(false));
        removeUnitButtons.forEach(button -> button.setDisable(false));
        instantiateArmy.setDisable(false);
    }

    /**
     * Static method for setting name of an Army File to be read at initialization of the page.
     * Used when sending Army File from the 'View Armies' page when the user wants to edit said Army.
     * @param armyFile Army File the user wants to read from and edit.
     */
    public static void setArmyFile(File armyFile) {
        CreateArmyController.armyFile = armyFile;
    }

    /**
     * Redirects to the 'Main Menu' page.
     * Army File is set to null so that the page is not loaded with the same info later.
     */
    @FXML
    public void onMenuButtonClicked(){
        armyFile = null;
        ViewSwitcher.switchTo(View.MENU);
    }

    /**
     * Redirects to the 'Battle Simulation' page.
     * Army File is set to null so that the page is not loaded with the same info later.
     */
    @FXML
    public void onSimulateButtonClicked(){
        armyFile = null;
        ViewSwitcher.switchTo(View.BATTLE_SIMULATION);
    }

    /**
     * Redirects to the 'View Armies' page.
     * Army File is set to null so that the page is not loaded with the same info later.
     */
    @FXML
    public void onViewArmiesButtonClicked(){
        armyFile = null;
        ViewSwitcher.switchTo(View.VIEW_ARMIES);
    }
}