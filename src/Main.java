import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) {
        stage.setTitle("Human Benchmark");

        VBox menu = new VBox();
        HBox titleBox = new HBox();
        Label titleLabel = new Label("Human Benchmark");

        Button startReactionTime = new Button("Reaction Time");
        Button startSequenceMemory = new Button("Sequence Memory");
        Button startAimTrainer = new Button("Aim Trainer");
        Button startNumberMemory = new Button("Number Memory");
        Button startVerbalMemory = new Button("Verbal Memory");
        Button startChimpTest = new Button("Chimp Test");
        Button startVisualMemory = new Button("Visual Memory");
        Button startTyping = new Button("Typing");

        List<Button> buttonList = List.of(
            startReactionTime, startSequenceMemory, startAimTrainer, startNumberMemory,
                startVerbalMemory, startChimpTest, startVisualMemory, startTyping
        );

        for(Button b : buttonList) {
            b.setPrefSize(350,55);
            // Change button font and appearance
        }

        titleLabel.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD,40));
        titleLabel.setTextFill(Color.WHITE);

        menu.alignmentProperty().setValue(Pos.TOP_CENTER);
        menu.setSpacing(10);

        menu.setBackground(new Background(new BackgroundFill(Color.rgb(71,136,214), CornerRadii.EMPTY, Insets.EMPTY)));

        titleBox.alignmentProperty().setValue(Pos.BASELINE_CENTER);
        titleBox.getChildren().add(titleLabel);

        menu.getChildren().addAll(titleBox, startReactionTime, startSequenceMemory, startAimTrainer,
                startNumberMemory, startVerbalMemory, startChimpTest, startVisualMemory, startTyping);

        stage.setScene(new Scene(menu,400,600));
        stage.show();
    }
}
