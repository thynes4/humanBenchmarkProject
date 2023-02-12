import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    private final IntegerProperty words;
    private final VBox endScreen;
    public VerbalMemoryGame (HighScoreTracker h) {
        this.h = h;
        root = new BorderPane();
        unusedWords = new ArrayList<>();
        usedWords = new ArrayList<>();
        currentWord = new SimpleStringProperty("NULL");
        score = new SimpleIntegerProperty(0);
        lives = new SimpleIntegerProperty(3);
        words = new SimpleIntegerProperty(0);

        Button initialStart = makeButtonGood(new Button("Start"));
        VBox gameScreen = getBlueVbox();
        VBox buttonBox = getBlueVbox();
        buttonBox.getChildren().add(initialStart);
        Label showWord = makeLabelGood(new Label());
        Label showScore = makeLabelGood(new Label());
        Label showLives = makeLabelGood(new Label());
        Label livesText = makeLabelGood(new Label("Lives |"));
        Label scoreText = makeLabelGood(new Label("      Score |"));
        showLives.textProperty().bind(lives.asString());
        Button seenWord = makeButtonGood(new Button("SEEN"));
        Button newWord = makeButtonGood(new Button("NEW"));
        HBox buttonHolder = new HBox();
        HBox infoHolder = new HBox();
        HBox container = new HBox();
        VBox smallBuff = new VBox();
        Label wordsScore = makeLabelGood(new Label("Words: "));
        Label endScore = makeLabelGood(new Label());
        Label resetToStartAgain = makeLabelGood(new Label("Click reset to try again."));
        endScreen = getBlueVbox();

        endScore.textProperty().bind(words.asString());

        wordsScore.setFont(Font.font("Helvetica", FontWeight.BOLD, 60));
        endScore.setFont(Font.font("Helvetica", FontWeight.BOLD,60));
        resetToStartAgain.setFont(Font.font("Helvetica", FontWeight.BOLD,17));

        container.setPrefWidth(10);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(wordsScore, endScore);

        endScreen.getChildren().addAll(container,resetToStartAgain);

        smallBuff.setPrefHeight(30);

        showWord.textProperty().bind(currentWord);
        showScore.textProperty().bind(score.asString());

        buttonHolder.setAlignment(Pos.TOP_CENTER);
        buttonHolder.setSpacing(20);
        infoHolder.setAlignment(Pos.TOP_CENTER);
        infoHolder.setSpacing(10);

        showLives.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,25));
        showScore.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,25));
        livesText.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,25));
        scoreText.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,25));
        showWord.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,60));
        livesText.setOpacity(.5);
        scoreText.setOpacity(.5);

        newWord.setPrefSize(120,50);
        newWord.setBackground(Background.fill(Color.rgb(248,208,62)));
        newWord.setBorder(null);
        newWord.setFont(Font.font("Helvetica", FontWeight.BOLD,20));

        seenWord.setPrefSize(120,50);
        seenWord.setBackground(Background.fill(Color.rgb(248,208,62)));
        seenWord.setBorder(null);
        seenWord.setFont(Font.font("Helvetica", FontWeight.BOLD,20));

        infoHolder.getChildren().addAll(livesText, showLives, scoreText, showScore);
        buttonHolder.getChildren().addAll(seenWord, newWord);

        gameScreen.getChildren().removeAll();

        gameScreen.setAlignment(Pos.TOP_CENTER);
        gameScreen.setSpacing(15);

        gameScreen.getChildren().addAll(infoHolder, showWord, smallBuff, buttonHolder);

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
                    words.set(score.get());
                    root.setCenter(null);
                    root.setCenter(endScreen);
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
                    words.set(score.get());
                    root.setCenter(null);
                    root.setCenter(endScreen);
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
