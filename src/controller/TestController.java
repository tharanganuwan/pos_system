package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.nio.file.Files;
import java.sql.*;

public class TestController {

    public ImageView image;
    public ImageView image2;

    //////////////////////////////////Image save database after get IMAGE AND SHOW/////////////////////////////////////////////
    public void btn(ActionEvent actionEvent) throws FileNotFoundException {     // ///////////////UPLOAD BUTTON
        final FileChooser fc = new FileChooser();
        fc.setTitle("Image Chooser");
        fc.getExtensionFilters().clear();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg*","*.png*","*.jpeg*"));
        File file = fc.showOpenDialog(null);
        if(file!=null){
            //image.setImage(new Image(file.toURI().toString()));
            System.out.println(file);
        }
        FileInputStream f = new FileInputStream(file); //image to convert binary code


        Connection connection = DBConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into pic values(?,?)");
            preparedStatement.setObject(1,3);

            preparedStatement.setBinaryStream(2,(InputStream) f,(int)file.length());
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnShow(ActionEvent actionEvent) {      /////////////////Download button
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from pic where id=?");
            preparedStatement.setObject(1,3);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean next = resultSet.next();
            if (next){
                InputStream is = resultSet.getBinaryStream(2);
                OutputStream os = new FileOutputStream(new File("photo.jpg"));

                byte[] content = new byte[1024];
                int size=0;
                while ((size = is.read(content)) != -1){
                    os.write(content,0,size);
                }
                os.close();
                is.close();

                Image i=new Image("file:photo.jpg",900,900,true,true);
                image.setImage(i);
            }
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//////////////////////////IMAGE GET AND SAVE SRC FOLDER  AFTER SAVE IMAGE PATH IN DATABASE. AFTER GET PATH AND LOAD FROM SRC FOLDER///////////////
    public void uplod(ActionEvent actionEvent) {         // ///////////////UPLOAD BUTTON
        final FileChooser fc = new FileChooser();

        fc.setTitle("Image Chooser");
        fc.getExtensionFilters().clear();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg*","*.png*","*.jpeg*"));
        File file = fc.showOpenDialog(null);
        if(file!=null){
            //image2.setImage(new Image(file.toURI().toString()));
            System.out.println(file);
            File newFile = new File("src/images/pic.jpeg");

            try {
                Files.copy(file.toPath(),newFile.toPath());

                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("insert into pic2 values(?)");
                preparedStatement.setObject(1,"src/images/pic.jpeg");
                int i = preparedStatement.executeUpdate();
                System.out.println(i);

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void show(ActionEvent actionEvent) {/////////////////Download button
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from pic2");
            System.out.println(resultSet);
            String url;
            if(resultSet.next()){
                url = resultSet.getString(1);
                System.out.println(url);


                File ff = new File(url);
                image2.setImage(new Image(ff.toURI().toString()));
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnOnAction(ActionEvent actionEvent) {
        //Image image = new Image("src/images/ok.png");

        File ff = new File("src/images/ok.png");
        Image image=new Image(ff.toURI().toString());


        Notifications notifications =Notifications.create();
        notifications.title("Item is Add Notification");
        notifications.text("Item add successfully");
        notifications.graphic(new ImageView(image));
        notifications.hideAfter(Duration.seconds(5));
        notifications.position(Pos.TOP_RIGHT);
        notifications.onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("AAAAAAAAAAA");
            }
        });
        notifications.showConfirm();
    }
}
