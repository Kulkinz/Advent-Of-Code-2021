import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        List<String> data = parseData(0);

        for (String string : data) {
            System.out.println(string);
        }
    }

    /**
     * Gets the data from the file with the specified day number, and splits it into individual lines
     * @param dayNumber the day of the challenge
     * @return List containing each line of data
     * @throws FileNotFoundException if the data file cannot be found
     */
    private List<String> parseData(int dayNumber) throws FileNotFoundException {
        List<String> data = new ArrayList<>();

        File file = new File("Data/data" + dayNumber + ".txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();

        data.add("");

        return data;
    }
}
