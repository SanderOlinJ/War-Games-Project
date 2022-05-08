package edu.ntnu.stud.idatt2001.sojohans.wargames.view;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
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

    private Army army;
    @FXML
    public void initialize(){
        army = new Army();
        numberOfGold.setText("1000000");
        numberOfGold1.setVisible(true);
    }

    private void checkIfTextFieldValuesAreValid(TextField textField, Text text) throws IOException {
        if (army == null){
            warningText.setText("Error: Name of Army needs to be set first");
            throw new IOException("Error: Name of Army needs to be set first");
        }

        if (Utilities.doesStringContainSymbolsOtherThanNumbers(textField.getText())){
            warningText.setText("Error: Number of 'Spear fighters' has to be a positive integer");
            throw new IOException("Error: Number of 'Spear fighters' has to be a positive integer");
        }

        int numberOfUnits = Integer.parseInt(textField.getText());
        int costPerUnit = Integer.parseInt(text.getText());
        int goldLeft = Integer.parseInt(numberOfGold.getText());
        int totalSum = numberOfUnits*costPerUnit;

        if (totalSum > goldLeft){
            warningText.setText("Error: Cannot add " + numberOfUnits + " units, not enough Gold");
            throw new IOException("Error: Cannot add " + numberOfUnits + " units, not enough Gold");
        }
    }

    @FXML
    void onSetArmyNameButtonClicked() throws IOException{
        if (textFieldArmyName.getText() == null || textFieldArmyName.getText().trim().isEmpty()){
            warningText.setText("Error: Name of Army is empty!");
            throw new IOException("Error: Name of Army is empty!");
        }
        if (Utilities.doesStringContainAnyNonAlphaNumericSymbols(textFieldArmyName.getText())){
            warningText.setText("Error: Army name can only contain alpha-numeric symbols");
            throw new IOException("Error: Army name can only contain alpha-numeric symbols");
        }
        army.setName(textFieldArmyName.getText());
        editNameTextSwap();

    }

    @FXML
    void onEditArmyNameButtonClicked(){
        setNameSwap();

        army.setName("");
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
    @FXML
    void onAddSpearFighterButtonClicked() throws IOException{
        checkIfTextFieldValuesAreValid(textFieldSpearFighter, costOfSpearFighter);
    }
    @FXML
    void onRemoveSpearFighterButtonClicked(){

    }
    @FXML
    void onAddSwordsmanButtonClicked(){

    }
    @FXML
    void onRemoveSwordsmanButtonClicked(){

    }
    @FXML
    void onAddAxemanButtonClicked(){

    }
    @FXML
    void onRemoveAxemanButtonClicked(){

    }
    @FXML
    void onAddArcherButtonClicked(){

    }
    @FXML
    void onRemoveArcherButtonClicked(){

    }
    @FXML
    void onAddLightCavalryButtonClicked(){

    }
    @FXML
    void onRemoveLightCavalryButtonClicked(){

    }
    @FXML
    void onAddPaladinButtonClicked(){

    }
    @FXML
    void onRemovePaladinButtonClicked(){

    }

    @FXML
    void onInstantiateArmyButtonClicked(ActionEvent event) {

    }

    @FXML
    void onMenuButtonClicked(MouseEvent event){
        ViewSwitcher.switchTo(View.MAIN_PAGE);
    }

}