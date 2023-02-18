import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class NumberMemoryGame extends Game {
    private final HighScoreTracker h;
    private final BorderPane root;
    private final IntegerProperty level;
    private final TextField numberField;
    private final LongProperty numToMemorize;
    private final VBox peakBox;
    private final VBox endScreen;
    private final VBox answerBox;

    /**
     * Draws the game, and specifies the behavior of what to do if the number entered is incorrect
     * and what to do if the number entered is correct
     * @param h the high score tracker to be updated if new high score is achieved
     */
    public NumberMemoryGame(HighScoreTracker h) {
        this.h = h;
        answerBox = getBlueVbox();
        peakBox = getBlueVbox();
        numToMemorize = new SimpleLongProperty(0);
        root = new BorderPane();
        level = new SimpleIntegerProperty(0);
        VBox buttonBox = getBlueVbox();
        HBox buff1 = new HBox();
        HBox buff2 = new HBox();
        HBox scoreHolder = new HBox();
        Button initialStart = makeButtonGood(new Button("Start"));
        Label score = makeLabelGood(new Label("Score: "));
        Label showScore = makeLabelGood(new Label());
        Label resetToStartAgain = makeLabelGood(new Label("Click reset to try again."));
        endScreen = getBlueVbox();

        showScore.setFont(Font.font("Helvetica", FontWeight.BOLD, 60));
        score.setFont(Font.font("Helvetica", FontWeight.BOLD,60));
        resetToStartAgain.setFont(Font.font("Helvetica", FontWeight.BOLD,17));

        showScore.textProperty().bind(level.asString());

        scoreHolder.getChildren().addAll(score,showScore);
        scoreHolder.setAlignment(Pos.CENTER);
        endScreen.getChildren().addAll(scoreHolder, resetToStartAgain);

        answerBox.setSpacing(10);

        numberField = new TextField();
        Label whatWasIt = makeLabelGood(new Label("What was the number?"));
        Label pressEnter = makeLabelGood(new Label("Press enter to submit"));

        buff2.setPrefWidth(167);
        buff1.setPrefWidth(200);
        whatWasIt.setFont(Font.font("Helvetica", FontWeight.BOLD,30));
        pressEnter.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,15));

        numberField.setFont(Font.font(20));
        numberField.setBackground(Background.fill(Color.rgb(61,116,198)));
        numberField.setStyle("-fx-text-fill: white;");
        numberField.setBorder(new Border(new BorderStroke(Color.rgb(96,162,244),BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        numberField.setPrefSize(300,40);


        Label showNumber = makeLabelGood(new Label());
        showNumber.textProperty().bind(numToMemorize.asString());
        showNumber.setFont(Font.font("Helvetica", FontWeight.BOLD,70));

        buff1.getChildren().addAll(buff2, numberField);
        answerBox.getChildren().addAll(whatWasIt,pressEnter,buff1);
        buttonBox.getChildren().addAll(initialStart);
        peakBox.getChildren().add(showNumber);
        root.setCenter(buttonBox);

        initialStart.setOnAction(event -> start());

        numberField.setOnAction(event -> {
            if (numberField.getText().equals(String.valueOf(numToMemorize.longValue()))) {
                level.set(level.get() + 1);
                numberField.setText("");
                start();
            } else {
                root.setCenter(null);
                root.setCenter(endScreen);
                updateHighScore();
            }
        });
    }
    /**
     * used to flash the number that the user must enter then shows the answer box
     */
    public void start() {
        AnimationTimer timer = new AnimationTimer() {
            final long startTime = System.nanoTime();
            @Override
            public void handle(long now) {
                if (now > startTime + ((TimeUnit.SECONDS.toNanos(1) * ((level.getValue().longValue())+1))*.7)) {
                    root.setCenter(null);
                    root.setCenter(answerBox);
                    this.stop();
                }
            }
        };
        getNewNumToMemorize();
        root.setCenter(null);
        root.setCenter(peakBox);
        timer.start();
    }
    /**
     * Gets new number to memorize with the length based on the current level
     */
    private void getNewNumToMemorize() {
        int lvl = level.intValue();
        long lowBound = (long) Math.pow(10,lvl);
        long upBound = (long) Math.pow(10,lvl) * 9;
        numToMemorize.setValue(lowBound + new Random().nextLong(upBound));
    }
    /**
     * @return the root containing the game to the Main class
     */
    public Node getRoot(){
        return root;
    }

    /**
     * Checks to see if a new high score is reached and if so it updates the high score tracker
     */
    private void updateHighScore() {
        if (h.getHighScores(4) < level.longValue()) {
            h.setHighScores(level.longValue(), 4);
        }
    }
}
