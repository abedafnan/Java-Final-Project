package com.abedafnan;

import com.abedafnan.utils.DBConnection;
import com.abedafnan.utils.Helper;
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
        if (!areFull()) {
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
        Helper helper = Helper.getHelper();
        helper.moveTo(mainPanel, "Main Menu", "../main.fxml");
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
            String query = "SELECT * FROM users WHERE name = '" + username + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                errorLabel.setText("");
                loginInfo = name + " " + password;
            } else {
                errorLabel.setText("Username doesn't exist");
            }

            Helper helper = Helper.getHelper();
            helper.writeIntoLog("Retrieved the login info for the user named " + username);

        } catch (SQLException ex) {
            System.out.println("Error in Query!");
            ex.printStackTrace();
        }

        return loginInfo;
    }

    /**
     * @return true if all fields are filled
     */
    private boolean areFull() {
        if (nameField.getText().equals("")) {
            errorLabel.setText("You must enter values");
            return false;
        } if (passField.getText().equals("")) {
            errorLabel.setText("You must enter values");
            return false;
        }
        return true;
    }

}
