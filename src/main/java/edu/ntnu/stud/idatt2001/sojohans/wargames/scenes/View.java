package edu.ntnu.stud.idatt2001.sojohans.wargames.scenes;

public enum View {
    START("start-screen-scene.fxml"),
    CREATE_ARMY("create-army-copy-scene.fxml"),
    BATTLE_SIMULATION("battle-simulation-scene.fxml"),
    VIEW_OR_EDIT_LOCAL_ARMIES("view-or-edit-local-armies-scene.fxml");


    private final String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
