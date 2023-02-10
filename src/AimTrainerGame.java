import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AimTrainerGame extends Game {
    private final HighScoreTracker h;
    private final LongProperty timePerTarget;
    private long startTime;
    private long stopTime;
    private boolean stop;
    private final IntegerProperty targetsRemaining;
    private final Pane gameBox;
    private final VBox endScreen;
    private final BorderPane root;
    public AimTrainerGame(HighScoreTracker h) {
        this.h = h;
        root = new BorderPane();
        VBox buttonBox = getBlueVbox();
        endScreen = getBlueVbox();
        HBox hBuffer = new HBox();
        gameBox = new Pane();
        Button initialStart = makeButtonGood(new Button("Start"));
        HBox topScreen = new HBox();
        Label targetCount = makeLabelGood(new Label());
        Label remaining = makeLabelGood(new Label(" Remaining"));
        remaining.setOpacity(.5);
        timePerTarget = new SimpleLongProperty(0);
        Label tps = makeLabelGood(new Label("Average time per target"));
        Label endScore = makeLabelGood(new Label());
        Label ms = makeLabelGood(new Label("ms"));
        HBox scoreBox = new HBox();
        Label resetToStartAgain = makeLabelGood(new Label("Click reset to try again."));

        endScore.textProperty().bind(timePerTarget.asString());

        targetsRemaining = new SimpleIntegerProperty(30);
        targetCount.textProperty().bind(targetsRemaining.asString());

        buttonBox.getChildren().add(initialStart);
        root.setCenter(buttonBox);

        hBuffer.setPrefWidth(165);

        topScreen.getChildren().addAll(hBuffer,targetCount, remaining);
        gameBox.setPrefSize(600,600);
        gameBox.setBackground(Background.fill(Color.rgb(71,136,214)));
        gameBox.getChildren().add(topScreen);

        tps.setFont(Font.font("Helvetica", FontWeight.BOLD,20));
        endScore.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,60));
        ms.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,60));
        resetToStartAgain.setFont(Font.font("Helvetica", FontWeight.BOLD,15));

        scoreBox.setAlignment(Pos.CENTER);

        scoreBox.getChildren().addAll(endScore,ms);
        endScreen.getChildren().addAll(tps,scoreBox, resetToStartAgain);

        initialStart.setOnAction(this::start);
    }
    public void start(ActionEvent actionEvent) {
        startTime = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (targetsRemaining.get() <= 0) {
                    stop = true;
                    stopTime = System.nanoTime();
                    timePerTarget.setValue(TimeUnit.NANOSECONDS.toMillis((stopTime - startTime)/30));
                    root.setCenter(null);
                    root.setCenter(endScreen);
                    updateHighScore(h);
                    this.stop();
                }
            }
        };
        timer.start();

        root.setCenter(null);
        root.setCenter(gameBox);
        createTarget(30);
    }
    private void createTarget(double radius) {
        Color color = new Color(1, 1, 1, .5);
        Pair<Double, Double> xy = getRandXY(radius * 2);
        Circle target = new Circle(xy.getKey(), xy.getValue(), radius, color);
        gameBox.getChildren().add(target);

        target.setOnMouseClicked(event -> {
            hitTarget();
            gameBox.getChildren().remove(target);
            if (!stop) {
                createTarget(radius);
            }
        });
    }
    private Pair<Double, Double> getRandXY(double lowerBound) {
        Random rand = new Random();
        double randX = rand.nextDouble(lowerBound, 550);
        double randY = rand.nextDouble(lowerBound, 550);
        return new Pair<>(randX, randY);
    }
    private void hitTarget() {
        targetsRemaining.setValue(targetsRemaining.get() - 1);
    }
    public Pane getRoot() {
        return root;
    }
    private void updateHighScore(HighScoreTracker h) {
        if (h.getHighScores(3) == 0) {
            h.setHighScores(timePerTarget.longValue(), 3);
        } else if (h.getHighScores(1) > timePerTarget.longValue()) {
            h.setHighScores(timePerTarget.longValue(), 3);
        }
    }
}