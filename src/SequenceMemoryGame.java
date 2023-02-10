import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SequenceMemoryGame extends Game {
    private final List<Integer> pattern;
    private final List<VBox> tiles;
    private final BorderPane root;
    private final HighScoreTracker h;
    private final VBox endScreen;
    private int current;
    private final IntegerProperty level;
    public SequenceMemoryGame(HighScoreTracker h) {
        level = new SimpleIntegerProperty(1);
        pattern = new ArrayList<>();
        this.h = h;
        root = new BorderPane();
        VBox buttonBox = getBlueVbox();
        HBox horizontalBuff1 = new HBox();
        HBox horizontalBuff2 = new HBox();
        HBox horizontalBuff3 = new HBox();
        Button initialStart = makeButtonGood(new Button("Start"));
        buttonBox.getChildren().addAll(initialStart);
        Label resetToStartAgain = makeLabelGood(new Label("Click reset to try again."));
        endScreen = getBlueVbox();

        endScreen.getChildren().addAll(resetToStartAgain);

        HBox holdLvl = new HBox();
        Label lvl = makeLabelGood(new Label("Level: "));
        Label lvlNum = makeLabelGood(new Label());

        lvl.setOpacity(.5);
        lvlNum.textProperty().bind(level.asString());
        holdLvl.setAlignment(Pos.CENTER);

        lvl.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,30));
        lvlNum.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,30));

        horizontalBuff1.setPrefWidth(145);
        horizontalBuff2.setPrefWidth(145);
        horizontalBuff3.setPrefWidth(145);

        HBox row1 = new HBox(10);
        HBox row2 = new HBox(10);
        HBox row3 = new HBox(10);

        VBox grid = new VBox(10);

        VBox tile1 = drawTile();
        VBox tile2 = drawTile();
        VBox tile3 = drawTile();
        VBox tile4 = drawTile();
        VBox tile5 = drawTile();
        VBox tile6 = drawTile();
        VBox tile7 = drawTile();
        VBox tile8 = drawTile();
        VBox tile9 = drawTile();

        tiles = List.of(tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9);

        row1.getChildren().addAll(horizontalBuff1, tile1,tile2,tile3);
        row2.getChildren().addAll(horizontalBuff2, tile4,tile5,tile6);
        row3.getChildren().addAll(horizontalBuff3, tile7,tile8,tile9);

        VBox mainBuff = new VBox();
        mainBuff.setPrefHeight(30);

        holdLvl.getChildren().addAll(lvl,lvlNum);
        grid.getChildren().addAll(holdLvl,mainBuff,row1,row2,row3);
        grid.setBackground(Background.fill(Color.rgb(71,136,214)));
        grid.setAlignment(Pos.BASELINE_CENTER);

        root.setCenter(buttonBox);

        initialStart.setOnAction(event -> {
            root.setCenter(null);
            root.setCenter(grid);
            addOnePattern();
            flashPattern();
        });
    }
    public Node getRoot(){
        return root;
    }

    private VBox drawTile() {
        VBox tile = new VBox();
        tile.setPrefSize(100,100);
        tile.setBackground(Background.fill(Color.rgb(61,116,198)));
        return tile;
    }

    private void turnWhite(int index) {
        tiles.get(index).setBackground(Background.fill(Color.WHITE));
    }

    private void turnBlue(int index) {
        tiles.get(index).setBackground(Background.fill(Color.rgb(61,116,198)));
    }

    private void addOnePattern() {
        Random rand = new Random();
        pattern.add(rand.nextInt(0, 9));
    }

    private void flashPattern () {
        AnimationTimer a = new AnimationTimer() {
            long startTime = System.nanoTime();
            @Override
            public void handle(long now) {
                turnWhite(pattern.get(current));
                if (now > startTime + TimeUnit.SECONDS.toNanos(1) && current < pattern.size()) {
                    turnBlue(pattern.get(current));
                    current++;
                    if (current == pattern.size()) {
                        this.stop();
                        generateClickGrid(0);
                    }
                    startTime = System.nanoTime();
                }
            }
        };
        current = 0;
        a.start();
    }

    private void generateClickGrid(int current) {
        if (current == pattern.size()) {
            level.set(level.get() + 1);
            addOnePattern();
            flashPattern();
            return;
        }
        VBox wantedTile = tiles.get(pattern.get(current));
        wantedTile.setOnMouseClicked(event -> {
                for (VBox tile: tiles) {
                    tile.setOnMouseClicked(event1 -> {});
                }
                generateClickGrid(current + 1);
                });
        for (VBox tile : tiles) {
            if (tile != wantedTile) {
                tile.setOnMouseClicked(event -> {
                    root.setCenter(null);
                    root.setCenter(endScreen);
                    updateHighScore(h);
                });
            }
        }
    }

    private void updateHighScore(HighScoreTracker h) {
        if (h.getHighScores(2) == 0) {
            h.setHighScores(level.longValue(), 2);
        } else if (h.getHighScores(2) > level.longValue()) {
            h.setHighScores(level.longValue(), 2);
        }
    }
}
