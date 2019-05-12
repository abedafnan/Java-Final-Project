package com.abedafnan.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     *
     * @param message the operation to be recorded into the log file
     */
    public void writeIntoLog(String message) {
        FileOutputStream outputStream = null;
        PrintWriter printWriter = null;
        try {
            File file = new File("log.txt");
            outputStream = new FileOutputStream(file, true);
            printWriter = new PrintWriter(outputStream);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String content = "\n" + dateFormat.format(date) + " - " + message;
            printWriter.write(content);
        } catch (IOException exception) {
            exception.printStackTrace();

        } finally {

            try {
                printWriter.close();
                outputStream.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        }
    }
}
