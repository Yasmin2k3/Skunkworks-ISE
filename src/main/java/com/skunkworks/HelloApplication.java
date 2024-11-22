package com.skunkworks;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application {



    int rowNum = 2;
    int colNum = 10;
    int cellHeight = 100;
    int cellWidth = 100;

    @Override
    public void start(Stage stage) throws IOException{
        stage.setTitle("Bok");
        GridPane gp = new GridPane();

        for (int col=0; col<colNum; col++){
            gp.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }
        for (int row=0; row<rowNum; row++){
            gp.getRowConstraints().add(new RowConstraints(cellHeight));
        }

        for (int row=0; row<rowNum; row++){
            for (int col=0; col<colNum; col++){
                Rectangle rec = new Rectangle(cellWidth-5, cellHeight-5, Color.BLACK);

                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                gp.getChildren().add(rec);
            }
        }

        gp.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gp, 350, 250);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}