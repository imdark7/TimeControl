import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class TimeAnalyser {

    public static final String FILE_PATH1 = "C://Users//Григорий//IdeaProjects//TextAnalyzer//test1.txt";
    public static final String FILE_PATH2 = "C://Users//Григорий//IdeaProjects//TextAnalyzer//test2.txt";
    public static final String FILE_PATH3 = "C://Users//Григорий//IdeaProjects//TextAnalyzer//test3.txt";
    public static final String BAT_PATH1 = "C:/Users/Григорий/IdeaProjects/TextAnalyzer/test1.bat";
    public static final String BAT_PATH2 = "C:/Users/Григорий/IdeaProjects/TextAnalyzer/test2.bat";
    public static final String BAT_PATH3 = "C:/Users/Григорий/IdeaProjects/TextAnalyzer/test3.bat";

    public static void main(String[] args) {

        HashMap<String, String> files = new HashMap<>();
        files.put(FILE_PATH1, BAT_PATH1);
        files.put(FILE_PATH2, BAT_PATH2);
        files.put(FILE_PATH3, BAT_PATH3);

        for (Map.Entry entry : files.entrySet()) {
            String filePath = entry.getKey().toString();
            String lastLine = "";
            long minutes = 0;
            long timeDifference;

            try {
                Scanner in = new Scanner(new File(filePath));
                while (in.hasNext()) {
                    lastLine = in.nextLine();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            String timeLastUpgrade = lastLine.substring(1, 17);

            long currentTime = new Date().getTime();
            SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            try {
                Date updateTime = formatDate.parse(timeLastUpgrade);
                timeDifference = currentTime - updateTime.getTime();
                minutes = timeDifference / 60000;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            System.out.println(minutes);

            if (minutes > 240) {
                try {
                    new ProcessBuilder("cmd", "/c", "start", entry.getValue().toString()).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (minutes > 300) {
                SendEmail.sendAlertMessage();
            }
        }
    }
}