package controller.stock;


import controller.DashboardFormController;
import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tm.SupplierIdComboTM;

import java.sql.*;


public class AddFormController extends DashboardFormController {

    public TextField txtItemId;
    public TextField txtItemName;
    public TextField txtItemSellPrice;
    public TextField txtItemQuantity;
    public TextField txtBuyPrice;
    public DatePicker dateMFD;
    public DatePicker dateEXP;
    public ComboBox<SupplierIdComboTM> cmbSupplierId;

    public void initialize(){
        setItemId();
        txtItemId.setEditable(false);
        dateMFD.setEditable(false);
        dateEXP.setEditable(false);
        txtItemName.requestFocus();
        supplierIdCombo();
    }

    public void supplierIdCombo(){
        ObservableList<SupplierIdComboTM> items = cmbSupplierId.getItems();
        items.clear();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from supplier");
            while (resultSet.next()){
                SupplierIdComboTM supplierIdComboTM=new SupplierIdComboTM(resultSet.getString(1),resultSet.getString(2));
                items.add(supplierIdComboTM);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setItemId(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from item order by id desc limit 1;");
            ResultSet resultSet = preparedStatement.executeQuery();
            String oldId = null;
            String newId=null;
            if(resultSet.next()){
                oldId = resultSet.getString(1);
            }
            if(oldId==null){
                newId="I00001";
            }else {
                int id=Integer.parseInt(oldId.substring(1,6));
                id=id+1;
                if (id<10){
                    newId="I0000"+id;
                }else if(id<100){
                    newId="I000"+id;
                }else if(id<1000){
                    newId="I00"+id;
                }else if(id<10000){
                    newId="I0"+id;
                }else if(id<100000){
                    newId="I"+id;
                }
            }
            txtItemId.setText(newId);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        setItemId();
        txtItemName.clear();
        txtItemSellPrice.clear();
        txtItemQuantity.clear();
        cmbSupplierId.setValue(null);
        txtBuyPrice.clear();
        dateMFD.setValue(null);
        dateEXP.setValue(null);
        txtBuyPrice.setStyle("[id=txt]");
        cmbSupplierId.setStyle("[id=txt]");
        txtItemQuantity.setStyle("[id=txt]");
        txtItemSellPrice.setStyle("[id=txt]");
        txtItemName.setStyle("[id=txt]");
    }

    public void btnSubmitOnAction(ActionEvent actionEvent) {
        String id=txtItemId.getText();
        String name=txtItemName.getText();
        String sellPrice=txtItemSellPrice.getText();
        String quantity=txtItemQuantity.getText();
        String buyPrice=txtBuyPrice.getText();
        String mfd= String.valueOf(dateMFD.getValue());
        String exp=String.valueOf(dateEXP.getValue());

        if(!(name.equals("")||sellPrice.equals("")||quantity.equals("")||buyPrice.equals("")||id.equals(""))){
            String supplerId=String.valueOf(cmbSupplierId.getValue());
            txtItemName.setStyle("[id=txt]");
            txtItemSellPrice.setStyle("[id=txt]");
            txtItemQuantity.setStyle("[id=txt]");
            txtBuyPrice.setStyle("[id=txt]");
            if((cmbSupplierId.getValue()==null)){
                new Alert(Alert.AlertType.WARNING, "please Select Supplier ID").showAndWait();
            }
            else {
                Connection connection = DBConnection.getInstance().getConnection();
                supplerId=String.valueOf(cmbSupplierId.getValue()).substring(0,5);
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("insert into item values(?,?,?,?,?,?,?,?)");
                    preparedStatement.setObject(1,id);
                    preparedStatement.setObject(2,name);
                    preparedStatement.setObject(3,sellPrice);
                    preparedStatement.setObject(4,quantity);
                    preparedStatement.setObject(7,supplerId);
                    preparedStatement.setObject(8,buyPrice);
                    if(dateMFD.getValue()==null){
                        preparedStatement.setObject(5,null);
                    }else {
                        preparedStatement.setObject(5,mfd);
                    }
                    if(dateEXP.getValue()==null){
                        preparedStatement.setObject(6,null);
                    }else {
                        preparedStatement.setObject(6,exp);
                    }
                    preparedStatement.executeUpdate();
                    btnClearOnAction(actionEvent);
                    //loadingStockTable();
                    //////////////////////////////////////////////////////////
                    Notifications notifications =Notifications.create();
                    notifications.title("Item Add Notification");
                    notifications.text("Item add successfully");
                    notifications.graphic(null);
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    /*
                    notifications.onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("AAAAAAAAAAA");
                        }
                    });

                     */
                    notifications.darkStyle();
                    notifications.showConfirm();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        else {
            //new Alert(Alert.AlertType.WARNING,"Please Fill this fields").showAndWait();
            if(name.equals("")){
                txtItemName.setStyle("-fx-border-color: red");
            }else {
                txtItemName.setStyle("[id=txt]");
            }
            if(sellPrice.equals("")){
                txtItemSellPrice.setStyle("-fx-border-color: red");
            }
            else {
                txtItemSellPrice.setStyle("[id=txt]");
            }
            if(quantity.equals("")){
                txtItemQuantity.setStyle("-fx-border-color: red");
            }else {
                txtItemQuantity.setStyle("[id=txt]");
            }
            if(buyPrice.equals("")){
                txtBuyPrice.setStyle("-fx-border-color: red");
            }else {
                txtBuyPrice.setStyle("[id=txt]");
            }
        }

    }

    public void txtItemSellPriceKeyTyped(KeyEvent keyEvent) {
        inputValidation(txtItemSellPrice);
    }

    public void txtItemQuantityKeyTyped(KeyEvent keyEvent) {
        inputValidation(txtItemQuantity);
    }

    public void txtBuyPriceOnKeyTyped(KeyEvent keyEvent) {
        inputValidation(txtBuyPrice);
    }

    public void inputValidation(TextField txt){
        txt.setOnKeyTyped(event -> {
            char input = event.getCharacter().charAt(0);
            if(input=='.'){

            }else if(Character.isDigit(input)!=true){
                event.consume();
            }
        });
    }
}
