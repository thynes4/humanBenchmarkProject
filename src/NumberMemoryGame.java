import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
    private final VBox answerBox;
    public NumberMemoryGame(HighScoreTracker h) {
        this.h = h;
        answerBox = getBlueVbox();
        peakBox = getBlueVbox();
        numToMemorize = new SimpleLongProperty(0);
        root = new BorderPane();
        level = new SimpleIntegerProperty(1);
        VBox buttonBox = getBlueVbox();
        Button initialStart = makeButtonGood(new Button("Start"));

        numberField = new TextField();
        Label whatWasIt = makeLabelGood(new Label("What was the number?"));
        Label pressEnter = makeLabelGood(new Label("Press enter to submit"));

        whatWasIt.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,30));
        pressEnter.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,15));

        numberField.setFont(Font.font(20));
        numberField.setBackground(Background.fill(Color.rgb(61,116,198)));
        numberField.setStyle("-fx-text-fill: white;");
        numberField.setBorder(new Border(new BorderStroke(Color.rgb(96,162,244),BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        numberField.setPrefSize(30,40);


        Label showNumber = makeLabelGood(new Label());
        showNumber.textProperty().bind(numToMemorize.asString());
        showNumber.setFont(Font.font("Helvetica", FontWeight.BOLD,70));

        answerBox.getChildren().addAll(whatWasIt,pressEnter,numberField);
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
            }
        });
    }
    public void start() {
        AnimationTimer timer = new AnimationTimer() {
            final long startTime = System.nanoTime();
            @Override
            public void handle(long now) {
                if (now > startTime + (TimeUnit.SECONDS.toNanos(1) * level.getValue().longValue())) {
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
    private void getNewNumToMemorize() {
        int lvl = level.intValue();
        long lowBound = (long) Math.pow(10,lvl-1);
        long upBound = (long) Math.pow(10,lvl-1) * 9;
        numToMemorize.setValue(lowBound + new Random().nextLong(upBound));
    }
    public Node getRoot(){
        return root;
    }
}
