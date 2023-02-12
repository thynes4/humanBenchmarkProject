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
        File outFile = new File("resources/"+username+"_scores.csv");
        try {
            if(!outFile.exists()) {
                boolean created = outFile.createNewFile();
                if(!created) {
                    System.out.println("Could not create scores file");
                }
            }

            FileWriter writer = new FileWriter(outFile);
            writer.write("name, reaction_time, sequence_memory, aim_trainer, chimp_test, visual_memory," +
                            " typing, number_memory, verbal_memory\n");

            writer.write(username + ", " + highScore1 + "ms, " + highScore2 + ", " + highScore3+ "ms, " + highScore4
            + ", " + highScore5 + ",");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
