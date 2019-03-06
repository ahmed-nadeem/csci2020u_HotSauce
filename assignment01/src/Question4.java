/*
Ahmed Nadeem
100619947

Assignment 1 - Question 4: Histogram
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Question4 extends Application {
    public class Histogram extends StackPane {
        private char[] chars = new char[26];
        private int countChars[] = new int[26];
        private Rectangle[] bars = new Rectangle[26];
        private File file;
        GridPane gp;

        double width = 350; // width
        double height = 1500; // height

        // method for creating instances of Histogram class
        public Histogram(String filename) {
            this.file = new File(filename);
            setWidth(width);
            setHeight(height);
            readFile();
            draw();
        }
        // reading the file to get all get text in a string
        private void readFile() {
            Scanner scanner;
            String text = "";
            try {
                scanner = new Scanner(file);
                while (scanner.hasNextLine()) {

                    text += scanner.nextLine();
                }
            } catch (IOException ex) {
            }

            // makes characters case insensitive, by making them all uppercase
            text = text.toUpperCase();
            // loops through all text to find count of each character
            for (int i = 0; i < text.length(); i++) {
                char character = text.charAt(i);
                if (Character.isLetter(character)) {
                    countChars[character - 'A']++;
                }
            }
        }
        // method to get total of all counts
        private double getTotal() {
            double total = 0;
            for (int count : countChars) {
                total += count;
            }
            return total;
        }

        // method to create the histogram for the count of each letter
        private void draw() {
            // creating grid pane
            gp = new GridPane();
            // get width of the bars
            double barWidth = width / chars.length;
            // getting total count of all letters
            double total = getTotal();

            for (int i = 0; i < countChars.length; i++) {
                chars[i] = (char) ('A' + i);
                // getting the percentage of the count of a letter from the total count of all letters
                double percentage = countChars[i] / total;
                // multiplying the height of the bar by the percentage & making that the bar height
                double barHeight = height * percentage;
                // creating the layout of the histogram bars
                bars[i] = new Rectangle(barWidth, barHeight);
                bars[i].setStroke(Color.BLACK);
                bars[i].setFill(Color.WHITE);
                // label for the chars and the bars & setting the bars to display above the chars
                Label label = new Label("  " + chars[i] + "  ", bars[i]);
                label.setContentDisplay(ContentDisplay.TOP);
                // layout settings for grid pane
                gp.setPadding(new Insets(10, 10, 10, 10));
                gp.add(label, i, 0);
                gp.setValignment(label, VPos.BOTTOM);
                gp.setAlignment(Pos.CENTER);
            }
            // adding grid pane to stack pane
            getChildren().addAll(gp);
        }
    }


    // creating stack pane, VBox, & text field
    StackPane pane = new StackPane();
    VBox vBox = new VBox();
    TextField fileName = new TextField();
    @Override
    public void start(Stage primaryStage) throws Exception {
        // creating label for text field
        Label fileNameLabel = new Label("Filename");
        // making textfield 20 column sizes long
        fileName.setPrefColumnCount(20);
        // creating view button
        Button btView = new Button("View");
        // creating HBox with spacing of 10
        HBox hBox = new HBox(10);
        // adding label, text field & button to HBox
        hBox.getChildren().addAll(fileNameLabel, fileName, btView);
        // aligning HBox to center
        hBox.setAlignment(Pos.CENTER);
        // giving HBox a border
        hBox.setStyle("-fx-border-width: 1px; -fx-border-color: black");

        // action even for "ENTER" key pressed in text field
        fileName.setOnAction(e-> {
            // clears histogram whenever enter key is pressed in text field
            pane.getChildren().clear();
            updateHistogram();
            primaryStage.sizeToScene();
        });
        // action event for "view" button clicked
        btView.setOnAction(e-> {
            // clears histogram whenever view button is pressed
            pane.getChildren().clear();
            updateHistogram();
            primaryStage.sizeToScene();
        });
        // aligning stack pane to center
        pane.setAlignment(Pos.CENTER);
        // adding stack pane & HBox to VBox
        vBox.getChildren().addAll(pane, hBox);
        // aligining VBox to bottom center
        vBox.setAlignment(Pos.BOTTOM_CENTER);

        primaryStage.setTitle("Histogram");
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //method to create histogram instance and update when new file is entered
    private void updateHistogram() {
        Histogram histogram = new Histogram(fileName.getText());
        // adding histogram to stack pane
        pane.getChildren().add(histogram);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
