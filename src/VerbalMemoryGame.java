import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerbalMemoryGame extends Game {
    private final BorderPane root;
    private final HighScoreTracker h;
    private final List<String> unusedWords;
    private final List<String> usedWords;
    private final StringProperty currentWord;
    private final IntegerProperty score;
    private final IntegerProperty lives;
    public VerbalMemoryGame (HighScoreTracker h) {
        this.h = h;
        root = new BorderPane();
        unusedWords = new ArrayList<>();
        usedWords = new ArrayList<>();
        currentWord = new SimpleStringProperty("NULL");
        score = new SimpleIntegerProperty(0);
        lives = new SimpleIntegerProperty(3);

        Button initialStart = makeButtonGood(new Button("Start"));
        VBox gameScreen = getBlueVbox();
        VBox buttonBox = getBlueVbox();
        buttonBox.getChildren().add(initialStart);
        Label showWord = makeLabelGood(new Label());
        showWord.textProperty().bind(currentWord);
        Label showScore = makeLabelGood(new Label());
        showScore.textProperty().bind(score.asString());
        Label showLives = makeLabelGood(new Label());
        showLives.textProperty().bind(lives.asString());
        Button seenWord = makeButtonGood(new Button("SEEN"));
        Button newWord = makeButtonGood(new Button("NEW"));
        HBox buttonHolder = new HBox();
        HBox infoHolder = new HBox();

        infoHolder.getChildren().addAll(showLives,showScore);
        buttonHolder.getChildren().addAll(seenWord, newWord);

        gameScreen.setSpacing(10);
        gameScreen.getChildren().addAll(infoHolder, showWord, buttonHolder);

        root.setCenter(buttonBox);

        readInDictionary();

        initialStart.setOnAction(event -> {
            getRandomWord(unusedWords);
            root.setCenter(null);
            root.setCenter(gameScreen);
        });

        seenWord.setOnAction(event -> {
            if (usedWords.contains(currentWord.getValue())) {
                score.set(score.get() + 1);
                chooseNextSource();
            } else {
                usedWords.add(currentWord.getValue());
                unusedWords.remove(currentWord.getValue());
                lives.set(lives.get() - 1);
                if (lives.get() == 0) {
                    root.setCenter(null);
                }
                chooseNextSource();
            }
        });

        newWord.setOnAction(event -> {
            if (unusedWords.contains(currentWord.getValue())) {
                score.set(score.get() + 1);
                usedWords.add(currentWord.getValue());
                unusedWords.remove(currentWord.getValue());
                chooseNextSource();
            } else {
                lives.set(lives.get() - 1);
                if (lives.get() == 0) {
                    root.setCenter(null);
                }
                chooseNextSource();
            }
        });
    }
    public Node getRoot(){
        return root;
    }
    private void readInDictionary() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("resources/dictionary.txt"));
            String word = reader.readLine();
            while (word != null) {
                unusedWords.add(word);
                word = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getRandomWord(List<String> source) {
        Random rand = new Random();
        currentWord.setValue(source.get(rand.nextInt(source.size())));
    }

    private void chooseNextSource() {
        Random rand = new Random();
        if (rand.nextInt(2) == 1) {
            getRandomWord(usedWords);
        } else {
            getRandomWord(unusedWords);
        }
    }
}
