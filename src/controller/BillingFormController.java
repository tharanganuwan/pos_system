package controller;

import db.DBConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tm.BillingItemsSummaryTM;
import tm.BuyItemBillingTM;
import tm.SearchItemsBillingTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

public class BillingFormController {
    public AnchorPane root;
    public ListView<SearchItemsBillingTM> lstSearchItems;
    public TextField txtSearchItems;
    public Label lblItemName;
    public TextField txtItemPrice;
    public TextField txtItemQuantity;
    public TableView<BuyItemBillingTM> tblBuyItems;
    private ObservableList<BuyItemBillingTM> buyItems;
    public TextField txtTotal;
    public TextField txtDiscount;
    public TextField txtPayment;
    public TextField txtBalance;
    public Label lblDate;
    public Label lblTime;
    public Label lblLoginName;
    public TableView<BillingItemsSummaryTM> tblBillingSummary;
    public boolean isDuplicateOrder=false;
    private boolean stopTime = false;
    private Thread thread2;
    public String cashierBalance=DashboardFormController.cashierBalance;

    public void initialize(){
        lstSearchItems.setVisible(false);
        lstSearchItems.requestFocus();
        txtItemPrice.setEditable(false);
        txtItemQuantity.setEditable(false);
        selectSearchItem();
        txtNumericValidation();
        timeNow();
        txtTotal.setEditable(false);
        txtBalance.setEditable(false);
        txtDiscount.setEditable(false);
        txtPayment.setEditable(false);
        lblLoginName.setText(LoginFormController.name);

        tblBuyItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblBuyItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        tblBuyItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblBuyItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("total"));

        tblBillingSummary.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        tblBillingSummary.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblBillingSummary.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("time"));
        tblBillingSummary.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("total"));
        tblBillingSummary.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblBillingSummary.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("payment"));
        tblBillingSummary.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("balance"));


    }

    public void timeNow(){
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
                    lblTime.setText(timeNow);
                });
                final String dateNow = simpleDateFormat2.format(new Date());
                Platform.runLater(()->{
                    lblDate.setText(dateNow);
                });
            }
        });
        thread2.start();
    }

    public void buyItemTableRefresh(){

        tblBuyItems.refresh();
        BuyItemBillingTM buyItemBillingTM = new BuyItemBillingTM();
        double total=0;
        for (int i=0;i<tblBuyItems.getItems().size();i++){
            buyItemBillingTM =tblBuyItems.getItems().get(i);
            total=total+Double.parseDouble(buyItemBillingTM.getTotal());
        }
        txtTotal.setText(String.valueOf(total));
    }

    public void txtNumericValidation(){
        txtItemQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    txtItemQuantity.setText(newValue.replaceAll("[^\\d]",""));
                }
            }
        });


    }

    public void selectSearchItem(){
        lstSearchItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SearchItemsBillingTM>() {
            @Override
            public void changed(ObservableValue<? extends SearchItemsBillingTM> observable, SearchItemsBillingTM oldValue, SearchItemsBillingTM newValue) {

                if(newValue!=null){
                    Connection connection = DBConnection.getInstance().getConnection();
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("select * from item where id = ? or name = ?");
                        preparedStatement.setObject(1,String.valueOf(newValue));
                        preparedStatement.setObject(2,String.valueOf(newValue));
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if(resultSet.next()){
                            txtItemQuantity.setEditable(true);
                            lblItemName.setText(resultSet.getString(2));
                            txtItemPrice.setText(resultSet.getString(3));
                            txtItemQuantity.setText("1");

                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    lstSearchItems.setVisible(false);
                    txtSearchItems.clear();
                    txtItemQuantity.requestFocus();
                }
            }

        });

    }

    public void btnCloseOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Do you want to exit to Dashboard",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            stopTime=true;
            thread2.stop();
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            Parent parent = FXMLLoader.load(this.getClass().getResource("../view/DashboardForm.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.setMaximized(false);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
            btnResetOnAction(actionEvent);
        }

    }

    public void btnMinimizeOnAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    public void txtSearchItemsOnKeyReleased(KeyEvent keyEvent) {
        if(!txtSearchItems.getText().isEmpty()){
            lstSearchItems.setVisible(true);
            ObservableList<SearchItemsBillingTM> itemsNames = lstSearchItems.getItems();
            itemsNames.clear();
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from item where name like ?");
                preparedStatement.setObject(1,txtSearchItems.getText()+"%");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    String id = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    double price = resultSet.getDouble(3);
                    double quantity = resultSet.getDouble(4);
                    SearchItemsBillingTM searchItemsBillingTM =new SearchItemsBillingTM();
                    searchItemsBillingTM.setNameItem(name);
                    itemsNames.add(searchItemsBillingTM);
                }
                PreparedStatement preparedStatement1 = connection.prepareStatement("select * from item where id like ?");
                preparedStatement1.setObject(1,txtSearchItems.getText()+"%");
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()){
                    String id = resultSet1.getString(1);
                    String name = resultSet1.getString(2);
                    double price = resultSet1.getDouble(3);
                    double quantity = resultSet1.getDouble(4);
                    SearchItemsBillingTM searchItemsBillingTM =new SearchItemsBillingTM();
                    searchItemsBillingTM.setIdItem(id);
                    itemsNames.add(searchItemsBillingTM);
                }
                lstSearchItems.refresh();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            lstSearchItems.setVisible(false);
        }

    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        lblItemName.setText("");
        txtItemPrice.clear();
        txtItemQuantity.clear();
        lstSearchItems.setVisible(false);
        txtSearchItems.clear();
        txtItemQuantity.setEditable(false);
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if(!((txtItemQuantity.getText().isEmpty()) || (Double.parseDouble(txtItemQuantity.getText())==0))){
            buyItems = tblBuyItems.getItems();
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from item where (name=? and unitPrice=?)");
                preparedStatement.setObject(1,lblItemName.getText());
                preparedStatement.setObject(2,txtItemPrice.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    String name = resultSet.getString(2);
                    String price = resultSet.getString(3);
                    String quantity = txtItemQuantity.getText();
                    String total = String.valueOf((Double.parseDouble(price)*Double.parseDouble(quantity)));
                    BuyItemBillingTM buyItemBillingTM = new BuyItemBillingTM(name,price,quantity,total);
                    buyItems.add(buyItemBillingTM);
                    txtDiscount.setEditable(true);
                    txtPayment.setEditable(true);
                    isDuplicateOrder=false;
                }
                buyItemTableRefresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            btnCancelOnAction(actionEvent);
        }else {
            txtItemQuantity.requestFocus();
        }

    }

    public void btnCancelOrderOnAction(ActionEvent actionEvent) {
        if(!tblBuyItems.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You Want To Cancel This Order..!",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)){
                buyItems.clear();
                buyItemTableRefresh();
                txtDiscount.setEditable(false);
                txtPayment.setEditable(false);
                txtPayment.setText("");
                txtBalance.setText("");
                txtDiscount.setText("0");
                isDuplicateOrder=false;
            }
        }
    }

    public void btnDeleteBuyItem(ActionEvent actionEvent) {
        if(!tblBuyItems.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You Want To Delete This Row..!",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)){
                BuyItemBillingTM selectedItem = tblBuyItems.getSelectionModel().getSelectedItem();
                buyItems.remove(selectedItem);
                buyItemTableRefresh();
                isDuplicateOrder=false;
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        buyItemTableRefresh();
        txtDiscount.setEditable(false);
        txtPayment.setEditable(false);
        txtDiscount.setText("0");
        txtPayment.setText("");
        txtBalance.setText("");
        isDuplicateOrder=false;
        txtSearchItems.clear();
        lstSearchItems.setVisible(false);
        lblItemName.setText("");
        txtItemQuantity.clear();
        txtItemPrice.clear();
        if(!tblBuyItems.getItems().isEmpty()){
            buyItems.clear();
        }
    }

    public void btnBalanceOnAction(ActionEvent actionEvent) {
        if (isDuplicateOrder){
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION,"Do you want to add Duplicate Bill",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.get().equals(ButtonType.NO)){
                return;
            }
        }
        if(!((txtPayment.getText().equals("")))){
            if((txtDiscount.getText().equals(""))){
                txtDiscount.setText("0");
            }
            double total=Double.parseDouble(txtTotal.getText());
            double discount=Double.parseDouble(txtDiscount.getText());
            double payment=Double.parseDouble(txtPayment.getText());
            double balance=0;

            if(!(payment<(total-discount))){
                balance=payment-total+discount;
                txtBalance.setText(String.valueOf(balance));
                addBillDatabaseAndTable();
                reduceQuantityFromDatabase();
                ////////////////////////////////////////////////////////////////
                isDuplicateOrder=true;
            }else {
                new Alert(Alert.AlertType.WARNING,"Payment is not Enough.......!").showAndWait();
                txtPayment.requestFocus();
            }
        }
    }

    public void addBillDatabaseAndTable(){
        ObservableList<BillingItemsSummaryTM> summary = tblBillingSummary.getItems();
        String invoiceId=invoiceNumber();
        String invoiceBy=LoginFormController.id;
        String date=lblDate.getText();
        String time=lblTime.getText();
        String total=txtTotal.getText();
        String discount=txtDiscount.getText();
        String payment = txtPayment.getText();
        String balance = txtBalance.getText();

        BillingItemsSummaryTM billingItemsSummaryTM=new BillingItemsSummaryTM(invoiceId,date,time,total,discount,payment,balance);
        summary.add(0,billingItemsSummaryTM);
        tblBillingSummary.refresh();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into billing values(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setObject(1,invoiceId);
            preparedStatement.setObject(2,invoiceBy);
            preparedStatement.setObject(3,date);
            preparedStatement.setObject(4,time);
            preparedStatement.setObject(5,Double.parseDouble(total));
            preparedStatement.setObject(6,Double.parseDouble(discount));
            preparedStatement.setObject(7,Double.parseDouble(payment));
            preparedStatement.setObject(8,Double.parseDouble(balance));
            preparedStatement.setObject(9,"No");
            preparedStatement.executeUpdate();

            cashierBalance=String.valueOf(Double.parseDouble(cashierBalance)+Double.parseDouble(total)-(Double.parseDouble(discount)));
            PreparedStatement preparedStatement1 = connection.prepareStatement("update casher_balance set balance=?");//set cashier balance
            preparedStatement1.setObject(1,cashierBalance);
            preparedStatement1.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void reduceQuantityFromDatabase(){
        tblBuyItems.refresh();
        BuyItemBillingTM buyItemBillingTM2 = new BuyItemBillingTM();
        String[]arrayItemName=new String[tblBuyItems.getItems().size()];
        String[]arrayItemQuantity=new String[tblBuyItems.getItems().size()];
        for(int i=0;i<tblBuyItems.getItems().size();i++){
            buyItemBillingTM2 =tblBuyItems.getItems().get(i);
            arrayItemName[i] = buyItemBillingTM2.getItemName();
            arrayItemQuantity[i] = buyItemBillingTM2.getQuantity();
        }
        Connection connection = DBConnection.getInstance().getConnection();
        for(int i=0;i<arrayItemName.length;i++){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from item where name=?");
                preparedStatement.setObject(1,arrayItemName[i]);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    String oldQuantity = resultSet.getString(4);
                    String newQuantity = String.valueOf(Double.parseDouble(oldQuantity)-Double.parseDouble(arrayItemQuantity[i]));
                    PreparedStatement preparedStatement1 = connection.prepareStatement("update item set quantity= ? where name=?");
                    preparedStatement1.setObject(1,newQuantity);
                    preparedStatement1.setObject(2,arrayItemName[i]);
                    preparedStatement1.executeUpdate();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public String invoiceNumber(){

        Calendar calendar=new GregorianCalendar();
        String day=String.valueOf(calendar.get(Calendar.DATE));
        String month=String.valueOf(calendar.get(Calendar.MONTH));
        String year=String.valueOf(calendar.get(Calendar.YEAR)).substring(2,4);
        String time=lblTime.getText().substring(0,2)+lblTime.getText().substring(3,5)+lblTime.getText().substring(6,8);
        return "R"+day+month+lblLoginName.getText().substring(0,2)+year+time;

    }

    public void txtDiscountOnKeyTyped(KeyEvent keyEvent) {
        txtDiscount.setOnKeyTyped(event -> {
            char input = event.getCharacter().charAt(0);
            if(input=='.'){

            }else if(Character.isDigit(input)!=true){
                event.consume();
            }
        });
    }

    public void txtPaymentOnKeyTyped(KeyEvent keyEvent) {
        txtPayment.setOnKeyTyped(event -> {
            char input = event.getCharacter().charAt(0);
            if(input=='.'){

            }else if(Character.isDigit(input)!=true){
                event.consume();
            }
        });
    }
}
