package edu.ntnu.stud.idatt2001.sojohans.wargames.scenes;

/**
 * Enum class View, used for easier switching between scenes.
 * Stores a fxml file (each scene) in every enum constant.
 */
public enum View {
    MENU("menu-scene.fxml"),
    CREATE_ARMY("create-army-scene.fxml"),
    BATTLE_SIMULATION("battle-simulation-scene.fxml"),
    VIEW_ARMIES("view-armies-scene.fxml"),
    UNIT_INFO("unit-info-scene.fxml");


    private final String fileName;

    /**
     * Method stores the fxml file the application is to switch to.
     * @param fileName fxml file application is to switch to.
     */
    View(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Method for getting the fxml file the application is to switch to.
     * @return file path of the fxml file.
     */
    public String getFileName() {
        return fileName;
    }
}
