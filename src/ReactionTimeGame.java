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

/** Welcome to Reaction Time Game **/
public class ReactionTimeGame extends Game {
    private final BorderPane root;
    private final VBox greenBox;
    private final VBox keepGoing;
    private final VBox redBox;
    private long reactionStartTime;
    private final LongProperty reactionTimeValue;

    /**
     * Draws the game and sets the behavior of what to do when each different color screen is pressed
     * @param h high score tracker
     */
    public ReactionTimeGame(HighScoreTracker h) {
        root = new BorderPane();
        greenBox = new VBox();
        redBox = new VBox();
        keepGoing = new VBox(7);

        VBox buttonBox = getBlueVbox();
        Button initialStart = makeButtonGood(new Button("Start"));

        Label dotDotDot2 = makeLabelGood(new Label("..."));
        Label click = makeLabelGood(new Label("Click!"));
        VBox buffBox2 = new VBox();
        buffBox2.setPrefHeight(100);

        greenBox.setBackground(Background.fill(Color.rgb(113, 219, 93)));
        greenBox.setAlignment(Pos.BASELINE_CENTER);
        greenBox.getChildren().addAll(buffBox2,dotDotDot2, click);

        Label clickToGo = makeLabelGood(new Label("Click to keep going"));
        VBox buffBox3 = new VBox();
        buffBox3.setPrefHeight(100);

        keepGoing.setBackground(Background.fill(Color.rgb(71,136,214)));
        keepGoing.setAlignment(Pos.BASELINE_CENTER);

        reactionTimeValue = new SimpleLongProperty(0);
        HBox reactionTimeHBox = new HBox(1);
        Label reactionTime = new Label();
        Label msUnits = makeLabelGood(new Label("ms"));

        reactionTime.setTextAlignment(TextAlignment.CENTER);
        reactionTime.setFont(Font.font("Helvetica", FontWeight.BOLD,40));
        reactionTime.setTextFill(Color.WHITE);

        reactionTimeHBox.setAlignment(Pos.CENTER);

        reactionTimeHBox.getChildren().addAll(reactionTime, msUnits);

        keepGoing.getChildren().addAll(buffBox3, reactionTimeHBox, clickToGo);
        buttonBox.getChildren().addAll(initialStart);

        Label dotDotDot = makeLabelGood(new Label("..."));
        Label waitGreen = makeLabelGood(new Label("Wait for green"));
        VBox buffBox1 = new VBox();
        buffBox1.setPrefHeight(100);

        redBox.setBackground(Background.fill(Color.rgb(191,35,51)));
        redBox.setAlignment(Pos.BASELINE_CENTER);
        redBox.getChildren().addAll(buffBox1,dotDotDot,waitGreen);

        root.setCenter(buttonBox);

        initialStart.setOnAction(this::startTimer);
        keepGoing.setOnMouseClicked(this::startTimer);
        greenBox.setOnMouseClicked(event -> finishTimer(h));

        reactionTime.textProperty().bind(reactionTimeValue.asString());
    }

    /**
     * Sets the screen to the red box then waits a random reasonable amount of time then sets the screen
     * to the green box to be clicked
     * @param event starts when start button is pressed
     */
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

    /**
     * When the green box is clicked this will calculate and send the score to check if it's a new highs core and then will
     * ask the user if they wish to keep going
     * @param h HighScoreTracker to be updated
     */
    public void finishTimer(HighScoreTracker h) {
        long finishTime = System.nanoTime();
        long reactionTimeNano = finishTime - reactionStartTime;
        long milliValue = TimeUnit.NANOSECONDS.toMillis(reactionTimeNano);
        reactionTimeValue.setValue(milliValue);
        updateHighScore(h);
        root.setCenter(null);
        root.setCenter(keepGoing);
    }

    /**
     * @return the root containing the game to the Main class
     */
    public Pane getRoot() {
        return root;
    }
    /**
     * Checks to see if a new high score is reached and if so it updates the high score tracker
     * @param h updated with new high score for game
     */
    private void updateHighScore(HighScoreTracker h) {
        if (h.getHighScores(1) == 0) {
            h.setHighScores(reactionTimeValue.longValue(),1);
        } else if (h.getHighScores(1) > reactionTimeValue.longValue()) {
            h.setHighScores(reactionTimeValue.longValue(),1);
        }
    }
}
