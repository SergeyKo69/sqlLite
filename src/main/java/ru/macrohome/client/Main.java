package ru.macrohome.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.macrohome.server.Connector;

public class Main extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/contact.fxml"));
        primaryStage.setTitle("Contacts");
        primaryStage.setScene(new Scene(root,300,320));
        primaryStage.show();
    }

    @Override
    public void stop(){
        Connector.closeSessionFactory();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
