/*
Ahmed Nadeem
100619947

Assignment 1 - Question 2: Investment-Value Calculator
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Question2 extends Application{

    @Override
    public void start(Stage primaryStage){
        // create layout for grip pane
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setHgap(5);
        gp.setVgap(2);

        // text fields for data
        TextField investment = new TextField();
        TextField years = new TextField();
        TextField annualIR = new TextField();
        TextField futureValue = new TextField();

        // setting size of text field box
        investment.setPrefColumnCount(10);
        years.setPrefColumnCount(10);
        annualIR.setPrefColumnCount(10);
        futureValue.setPrefColumnCount(10);
        // disabling future value text, so you can't write in it
        futureValue.setDisable(true);

        // set allignments of the textfields to the right
        investment.setAlignment(Pos.BOTTOM_RIGHT);
        years.setAlignment(Pos.BOTTOM_RIGHT);
        annualIR.setAlignment(Pos.BOTTOM_RIGHT);
        futureValue.setAlignment(Pos.BOTTOM_RIGHT);

        // creating labels for each text field and bolding them
        Label text1 = new Label("Investment Amount");
        text1.setStyle("-fx-font-weight: bold");
        Label text2 = new Label("Years");
        text2.setStyle("-fx-font-weight: bold");
        Label text3 = new Label("Annual Interest Rate");
        text3.setStyle("-fx-font-weight: bold");
        Label text4 = new Label("Future Value");
        text4.setStyle("-fx-font-weight: bold");

        // adding label and textfield to grid pane
        gp.add(text1, 0, 0);
        gp.add(investment, 1, 0);
        gp.add(text2, 0, 1);
        gp.add(years, 1, 1);
        gp.add(text3, 0, 2);
        gp.add(annualIR, 1, 2);
        gp.add(text4, 0, 3);
        gp.add(futureValue, 1, 3);

        // creating layout for hbox
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setAlignment(Pos.BOTTOM_RIGHT);

        // button for calculating future value & bolding the text
        Button btCalc = new Button("Calculate");
        btCalc.setStyle("-fx-font-weight: bold");

        // adding button to hbox
        hBox.getChildren().add(btCalc);

        // creating layout of border pane to include both grid pane and hbox
        BorderPane bp = new BorderPane();
        bp.setCenter(gp);
        bp.setBottom(hBox);

        // when calculate button is pressed, future value is calculated
        btCalc.setOnAction(e -> {
            double inv = Double.parseDouble(investment.getText());
            int yr = Integer.parseInt(years.getText());
            double rt = Double.parseDouble(annualIR.getText());
            double res = inv * (Math.pow((1 + rt/12/100), (yr*12)));

            // to two decimal places
            futureValue.setText(String.format("%.2f", res));
        });

        primaryStage.setTitle("Investment-Value Calculator");
        Scene scene = new Scene(bp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[]args){
        launch(args);
    }
}
