package com.skunkworks;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HelloApplication extends Application {

    //TODO: math so that rownum and colnum depends on arraylist
    int rowNum = 2;
    int colNum = 10;
    int cellHeight = 70;
    int cellWidth = 70;

    public ArrayList makeList(){
        ArrayList<Integer> arr = new ArrayList<>();

        for(int i=0; i<20; i++){
            arr.add((int)(Math.random()*100 +1));
        }

        Collections.sort(arr);
        return arr;
    }

    //algorithm gotten from geeks for geeks
    //TODO: mid will make the grid rectangle yellow
    private int binarySearch(ArrayList<Integer> arr, int x, GridPane gp)
    {
        int low = 0, high = arr.size() - 1;
        while (low <= high) {
            Rectangle rec = new Rectangle(cellWidth-5, cellHeight-5);
            rec.setFill(Color.rgb(232, 215, 86));
            rec.setStroke(Color.rgb(204, 183, 47));
            rec.setStrokeWidth(2);
//
//            GridPane.setRowIndex(stackPane, row);
//            GridPane.setColumnIndex(stackPane, col);
//            gp.getChildren().add(stackPane);

            int mid = low + (high - low) / 2;

            // Check if x is present at mid
            if (arr.get(mid) == x)
                return mid;

            // If x greater, ignore left half
            if (arr.get(mid) < x)
                low = mid + 1;

                // If x is smaller, ignore right half
            else
                high = mid - 1;
        }

        // If we reach here, then element was
        // not present
        return -1;
    }

    @Override
    public void start(Stage stage) throws IOException{
        stage.setTitle("Bok");
        GridPane gp = new GridPane();
        ArrayList<Integer> sort = makeList();
        System.out.println(sort);

        for (int col=0; col<colNum; col++){
            gp.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }
        for (int row=0; row<rowNum; row++){
            gp.getRowConstraints().add(new RowConstraints(cellHeight));
        }

        for (int row=0; row<rowNum; row++){
            for (int col=0; col<colNum; col++){
                //System.out.println("index: " + (col + (10*row)));
                String textContents = Integer.toString(sort.get(col + (10*row)));
                Text text = new Text(textContents);
                text.setFill(Color.BLACK);
                text.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR,12));

                Rectangle rec = new Rectangle(cellWidth-5, cellHeight-5);
                rec.setFill(Color.rgb(91, 127, 255));
                rec.setStroke(Color.rgb(82, 64, 163));
                rec.setStrokeWidth(2);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rec, text);

                //use this for updating the colours:
                GridPane.setRowIndex(stackPane, row);
                GridPane.setColumnIndex(stackPane, col);
                gp.getChildren().add(stackPane);
            }
        }

        gp.setAlignment(Pos.CENTER);

        int randomNum = sort.get((int) (Math.random() * sort.size()));

        System.out.println("Finding: " + randomNum);
        System.out.println(binarySearch(sort, randomNum, gp));

        Scene scene = new Scene(gp, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}