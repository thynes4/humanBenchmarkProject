import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HighScoreTracker {
    private String username;
    private long highScore1 = 0;
    private long highScore2 = 0;
    private long highScore3 = 0;
    private long highScore4 = 0;
    private long highScore5 = 0;
    private long highScore6 = 0;
    private long highScore7 = 0;
    private long highScore8 = 0;

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
    public void setUsername(String username) {
        this.username = username;
    }

    public void printScores() {
        File outFile = new File("resources/scores.csv");

        try {

            if(!outFile.exists()) {
                outFile.createNewFile();
                FileWriter writer = new FileWriter(outFile);
                BufferedWriter br2 = new BufferedWriter(writer);
                br2.write("    name, reaction_time, sequence_memory, aim_trainer, chimp_test, visual_memory," +
                        " typing, number_memory, verbal_memory");
                br2.close();
                writer.close();
            }

            FileWriter writer2 = new FileWriter(outFile, true);
            BufferedWriter br = new BufferedWriter(writer2);
            br.append("\n").append(String.format("%8s", username)).append(", ").append(String.valueOf(String.format("%6s", highScore1))).append("ms, ").append(String.valueOf(String.format("%6s", highScore2)))
                    .append(", ").append(String.valueOf(String.format("%6s", highScore3))).append("ms, ").append(String.valueOf(String.format("%6s", highScore4))).append(", ")
                    .append(String.valueOf(String.format("%6s", highScore5))).append(", ").append(String.valueOf(String.format("%6s", highScore6))).append(", ")
                    .append(String.valueOf(String.format("%6s", highScore7))).append(", ").append(String.valueOf(String.format("%6s", highScore8)));
            br.close();
            writer2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
