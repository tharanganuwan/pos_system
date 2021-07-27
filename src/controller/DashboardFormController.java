package controller;

import db.DBConnection;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tm.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class DashboardFormController {
    public AnchorPane root;
    public Pane paneDashboard;
    public Pane paneStock;
    public Pane paneSuppliers;
    public Pane paneCustomers;
    public Pane paneReports;
    public Pane paneSettings;
    public Pane menuPaneDashboard;
    public Pane menuPaneStock;
    public Pane menuPaneSuppliers;
    public Pane menuPaneCustomers;
    public Pane menuPaneReports;
    public Pane menuPaneSettings;
    public ImageView image;
    public Label lblUserId;
    public Label lblUserName;
    public Label lblEmail;
    public Label lblPhone;
    public TableView<LoginTM> tmLoginHistory;
    public Label lblTime;
    public Label lblDate;
    public String userid = LoginFormController.id;
    public TextField txtCashierBalance;
    public Button btnChangeBalance;
    public Label lblTodaySellPrice;
    public Label lblLoginStatus;
    public TableView<StockTM> tblStock;
    public ObservableList<StockTM> items;
    public boolean stockRowIsDetected=false;
    public Button btnEndSoonItems;
    public Button btnStockOutItems;
    public Button btnExpireItems;
    public boolean btnEndSoonItemsIsClicked=false;
    public boolean btnStockOutItemsIsClicked=false;
    public boolean btnExpireItemsIsClicked=false;
    public TextField txtSearchStockItems;
    public TableView<SystemUsersTM> tblSystemUser;
    private boolean stopTime = false;
    public String loginTime;
    public String loginDate;
    public Thread thread;
    public static String cashierBalance;
    public boolean changeCashierBalance=false;
    public static String updateSelectItem;

    public void initialize(){
        //userid="C001";//////////////////////////////////////////////////////////////////////////////////////
        loginDetails();
        loginHistory();
        timeNow();
        setCashierBalance();
        calculateTodaySellPrice();

        tblStock.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStock.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStock.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        tblStock.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblStock.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("mfd"));
        tblStock.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("exp"));


    }

    public void PaneDashboardOnMouseClicked(MouseEvent mouseEvent) {
        mainVisible(1);
        setCashierBalance();
    }

    public void PaneStockOnMouseClicked(MouseEvent mouseEvent) {
        mainVisible(2);
        loadingStockTable();
    }

    public void PaneSuppliersOnMouseClicked(MouseEvent mouseEvent) {
        mainVisible(3);
    }

    public void PaneCustomersOnMouseClicked(MouseEvent mouseEvent) {
        mainVisible(4);
    }

    public void PaneReportsOnMouseClicked(MouseEvent mouseEvent) {
        mainVisible(5);
    }

    public void PaneSettingsOnMouseClicked(MouseEvent mouseEvent) {
        mainVisible(6);
        systemUsersTable();

    }

    public void systemUsersTable(){
        ObservableList<SystemUsersTM> items = tblSystemUser.getItems();
        items.clear();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from systemuser");
            while (resultSet.next()){
                String id =resultSet.getString(1);
                String name=resultSet.getString(2);
                String email=resultSet.getString(3);
                String phone=resultSet.getString(4);
                String nic=resultSet.getString(5);
                String role=resultSet.getString(8);

                SystemUsersTM users = new SystemUsersTM(id,name,email,phone,nic,role);
                items.add(users);
                tblSystemUser.refresh();
            }

            tblSystemUser.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
            tblSystemUser.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
            tblSystemUser.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("email"));
            tblSystemUser.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("phone"));
            tblSystemUser.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("nic"));
            tblSystemUser.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("role"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void mainVisible(int paneNumber){
        paneDashboard.setVisible(false);
        paneStock.setVisible(false);
        paneSuppliers.setVisible(false);
        paneCustomers.setVisible(false);
        paneReports.setVisible(false);
        paneSettings.setVisible(false);

        menuPaneDashboard.setStyle("*-fx-background-color: [paneMenu]");
        menuPaneStock.setStyle("*-fx-background-color: [paneMenu]");
        menuPaneSuppliers.setStyle("*-fx-background-color: [paneMenu]");
        menuPaneCustomers.setStyle("*-fx-background-color: [paneMenu]");
        menuPaneReports.setStyle("*-fx-background-color: [paneMenu]");
        menuPaneSettings.setStyle("*-fx-background-color: [paneMenu]");

        if(paneNumber==1){
            paneDashboard.setVisible(true);
            menuPaneDashboard.setStyle("-fx-background-color: white");
        }else if(paneNumber==2){
            paneStock.setVisible(true);
            menuPaneStock.setStyle("-fx-background-color: white");
        }else if(paneNumber==3){
            paneSuppliers.setVisible(true);
            menuPaneSuppliers.setStyle("-fx-background-color: white");
        }else if(paneNumber==4){
            paneCustomers.setVisible(true);
            menuPaneCustomers.setStyle("-fx-background-color: white");
        }else if(paneNumber==5){
            paneReports.setVisible(true);
            menuPaneReports.setStyle("-fx-background-color: white");
        }else if(paneNumber==6){
            paneSettings.setVisible(true);
            menuPaneSettings.setStyle("-fx-background-color: white");
        }
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You Want To Exit",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            logoutSaveDetails();
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.close();
        }

    }

    public void btnMinimizeOnAction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    public void btnLogOut(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You Want To Log Out", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)){
            logoutSaveDetails();
            Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        }
    }

    public void calculateTodaySellPrice(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from billing where DATE(date)=curdate()");
            double totalSell=0;
            while (resultSet.next()){
                totalSell=totalSell+Double.parseDouble(resultSet.getString(5));
            }
            lblTodaySellPrice.setText(String.valueOf(totalSell));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setCashierBalance(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from casher_balance");
            if(resultSet.next()){
                cashierBalance=resultSet.getString(1);
                txtCashierBalance.setText(cashierBalance);
                btnChangeBalance.setText("Change Balance");
                txtCashierBalance.setEditable(false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void timeNow(){
        thread = new Thread(() ->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            int loginTimeAndDate=1;// for the get first trad
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
                if(loginTimeAndDate==2){
                    loginTime=timeNow;
                    loginDate=dateNow;
                }
                loginTimeAndDate++;
            }
        });
        thread.start();
    }

    public void loginDetails(){
        mainVisible(1);
        String url = null;
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from systemUser where id=?");
            preparedStatement.setObject(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                lblUserId.setText(resultSet.getString(1));
                lblUserName.setText(resultSet.getString(2));
                lblEmail.setText(resultSet.getString(3));
                lblPhone.setText(resultSet.getString(4));
                url=resultSet.getString(7);
                lblLoginStatus.setText(resultSet.getString(2));
            }
            File file = new File(Objects.requireNonNull(url));
            File fileDefault = new File("src/images/default.jpg");
            if(file.exists()){
                image.setImage(new Image(file.toURI().toString()));
            } else {
                image.setImage(new Image(fileDefault.toURI().toString()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void loginHistory(){
        ObservableList<LoginTM> items = tmLoginHistory.getItems();
        items.clear();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from loginHistory L ,systemUser U where (L.userId=U.id) order by L.date desc,L.LoginTime desc limit 20");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String date=resultSet.getString(1);
                String name=resultSet.getString(7);
                String loginTime = resultSet.getString(3);
                String logoutTime = resultSet.getString(4);

                LoginTM loginTM = new LoginTM(date,name,loginTime,logoutTime);
                items.add(loginTM);
            }
            tmLoginHistory.refresh();

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        tmLoginHistory.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        tmLoginHistory.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tmLoginHistory.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("loginTime"));
        tmLoginHistory.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("logOutTime"));

    }

    public void logoutSaveDetails(){
        Time logoutTime = Time.valueOf(lblTime.getText());

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into loginHistory values(?,?,?,?,?);");
            preparedStatement.setObject(1,LoginFormController.loginDate);
            preparedStatement.setObject(2,userid);
            preparedStatement.setObject(3,LoginFormController.loginTime);
            preparedStatement.setObject(4,logoutTime);
            preparedStatement.setObject(5,"500");

            int i = preparedStatement.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        stopTime=true;
        LoginFormController.thread2.stop();
        thread.stop();
    }

    public void loadingStockTable(){
        Connection connection = DBConnection.getInstance().getConnection();
        items = tblStock.getItems();
        items.clear();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from item");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String id=resultSet.getString(1);
                String name=resultSet.getString(2);
                String price=resultSet.getString(3);
                String quantity=resultSet.getString(4);
                String mfd = resultSet.getString(5);
                String exp =resultSet.getString(6);

                StockTM stockTM=new StockTM(id,name,price,quantity,mfd,exp);
                items.add(stockTM);
            }
            tblStock.refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnBillingDashboardOnAction(ActionEvent actionEvent) throws IOException {
        thread.stop();
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/BillingForm.fxml"));
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public void btnChangeBalanceOnAction(ActionEvent actionEvent) {
        if(changeCashierBalance){

            if((txtCashierBalance.getText().isEmpty())||(Double.parseDouble(txtCashierBalance.getText())<0)){
                txtCashierBalance.clear();
                txtCashierBalance.requestFocus();
            }else {
                Connection connection = DBConnection.getInstance().getConnection();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("update casher_balance set  balance=?");
                    preparedStatement.setObject(1,txtCashierBalance.getText());
                    int i = preparedStatement.executeUpdate();
                    setCashierBalance();
                    changeCashierBalance=false;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else {
            Optional<ButtonType> alert=new Alert(Alert.AlertType.WARNING,"You are going to change CashierBalance").showAndWait();
            txtCashierBalance.clear();
            txtCashierBalance.requestFocus();
            btnChangeBalance.setText("Update Now");
            changeCashierBalance=true;
            txtCashierBalance.setEditable(true);
        }

    }

    public void txtCashierBalanceOnKeyTyped(KeyEvent keyEvent) {
        txtCashierBalance.setOnKeyTyped(event -> {
            char input = event.getCharacter().charAt(0);
            if(Character.isDigit(input)!=true){
                if(input=='.'){

                }
                else {event.consume();}
            }
        });
    }

    public void btnStockAddOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/stock/AddForm.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Add new items");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.show();
        btnClearAndRefreshOnAction(actionEvent);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if(!stockRowIsDetected){
            if(!tblStock.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You Want To Delete This Item..!",ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)){
                    StockTM selectedItem = tblStock.getSelectionModel().getSelectedItem();
                    String deleteItemId = selectedItem.getId();
                    Connection connection = DBConnection.getInstance().getConnection();
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("delete from item where id=?");
                        preparedStatement.setObject(1,deleteItemId);
                        preparedStatement.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    items.remove(selectedItem);
                    stockRowIsDetected=true;
                    btnClearAndRefreshOnAction(actionEvent);
                    Notifications notifications =Notifications.create();
                    notifications.title("Delete Notification");
                    notifications.text("Item Delete successfully");
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
                    notifications.showWarning();
                }
            }
        }

    }

    public void tblStockOnMousePressed(MouseEvent mouseEvent) {
        stockRowIsDetected=false;
    }

    public void btnStockUpdateOnAction(ActionEvent actionEvent) throws IOException {
        if(!tblStock.getSelectionModel().isEmpty()){
            StockTM selectedItem = tblStock.getSelectionModel().getSelectedItem();
            updateSelectItem = selectedItem.getId();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/stock/UpdateForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Update new items");
            stage.show();
        }
    }

    public void btnClearAndRefreshOnAction(ActionEvent actionEvent) {
        loadingStockTable();
        btnStockOutItemsIsClicked=false;
        btnExpireItemsIsClicked=false;
        btnEndSoonItemsIsClicked=false;
        btnStockOutItems.setStyle("*-fx-background-color: [paneMenuBackground]");
        btnExpireItems.setStyle("*-fx-background-color: [paneMenuBackground]");
        btnEndSoonItems.setStyle("*-fx-background-color: [paneMenuBackground]");
        txtSearchStockItems.clear();
    }

    public void btnStockOutItemsOnAction(ActionEvent actionEvent) {
        btnClearAndRefreshOnAction(actionEvent);
        if(!btnStockOutItemsIsClicked){
            btnStockOutItems.setStyle("-fx-background-color: #002000");
            btnStockOutItemsIsClicked=true;
            Connection connection = DBConnection.getInstance().getConnection();
            items = tblStock.getItems();
            items.clear();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from item where quantity<=0");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    String id=resultSet.getString(1);
                    String name=resultSet.getString(2);
                    String price=resultSet.getString(3);
                    String quantity=resultSet.getString(4);
                    String mfd = resultSet.getString(5);
                    String exp =resultSet.getString(6);

                    StockTM stockTM=new StockTM(id,name,price,quantity,mfd,exp);
                    items.add(stockTM);
                }
                tblStock.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }


    }

    public void btnExpireItemsOnAction(ActionEvent actionEvent) {
        btnClearAndRefreshOnAction(actionEvent);
        if(!btnExpireItemsIsClicked){
            btnExpireItemsIsClicked=true;
            btnExpireItems.setStyle("-fx-background-color: #002000");

            Connection connection = DBConnection.getInstance().getConnection();
            items = tblStock.getItems();
            items.clear();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from item where exp<curdate()");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    String id=resultSet.getString(1);
                    String name=resultSet.getString(2);
                    String price=resultSet.getString(3);
                    String quantity=resultSet.getString(4);
                    String mfd = resultSet.getString(5);
                    String exp =resultSet.getString(6);

                    StockTM stockTM=new StockTM(id,name,price,quantity,mfd,exp);
                    items.add(stockTM);
                }
                tblStock.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }

    public void btnEndSoonItemsOnAction(ActionEvent actionEvent) {
        btnClearAndRefreshOnAction(actionEvent);
        if(!btnEndSoonItemsIsClicked){
            btnEndSoonItemsIsClicked=true;
            btnEndSoonItems.setStyle("-fx-background-color: #002000");

            Connection connection = DBConnection.getInstance().getConnection();
            items = tblStock.getItems();
            items.clear();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from item where quantity<=10");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    String id=resultSet.getString(1);
                    String name=resultSet.getString(2);
                    String price=resultSet.getString(3);
                    String quantity=resultSet.getString(4);
                    String mfd = resultSet.getString(5);
                    String exp =resultSet.getString(6);

                    StockTM stockTM=new StockTM(id,name,price,quantity,mfd,exp);
                    items.add(stockTM);
                }
                tblStock.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }

    }

    public void txtSearchItemKeyReleased(KeyEvent keyEvent) {
        if(txtSearchStockItems.getText().isEmpty()){
            loadingStockTable();
        }
        else{
            ObservableList<StockTM> items = tblStock.getItems();
            items.clear();
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from item where name like ?");
                preparedStatement.setObject(1,txtSearchStockItems.getText()+"%");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    String id=resultSet.getString(1);
                    String name=resultSet.getString(2);
                    String price=resultSet.getString(3);
                    String quantity=resultSet.getString(4);
                    String mfd = resultSet.getString(5);
                    String exp =resultSet.getString(6);

                    StockTM stockTM=new StockTM(id,name,price,quantity,mfd,exp);
                    items.add(stockTM);
                }
                PreparedStatement preparedStatement1 = connection.prepareStatement("select * from item where id like ?");
                preparedStatement1.setObject(1,txtSearchStockItems.getText()+"%");
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()){
                    String id=resultSet1.getString(1);
                    String name=resultSet1.getString(2);
                    String price=resultSet1.getString(3);
                    String quantity=resultSet1.getString(4);
                    String mfd = resultSet1.getString(5);
                    String exp =resultSet1.getString(6);

                    StockTM stockTM=new StockTM(id,name,price,quantity,mfd,exp);
                    items.add(stockTM);
                }
                tblStock.refresh();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }




        }
    }

    public void btnDeleteAllItemsOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteAllSuppliersOnAction(ActionEvent actionEvent) {

    }

    public void btnResetHistoryOnAction(ActionEvent actionEvent) {

    }

    public void btnClearBillingHistoryOnAction(ActionEvent actionEvent) {

    }

    public void btnFactryDataResetOnAction(ActionEvent actionEvent) {

    }
}
