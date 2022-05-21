package edu.ntnu.stud.idatt2001.sojohans.wargames.scenes;

public enum View {
    MENU("menu-scene.fxml"),
    CREATE_ARMY("create-army-scene.fxml"),
    BATTLE_SIMULATION("battle-simulation-scene.fxml"),
    VIEW_ARMIES("view-armies-scene.fxml"),
    UNIT_INFO("unit-info-scene.fxml");


    private final String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
