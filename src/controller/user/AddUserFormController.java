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
import java.sql.*;

public class AddUserFormController extends DashboardFormController {
    public TextField txtUserId;
    public TextField txtUserName;
    public TextField txtEmail;
    public TextField txtPhone;
    public TextField txtNIC;
    public ImageView imgUser;
    public PasswordField pwd;
    public PasswordField pwdConform;
    public String url="src/images/default.jpg";
    public File file;
    public ComboBox<SystemUserComboTM> cmbRole;


    public void initialize(){
        setUserId();
        SystemUserIdCombo();
        file=new File(url);
        imgUser.setImage(new Image(file.toURI().toString()));
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

    public void btnChoseImage(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Image Chooser");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg*","*.png*","*.jpeg*"));
        file = fileChooser.showOpenDialog(null);
        if(file!=null){
            imgUser.setImage(new Image(file.toURI().toString()));
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
                    if(!file.toString().equals("src\\images\\default.jpg"))
                    {
                        url="src/images/IMG"+txtUserId.getText()+".jpeg";
                        File newFile = new File(url);
                        try {
                            Files.copy(file.toPath(),newFile.toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Connection connection = DBConnection.getInstance().getConnection();
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("insert into systemuser values(?,?,?,?,?,?,?,?)");
                        preparedStatement.setObject(1,id);
                        preparedStatement.setObject(2,name);
                        preparedStatement.setObject(3,email);
                        preparedStatement.setObject(4,phone);
                        preparedStatement.setObject(5,nic);
                        preparedStatement.setObject(6,password);
                        preparedStatement.setObject(7,url);
                        preparedStatement.setObject(8,role);
                        preparedStatement.executeUpdate();

                        Notifications notifications =Notifications.create();
                        notifications.title("User Add Notification");
                        notifications.text("new User add successfully");
                        notifications.graphic(null);
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.darkStyle();
                        notifications.showConfirm();
                        btnClear(actionEvent);

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

    public void btnClear(ActionEvent actionEvent) {
        txtUserName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtNIC.clear();
        pwd.clear();
        pwdConform.clear();
        cmbRole.setValue(null);
        url="src/images/default.jpg";
        setUserId();
        file=new File(url);
        imgUser.setImage(new Image(file.toURI().toString()));
        txtUserName.setStyle("[id=txt]");
        txtEmail.setStyle("[id=txt]");
        txtPhone.setStyle("[id=txt]");
        txtNIC.setStyle("[id=txt]");
        pwd.setStyle("[id=txt]");
        pwdConform.setStyle("[id=txt]");
    }

    public void setUserId(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from systemuser order by id desc limit 1;");
            ResultSet resultSet = preparedStatement.executeQuery();
            String oldId = null;
            String newId=null;
            if(resultSet.next()){
                oldId = resultSet.getString(1);
            }
            if(oldId==null){
                newId="C001";
            }else {
                int id=Integer.parseInt(oldId.substring(1,4));
                id=id+1;
                if (id<10){
                    newId="C00"+id;
                }else if(id<100){
                    newId="C0"+id;
                }else if(id<1000){
                    newId="C"+id;
                }
            }
            txtUserId.setText(newId);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
