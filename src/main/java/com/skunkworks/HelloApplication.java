package com.skunkworks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        stage.setTitle("Bok");
        Text text1 = new Text("Email");
        Text text2 = new Text("Password");
        Text text3 = new Text("3");
        Text text4 = new Text("f");

        GridPane gp = new GridPane();
        gp.getColumnConstraints().add(new ColumnConstraints(10));
        gp.setGridLinesVisible(true);

        gp.setMinSize(400, 200);
        gp.setPadding(new Insets(10, 10, 10, 10));

        gp.setVgap(5);
        gp.setHgap(5);

        Scene scene = new Scene(gp);
        stage.setScene(scene);
        stage.show();
    }
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }

    public static void main(String[] args) {
        launch();
    }
}