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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tm.StockTM;
import tm.SupplierIdComboTM;

import java.sql.*;
import java.time.LocalDate;


public class UpdateFormController extends DashboardFormController {

    public TextField txtItemId;
    public TextField txtItemName;
    public TextField txtItemSellPrice;
    public TextField txtItemQuantity;
    public TextField txtBuyPrice;
    public DatePicker dateMFD;
    public DatePicker dateEXP;
    public ComboBox<SupplierIdComboTM> cmbSupplierId;

    public void initialize(){
        txtItemId.setEditable(false);
        txtItemName.requestFocus();
        dateMFD.setEditable(false);
        dateEXP.setEditable(false);
        supplierIdCombo();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from item I,supplier S where (I.supplier_id=S.id) and (I.id=?)");
            preparedStatement.setObject(1, DashboardFormController.updateSelectItem);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                txtItemId.setText(resultSet.getString(1));
                txtItemName.setText(resultSet.getString(2));
                txtItemSellPrice.setText(resultSet.getString(3));
                txtItemQuantity.setText(resultSet.getString(4));
                txtBuyPrice.setText(resultSet.getString(8));
                if(!((resultSet.getString(5)==null) ||(resultSet.getString(6))==null)){
                    dateMFD.setValue(LocalDate.parse(resultSet.getString(5)));
                    dateEXP.setValue(LocalDate.parse(resultSet.getString(6)));
                }

                String supplier = resultSet.getString(7);
                for(int i=0;i<cmbSupplierId.getItems().size();i++){
                    String s = String.valueOf(cmbSupplierId.getItems().get(i));
                    if(supplier.equals(s.substring(0,5))){
                        cmbSupplierId.getSelectionModel().select(i);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    public void btnClearOnAction(ActionEvent actionEvent) {

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
        String supplerId=String.valueOf(cmbSupplierId.getValue()).substring(0,5);
        String buyPrice=txtBuyPrice.getText();
        String mfd= String.valueOf(dateMFD.getValue());
        String exp=String.valueOf(dateEXP.getValue());

        if(!(name.equals("")||sellPrice.equals("")||quantity.equals("")||buyPrice.equals("")||id.equals(""))){

            txtItemName.setStyle("[id=txt]");
            txtItemSellPrice.setStyle("[id=txt]");
            txtItemQuantity.setStyle("[id=txt]");
            txtBuyPrice.setStyle("[id=txt]");
            if((cmbSupplierId.getValue()==null)){
                new Alert(Alert.AlertType.WARNING, "please Select Supplier ID").showAndWait();
            }
            else {
                Connection connection = DBConnection.getInstance().getConnection();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("update item set name=?,unitPrice=?,quantity=?,mfd=?,exp=?,supplier_id=?,buyPrice=? where id=?");
                    preparedStatement.setObject(8,id);
                    preparedStatement.setObject(1,name);
                    preparedStatement.setObject(2,sellPrice);
                    preparedStatement.setObject(3,quantity);
                    preparedStatement.setObject(6,supplerId);
                    preparedStatement.setObject(7,buyPrice);
                    if(dateMFD.getValue()==null){
                        preparedStatement.setObject(4,null);
                    }else {
                        preparedStatement.setObject(4,mfd);
                    }
                    if(dateEXP.getValue()==null){
                        preparedStatement.setObject(5,null);
                    }else {
                        preparedStatement.setObject(5,exp);
                    }
                    preparedStatement.executeUpdate();
                    btnClearOnAction(actionEvent);
                    //loadingStockTable();
                    Notifications notifications =Notifications.create();
                    notifications.title("Item update Notification");
                    notifications.text("Item update successfully");
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

    public void txtItemSellPriceOnKeyTyped(KeyEvent keyEvent) {
        inputValidation(txtItemSellPrice);
    }

    public void txtItemQuantityOnKeyTyped(KeyEvent keyEvent) {
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
