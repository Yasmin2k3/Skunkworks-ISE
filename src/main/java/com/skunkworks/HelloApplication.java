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
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
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
    //TODO: make sort in constructor
    int rowNum = 2;
    int colNum = 10;
    int cellHeight = 70;
    int cellWidth = 70;
    VBox display = new VBox(30);
    VBox inputGrid = new VBox(8);

    public ArrayList<Integer> makeList(){
        ArrayList<Integer> arr = new ArrayList<>();

        for(int i=0; i<20; i++){
            arr.add((int)(Math.random()*100 +1));
        }

        Collections.sort(arr);
        return arr;
    }

    //algorithm gotten from geeks for geeks
    //TODO: remove yellow rectangles if they already exist
    private void binarySearch(ArrayList<Integer> arr, int x, GridPane gp) throws InterruptedException {

        Rectangle rec = new Rectangle(cellWidth-5, cellHeight-5);
        rec.setFill(Color.rgb(232, 215, 86));
        rec.setStroke(Color.rgb(204, 183, 47));
        rec.setStrokeWidth(2);

        //these need to be final for some reason
        final int low = 0, high = arr.size() - 1;

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Store variables that need to change between keyframes
        int[] currentLow = {low};
        int[] currentHigh = {high};
        boolean[] found = {false};

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            //to use for 'found' or 'not found'
            Label resultLabel;

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
                System.out.println("Found index: " + mid);
            } else if (arr.get(mid) < x) {
                currentLow[0] = mid + 1;
            } else {
                currentHigh[0] = mid - 1;
            }
        }));

        timeline.play();
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

    private VBox randomSearch(ArrayList<Integer> sort){
        VBox randomGrid = new VBox(8);
        System.out.println(sort);
        GridPane gp = makeGrid(sort);

        //adding button
        Button button = new Button("Search for a random number");
        button.setDefaultButton(true);
        //make button do something on action:
        EventHandler<ActionEvent> event = new EventHandler<>() {
            public void handle(ActionEvent e) {
                int randomNum = sort.get((int) (Math.random() * sort.size()));
                Label finding = new Label("finding: " + randomNum);
                HBox buttonAndText = new HBox(5, button, finding);
                buttonAndText.setAlignment(Pos.CENTER);
                //refresh:
                randomGrid.getChildren().clear();
                GridPane gp = makeGrid(sort);
                randomGrid.getChildren().addAll(buttonAndText, gp);

                try {
                    binarySearch(sort, randomNum, gp);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        button.setOnAction(event);

        //displays everything on screen in a nice format
        randomGrid.getChildren().addAll(button, gp);
        randomGrid.setAlignment(Pos.TOP_CENTER);

        return randomGrid;
    }

    private VBox inputSearch(ArrayList<Integer> sort){
        VBox inputGrid = new VBox(8);
        GridPane gp = makeGrid(sort);

        //adding input
        Label label1 = new Label("Search number:");
        TextField textField = new TextField();
        //button for input
        Button button = new Button("Search");
        button.setDefaultButton(true);
        //make button do something on action:

        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);

        EventHandler<ActionEvent> event = new EventHandler<>() {
            public void handle(ActionEvent e) {
                //handling inputted number:
                int num=0;
                try{
                    num = Integer.parseInt(textField.getText());
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    throw new RuntimeException();
                }


                Label finding = new Label("finding: " + num);
                HBox buttonAndText = new HBox(5, hb, finding);
                buttonAndText.setAlignment(Pos.CENTER);
                //refresh:
                inputGrid.getChildren().clear();
                GridPane gp = makeGrid(sort);
                inputGrid.getChildren().addAll(buttonAndText, gp);

                try {
                    binarySearch(sort, num, gp);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        button.setOnAction(event);

        inputGrid.getChildren().addAll(hb, gp);
        inputGrid.setAlignment(Pos.TOP_CENTER);

        return inputGrid;
    }

    @Override
    public void start(Stage stage) throws IOException{
        stage.setTitle("Binary Search Visualizer");
        ArrayList<Integer> sort = makeList();
        VBox randomGrid = randomSearch(sort);
        VBox inputGrid = inputSearch(sort);

        display.getChildren().addAll(randomGrid, inputGrid);

        Scene scene = new Scene(display, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}