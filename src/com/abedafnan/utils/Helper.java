package com.abedafnan.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Helper {
    private static Helper helper;

    private Helper() {}

    public static Helper getHelper() {
        if (helper == null) {
            helper = new Helper();
        }
        return helper;
    }

    /**
     *
     * @param pane is the current pane
     * @param title is the title of the next pane
     * @param resource is of the next pane
     */
    public void moveTo(Pane pane, String title, String resource) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(loadScene(resource));
    }

    /**
     *
     * @param resource of the fxml file to be loaded
     * @return the new scene to be displayed
     */
    private Scene loadScene(String resource) {
        Scene scene = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent parent = loader.load();
            scene = new Scene(parent);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return scene;
    }
}
