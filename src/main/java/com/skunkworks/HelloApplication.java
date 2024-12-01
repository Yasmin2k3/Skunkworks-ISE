package com.skunkworks;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

//TODO: Add stackpane with grid and then label after?

public class HelloApplication extends Application {

    //TODO: math so that rownum and colnum depends on arraylist
    int rowNum = 2;
    int colNum = 10;
    int cellHeight = 70;
    int cellWidth = 70;
    VBox display = new VBox(8);

    public ArrayList makeList(){
        ArrayList<Integer> arr = new ArrayList<>();

        for(int i=0; i<20; i++){
            arr.add((int)(Math.random()*100 +1));
        }

        Collections.sort(arr);
        return arr;
    }

    //algorithm gotten from geeks for geeks
    //TODO: remove yellow rectangles if they already exist
    private int binarySearch(ArrayList<Integer> arr, int x, GridPane gp) throws InterruptedException {

        Rectangle rec = new Rectangle(cellWidth-5, cellHeight-5);
        rec.setFill(Color.rgb(232, 215, 86));
        rec.setStroke(Color.rgb(204, 183, 47));
        rec.setStrokeWidth(2);

        int low = 0, high = arr.size() - 1;

        // Create a Timeline for animations
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Store variables that need to change between keyframes
        final int[] currentLow = {low};
        final int[] currentHigh = {high};
        final boolean[] found = {false};

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.7), event -> {
            if (currentLow[0] > currentHigh[0] || found[0]) {
                timeline.stop();
                return;
            }

            int mid = currentLow[0] + (currentHigh[0] - currentLow[0]) / 2;

            // Update the grid with the current rectangle position
            GridPane.setRowIndex(rec, mid / 10); // 10 = number of columns
            GridPane.setColumnIndex(rec, mid % 10);
            if (!gp.getChildren().contains(rec)) {
                gp.getChildren().add(rec);
            }

            // Check if x is present at mid
            if (arr.get(mid) == x) {
                found[0] = true;
                System.out.println("Found at index: " + mid);
            } else if (arr.get(mid) < x) {
                currentLow[0] = mid + 1;
            } else {
                currentHigh[0] = mid - 1;
            }
        }));

        timeline.play();
        return -1;
    }

    private GridPane makeGrid(ArrayList<Integer> sort){
        GridPane gp = new GridPane();

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
                Label text = new Label(textContents);
                text.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR,12));

                Rectangle rec = new Rectangle(cellWidth-5, cellHeight-5);
                rec.setFill(Color.rgb(91, 127, 255));
                rec.setStroke(Color.rgb(82, 64, 163));
                rec.setStrokeWidth(2);

                //use this for updating the colours:
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                gp.getChildren().add(rec);
                gp.add(text, col, row);
                GridPane.setHalignment(text, HPos.CENTER);
            }
        }

        gp.setAlignment(Pos.CENTER);
        return gp;
    }


    @Override
    public void start(Stage stage) throws IOException{
        stage.setTitle("Binary Search Visualizer");
        ArrayList<Integer> sort = makeList();
        System.out.println(sort);

        GridPane gp = makeGrid(sort);

        //adding button
        Button button = new Button("Search for a random number");
        button.setDefaultButton(true);
        //make button do something on action:
        EventHandler<ActionEvent> event = new EventHandler<>() {
            public void handle(ActionEvent e) {
                int randomNum = sort.get((int) (Math.random() * sort.size()));
                System.out.println("Finding: " + randomNum);
                try {
                    binarySearch(sort, randomNum, gp);
                    //if we find it: celebrate
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        button.setOnAction(event);

        //displays everything on screen in a nice format
        display.getChildren().addAll(button, gp);
        display.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(display, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}