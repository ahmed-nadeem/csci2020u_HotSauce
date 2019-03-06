/*
Ahmed Nadeem
100619947

Assignment 1 - Question 1: Displaying Three Cards
 */

import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Question1 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // creating horizontal box with spacing of 5 between nodes
        HBox hBox = new HBox(5);
        // creating image views of 3 random cards from 54 cards
        ImageView card1 = new ImageView("Cards/"+(int)(Math.random()*54+1)+".png");
        ImageView card2 = new ImageView("Cards/"+(int)(Math.random()*54+1)+".png");
        ImageView card3 = new ImageView("Cards/"+(int)(Math.random()*54+1)+".png");

        // adding the 3 random cards in the box
        hBox.getChildren().addAll(card1, card2, card3);

        primaryStage.setTitle("Displaying Three Cards");
        Scene scene = new Scene(hBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
