import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Result {
    URL url;
    ArrayList<Integer> scoreList;

    public Result() {
        url = getClass().getResource("score.txt");
        readScore();
    }
    public void readScore() {
        scoreList = new ArrayList<Integer>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            int i = 0;
            while((line = reader.readLine()) != null) {
                try {
                    scoreList.add(Integer.parseInt(line));
                } catch(NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                i++;
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public void writeScore(int score) {
        scoreList.add(score);
        Collections.sort(scoreList, Collections.reverseOrder());

        try {
            PrintStream out = new PrintStream(new FileOutputStream(url.getPath()));
            for(int i = 0; i < Math.min(3, scoreList.size()); i++) {
                out.println(scoreList.get(i));
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        readScore();
    }
    public ArrayList<Integer> getScore() {
        return scoreList;
    }
}
