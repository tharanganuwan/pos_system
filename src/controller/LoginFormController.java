package controller;

import db.DBConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LoginFormController {
    public AnchorPane root;
    public PasswordField txtPassword;
    public TextField txtUserName;
    public String userNameID;
    public static String id;
    public static String name;
    public String password;
    public Label lblPasswordError;
    public Label lblUserNameError;
    private boolean stopTime = false;
    public static String loginTime;
    public static String loginDate;
    public static Thread thread2;
    public boolean isThreadStart=false;

    public void initialize() {
        lblPasswordError.setVisible(false);
        lblUserNameError.setVisible(false);
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {

        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        if(isThreadStart){
            thread2.stop();
        }
        primaryStage.close();
        stopTime=true;
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        userNameID = txtUserName.getText();
        password = txtPassword.getText();
        lblUserNameError.setVisible(false);
        lblPasswordError.setVisible(false);

        if(userNameID.isEmpty() && password.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"User name and Password Empty").showAndWait();
            txtUserName.requestFocus();
        }
        else if(userNameID.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"User name is Empty").showAndWait();
            txtUserName.requestFocus();
        }else if(password.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Password is Empty").showAndWait();
            txtPassword.requestFocus();
        }
        else{
            login();
        }


    }

    public void login(){
        lblUserNameError.setVisible(false);
        lblPasswordError.setVisible(false);
        txtUserName.setStyle("-fx-border-color:  transparent");
        txtPassword.setStyle("-fx-border-color:  transparent");
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from systemUser where userName=? or id=?;");
            preparedStatement.setObject(1, userNameID);
            preparedStatement.setObject(2,userNameID);

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean isExits = resultSet.next();

            if(isExits){
                timeNow();
                id =resultSet.getString(1);
                name=resultSet.getString(2);
                String correctPassword = resultSet.getString(6);
                if (password.equals(correctPassword )){

                    txtUserName.clear();
                    txtPassword.clear();

                    Parent parent = FXMLLoader.load(this.getClass().getResource("../view/DashboardForm.fxml"));
                    Scene scene = new Scene(parent);

                    Stage primaryStage = (Stage) this.root.getScene().getWindow();

                    primaryStage.setScene(scene);
                    primaryStage.centerOnScreen();
                    primaryStage.show();
                    stopTime=true;
                }
                else {
                    lblPasswordError.setVisible(true);
                    txtPassword.requestFocus();
                    txtPassword.setStyle("-fx-border-color: red");
                }
            }else {
                lblUserNameError.setVisible(true);
                lblPasswordError.setVisible(true);
                txtUserName.setStyle("-fx-border-color: red");
                txtPassword.setStyle("-fx-border-color: red");
                txtUserName.requestFocus();
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    public void timeNow(){
        isThreadStart=true;
        thread2 = new Thread(() ->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

            while (!stopTime){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String timeNow = simpleDateFormat.format(new Date());
                Platform.runLater(()->{
                   loginTime=timeNow;
                });
                final String dateNow = simpleDateFormat2.format(new Date());
                Platform.runLater(()->{
                    loginDate=dateNow;
                });
            }
        });
        thread2.start();
    }


}
