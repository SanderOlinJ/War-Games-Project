package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
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
import java.util.*;

/**
 Controller class for the 'View Armies' page.
 */
public class ViewArmiesController {
    @FXML private Text archers1, archers2, archers3, archers4, archers5, archers6, archers7, armyName1,
            armyName2, armyName3, armyName4, armyName5, armyName6, armyName7, axemen1, axemen2, axemen3,
            axemen4, axemen5, axemen6, axemen7, lightCavalry1, lightCavalry2, lightCavalry3, lightCavalry4,
            lightCavalry5, lightCavalry6, lightCavalry7, paladins1, paladins2, paladins3, paladins4, paladins5,
            paladins6, paladins7, spearFighters1, spearFighters2, spearFighters3, spearFighters4, spearFighters5,
            spearFighters6, spearFighters7, swordsmen1, swordsmen2, swordsmen3, swordsmen4, swordsmen5, swordsmen6,
            swordsmen7, totalUnits1, totalUnits2, totalUnits3, totalUnits4, totalUnits5, totalUnits6, totalUnits7,
            warningText;
    @FXML private ImageView armyIcon1, armyIcon2, armyIcon3, armyIcon4, armyIcon5, armyIcon6, armyIcon7, delete1,
            delete2, delete3, delete4, delete5, delete6, delete7;
    @FXML private Button editArmyButton1, editArmyButton2, editArmyButton3, editArmyButton4, editArmyButton5,
            editArmyButton6, editArmyButton7;
    @FXML private HBox hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7, line1, line2, line3, line4, line5, line6,
            line7;

    private List<String> armyFileNamesInOverviewFile;
    private List<Army> armiesInOverview;
    private List<HBox> hBoxes, lines;
    private List<ImageView> armyIcons, deleteButtons;
    private List<Text> armyNames, totalUnits, spearFighterNumbers, swordsmanNumbers, axemanNumbers, archerNumbers,
            lightCavalryNumbers, paladinNumbers;
    private List<Button> editButtons;


    /**
     * Method for initializing the 'View Armies' page.
     * Method displays the page with all locally stored Armies.
     */
    @FXML
    public void initialize(){
        initializeLists();
        readAllArmiesFromOverviewFile();
        setArmyInfoToGUI();
        setOnActionToButtons();
    }

    /**
     * Method fir initializing lists.
     */
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
        axemanNumbers = new ArrayList<>(Arrays.asList(axemen1, axemen2, axemen3, axemen4, axemen5, axemen6,
                axemen7));
        archerNumbers = new ArrayList<>(Arrays.asList(archers1, archers2, archers3, archers4, archers5, archers6,
                archers7));
        lightCavalryNumbers = new ArrayList<>(Arrays.asList(lightCavalry1, lightCavalry2, lightCavalry3,
                lightCavalry4, lightCavalry5, lightCavalry6, lightCavalry7));
        paladinNumbers = new ArrayList<>(Arrays.asList(paladins1, paladins2, paladins3, paladins4, paladins5,
                paladins6, paladins7));
        editButtons = new ArrayList<>(Arrays.asList(editArmyButton1, editArmyButton2, editArmyButton3,
                editArmyButton4, editArmyButton5, editArmyButton6, editArmyButton7));
        deleteButtons = new ArrayList<>(Arrays.asList(delete1, delete2, delete3, delete4, delete5, delete6, delete7));
    }

    /**
     * Method for reading all locally stored Army files.
     */
    private void readAllArmiesFromOverviewFile(){
        try {
            armyFileNamesInOverviewFile = ArmyReader.readArmyFileNamesFromOverviewFile();
        } catch (IOException exception){
            if (exception.getMessage().equals("File could not be read: armyFilesOverview.csv is empty!")){
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

    /**
     * Method for displaying up to 7 locally stored Armies in GUI.
     */
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
                                    if (armiesInOverview.size() > 7) {
                                        printErrorMessage("Only 7 armies may be shown at a time!");
                                        /*
                                        This is due to not finding a method that can generate boxes
                                        with info and buttons, so that they are addressable.
                                        */
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method for displaying Army information to GUI.
     * @param index Index of the Army in the 'armiesInOverview' array.
     */
    private void setHBoxWithInfo(int index){
        hBoxes.get(index).setVisible(true);
        hBoxes.get(index).setDisable(false);
        hBoxes.get(index).setPrefHeight(167);

        lines.get(index).setVisible(true);
        lines.get(index).setDisable(false);
        lines.get(index).setPrefHeight(2);

        armyNames.get(index).setText(armiesInOverview.get(index).getName());
        totalUnits.get(index).setText(String.valueOf(armiesInOverview.get(index).getUnits().size()));
        spearFighterNumbers.get(index).setText
                (String.valueOf(armiesInOverview.get(index).getSpearFighterUnits().size()));
        swordsmanNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getSwordsmanUnits().size()));
        axemanNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getAxemanUnits().size()));
        archerNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getRangedUnits().size()));
        lightCavalryNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getCavalryUnits().size()));
        paladinNumbers.get(index).setText(String.valueOf(armiesInOverview.get(index).getCommanderUnits().size()));
        armyIcons.get(index).setImage(getRandomArmyIconImage());
    }

    /**
     * Method sets actions to every Edit and Remove button.
     * Each button is given an action with the same index argument as the
     * corresponding Army's index in 'armiesInOverview' array as well as the other arrays
     * displaying the Army info.
     */
    private void setOnActionToButtons(){
        for (int i = 0; i < editButtons.size() && i < deleteButtons.size(); i++){
            int index = i;
            editButtons.get(index).setOnAction(event -> editArmy(index));
            deleteButtons.get(index).setOnMouseClicked(mouseEvent -> deleteArmy(index));
        }
    }

    /**
     * Method for redirecting and sending a locally stored armyFile to the CreateArmyController,
     * so that the Army may be edited there.
     * @param index Index of the Army in the 'armiesInOverview' array.
     */
    private void editArmy(int index){
        String fileName = armyFileNamesInOverviewFile.get(index);
        File file = Utilities.convertStringToArmyFile(fileName);
        CreateArmyController.setArmyFile(file);
        ViewSwitcher.switchTo(View.CREATE_ARMY);
    }

    /**
     * Method for deleting a locally stored Army File.
     * Method deletes it from the local 'armyFiles' folder, and removes it from the Army overview File.
     * @param index Index of the Army in the 'armiesInOverview' array.
     */
    private void deleteArmy(int index){
        //  Customizes and opens an Alert and DialogPane.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Army");
        alert.setHeaderText("Deleting: " + armiesInOverview.get(index).getName());
        alert.setContentText("Are you sure you want to delete this army?");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass()
                .getResource("/edu/ntnu/stud/idatt2001/sojohans/wargames/css/style.css")).toString());
        dialogPane.getStyleClass().add("dialog-pane");
        alert.showAndWait();

        //  If the response from the user was 'OK', then the File is deleted.
        //  It is also removed from the overview file and the 'armiesInOverview' array.
        if (alert.getResult() == ButtonType.OK){
            String fileName = armyFileNamesInOverviewFile.get(index);
            File file = Utilities.convertStringToArmyFile(fileName);
            try {
                ArmyWriter.removeArmyFileNameFromOverviewFile(fileName);
                if (file.delete()){
                    printErrorMessage("File was successfully deleted!");

                    //  GUI is then refreshed with the deleted army now gone.
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

    /**
     * Method for generating a random image icon to each Army.
     * @return a Random Image to be used as Army Icon
     */
    private Image getRandomArmyIconImage(){
        Random random = new Random();
        int randomInt = random.nextInt(5);
        return switch (randomInt){
            case 0 -> new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                    "wargames/images/shield1.png");
            case 1 -> new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                    "wargames/images/shield2.png");
            case 2 -> new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                    "wargames/images/shield3.png");
            case 3 -> new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                    "wargames/images/shield4.png");
            case 4 -> new Image("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                    "wargames/images/shield5.png");
            default -> throw new IllegalStateException("Unexpected value: " + randomInt);
        };
    }

    /**
     * Method for removing Army information from GUI.
     * @param index Index of the Army in the 'armiesInOverview' array.
     */
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

    /**
     * Method for printing error message to GUI.
     * @param errorMessage Error message to be displayed.
     */
    private void printErrorMessage(String errorMessage){
        warningText.setText(errorMessage);
    }

    /**
     * Redirects to the 'Main Menu' page.
     */
    @FXML
    public void onMenuButtonClicked(){
        ViewSwitcher.switchTo(View.MENU);
    }

    /**
     * Redirects to the 'Create Army' page.
     */
    public void onCreateArmyButtonClicked(){
        ViewSwitcher.switchTo(View.CREATE_ARMY);
    }

    /**
     * Redirects to the 'Battle Simulation' page.
     */
    @FXML
    public void onSimulateButtonClicked() {
        ViewSwitcher.switchTo(View.BATTLE_SIMULATION);
    }
}
