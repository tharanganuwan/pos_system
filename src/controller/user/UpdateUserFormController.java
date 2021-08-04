package controller.user;


import controller.DashboardFormController;
import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tm.SystemUserComboTM;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUserFormController {

    public TextField txtUserId;
    public TextField txtUserName;
    public TextField txtEmail;
    public TextField txtPhone;
    public ComboBox<SystemUserComboTM> cmbRole;
    public TextField txtNIC;
    public ImageView imgUser;
    public PasswordField pwd;
    public PasswordField pwdConform;
    public File file;
    public String url;
    public boolean isImageChange=false;

    public void initialize(){
        txtUserId.setEditable(false);
        SystemUserIdCombo();
        String selectedUserId = DashboardFormController.selectedUserId;
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from systemuser where id=?");
            preparedStatement.setObject(1,selectedUserId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                txtUserId.setText(resultSet.getString(1));
                txtUserName.setText(resultSet.getString(2));
                txtEmail.setText(resultSet.getString(3));
                txtPhone.setText(resultSet.getString(4));
                txtNIC.setText(resultSet.getString(5));
                pwd.setText(resultSet.getString(6));
                pwdConform.setText(resultSet.getString(6));
                url=resultSet.getString(7);
                String role = resultSet.getString(8);
                int roleNum;
                if(role.equals("Admin"))
                    roleNum=0;
                else
                    roleNum=1;
                cmbRole.getSelectionModel().select(roleNum);
                File file1 =new File(resultSet.getString(7));
                imgUser.setImage(new Image(file1.toURI().toString()));
                file=new File(url);
                imgUser.setImage(new Image(file.toURI().toString()));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void SystemUserIdCombo(){
        ObservableList<SystemUserComboTM> role = cmbRole.getItems();
        role.clear();
        for(int i=0;i<2;i++){
            if(i==0){
                SystemUserComboTM systemUsersTM=new SystemUserComboTM("Admin");
                role.add(systemUsersTM);
            }else {
                SystemUserComboTM systemUsersTM=new SystemUserComboTM("User");
                role.add(systemUsersTM);
            }
        }
    }

    public void btnSubmit(ActionEvent actionEvent) {
        String id=txtUserId.getText();
        String name=txtUserName.getText();
        String email=txtEmail.getText();
        String phone=txtPhone.getText();
        String nic=txtNIC.getText();
        String password=pwd.getText();
        String comPassword=pwdConform.getText();
        if(!(name.equals("")||email.equals("")||phone.equals("")||nic.equals("")||password.equals("")||comPassword.equals(""))){
            String role=String.valueOf(cmbRole.getValue());
            txtUserName.setStyle("[id=txt]");
            txtEmail.setStyle("[id=txt]");
            txtPhone.setStyle("[id=txt]");
            txtNIC.setStyle("[id=txt]");
            pwd.setStyle("[id=txt]");
            pwdConform.setStyle("[id=txt]");
            if(cmbRole.getValue()==null){
                new Alert(Alert.AlertType.WARNING, "please Select User Role").showAndWait();
            }else {
                if(!password.equals(comPassword)){
                    new Alert(Alert.AlertType.WARNING, "Password are does not match").showAndWait();
                    pwdConform.clear();
                    pwdConform.requestFocus();
                }else{

                    if(file != null){
                        if(!file.toString().equals("src/images/default.jpg"))
                        {
                            if(isImageChange){

                                url="src/images/IMG"+txtUserId.getText()+".jpeg";
                                File alradyFile = new File(url);
                                alradyFile.delete();
                                File newFile = new File(url);
                                try {
                                    Files.copy(file.toPath(),newFile.toPath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }

                    Connection connection = DBConnection.getInstance().getConnection();
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("update systemuser set userName=?,email=?,phone=?,nic=?,password=?,imageURL=?,role=? where id=?");
                        preparedStatement.setObject(8,id);
                        preparedStatement.setObject(1,name);
                        preparedStatement.setObject(2,email);
                        preparedStatement.setObject(3,phone);
                        preparedStatement.setObject(4,nic);
                        preparedStatement.setObject(5,password);
                        preparedStatement.setObject(6,url);
                        preparedStatement.setObject(7,role);
                        preparedStatement.executeUpdate();

                        Notifications notifications =Notifications.create();
                        notifications.title("User Update Notification");
                        notifications.text("User edit successfully");
                        notifications.graphic(null);
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.darkStyle();
                        notifications.showConfirm();

                        //systemUsersTable();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }else {
            if(name.equals("")){
                txtUserName.setStyle("-fx-border-color: red");
            }else {
                txtUserName.setStyle("[id=txt]");
            }
            if(email.equals("")){
                txtEmail.setStyle("-fx-border-color: red");
            }
            else {
                txtEmail.setStyle("[id=txt]");
            }
            if(phone.equals("")){
                txtPhone.setStyle("-fx-border-color: red");
            }else {
                txtPhone.setStyle("[id=txt]");
            }
            if(nic.equals("")){
                txtNIC.setStyle("-fx-border-color: red");
            }else {
                txtNIC.setStyle("[id=txt]");
            }
            if(password.equals("")){
                pwd.setStyle("-fx-border-color: red");
            }else {
                pwd.setStyle("[id=txt]");
            }
            if(comPassword.equals("")){
                pwdConform.setStyle("-fx-border-color: red");
            }else {
                pwdConform.setStyle("[id=txt]");
            }

        }
    }

    public void btnChoseImage(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Image Chooser");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg*","*.png*","*.jpeg*"));
        file = fileChooser.showOpenDialog(null);
        if(file!=null){
            imgUser.setImage(new Image(file.toURI().toString()));
            isImageChange=true;
        }


    }
}
