package edu.ntnu.stud.idatt2001.sojohans.wargames.scenes;

public enum View {
    START("start-screen.fxml");


    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
