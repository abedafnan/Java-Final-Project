package com.abedafnan;

import com.abedafnan.db.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    @FXML
    VBox mainPanel;
    @FXML
    TextField nameField;
    @FXML
    TextField passField;
    @FXML
    Label errorLabel;
    private DBConnection dbConnection;
    private Statement statement;
    private String loginInfo;
    private String[] infoArray;

    public void login() {
        // Check if the fields are empty
        if (!areEmpty()) {
            return;
        }

        // Get the user login info
        loginInfo = getDBQuery(nameField.getText());
        if (loginInfo.equals("")) {
             return;
        }

        // Get password and check if the entered password is true
        infoArray = loginInfo.split(" ");
        String password = infoArray[1];
        if (!passField.getText().equals(password)) {
            errorLabel.setText("Wrong Password!");
            return;
        }

        continueToNextPage();
    }

    private void continueToNextPage() {
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.setTitle("Main");
        stage.setScene(loadScene("main.fxml"));
    }

    public void cancel() {
        System.exit(0);
    }

    /**
     * @param username the entered username
     * @return the login info user fetched from the database
     */
    private String getDBQuery(String username) {
        String loginInfo = "";
        dbConnection = DBConnection.getDbConnection();
        try {
            statement = dbConnection.createStatement();
            String query = "SELECT * FROM users WHERE email = '" + username + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String name = resultSet.getString("email");
                String password = resultSet.getString("password");
                errorLabel.setText("");
                loginInfo = name + " " + password;
            } else {
                errorLabel.setText("Username doesn't exist");
            }

        } catch (SQLException ex) {
            System.out.println("Error in Query!");
            ex.printStackTrace();
        }

        return loginInfo;
    }

    /**
     * @return true if all fields are filled
     */
    private boolean areEmpty() {
        if (nameField.getText().equals("")) {
            errorLabel.setText("You must enter values");
            return false;
        } if (passField.getText().equals("")) {
            errorLabel.setText("You must enter values");
            return false;
        }
        return true;
    }

    /**
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
