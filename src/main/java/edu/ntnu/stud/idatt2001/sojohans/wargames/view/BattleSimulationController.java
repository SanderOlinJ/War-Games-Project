package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.BattleException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Battle;
import edu.ntnu.stud.idatt2001.sojohans.wargames.io.readers.ArmyReader;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BattleSimulationController {
    @FXML private Text archer1;
    @FXML private Text archer2;
    @FXML private Text army1Name;
    @FXML private Text army2Name;
    @FXML private Text axeman1;
    @FXML private Text axeman2;
    @FXML private ComboBox<String> chooseArmyComboBox1;
    @FXML private ComboBox<String> chooseArmyComboBox2;
    @FXML private Text filePath1;
    @FXML private Text filePath2;
    @FXML private Text filePathTitle1;
    @FXML private Text filePathTitle2;
    @FXML private Text lightCavalry1;
    @FXML private Text lightCavalry2;
    @FXML private Button loadFileButton1;
    @FXML private Button loadFileButton2;
    @FXML private Text paladin1;
    @FXML private Text paladin2;
    @FXML private Button resetButton;
    @FXML private Button simulateButton;
    @FXML private Button pauseButton;
    @FXML private Text spearFighter1;
    @FXML private Text spearFighter2;
    @FXML private Text swordsman1;
    @FXML private Text swordsman2;
    @FXML private ComboBox<String> terrainComboBox;
    @FXML private ImageView terrainImageView;
    @FXML private Text totalUnits1;
    @FXML private Text totalUnits2;
    @FXML private Text warningText;
    @FXML private Text updateText;
    @FXML private ImageView spearFighterLogo1;
    @FXML private ImageView spearFighterLogo2;
    @FXML private ImageView swordsmanLogo1;
    @FXML private ImageView swordsmanLogo2;
    @FXML private ImageView axemanLogo1;
    @FXML private ImageView axemanLogo2;
    @FXML private ImageView archerLogo1;
    @FXML private ImageView archerLogo2;
    @FXML private ImageView lightCavalryLogo1;
    @FXML private ImageView lightCavalryLogo2;
    @FXML private ImageView paladinLogo1;
    @FXML private ImageView paladinLogo2;




    private File fileArmy1;
    private File fileArmy2;
    private Army army1;
    private Army army2;
    private Battle battle;

    private TerrainType terrainType;

    @FXML
    public void initialize(){
        List<String> armiesInOverviewFile = new ArrayList<>();
        try {
            armiesInOverviewFile = ArmyReader.readArmyFileNamesFromOverviewFile();
        } catch (IOException exception) {
            if (exception.getMessage().equals("File could not be read: File is empty!")){
                printErrorMessage("Army overview is empty! Creating new armies is recommended!");
            } else {
                printErrorMessage(exception.getMessage());
            }
        }
        resetButton.setDisable(true);
        setItemsAndListenerToTerrainComboBox();
        setItemsListenerAndAddActionToChooseArmyComboBox1(armiesInOverviewFile);
        setItemsListenerAndAddActionToChooseArmyComboBox2(armiesInOverviewFile);
        addListenerToTerrainTypeComboBoxAndAssignTerrain();
        setToolTipToUnitLogos();
    }

    @FXML
    public void onSimulateButtonClicked(){
        if (army1 == null || army2 == null){
            printErrorMessage("Error: Battle cannot be started until both Armies have been chosen!");
        }
        else if (!army1.hasUnits() || !army2.hasUnits()){
            printErrorMessage("Error: Armies need units to fight!");
        }
        else if (army1.equals(army2)){
            printErrorMessage("Error: Battle cannot be fought between the same Army!");
        }
        else if (terrainType == null){
            printErrorMessage("Error: Terrain must be chosen!");
        }
        else {
            try {
                removeErrorMessage();
                Battle.startBattle();
                battle = new Battle(army1, army2);
                battle.setTerrainType(terrainType);
                battle.addListener(this::updateUnitNumbers);
                buttonStatusDuringSimulation();
                new Thread(() -> {
                    try {
                        printUpdateMessage("Simulation ongoing!");
                        battle.simulate();
                    } catch (BattleException exception) {
                        printErrorMessage(exception.getMessage());
                    }
                    Platform.runLater(() -> {
                        if (battle.getVictor() == null){
                            printUpdateMessage("Battle paused");
                            swapToSimulateButton();
                            resetButton.setDisable(false);
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

    private void addListenerToTerrainTypeComboBoxAndAssignTerrain(){
        terrainComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    switch (newValue){
                        case "Forest" -> terrainType = TerrainType.FOREST;
                        case "Plains" -> terrainType = TerrainType.PLAINS;
                        case "Hill" -> terrainType = TerrainType.HILL;
                        default -> terrainType = null;
                    }
                }
        );
    }

    private void buttonStatusDuringSimulation(){
        swapToPauseButton();
        resetButton.setDisable(true);
        terrainComboBox.setDisable(true);
        chooseArmyComboBox1.setDisable(true);
        chooseArmyComboBox2.setDisable(true);
        loadFileButton1.setDisable(true);
        loadFileButton2.setDisable(true);
    }

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

    private void swapToPauseButton(){
        simulateButton.setDisable(true);
        simulateButton.setVisible(false);
        simulateButton.setPrefWidth(0);

        pauseButton.setDisable(false);
        pauseButton.setVisible(true);
        pauseButton.setPrefWidth(111);
    }

    private void swapToSimulateButton(){
        simulateButton.setDisable(false);
        simulateButton.setVisible(true);
        simulateButton.setPrefWidth(111);

        pauseButton.setDisable(true);
        pauseButton.setVisible(false);
        pauseButton.setPrefWidth(0);
    }


    public void updateUnitNumbers(){
        Platform.runLater(() -> {
            fillArmy1WithInfo();
            fillArmy2WithInfo();
        });
    }

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
            removeUpdateMessage();
            removeErrorMessage();
        } catch (IOException exception){
            printErrorMessage(exception.getMessage());
        }
    }

    @FXML
    public void onPauseButtonClicked(){
        Battle.shutdownBattle();
        buttonStatusDuringSimulation();
    }

    @FXML
    public void onHereTextClicked(){
        ViewSwitcher.switchTo(View.UNIT_INFO);
    }
    @FXML
    public void onLoadFileButton1Clicked(){
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
            printErrorMessage("Error: Wrong format in imported file: " + exception.getMessage());
        }
    }

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
            printErrorMessage("Error: Wrong format in imported file: " + exception.getMessage());
        }
    }

    private void setItemsAndListenerToTerrainComboBox(){
        terrainComboBox.getItems().addAll("Forest", "Hill", "Plains");
        terrainComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    try {
                        terrainImageView.setImage(
                                new Image(geTerrainImageFilePath(newValue))
                        );
                    } catch (IOException exception) {
                        printErrorMessage(exception.getMessage());
                    }
                });
    }

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

    private void setItemsListenerAndAddActionToChooseArmyComboBox1(List<String> list){
        list.forEach(s -> chooseArmyComboBox1.getItems().add(s));
        chooseArmyComboBox1.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    try {
                        clearArmy1Info();
                        army1 = ArmyReader.readArmyFromLocalFileWithNameOfFile(newValue);
                        fileArmy1 = Utilities.convertStringToArmyFile(newValue);
                        filePathTitle1.setVisible(true);
                        filePath1.setText(fileArmy1.getAbsolutePath());
                        fillArmy1WithInfo();
                        removeErrorMessage();
                        removeUpdateMessage();
                    } catch (IOException exception) {
                        printErrorMessage(exception.getMessage());
                    }
                }
        );
    }

    private void setItemsListenerAndAddActionToChooseArmyComboBox2(List<String> list){
        list.forEach(s -> chooseArmyComboBox2.getItems().add(s));
        chooseArmyComboBox2.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    try {
                        clearArmy2Info();
                        army2 = ArmyReader.readArmyFromLocalFileWithNameOfFile(newValue);
                        fileArmy2 = Utilities.convertStringToArmyFile(newValue);
                        filePathTitle2.setVisible(true);
                        filePath2.setText(fileArmy2.getAbsolutePath());
                        fillArmy2WithInfo();
                        removeErrorMessage();
                        removeUpdateMessage();
                    } catch (IOException exception) {
                        printErrorMessage(exception.getMessage());
                    }
                }
        );
    }

    private void fillArmy1WithInfo(){
        setInfoToArmies(army1Name, totalUnits1, spearFighter1, swordsman1, axeman1,
                archer1, lightCavalry1, paladin1, army1);
    }

    private void fillArmy2WithInfo(){
        setInfoToArmies(army2Name, totalUnits2, spearFighter2, swordsman2, axeman2,
                archer2, lightCavalry2, paladin2, army2);
    }

    private void setInfoToArmies(Text armyName, Text totalUnits, Text spearFighter, Text swordsman, Text axeman,
                                 Text archer, Text lightCavalry, Text paladin, Army army) {
        armyName.setText(String.valueOf(army.getName()));
        totalUnits.setText(String.valueOf(army.getUnits().size()));
        spearFighter.setText(String.valueOf(army.getSpearFighterUnits().size()));
        swordsman.setText(String.valueOf(army.getSwordsmanUnits().size()));
        axeman.setText(String.valueOf(army.getAxemanUnits().size()));
        archer.setText(String.valueOf(army.getRangedUnits().size()));
        lightCavalry.setText(String.valueOf(army.getCavalryUnits().size()));
        paladin.setText(String.valueOf(army.getCommanderUnits().size()));
    }

    private void clearArmy1Info(){
        clearArmyInfo(army1Name, totalUnits1, spearFighter1, swordsman1, axeman1, archer1, lightCavalry1,
                paladin1, filePathTitle1, filePath1);
    }
    private void clearArmy2Info(){
        clearArmyInfo(army2Name, totalUnits2, spearFighter2, swordsman2, axeman2, archer2, lightCavalry2,
                paladin2, filePathTitle2, filePath2);
    }

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


    private void printErrorMessage(String errorMessage){
        warningText.setText(errorMessage);
    }

    private void printUpdateMessage(String updateMessage){
        updateText.setText(updateMessage);
    }

    private void removeUpdateMessage(){
        updateText.setText("");
    }

    private void removeErrorMessage(){
        warningText.setText("");
    }

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
    private void setToolTipToImageView(ImageView imageView, String str){
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-background-color: #240000; -fx-text-fill: white; -fx-font: 16px Arial;" +
                "-fx-font-weight: Bold;");
        tooltip.setText(str);
        Tooltip.install(imageView, tooltip);
    }

    @FXML
    public void onMenuButtonClicked(){
        Battle.shutdownBattle();
        ViewSwitcher.switchTo(View.MENU);
    }

    @FXML
    public void onCreateArmyButtonClicked(){
        Battle.shutdownBattle();
        ViewSwitcher.switchTo(View.CREATE_ARMY);
    }

    @FXML
    public void onViewArmiesButtonClicked(){
        Battle.shutdownBattle();
        ViewSwitcher.switchTo(View.VIEW_ARMIES);
    }

}
