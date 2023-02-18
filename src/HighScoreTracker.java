import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This HighScoreTracker object contains the high score data to be tracked through the games
 * one instance of this object will be created and passed through all games
 */
public final class HighScoreTracker {
    private String username;
    private long highScore1 = 0;
    private long highScore2 = 0;
    private long highScore3 = 0;
    private long highScore4 = 0;
    private long highScore5 = 0;
    private long highScore6 = 0;
    private long highScore7 = 0;
    private long highScore8 = 0;

    /**
     * used to set high scores when games end
     * @param highScore the new high score
     * @param game used to mark what games high score to update
     */
    public void setHighScores(long highScore, int game) {
        switch (game) {
            case 1 -> this.highScore1 = highScore;
            case 2 -> this.highScore2 = highScore;
            case 3 -> this.highScore3 = highScore;
            case 4 -> this.highScore4 = highScore;
            case 5 -> this.highScore5 = highScore;
            case 6 -> this.highScore6 = highScore;
            case 7 -> this.highScore7 = highScore;
            case 8 -> this.highScore8 = highScore;
        }
    }

    /**
     * @param game game to get the high score for
     * @return the current high score for the given game
     */
    public long getHighScores(int game) {
        switch (game) {
            case 1 -> {
                return highScore1;
            }
            case 2 -> {
                return highScore2;
            }
            case 3 -> {
                return highScore3;
            }
            case 4 -> {
                return highScore4;
            }
            case 5 -> {
                return highScore5;
            }
            case 6 -> {
                return highScore6;
            }
            case 7 -> {
                return highScore7;
            }
            case 8 -> {
                return highScore8;
            }
        }
        return 0;
    }

    /**
     * @param username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * When the print scores and quit button is pressed,
     * this is will create a new file if one doesn't already exist and print the new high scores for the session
     */
    public void printScores() {
        File outFile = new File("resources/scores.csv");

        try {

            if(!outFile.exists()) {
                outFile.createNewFile();
                FileWriter writer = new FileWriter(outFile);
                BufferedWriter br2 = new BufferedWriter(writer);
                br2.write("       name, reaction_time, sequence_memory, aim_trainer, chimp_test, visual_memory," +
                        " typing, number_memory, verbal_memory");
                br2.close();
                writer.close();
            }

            FileWriter writer2 = new FileWriter(outFile, true);
            BufferedWriter br = new BufferedWriter(writer2);
            br.append("\n").append(String.format("%11s", username)).append(", ").append(String.valueOf(String.format("%11s", highScore1))).append("ms, ").append(String.valueOf(String.format("%15s", highScore2)))
                    .append(", ").append(String.valueOf(String.format("%9s", highScore3))).append("ms, ").append(String.valueOf(String.format("%10s", highScore4))).append(", ")
                    .append(String.valueOf(String.format("%13s", highScore5))).append(", ").append(String.valueOf(String.format("%6s", highScore6))).append(", ")
                    .append(String.valueOf(String.format("%13s", highScore7))).append(", ").append(String.valueOf(String.format("%13s", highScore8)));
            br.close();
            writer2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
