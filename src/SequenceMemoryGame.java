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

/** Welcome to Sequence Memory Game **/
public class SequenceMemoryGame extends Game {
    private final List<Integer> pattern;
    private final List<VBox> tiles;
    private final BorderPane root;
    private final HighScoreTracker h;
    private final VBox endScreen;
    private int current;
    private final IntegerProperty level;

    /**
     * Contains the drawing for the game but most of the game functionality is stored in the
     * generateClickGrid function
     * @param h high score tracker to be updated
     */
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
    /**
     * @return the root containing the game to the Main class
     */
    public Node getRoot(){
        return root;
    }

    /**
     * @return a square tile to add to the grid
     */
    private VBox drawTile() {
        VBox tile = new VBox();
        tile.setPrefSize(100,100);
        tile.setBackground(Background.fill(Color.rgb(61,116,198)));
        return tile;
    }

    /**
     * turns tile at index white
     * @param index position in list of tile to turn white
     */
    private void turnWhite(int index) {
        tiles.get(index).setBackground(Background.fill(Color.WHITE));
    }

    /**
     * turns tile at index blue
     * @param index position in list of tile to turn blue
     */
    private void turnBlue(int index) {
        tiles.get(index).setBackground(Background.fill(Color.rgb(61,116,198)));
    }

    /**
     * Adds one to the current pattern
     */
    private void addOnePattern() {
        Random rand = new Random();
        pattern.add(rand.nextInt(0, 9));
    }

    /**
     * Reveals the current pattern in order showing each highlighted pane for one second
     */
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

    /**
     * updates the level, adds one to the pattern, reveals the patten and then sets the behavior of the tiles
     * @param current the current level
     */
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
    /**
     * Checks to see if a new high score is reached and if so it updates the high score tracker
     * @param h updated with new high score for game
     */
    private void updateHighScore(HighScoreTracker h) {
        if (h.getHighScores(2) < level.longValue()) {
            h.setHighScores(level.longValue(), 2);
        }
    }
}
