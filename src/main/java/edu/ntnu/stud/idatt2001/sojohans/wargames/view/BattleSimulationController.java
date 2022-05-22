package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.BattleException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.Unit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Battle;
import edu.ntnu.stud.idatt2001.sojohans.wargames.io.readers.ArmyReader;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the 'Battle Simulation' page.
 */
public class BattleSimulationController {
    @FXML private Text archer1, archer2, army1Name, army2Name, axeman1, axeman2, filePath1, filePath2,
            filePathTitle1, filePathTitle2, lightCavalry1, lightCavalry2, paladin1, paladin2, spearFighter1,
            spearFighter2, swordsman1, swordsman2, totalUnits1, totalUnits2, warningText, updateText,
            nrOfAttacks1, nrOfAttacks2;
    @FXML private ComboBox<String> chooseArmyComboBox1, chooseArmyComboBox2, terrainComboBox;
    @FXML private Button loadFileButton1, loadFileButton2, resetButton, simulateButton, pauseButton;
    @FXML private ImageView terrainImageView, spearFighterLogo1, spearFighterLogo2, swordsmanLogo1,
            swordsmanLogo2, axemanLogo1, axemanLogo2, archerLogo1, archerLogo2, lightCavalryLogo1,
            lightCavalryLogo2, paladinLogo1, paladinLogo2;
    @FXML private TableView<Unit> tableViewArmy1, tableViewArmy2;
    @FXML private TableColumn<Unit, String> unitTypeArmy1, unitTypeArmy2;
    @FXML private TableColumn<Unit, Integer> unitHealthArmy1, unitHealthArmy2;
    private File fileArmy1, fileArmy2;
    private Army army1, army2;
    private Battle battle;
    private TerrainType terrainType;

    /**
     * Method initializes the page with content.
     * Local armies are read from the army overview file, listeners are set to combo boxes
     * and ToolTips are installed to Unit icons.
     * </p>
     */
    @FXML
    public void initialize(){
        //  Method reads all army file names from Army overview file.
        List<String> armiesInOverviewFile = new ArrayList<>();
        try {
            armiesInOverviewFile = ArmyReader.readArmyFileNamesFromOverviewFile();
        } catch (IOException exception) {
            if (exception.getMessage().equals("File could not be read: armyFilesOverview.csv is empty!")){
                printErrorMessage("Army overview is empty! Creating new armies is recommended!");
            } else {
                printErrorMessage(exception.getMessage());
            }
        }
        resetButton.setDisable(true);
        //  Listeners are then added to every combo box.
        setItemsAndListenerToTerrainComboBox();
        setItemsAndListenerToChooseArmyComboBox1(armiesInOverviewFile);
        setItemsAndListenerToChooseArmyComboBox2(armiesInOverviewFile);
        //  ToolTip is installed to each Unit icon.
        setToolTipToUnitLogos();
        initializeTableView();
    }

    /**
     * Method sets the TableColumns in the TableViews with informative values from units.
     */
    private void initializeTableView(){
        unitTypeArmy1.setCellValueFactory((TableColumn.CellDataFeatures<Unit, String> unitInfo) ->
                new SimpleObjectProperty<>(unitInfo.getValue().getName()));

        unitTypeArmy2.setCellValueFactory((TableColumn.CellDataFeatures<Unit, String> unitInfo) ->
                new SimpleObjectProperty<>(unitInfo.getValue().getName()));

        unitHealthArmy1.setCellValueFactory((TableColumn.CellDataFeatures<Unit, Integer> unitInfo) ->
                new SimpleObjectProperty<>(unitInfo.getValue().getHealth()));

        unitHealthArmy2.setCellValueFactory((TableColumn.CellDataFeatures<Unit, Integer> unitInfo) ->
                new SimpleObjectProperty<>(unitInfo.getValue().getHealth()));
    }

    /**
     * Method for starting a Battle simulation.
     * Simulation is only allowed to start if two different armies with units and a Terrain has been chosen.
     * Simulation then tells Battle it is able to start simulating, in case it was paused earlier.
     * A listener is then added to the Battle so that it may update the GUI with info during the simulation.
     */
    @FXML
    public void onSimulateButtonClicked(){
        if (army1 == null || army2 == null){
            printErrorMessage("Error: Battle cannot be started until both Armies have been chosen!");
        }
        if (!army1.hasUnits() || !army2.hasUnits()){
            printErrorMessage("Error: Armies need units to fight!");
        }
        if (army1.equals(army2)){
            printErrorMessage("Error: Battle cannot be fought between the same Army!");
        }
        if (terrainType == null){
            printErrorMessage("Error: Terrain must be chosen!");
        }
        else {
            try {
                removeErrorMessage();
                //  Tells Battle it is able to start simulating when simulate() is called.
                Battle.enableSimulate();
                //  If battle hasn't been instantiated yet and if it doesn't have a winner yet,
                //  then a new Battle is instantiated. This is so that a new Battle isn't instantiated
                //  when the user just wants to pause the Battle.
                if (battle == null || battle.getVictor() != null) {
                    battle = new Battle(army1, army2, terrainType);

                    // Listener is then added to Battle
                    battle.addListener(this::updateUnitNumbers);
                }
                //  Certain buttons are then disabled so errors won't occur.
                buttonStatusDuringSimulation();
                new Thread(() -> {
                    try {
                        printUpdateMessage("Simulation ongoing!");
                        battle.simulate();
                    } catch (BattleException exception) {
                        printErrorMessage(exception.getMessage());
                    }
                    Platform.runLater(() -> {
                        //  If Battle is paused, then a message will be displayed
                        if (battle.getVictor() == null){
                            printUpdateMessage("Battle paused");
                            swapToSimulateButton();
                            resetButton.setDisable(false);

                            //  Or if the Battle is finished, the victor will be displayed
                        } else if (battle.getVictor().equals(army1)) {
                            printUpdateMessage(army1.getName() + " won!");
                            buttonStatusAfterSimulation();
                        } else {
                            printUpdateMessage(army2.getName() + " won!");
                            buttonStatusAfterSimulation();
                        }
                    });
                }).start();
            } catch (BattleException exception){
                printErrorMessage(exception.getMessage());
            }
        }
    }

    /**
     * Method for updating GUI with Battle information.
     * Method is added as Listener to the Battle
     */
    private void updateUnitNumbers(){
        Platform.runLater(() -> {
            fillArmy1WithInfo();
            fillArmy2WithInfo();
        });
    }

    /**
     * Method for updating GUI with info about Army 1
     */
    private void fillArmy1WithInfo(){
        setInfoToArmies(army1Name, totalUnits1, spearFighter1, swordsman1, axeman1,
                archer1, lightCavalry1, paladin1, tableViewArmy1, army1);
    }

    /**
     * Method for updating GUI with info about Army 2
     */
    private void fillArmy2WithInfo(){
        setInfoToArmies(army2Name, totalUnits2, spearFighter2, swordsman2, axeman2,
                archer2, lightCavalry2, paladin2, tableViewArmy2, army2);
    }

    /**
     * Method for setting Army info to Text in GUI.
     * @param armyName Text containing name of the Army.
     * @param totalUnits Text containing total number of units in the Army.
     * @param spearFighter Text containing total number of SpearFighterUnits in the Army.
     * @param swordsman Text containing total number of SwordsmanUnits in the Army.
     * @param axeman Text containing total number of AxemanUnits in the Army.
     * @param archer Text containing total number of RangedUnits in the Army.
     * @param lightCavalry Text containing total number of CavalryUnits in the Army.
     * @param paladin Text containing total number of CommanderUnits in the Army.
     * @param armyTableView TableView containing information about individual units in Army
     * @param army Army which information is to be set from.
     */
    private void setInfoToArmies(Text armyName, Text totalUnits, Text spearFighter, Text swordsman, Text axeman,
                                 Text archer, Text lightCavalry, Text paladin, TableView<Unit> armyTableView,
                                 Army army) {
        armyName.setText(String.valueOf(army.getName()));
        totalUnits.setText(String.valueOf(army.getAllUnits().size()));
        spearFighter.setText(String.valueOf(army.getSpearFighterUnits().size()));
        swordsman.setText(String.valueOf(army.getSwordsmanUnits().size()));
        axeman.setText(String.valueOf(army.getAxemanUnits().size()));
        archer.setText(String.valueOf(army.getRangedUnits().size()));
        lightCavalry.setText(String.valueOf(army.getCavalryUnits().size()));
        paladin.setText(String.valueOf(army.getCommanderUnits().size()));
        if (battle != null) {
            nrOfAttacks1.setText(String.valueOf(battle.getArmyOneAttacks()));
            nrOfAttacks2.setText(String.valueOf(battle.getArmyTwoAttacks()));
        }
        // Sets information about each individual Unit in Army to the TableView.
        ObservableList<Unit> unitData = FXCollections.observableList(army.getAllUnits());
        armyTableView.setItems(unitData);

    }

    /**
     * Method sets items and listener to TerrainBox.
     * Listener waits for an item property to be set in the Terrain ComboBox,
     * so that an Image corresponding to the Terrain is set.
     * It also sets the TerrainType to the property 'terrainType'.
     */
    private void setItemsAndListenerToTerrainComboBox(){
        terrainComboBox.getItems().addAll("Forest", "Hill", "Plains");
        terrainComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    try {
                        //  When a value is chosen, an image is set
                        terrainImageView.setImage(new Image(geTerrainImageFilePath(newValue)));
                        //  The Terrain value is then stored in the 'terrainType' property as a TerrainType enum.
                        switch (newValue) {
                            case "Forest" -> terrainType = TerrainType.FOREST;
                            case "Plains" -> terrainType = TerrainType.PLAINS;
                            case "Hill" -> terrainType = TerrainType.HILL;
                        }
                    } catch (IOException exception) {
                        printErrorMessage(exception.getMessage());
                    }
                });
    }

    /**
     * Method for getting the Image path to each Terrain Image
     * @param terrainType Type of terrain to be shown as an image.
     * @return Path to the image file.
     * @throws IOException If terrain argument does not have an Image stored for it.
     */
    private String geTerrainImageFilePath(String terrainType) throws IOException {
        return switch (terrainType){
            case "Forest" -> "file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                    "wargames/images/forest.png";
            case "Plains" -> "file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                    "wargames/images/plains.png";
            case "Hill" -> "file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                    "wargames/images/hills.png";
            default -> throw new IOException("Terrain input not expected, could not find image file!");
        };
    }

    /**
     * Method sets all local army file names as items to the 'Choose Army' combo box number 1 and adds a listener.
     * When an item is selected (army file) the army file is read to an Army
     * and the both the Army and Army file path is stored.
     * GUI is then updated with the Army 1 info.
     * @param list List of local army file names from overview file.
     */
    private void setItemsAndListenerToChooseArmyComboBox1(List<String> list){
        //  Adds each army file name to combo box.
        list.forEach(s -> chooseArmyComboBox1.getItems().add(s));
        chooseArmyComboBox1.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    try {
                        clearArmy1Info();
                        //  Reads the respective army file and stores the army and file path
                        army1 = ArmyReader.readArmyFromLocalFileWithNameOfFile(newValue);
                        fileArmy1 = Utilities.convertStringToArmyFile(newValue);
                        //  Displays the file path along with the army info.
                        filePathTitle1.setVisible(true);
                        filePath1.setText(fileArmy1.getAbsolutePath());
                        fillArmy1WithInfo();
                        //  Removes any error or update message
                        removeErrorMessage();
                        removeUpdateMessage();
                    } catch (IOException exception) {
                        printErrorMessage(exception.getMessage());
                    }
                }
        );
    }

    /**
     * Method sets all local army file names as items to the 'Choose Army' combo box number 2 and adds a listener.
     * When an item is selected (army file) the army file is read to an Army
     * and the both the Army and Army file path is stored.
     * GUI is then updated with the Army 2 info.
     * @param list List of local army file names from overview file.
     */
    private void setItemsAndListenerToChooseArmyComboBox2(List<String> list){
        //  Adds each army file name to combo box.
        list.forEach(s -> chooseArmyComboBox2.getItems().add(s));
        chooseArmyComboBox2.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    try {
                        clearArmy2Info();
                        //  Reads the respective army file and stores the army and file path
                        army2 = ArmyReader.readArmyFromLocalFileWithNameOfFile(newValue);
                        fileArmy2 = Utilities.convertStringToArmyFile(newValue);
                        //  Displays the file path along with the army info.
                        filePathTitle2.setVisible(true);
                        filePath2.setText(fileArmy2.getAbsolutePath());
                        fillArmy2WithInfo();
                        //  Removes any error or update message
                        removeErrorMessage();
                        removeUpdateMessage();
                    } catch (IOException exception) {
                        printErrorMessage(exception.getMessage());
                    }
                }
        );
    }

    /**
     * Method for setting ToolTip to each Unit icon.
     */
    private void setToolTipToUnitLogos(){
        setToolTipToImageView(spearFighterLogo1, "Spear fighter; Attack: 15, Armor: 10");
        setToolTipToImageView(spearFighterLogo2, "Spear fighter; Attack: 15, Armor: 10");
        setToolTipToImageView(swordsmanLogo1, "Swordsman; Attack: 15, Armor: 10");
        setToolTipToImageView(swordsmanLogo2, "Swordsman; Attack: 15, Armor: 10");
        setToolTipToImageView(axemanLogo1, "Axeman; Attack: 15, Armor: 10");
        setToolTipToImageView(axemanLogo2, "Axeman; Attack: 15, Armor: 10");
        setToolTipToImageView(archerLogo1, "Archer; Attack: 15, Armor: 8");
        setToolTipToImageView(archerLogo2, "Archer; Attack: 15, Armor: 10");
        setToolTipToImageView(lightCavalryLogo1,"Light Cavalry; Attack: 20, Armor: 12");
        setToolTipToImageView(lightCavalryLogo2, "Light Cavalry; Attack: 20, Armor: 12");
        setToolTipToImageView(paladinLogo1, "Paladin; Attack: 25, Armor: 15");
        setToolTipToImageView(paladinLogo2, "Paladin; Attack: 25, Armor: 15");
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
     * Method for loading in Army file for Army 1 using FileChooser.
     */
    @FXML
    public void onLoadFileButton1Clicked(){
        //  Opens file chooser and waits for a file.
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        try {
            army1 = ArmyReader.readArmyFileWithPath(file);
            fileArmy1 = file;
            fillArmy1WithInfo();
            filePathTitle1.setVisible(true);
            filePath1.setText(fileArmy1.getAbsolutePath());
            removeUpdateMessage();
            printErrorMessage("Unregulated file, battle may not be fair!");
        } catch (IOException exception){
            printErrorMessage(exception.getMessage());
        }
    }

    /**
     * Method for loading in Army file for Army 2 using FileChooser.
     */
    @FXML
    public void onLoadFileButton2Clicked(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        try {
            army2 = ArmyReader.readArmyFileWithPath(file);
            fileArmy2 = file;
            fillArmy2WithInfo();
            filePathTitle2.setVisible(true);
            filePath2.setText(file.getAbsolutePath());
            removeUpdateMessage();
            printErrorMessage("Unregulated file, battle may not be fair!");
        } catch (IOException exception){
            printErrorMessage(exception.getMessage());
        }
    }

    /**
     * Method for resetting a Battle.
     * Resets the Armies to their original state by reading from their respective file again.
     * The GUI is then refreshed and battle is set back to null.
     */
    @FXML
    public void onResetButtonClicked(){
        try {
            buttonStatusAfterSimulation();
            army1 = ArmyReader.readArmyFileWithPath(fileArmy1);
            army2 = ArmyReader.readArmyFileWithPath(fileArmy2);
            fillArmy1WithInfo();
            fillArmy2WithInfo();
            simulateButton.setDisable(false);
            resetButton.setDisable(true);
            nrOfAttacks1.setText("");
            nrOfAttacks2.setText("");
            battle = null;
            removeUpdateMessage();
            removeErrorMessage();
        } catch (IOException exception){
            printErrorMessage(exception.getMessage());
        }
    }

    /**
     * Method for pausing a Battle.
     * Tells Battle to stop simulating for now.
     */
    @FXML
    public void onPauseButtonClicked(){
        Battle.shutdownSimulate();
    }

    /**
     * Redirects to the 'Unit Information' page.
     */
    @FXML
    public void onHereTextClicked(){
        ViewSwitcher.switchTo(View.UNIT_INFO);
    }

    /**
     * Method for enabling and disabling certain buttons when a Simulation is ongoing.
     */
    private void buttonStatusDuringSimulation(){
        swapToPauseButton();
        resetButton.setDisable(true);
        terrainComboBox.setDisable(true);
        chooseArmyComboBox1.setDisable(true);
        chooseArmyComboBox2.setDisable(true);
        loadFileButton1.setDisable(true);
        loadFileButton2.setDisable(true);
    }

    /**
     * Method for enabling and disabling certain buttons when a Simulation is finished.
     */
    private void buttonStatusAfterSimulation(){
        swapToSimulateButton();
        simulateButton.setDisable(true);
        resetButton.setDisable(false);
        terrainComboBox.setDisable(false);
        chooseArmyComboBox1.setDisable(false);
        chooseArmyComboBox2.setDisable(false);
        loadFileButton1.setDisable(false);
        loadFileButton2.setDisable(false);
    }

    /**
     * Method for swapping out the 'Simulate' button with the 'Pause' button.
     */
    private void swapToPauseButton(){
        simulateButton.setDisable(true);
        simulateButton.setVisible(false);
        simulateButton.setPrefWidth(0);

        pauseButton.setDisable(false);
        pauseButton.setVisible(true);
        pauseButton.setPrefWidth(111);
    }

    /**
     * Method for swapping out the 'Pause' button with the 'Simulate' button.
     */
    private void swapToSimulateButton(){
        simulateButton.setDisable(false);
        simulateButton.setVisible(true);
        simulateButton.setPrefWidth(111);

        pauseButton.setDisable(true);
        pauseButton.setVisible(false);
        pauseButton.setPrefWidth(0);
    }

    /**
     * Method for clearing GUI from info about Army 1
     */
    private void clearArmy1Info(){
        clearArmyInfo(army1Name, totalUnits1, spearFighter1, swordsman1, axeman1, archer1, lightCavalry1,
                paladin1, filePathTitle1, filePath1);
    }

    /**
     * Method for clearing GUI from info about Army 2
     */
    private void clearArmy2Info(){
        clearArmyInfo(army2Name, totalUnits2, spearFighter2, swordsman2, axeman2, archer2, lightCavalry2,
                paladin2, filePathTitle2, filePath2);
    }

    /**
     * Method for clearing Army info from Text in GUI.
     * @param armyName Text containing name of the Army.
     * @param totalUnits Text containing total number of units in the Army.
     * @param spearFighter Text containing total number of SpearFighterUnits in the Army.
     * @param swordsman Text containing total number of SwordsmanUnits in the Army.
     * @param axeman Text containing total number of AxemanUnits in the Army.
     * @param archer Text containing total number of RangedUnits in the Army.
     * @param lightCavalry Text containing total number of CavalryUnits in the Army.
     * @param paladin Text containing total number of CommanderUnits in the Army.
     * @param filePath File path from where the Army was read from.
     */
    private void clearArmyInfo(Text armyName, Text totalUnits, Text spearFighter, Text swordsman, Text axeman,
                               Text archer, Text lightCavalry, Text paladin, Text filePathTitle, Text filePath) {
        armyName.setText("");
        totalUnits.setText("");
        spearFighter.setText("");
        swordsman.setText("");
        axeman.setText("");
        archer.setText("");
        lightCavalry.setText("");
        paladin.setText("");
        filePathTitle.setVisible(false);
        filePath.setText("");
    }

    /**
     * Method for printing an error message to a Text in GUI.
     * @param errorMessage Error message to be displayed.
     */
    private void printErrorMessage(String errorMessage){
        warningText.setText(errorMessage);
    }

    /**
     * Method for printing an update message to a Text in GUI.
     * @param updateMessage Update message to be displayed.
     */
    private void printUpdateMessage(String updateMessage){
        updateText.setText(updateMessage);
    }

    /**
     * Method for removing an error message from GUI.
     */
    private void removeUpdateMessage(){
        updateText.setText("");
    }

    /**
     * Method for removing an update message from GUI.
     */
    private void removeErrorMessage(){
        warningText.setText("");
    }

    /**
     * Redirects to the 'Main Menu' page.
     * Method also tells Battle to stop simulating.
     */
    @FXML
    public void onMenuButtonClicked(){
        Battle.shutdownSimulate();
        ViewSwitcher.switchTo(View.MENU);
    }

    /**
     * Redirects to the 'Create Army' page.
     * Method also tells Battle to stop simulating.
     */
    @FXML
    public void onCreateArmyButtonClicked(){
        Battle.shutdownSimulate();
        ViewSwitcher.switchTo(View.CREATE_ARMY);
    }

    /**
     * Redirects to the 'View Armies' page.
     * Method also tells Battle to stop simulating.
     */
    @FXML
    public void onViewArmiesButtonClicked(){
        Battle.shutdownSimulate();
        ViewSwitcher.switchTo(View.VIEW_ARMIES);
    }

}
