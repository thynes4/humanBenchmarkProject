import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * This class contains general behavior that is used by most games
 */
public class Game {
    /**
     * @return draws a blue VBox that is reused through most of the games whenever the screen is blue
     */
    public VBox getBlueVbox() {
        VBox buttonBox = new VBox();
        VBox buffer = new VBox();
        buffer.setPrefHeight(130);
        buttonBox.getChildren().add(buffer);

        buttonBox.setAlignment(Pos.BASELINE_CENTER);
        buttonBox.setBackground(Background.fill(Color.rgb(71,136,214)));
        return buttonBox;
    }
    /**
     * @return draws a fancy looking button that is reused through most of the games for whenever there is a button
     */
    public Button makeButtonGood(Button button) {
        button.setPrefSize(120,50);
        button.setBackground(Background.fill(Color.rgb(163,196,235)));
        button.setBorder(Border.stroke(Color.BLACK));
        button.setFont(Font.font("Helvetica", FontWeight.BOLD,20));
        return button;
    }
    /**
     * @return draws a good label with a good size, font and other properties that is reused when most text is made
     */
    public Label makeLabelGood(Label label) {
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font("Helvetica", FontWeight.BOLD,40));
        label.setTextFill(Color.WHITE);
        return label;
    }
}
