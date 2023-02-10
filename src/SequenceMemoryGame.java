import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SequenceMemoryGame extends Game {
    private BorderPane root;
    private HighScoreTracker h;
    private int level;
    public SequenceMemoryGame(HighScoreTracker h) {
        this.h = h;
        root = new BorderPane();
        VBox buttonBox = getBlueVbox();
        HBox horizontalBuff1 = new HBox();
        HBox horizontalBuff2 = new HBox();
        HBox horizontalBuff3 = new HBox();
        Button initialStart = makeButtonGood(new Button("Start"));
        buttonBox.getChildren().addAll(initialStart);

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

        List<VBox> tiles = List.of(tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9);

        row1.getChildren().addAll(horizontalBuff1, tile1,tile2,tile3);
        row2.getChildren().addAll(horizontalBuff2, tile4,tile5,tile6);
        row3.getChildren().addAll(horizontalBuff3, tile7,tile8,tile9);

        grid.getChildren().addAll(row1,row2,row3);

        grid.setBackground(Background.fill(Color.rgb(71,136,214)));
        grid.setAlignment(Pos.CENTER);

        root.setCenter(buttonBox);

        initialStart.setOnAction(event -> {
            root.setCenter(null);
            root.setCenter(grid);
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
}
