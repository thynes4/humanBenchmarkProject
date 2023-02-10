import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;
public class Main extends Application {
    private int currentGame;

    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        HighScoreTracker h = new HighScoreTracker();

        stage.setTitle("Human Benchmark");

        BorderPane root = new BorderPane();
        VBox menu = new VBox();
        HBox titleBox = new HBox();
        Label titleLabel = new Label("Welcome to Human Benchmark");
        TextField usernameField = new TextField();
        Button startReactionTime = new Button("Reaction Time");
        Button startSequenceMemory = new Button("Sequence Memory");
        Button startAimTrainer = new Button("Aim Trainer");
        Button startNumberMemory = new Button("Number Memory");
        Button startVerbalMemory = new Button("Verbal Memory");
        Button startChimpTest = new Button("Chimp Test");
        Button startVisualMemory = new Button("Visual Memory");
        Button startTyping = new Button("Typing");
        Button getScores = new Button("Get Scores and Quit");
        Button resetButton = new Button("Reset");
        Button backButton = new Button("Back");

        List<Button> buttonList = List.of(
                startReactionTime, startSequenceMemory, startAimTrainer, startNumberMemory,
                startVerbalMemory, startChimpTest, startVisualMemory, startTyping, getScores
        );

        for (Button b : buttonList) {
            b.setPrefSize(350, 47);
            b.setBackground(Background.fill(Color.LIGHTGREY));
            b.setBorder(Border.stroke(Color.BLACK));
            b.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
            // Change button font and appearance
        }

        titleLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        titleLabel.setTextFill(Color.WHITE);

        menu.alignmentProperty().setValue(Pos.TOP_CENTER);
        menu.setSpacing(10);

        menu.setBackground(new Background(new BackgroundFill(Color.rgb(71, 136, 214), CornerRadii.EMPTY, Insets.EMPTY)));

        titleBox.alignmentProperty().setValue(Pos.BASELINE_CENTER);
        titleBox.getChildren().add(titleLabel);

        menu.getChildren().addAll(titleBox, startReactionTime, startSequenceMemory, startAimTrainer,
                startNumberMemory, startVerbalMemory, startChimpTest, startVisualMemory, startTyping, getScores);

        root.setCenter(drawBootScreen(usernameField));

        stage.setScene(new Scene(root, 800, 600));
        stage.show();

        usernameField.setOnAction(event -> {
            h.setUsername(usernameField.getText());
            root.setCenter(null);
            root.setCenter(menu);
        });

        startReactionTime.setOnAction(event -> {
            currentGame = 1;
            root.setLeft(drawGameMenu(resetButton, backButton));
            drawInitialScreen(root, h, "Reaction Time Test", "When the red box turns green, click as quickly " + "as you can.");
        });

        startSequenceMemory.setOnAction(event -> {
            currentGame = 2;
            root.setLeft(drawGameMenu(resetButton, backButton));
            drawInitialScreen(root, h, "Sequence Memory Test", "Memorize the pattern.");
        });

        startAimTrainer.setOnAction(event -> {
            currentGame = 3;
            root.setLeft(drawGameMenu(resetButton, backButton));
            drawInitialScreen(root, h, "Aim Trainer", "Hit 30 targets as quickly as you can.");
        });

        startNumberMemory.setOnAction(event -> {
            currentGame = 4;
            root.setLeft(drawGameMenu(resetButton, backButton));
            drawInitialScreen(root, h, "Number Memory", "The average person can remember 7 numbers at once." +
                    " Can you do more?");
        });

        startVerbalMemory.setOnAction(event -> {
            currentGame = 5;
            root.setLeft(drawGameMenu(resetButton, backButton));
            drawInitialScreen(root, h, "Verbal Memory Test", "\t\t\t\tYou will be shown words, one at a time. \n If " +
                    "you've seen a word during the test, click SEEN If it's a new word, click NEW");
        });

        startChimpTest.setOnAction(event -> {
            currentGame = 6;
            root.setLeft(drawGameMenu(resetButton, backButton));
            drawInitialScreen(root, h, "   Are You Smarter\nThan A Chimpanzee?",
                    "Click the squares in order according to their numbers. The test will get progressively" +
                            " harder.");
        });

        startVisualMemory.setOnAction(event -> {
            currentGame = 7;
            root.setLeft(drawGameMenu(resetButton, backButton));
            drawInitialScreen(root, h, "Visual Memory Test", "Memorize the squares.");
        });

        startTyping.setOnAction(event -> {
            currentGame = 8;
            root.setLeft(drawGameMenu(resetButton, backButton));
            drawInitialScreen(root, h, "Typing Test", "How many words per minute can you type?");
        });

        getScores.setOnAction(event -> {
            System.out.println(h.getUsername() + " " + h.getHighScores(1));
            h.printScores();
            stage.close();
        });

        backButton.setOnAction(event -> {
            root.setLeft(null);
            root.setCenter(menu);
        });

        resetButton.setOnAction(event -> {
            switch (currentGame) {
                case 1:
                    drawInitialScreen(root, h, "Reaction Time Test", "When the red box turns green, click as quickly " + "as you can.");
                    break;
                case 2:
                    drawInitialScreen(root, h, "Sequence Memory Test", "Memorize the pattern.");
                    break;
                case 3:
                    drawInitialScreen(root, h, "Aim Trainer", "Hit 30 targets as quickly as you can.");
                    break;
                case 4:
                    drawInitialScreen(root, h, "Number Memory", "The average person can remember 7 numbers at once." +
                            " Can you do more?");
                    break;
                case 5:
                    drawInitialScreen(root, h, "Verbal Memory Test", "\t\t\t\tYou will be shown words, one at a time. \n If " +
                            "you've seen a word during the test, click SEEN If it's a new word, click NEW");
                    break;
                case 6:
                    drawInitialScreen(root, h, "   Are You Smarter\nThan A Chimpanzee?",
                            "Click the squares in order according to their numbers. The test will get progressively" +
                                    " harder.");
                    break;
                case 7:
                    drawInitialScreen(root, h, "Visual Memory Test", "Memorize the squares.");
                    break;
                case 8:
                    root.setLeft(drawGameMenu(resetButton, backButton));
                    drawInitialScreen(root, h, "Typing Test", "How many words per minute can you type?");
                    break;
                case 9:
            }
        });
    }

    private VBox drawGameMenu(Button resetButton, Button backButton) {

        VBox buffer = new VBox();
        HBox buffer2 = new HBox();
        HBox buffer3 = new HBox();
        HBox buffer4 = new HBox();
        HBox buffer5 = new HBox();
        HBox buffer6 = new HBox();
        HBox buffer7 = new HBox();

        VBox gameMenu = new VBox();

        buffer.setPrefHeight(80);
        buffer2.setPrefWidth(20);
        buffer5.setPrefWidth(20);
        buffer6.setPrefWidth(20);
        buffer7.setPrefWidth(20);
        buffer3.setPrefWidth(20);
        gameMenu.setSpacing(30);
        gameMenu.setBorder(Border.stroke(Color.BLACK));

        gameMenu.setBackground(new Background(new BackgroundFill(Color.rgb(71, 136, 214), CornerRadii.EMPTY, Insets.EMPTY)));

        resetButton.setPrefSize(120, 50);
        backButton.setPrefSize(120, 50);

        resetButton.setBackground(Background.fill(Color.LIGHTGREY));
        resetButton.setBorder(Border.stroke(Color.BLACK));
        resetButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        backButton.setBackground(Background.fill(Color.LIGHTGREY));
        backButton.setBorder(Border.stroke(Color.BLACK));
        backButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        buffer3.getChildren().addAll(buffer2, resetButton, buffer6);
        buffer4.getChildren().addAll(buffer5, backButton, buffer7);

        gameMenu.getChildren().addAll(buffer, buffer3, buffer4);
        return gameMenu;
    }

    private VBox drawBootScreen(TextField usernameField) {
        Label enterUserLabel = new Label("Enter Username:");
        VBox bootScreen = new VBox();
        VBox buffer1 = new VBox();
        HBox buffer2 = new HBox();
        HBox holder1 = new HBox();

        buffer1.setPrefHeight(100);
        buffer2.setPrefWidth(300);
        enterUserLabel.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 30));
        enterUserLabel.setTextFill(Color.WHITE);
        bootScreen.setAlignment(Pos.BASELINE_CENTER);
        bootScreen.setSpacing(20);

        usernameField.setPrefWidth(200);
        bootScreen.setBackground(new Background(new BackgroundFill(Color.rgb(71, 136, 214), CornerRadii.EMPTY, Insets.EMPTY)));

        holder1.getChildren().addAll(buffer2, usernameField);
        bootScreen.getChildren().addAll(buffer1, enterUserLabel, holder1);
        return bootScreen;
    }

    public void drawInitialScreen(BorderPane root, HighScoreTracker h, String title, String subtitle) {
        VBox bufferBox = new VBox();
        VBox titleBox = new VBox();
        Label titleLabel = new Label(title);
        Label subTitleText = new Label(subtitle);
        Label subTitleText2 = new Label("Click anywhere to start.");

        bufferBox.setPrefHeight(100);

        titleLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        subTitleText.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        subTitleText2.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));

        titleLabel.setTextFill(Color.WHITE);
        subTitleText.setTextFill(Color.WHITE);
        subTitleText2.setTextFill(Color.WHITE);

        titleBox.alignmentProperty().setValue(Pos.TOP_CENTER);
        titleBox.setBackground(new Background(new BackgroundFill(Color.rgb(71, 136, 214), CornerRadii.EMPTY, Insets.EMPTY)));
        titleBox.getChildren().addAll(bufferBox, titleLabel, subTitleText, subTitleText2);

        titleBox.setOnMouseClicked(event -> {
            switch (currentGame) {
                case 1:
                    ReactionTimeGame r = new ReactionTimeGame(h);
                    root.setCenter(null);
                    root.setCenter(r.getRoot());
                    break;
                case 2:
                    SequenceMemoryGame s = new SequenceMemoryGame();
                    s.start(root);
                    break;
                case 3:
                    AimTrainerGame a = new AimTrainerGame();
                    a.start(root);
                    break;
                case 4:
                    NumberMemoryGame n = new NumberMemoryGame();
                    n.start(root);
                    break;
                case 5:
                    VerbalMemoryGame v = new VerbalMemoryGame();
                    v.start(root);
                    break;
                case 6:
                    ChimpTestGame chimp = new ChimpTestGame();
                    chimp.start(root);
                    break;
                case 7:
                    VisualMemoryGame vis = new VisualMemoryGame();
                    vis.start(root);
                    break;
                case 8:
                    TypingGame type = new TypingGame();
                    type.start(root);
                    break;
                case 9:
            }
        });

        root.setCenter(null);
        root.setCenter(titleBox);
    }
}