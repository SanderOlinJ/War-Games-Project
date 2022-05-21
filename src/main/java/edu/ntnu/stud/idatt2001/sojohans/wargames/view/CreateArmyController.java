package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitFactory;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.Unit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Battle;
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
 Controller class for Create Army page
 */
public class CreateArmyController {

    @FXML private Text numberOfGold;
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
    @FXML private ImageView spearFighterIcon;
    @FXML private ImageView swordsmanIcon;
    @FXML private ImageView axemanIcon;
    @FXML private ImageView archerIcon;
    @FXML private ImageView lightCavalryIcon;
    @FXML private ImageView paladinIcon;

    private List<TextField> nrOfUnitsToBeAddedOrRemovedTextFields;
    private List<Text> nrOfUnitsAddedTexts;
    private List<Button> addUnitButtons;
    private List<Button> removeUnitButtons;
    private int[] costPerUnit;
    private int[] nrOfSpecificUnits;
    private int gold;
    private Army army;
    private Army newArmy;
    private static File armyFile;

    /**
     Method for initializing the main page.
     Method sets the amount of gold the user has at disposal for purchasing units.
     Method also arranges list of unit information for later indexing,
     as well as setting action to each add and remove button.
     */
    @FXML
    public void initialize(){
        addListenerToAddedUnitsAndUpdateGold();
        gold = 100000;  // Sets the amount of gold the user to 100000. In the future this would be an option.
        numberOfGold.setText(String.valueOf(gold));
        nrOfSpecificUnits = new int[6];  // Array of the number of each unit.
        costPerUnit = new int[6];   // Array of the cost of each unit.

        nrOfUnitsAddedTexts = new ArrayList<>(Arrays.asList(spearFighterNumber, swordsmanNumber, axemanNumber,
                archerNumber, lightCavalryNumber, paladinNumber));
        nrOfUnitsToBeAddedOrRemovedTextFields = new ArrayList<>(Arrays.asList(textFieldSpearFighter, textFieldSwordsman,
                textFieldAxeman, textFieldArcher, textFieldLightCavalry, textFieldPaladin));

        // Arrays of all the add and remove unit buttons.
        addUnitButtons = new ArrayList<>(Arrays.asList(addSpearFighterButton, addSwordsmanButton, addAxemanButton,
                addArcherButton, addLightCavalryButton, addPaladinButton));
        removeUnitButtons = new ArrayList<>(Arrays.asList(removeSpearFighterButton, removeSwordsmanButton,
                removeAxemanButton, removeArcherButton, removeLightCavalryButton, removePaladinButton));

        /*
        Set an Add Unit or Remove Unit action to each button.
        Each button is given the same index as the 'costPerUnit' and 'nrOfSpecificUnits'.
        This is for easier locating of relevant data to each unit.
        Example:
        Spear fighter has the index of 0.
        This means the cost of the unit is stored in the 'costPerUnit' array at index 0,
        and the number of added spear fighters so far is stored in the 'nrOfSpecificUnits' array at index 0.
        The buttons for adding and removing spear fighters is also given the index of 0,
        as well as the text field for typing in how many spear fighters the user wants.
         */

        for (int i = 0; i < addUnitButtons.size() && i < removeUnitButtons.size(); i++){
            int index = i;
            addUnitButtons.get(index).setOnAction(event -> onAddUnitButtonClicked(index));
            removeUnitButtons.get(index).setOnAction(event -> onRemoveUnitButtonClicked(index));
        }

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
    public void onSetArmyNameButtonClicked(){
        // Checks if anything was typed into the text field.
        if (textFieldArmyName.getText() == null || textFieldArmyName.getText().trim().isEmpty()){
            printErrorMessage("Error: Name of Army is empty!");
        }
        // Checks if any non-alphanumeric symbols where typed into the text field.
        else if (Utilities.checkIfStringContainsAnyNonAlphaNumericSymbols(textFieldArmyName.getText())){
            printErrorMessage("Error: Army name can only contain alpha-numeric symbols!");
        }
        // Checks if there already exists an army file under the name that was typed.
        else if (Utilities.doesArmyFileExist(textFieldArmyName.getText().trim())){
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
     Method then updates the gold spent to the total balance.
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
     Method then updates the gold returned to the total balance.
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
            printErrorMessage("Error: Number of units is empty!"); //Prints error message to GUI
            bool = false;
        }
        else if (Utilities.checkIfStringContainsSymbolsOtherThanNumbers(textField.getText().trim())){
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
        int numberOfUnits = Integer.parseInt(nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnits).getText().trim());
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
        int numberOfUnits = Integer.parseInt(nrOfUnitsToBeAddedOrRemovedTextFields.get(indexOfUnit).getText().trim());
        if (nrOfSpecificUnits[indexOfUnit] - numberOfUnits < 0){
            printErrorMessage("Error: Cannot remove " + numberOfUnits +
                    " units of this type, more than registered!");
            bool = false;
        }
        return bool;
    }

    /**
     * Method for setting cost of each unit.
     */
    private void fillCostsPerUnit(){
        costPerUnit[0] = 50; // Sets cost: 10 to spear fighter
        costPerUnit[1] = 50; // Sets cost: 15 to swordsman
        costPerUnit[2] = 60; // Sets cost: 20 to axeman
        costPerUnit[3] = 120; // Sets cost: 15 to archer
        costPerUnit[4] = 300; // Sets cost: 30 to light cavalry
        costPerUnit[5] = 5000; // Sets cost: 100 to paladin
    }


    /**
     * Method for displaying cost of each unit in GUI.
     */
    private void showCostOfUnitInGUI(){
        costOfSpearFighter.setText(costPerUnit[0] + "");
        costOfSwordsman.setText(costPerUnit[1] + "");
        costOfAxeman.setText(costPerUnit[2] + "");
        costOfArcher.setText(costPerUnit[3] + "");
        costOfLightCavalry.setText(costPerUnit[4] + "");
        costOfPaladin.setText(costPerUnit[5] + "");
    }


    /**
     * Method for instantiating an army and writing it to file, when user deems the army ready to be instantiated.
     */
    @FXML
    void onInstantiateArmyButtonClicked(){
        List<Unit> units = new ArrayList<>();
        if (newArmy == null){
            printErrorMessage("Error: Name of Army needs to be set first!");
        }
        else {
            // Method uses factory to create units of requested type to be added to list.
            if (nrOfSpecificUnits[0] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountUnits(UnitType.SPEAR_FIGHTER_UNIT, "Spear fighter", nrOfSpecificUnits[0]));
            }
            if (nrOfSpecificUnits[1] > 0){
                units.addAll(UnitFactory
                        .getCertainAmountUnits(UnitType.SWORDSMAN_UNIT, "Swordsman", nrOfSpecificUnits[1]));
            }
            if (nrOfSpecificUnits[2] > 0){
                units.addAll(UnitFactory
                        .getCertainAmountUnits(UnitType.AXEMAN_UNIT, "Axeman", nrOfSpecificUnits[2]));
            }
            if (nrOfSpecificUnits[3] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountUnits(UnitType.RANGED_UNIT, "Archer", nrOfSpecificUnits[3]));
            }
            if (nrOfSpecificUnits[4] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountUnits(UnitType.CAVALRY_UNIT, "Light Cavalry", nrOfSpecificUnits[4]));
            }
            if (nrOfSpecificUnits[5] > 0) {
                units.addAll(UnitFactory
                        .getCertainAmountUnits(UnitType.COMMANDER_UNIT, "Paladin", nrOfSpecificUnits[5]));
            }
        }

        if (units.isEmpty()){
            printErrorMessage("Error: Army needs units before instantiation!");
        } else {
            newArmy.addAllUnits(units);
            try {
                if (army != null){
                    if (armyFile.delete()) {
                        ArmyWriter.removeArmyFileNameFromOverviewFile(Utilities.convertStringToFileName(army.getName()));
                        armyFile = null;
                        army = null;
                    }
                }
                ArmyWriter.writeArmyToFile(newArmy, newArmy.getName());
            } catch (IOException exception){
                exception.printStackTrace();
            } finally {
                wipeAllInfo();
                printErrorMessage("Army was saved to file");
            }
        }
    }

    private void setToolTipToUnitLogos(){
        setToolTipToImageView(spearFighterIcon, "Spear fighter; Attack: 15, Armor: 10");
        setToolTipToImageView(swordsmanIcon, "Swordsman; Attack: 15, Armor: 10");
        setToolTipToImageView(axemanIcon, "Axeman; Attack: 15, Armor: 10");
        setToolTipToImageView(archerIcon, "Archer; Attack: 15, Armor: 8");
        setToolTipToImageView(lightCavalryIcon,"Light Cavalry; Attack: 20, Armor: 12");
        setToolTipToImageView(paladinIcon, "Paladin; Attack: 25, Armor: 15");

    }
    private void setToolTipToImageView(ImageView imageView, String str){
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-background-color: #240000; -fx-text-fill: white; -fx-font: 16px Arial;" +
                "-fx-font-weight: Bold;");
        tooltip.setText(str);
        Tooltip.install(imageView, tooltip);
    }

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
     * @param errorMessage error message to be displayed.
     */
    private void printErrorMessage(String errorMessage){
        warningText.setText(errorMessage);
    }

    /**
     * Method for enabling all add and remove buttons, as well as the "instantiate army" button.
     */
    private void enableButtons(){
        addUnitButtons.forEach(button -> button.setDisable(false));
        removeUnitButtons.forEach(button -> button.setDisable(false));
        instantiateArmy.setDisable(false);
    }

    /**
     * Method for disabling all add and remove buttons, as well as the "instantiate army" button.
     */
    private void disableButtons(){
        addUnitButtons.forEach(button -> button.setDisable(true));
        removeUnitButtons.forEach(button -> button.setDisable(true));
        instantiateArmy.setDisable(true);
    }

    public static void setArmyFile(File armyFile) {
        CreateArmyController.armyFile = armyFile;
    }


    @FXML
    public void onMenuButtonClicked(){
        armyFile = null;
        ViewSwitcher.switchTo(View.MENU);
    }

    @FXML
    public void onSimulateButtonClicked(){
        armyFile = null;
        Battle.startBattle();
        ViewSwitcher.switchTo(View.BATTLE_SIMULATION);
    }

    @FXML
    public void onViewArmiesButtonClicked(){
        armyFile = null;
        ViewSwitcher.switchTo(View.VIEW_ARMIES);
    }
}