/*
Ahmed Nadeem
100619947

Assignment 1 - Question 3: Dragging Points on a Circle
 */

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Question3 extends Application {

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // creating layout for pane
        Pane pane = new Pane();
        double width = 400;
        double height = 400;

        // creating layout for circle
        Circle circle = new Circle(width/2,height/2,100);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        pane.getChildren().add(circle);

        // arrays for 3 points on circle, lines, text for angles
        Circle[] points = new Circle[3];
        Line[] lines = new Line[3];
        Text[] texts = new Text[3];

        // establishing points, and texts
        for (int i = 0; i < 3; i++) {
            texts[i] = new Text();
            points[i] = new Circle(0,0,5);
            points[i].setFill(Color.RED);
            points[i].setStroke(Color.BLACK);

            // randomizing location of points on circle
            randomize(points[i], circle);

            // for use in lambda expression action event
            final int index = i;
            // when mouse drags a point, the lines and angles update corresponding to movement
            points[i].setOnMouseDragged(e -> {
                double radian = Math.atan2(e.getY() - circle.getCenterY(), e.getX() - circle.getCenterX());
                double x = circle.getCenterX() + circle.getRadius() * Math.cos(radian);
                double y = circle.getCenterY() + circle.getRadius() * Math.sin(radian);
                points[index].setCenterX(x);
                points[index].setCenterY(y);

                updateLines(lines, points, texts);
            });
        }

        for (int i = 0; i < 3; i++) {
            int j = (i + 1 >= 3) ? 0 : i + 1;
            lines[i] = new Line(
                    points[i].getCenterX(), points[i].getCenterY(),
                    points[j].getCenterX(), points[j].getCenterY());
        }
        // for initial display of angles
        updateLines(lines, points, texts);

        // adding points, lines, and angles to pane
        pane.getChildren().addAll(lines);
        pane.getChildren().addAll(texts);
        pane.getChildren().addAll(points);

        primaryStage.setTitle("Dragging Points on a Circle");
        Scene scene = new Scene(pane, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // updating lines and angles method
    private void updateLines(Line[] lines, Circle[] p, Text[] angles) {
        for (int i = 0; i < 3; i++) {
            int j = (i + 1 >= 3) ? 0 : i + 1;
            lines[i].setStartX(p[i].getCenterX());
            lines[i].setStartY(p[i].getCenterY());
            lines[i].setEndX(p[j].getCenterX());
            lines[i].setEndY(p[j].getCenterY());

            angles[i].setX(p[i].getCenterX());
            angles[i].setY(p[i].getCenterY() - 10);
        }

        double a = new Point2D(p[2].getCenterX(), p[2].getCenterY()).
                distance(p[1].getCenterX(), p[1].getCenterY());
        double b = new Point2D(p[2].getCenterX(), p[2].getCenterY()).
                distance(p[0].getCenterX(), p[0].getCenterY());
        double c = new Point2D(p[1].getCenterX(), p[1].getCenterY()).
                distance(p[0].getCenterX(), p[0].getCenterY());

        int A = (int)Math.round(Math.toDegrees(Math.acos((a * a - b * b - c * c) / (-2 * b * c))));
        angles[0].setText(A + "");

        int B = (int)Math.round(Math.toDegrees(Math.acos((b * b - a * a - c * c) / (-2 * a * c))));
        angles[1].setText(B + "");

        int C = (int)Math.round(Math.toDegrees(Math.acos((c * c - b * b - a * a) / (-2 * a * b))));
        angles[2].setText(C + "");


    }
    // randomize method for points on circle
    private void randomize(Circle point, Circle circle) {
        double angle = Math.random() * 360;
        double x = circle.getCenterX() + circle.getRadius() * Math.cos(Math.toRadians(angle));
        double y = circle.getCenterY() + circle.getRadius() * Math.sin(Math.toRadians(angle));
        point.setCenterX(x);
        point.setCenterY(y);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
