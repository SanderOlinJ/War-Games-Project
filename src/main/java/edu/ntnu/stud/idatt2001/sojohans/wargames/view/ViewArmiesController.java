package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Battle;
import edu.ntnu.stud.idatt2001.sojohans.wargames.io.readers.ArmyReader;
import edu.ntnu.stud.idatt2001.sojohans.wargames.io.writers.ArmyWriter;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.View;
import edu.ntnu.stud.idatt2001.sojohans.wargames.scenes.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ViewArmiesController {
    @FXML private Text archers1;
    @FXML private Text archers2;
    @FXML private Text archers3;
    @FXML private Text archers4;
    @FXML private Text archers5;
    @FXML private Text archers6;
    @FXML private Text archers7;
    @FXML private ImageView armyIcon1;
    @FXML private ImageView armyIcon2;
    @FXML private ImageView armyIcon3;
    @FXML private ImageView armyIcon4;
    @FXML private ImageView armyIcon5;
    @FXML private ImageView armyIcon6;
    @FXML private ImageView armyIcon7;
    @FXML private Text armyName1;
    @FXML private Text armyName2;
    @FXML private Text armyName3;
    @FXML private Text armyName4;
    @FXML private Text armyName5;
    @FXML private Text armyName6;
    @FXML private Text armyName7;
    @FXML private Text axemen1;
    @FXML private Text axemen2;
    @FXML private Text axemen3;
    @FXML private Text axemen4;
    @FXML private Text axemen5;
    @FXML private Text axemen6;
    @FXML private Text axemen7;
    @FXML private ImageView delete1;
    @FXML private ImageView delete2;
    @FXML private ImageView delete3;
    @FXML private ImageView delete4;
    @FXML private ImageView delete5;
    @FXML private ImageView delete6;
    @FXML private ImageView delete7;
    @FXML private Button editArmyButton1;
    @FXML private Button editArmyButton2;
    @FXML private Button editArmyButton3;
    @FXML private Button editArmyButton4;
    @FXML private Button editArmyButton5;
    @FXML private Button editArmyButton6;
    @FXML private Button editArmyButton7;
    @FXML private Text lightCavalry1;
    @FXML private Text lightCavalry2;
    @FXML private Text lightCavalry3;
    @FXML private Text lightCavalry4;
    @FXML private Text lightCavalry5;
    @FXML private Text lightCavalry6;
    @FXML private Text lightCavalry7;
    @FXML private Text paladins1;
    @FXML private Text paladins2;
    @FXML private Text paladins3;
    @FXML private Text paladins4;
    @FXML private Text paladins5;
    @FXML private Text paladins6;
    @FXML private Text paladins7;
    @FXML private Text spearFighters1;
    @FXML private Text spearFighters2;
    @FXML private Text spearFighters3;
    @FXML private Text spearFighters4;
    @FXML private Text spearFighters5;
    @FXML private Text spearFighters6;
    @FXML private Text spearFighters7;
    @FXML private Text swordsmen1;
    @FXML private Text swordsmen2;
    @FXML private Text swordsmen3;
    @FXML private Text swordsmen4;
    @FXML private Text swordsmen5;
    @FXML private Text swordsmen6;
    @FXML private Text swordsmen7;
    @FXML private Text totalUnits1;
    @FXML private Text totalUnits2;
    @FXML private Text totalUnits3;
    @FXML private Text totalUnits4;
    @FXML private Text totalUnits5;
    @FXML private Text totalUnits6;
    @FXML private Text totalUnits7;
    @FXML private Text warningText;
    @FXML private HBox hBox1;
    @FXML private HBox hBox2;
    @FXML private HBox hBox3;
    @FXML private HBox hBox4;
    @FXML private HBox hBox5;
    @FXML private HBox hBox6;
    @FXML private HBox hBox7;
    @FXML private HBox line1;
    @FXML private HBox line2;
    @FXML private HBox line3;
    @FXML private HBox line4;
    @FXML private HBox line5;
    @FXML private HBox line6;
    @FXML private HBox line7;

    private List<String> armyFileNamesInOverviewFile;
    private List<Army> armiesInOverview;
    private List<HBox> hBoxes;
    private List<HBox> lines;
    private List<ImageView> armyIcons;
    private List<Text> armyNames;
    private List<Text> totalUnits;
    private List<Text> spearFighterNumbers;
    private List<Text> swordsmanNumbers;
    private List<Text> axemanNumbers;
    private List<Text> archerNumbers;
    private List<Text> lightCavalryNumbers;
    private List<Text> paladinNumbers;
    private List<Button> editButtons;
    private List<ImageView> deleteButtons;


    @FXML
    public void initialize(){
        initializeLists();
        readAllArmiesFromOverviewFile();
        setArmyInfoToGUI();
        setOnActionToButtons();
    }

    private void initializeLists(){
        armiesInOverview = new ArrayList<>();
        armyFileNamesInOverviewFile = new ArrayList<>();
        hBoxes = new ArrayList<>(Arrays.asList(hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7));
        lines = new ArrayList<>(Arrays.asList(line1, line2, line3, line4, line5, line6, line7));
        armyIcons = new ArrayList<>(Arrays.asList(armyIcon1, armyIcon2, armyIcon3, armyIcon4, armyIcon5, armyIcon6,
                armyIcon7));
        armyNames = new ArrayList<>(Arrays.asList(armyName1, armyName2, armyName3, armyName4, armyName5,
                armyName6, armyName7));
        totalUnits = new ArrayList<>(Arrays.asList(totalUnits1, totalUnits2, totalUnits3, totalUnits4,
                totalUnits5, totalUnits6, totalUnits7));
        spearFighterNumbers = new ArrayList<>(Arrays.asList(spearFighters1, spearFighters2, spearFighters3,
                spearFighters4, spearFighters5, spearFighters6, spearFighters7));
        swordsmanNumbers = new ArrayList<>(Arrays.asList(swordsmen1, swordsmen2, swordsmen3, swordsmen4,
                swordsmen5, swordsmen6, swordsmen7));
        axemanNumbers = new ArrayList<>(Arrays.asList(axemen1, axemen2, axemen3, axemen4, axemen5, axemen6, axemen7));
        archerNumbers = new ArrayList<>(Arrays.asList(archers1, archers2, archers3, archers4, archers5, archers6,
                archers7));
        lightCavalryNumbers = new ArrayList<>(Arrays.asList(lightCavalry1, lightCavalry2, lightCavalry3, lightCavalry4,
                lightCavalry5, lightCavalry6, lightCavalry7));
        paladinNumbers = new ArrayList<>(Arrays.asList(paladins1, paladins2, paladins3, paladins4, paladins5, paladins6,
                paladins7));
        editButtons = new ArrayList<>(Arrays.asList(editArmyButton1, editArmyButton2, editArmyButton3,
                editArmyButton4, editArmyButton5, editArmyButton6, editArmyButton7));
        deleteButtons = new ArrayList<>(Arrays.asList(delete1, delete2, delete3, delete4, delete5, delete6, delete7));
    }

    private void setOnActionToButtons(){
        for (int i = 0; i < editButtons.size() && i < deleteButtons.size(); i++){
            int index = i;
            editButtons.get(index).setOnAction(event -> editArmy(index));
            deleteButtons.get(index).setOnMouseClicked(mouseEvent -> deleteArmy(index));
        }
    }

    private void deleteArmy(int index){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Army");
        alert.setHeaderText("Deleting: " + armiesInOverview.get(index).getName());
        alert.setContentText("Are you sure you want to delete this army?");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStyleClass().add("dialog-pane");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK){
            String fileName = armyFileNamesInOverviewFile.get(index);
            File file = Utilities.convertStringToArmyFile(fileName);
            try {
                ArmyWriter.removeArmyFileNameFromOverviewFile(fileName);
                if (file.delete()){
                    printErrorMessage("File was successfully deleted!");
                    armyFileNamesInOverviewFile.remove(index);
                    armiesInOverview.remove(index);
                    for (int i = 0; i <= armiesInOverview.size() && i < hBoxes.size(); i++) {
                        removeHBoxesWithInfo(i);
                    }
                    setArmyInfoToGUI();
                } else {
                    printErrorMessage("File could not be deleted!");
                }
            } catch (IOException exception){
                printErrorMessage(exception.getMessage());
            }
        }
    }

    private void editArmy(int index){
        String fileName = armyFileNamesInOverviewFile.get(index);
        File file = Utilities.convertStringToArmyFile(fileName);
        CreateArmyController.setArmyFile(file);
        ViewSwitcher.switchTo(View.CREATE_ARMY);
    }
    private void readAllArmiesFromOverviewFile(){
        try {
            armyFileNamesInOverviewFile = ArmyReader.readArmyFileNamesFromOverviewFile();
        } catch (IOException exception){
            if (exception.getMessage().equals("File could not be read: File is empty!")){
                printErrorMessage("Army overview is empty! Creating new armies is recommended!");
            } else {
                printErrorMessage(exception.getMessage());
            }
        }

        armyFileNamesInOverviewFile.forEach(s -> {
            try {
                armiesInOverview.add(ArmyReader.readArmyFromLocalFileWithNameOfFile(s));
            } catch (IOException exception){
                printErrorMessage(exception.getMessage());
            }
        });
    }

    private void setArmyInfoToGUI(){
        if (armiesInOverview.size() > 0){
            setHBoxWithInfo(0);
            if (armiesInOverview.size() > 1){
                setHBoxWithInfo(1);
                if (armiesInOverview.size() > 2){
                    setHBoxWithInfo(2);
                    if (armiesInOverview.size() > 3){
                        setHBoxWithInfo(3);
                        if (armiesInOverview.size() > 4){
                            setHBoxWithInfo(4);
                            if (armiesInOverview.size() > 5){
                                setHBoxWithInfo(5);
                                if (armiesInOverview.size() > 6){
                                    setHBoxWithInfo(6);
                                    printErrorMessage("Only 7 armies may be shown at a time!");
                                    /*
                                    This is due to not finding a method that can generate boxes with info and buttons,
                                    so that they are addressable.
                                     */
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void setHBoxWithInfo(int index){
        hBoxes.get(index).setVisible(true);
        hBoxes.get(index).setDisable(false);
        hBoxes.get(index).setPrefHeight(167);

        lines.get(index).setVisible(true);
        lines.get(index).setDisable(false);
        lines.get(index).setPrefHeight(2);

        armyNames.get(index).setText(armiesInOverview.get(index).getName());
        totalUnits.get(index).setText(String.valueOf(armiesInOverview.get(index).getUnits().size()));
        spearFighterNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getSpearFighterUnits().size()));
        swordsmanNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getSwordsmanUnits().size()));
        axemanNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getAxemanUnits().size()));
        archerNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getRangedUnits().size()));
        lightCavalryNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getCavalryUnits().size()));
        paladinNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getCommanderUnits().size()));
        armyIcons.get(index).setImage(getRandomArmyIconImage());
    }

    private Image getRandomArmyIconImage(){
        Random random = new Random();
        int randomInt = random.nextInt(5);

        return switch (randomInt){
            case 0 -> new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/images/shield1.png");
            case 1 -> new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/images/shield2.png");
            case 2 -> new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/images/shield3.png");
            case 3 -> new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/images/shield4.png");
            case 4 -> new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/images/shield5.png");
            default -> throw new IllegalStateException("Unexpected value: " + randomInt);
        };
    }

    private void removeHBoxesWithInfo(int index){
        hBoxes.get(index).setVisible(false);
        hBoxes.get(index).setDisable(true);
        hBoxes.get(index).setPrefHeight(0);

        lines.get(index).setVisible(false);
        lines.get(index).setDisable(true);
        lines.get(index).setPrefHeight(0);

        armyNames.get(index).setText("");
        totalUnits.get(index).setText("");
        spearFighterNumbers.get(index).setText("");
        swordsmanNumbers.get(index).setText("");
        axemanNumbers.get(index).setText("");
        archerNumbers.get(index).setText("");
        lightCavalryNumbers.get(index).setText("");
        paladinNumbers.get(index).setText("");
        armyIcons.get(index).setImage(null);

    }

    @FXML
    public void onMenuButtonClicked(){
        ViewSwitcher.switchTo(View.MENU);
    }

    public void onCreateArmyButtonClicked(){
        ViewSwitcher.switchTo(View.CREATE_ARMY);
    }
    @FXML
    public void onSimulateButtonClicked() {
        Battle.startBattle();
        ViewSwitcher.switchTo(View.BATTLE_SIMULATION);
    }

    private void printErrorMessage(String errorMessage){
        warningText.setText(errorMessage);
    }
}
