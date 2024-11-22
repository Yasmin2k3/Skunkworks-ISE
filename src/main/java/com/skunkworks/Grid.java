package com.skunkworks;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class Grid{
    int rowNum = 2;
    int colNum = 10;
    int cellHeight = 100;
    int cellWidth = 100;

    public void createGrid(){
        GridPane gp = new GridPane();

        try{
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
        }
        catch( Exception e){
            System.out.println("Failed to create grid" + e);
        }


        gp.setAlignment(Pos.CENTER);
    }
}
