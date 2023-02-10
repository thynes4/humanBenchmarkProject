import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class ReactionTimeGame {
    private final BorderPane root;
    private final VBox greenBox;
    private final VBox keepGoing;
    private final VBox redBox;
    private long reactionStartTime;
    private final LongProperty reactionTimeValue;
    public ReactionTimeGame(HighScoreTracker h) {
        root = new BorderPane();
        greenBox = new VBox();
        VBox buttonBox = new VBox();
        Button initialStart = new Button("Start");

        Label dotDotDot2 = new Label("...");
        Label click = new Label("Click!");
        VBox buffBox2 = new VBox();
        buffBox2.setPrefHeight(100);

        click.setTextAlignment(TextAlignment.CENTER);
        click.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        click.setTextFill(Color.WHITE);

        dotDotDot2.setTextAlignment(TextAlignment.CENTER);
        dotDotDot2.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        dotDotDot2.setTextFill(Color.WHITE);

        greenBox.setBackground(Background.fill(Color.rgb(113, 219, 93)));
        greenBox.setAlignment(Pos.BASELINE_CENTER);
        greenBox.getChildren().addAll(buffBox2,dotDotDot2, click);

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setBackground(Background.fill(Color.rgb(71,136,214)));
        initialStart.setPrefSize(120,50);
        initialStart.setBackground(Background.fill(Color.LIGHTGREY));
        initialStart.setBorder(Border.stroke(Color.BLACK));
        initialStart.setFont(Font.font("Helvetica", FontWeight.BOLD,20));

        keepGoing = new VBox(7);
        Label clickToGo = new Label("Click to keep going");
        VBox buffBox3 = new VBox();
        buffBox3.setPrefHeight(100);

        clickToGo.setTextAlignment(TextAlignment.CENTER);
        clickToGo.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        clickToGo.setTextFill(Color.WHITE);

        keepGoing.setBackground(Background.fill(Color.rgb(71,136,214)));
        keepGoing.setAlignment(Pos.BASELINE_CENTER);

        reactionTimeValue = new SimpleLongProperty(0);
        HBox reactionTimeHBox = new HBox(1);
        Label reactionTime = new Label();
        Label msUnits = new Label("ms");

        reactionTime.setTextAlignment(TextAlignment.CENTER);
        reactionTime.setFont(Font.font("Helvetica", FontWeight.BOLD,40));
        reactionTime.setTextFill(Color.WHITE);

        msUnits.setTextAlignment(TextAlignment.CENTER);
        msUnits.setFont(Font.font("Helvetica", FontWeight.BOLD,40));
        msUnits.setTextFill(Color.WHITE);

        reactionTimeHBox.setAlignment(Pos.CENTER);

        reactionTimeHBox.getChildren().addAll(reactionTime, msUnits);

        keepGoing.getChildren().addAll(buffBox3, reactionTimeHBox, clickToGo);
        buttonBox.getChildren().addAll(initialStart);

        redBox = new VBox();
        Label dotDotDot = new Label("...");
        Label waitGreen = new Label("Wait for green");
        VBox buffBox1 = new VBox();
        buffBox1.setPrefHeight(100);

        waitGreen.setTextAlignment(TextAlignment.CENTER);
        waitGreen.setFont(Font.font("Helvetica", FontWeight.BOLD,40));
        waitGreen.setTextFill(Color.WHITE);

        dotDotDot.setTextAlignment(TextAlignment.CENTER);
        dotDotDot.setFont(Font.font("Helvetica", FontWeight.BOLD,40));
        dotDotDot.setTextFill(Color.WHITE);

        redBox.setBackground(Background.fill(Color.rgb(191,35,51)));
        redBox.setAlignment(Pos.BASELINE_CENTER);
        redBox.getChildren().addAll(buffBox1,dotDotDot,waitGreen);

        root.setCenter(buttonBox);

        initialStart.setOnAction(this::startTimer);
        keepGoing.setOnMouseClicked(this::startTimer);
        greenBox.setOnMouseClicked(event -> finishTimer(h));

        reactionTime.textProperty().bind(reactionTimeValue.asString());
    }
    public void startTimer(Event event) {
        Random rand = new Random();
        root.setCenter(null);
        root.setCenter(redBox);

        AnimationTimer a = new AnimationTimer() {
            private final long startTime = System.nanoTime();
            @Override
            public void handle(long now) {
                if (now > startTime +TimeUnit.MILLISECONDS.toNanos(rand.nextLong(1000,5000)))
                {
                    root.setCenter(null);
                    root.setCenter(greenBox);
                    reactionStartTime = System.nanoTime();
                    this.stop();
                }
            }
        };
        AnimationTimer b = new AnimationTimer() {
            @Override
            public void handle(long now) {
                root.setCenter(null);
                root.setCenter(redBox);
                this.stop();
                a.start();
            }
        };
        b.start();
    }

    public void finishTimer(HighScoreTracker h) {
        long finishTime = System.nanoTime();
        long reactionTimeNano = finishTime - reactionStartTime;
        long milliValue = TimeUnit.NANOSECONDS.toMillis(reactionTimeNano);
        reactionTimeValue.setValue(milliValue);
        updateHighScore(h);
        root.setCenter(null);
        root.setCenter(keepGoing);
    }

    public Pane getRoot() {
        return root;
    }
    private void updateHighScore(HighScoreTracker h) {
        if (h.getHighScores(1) == 0) {
            h.setHighScores(reactionTimeValue.longValue(),1);
        } else if (h.getHighScores(1) > reactionTimeValue.longValue()) {
            h.setHighScores(reactionTimeValue.longValue(),1);
        }
    }
}
