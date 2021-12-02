import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    private Scanner sc;

    public Parser(int dayNumber) throws FileNotFoundException {

        File file = new File("Data/data" + dayNumber + ".txt");
        sc = new Scanner(file);
    }

    /**
     * Gets the data from the file with the specified day number, and splits it into individual lines as strings
     * @return List containing each line of data
     */
    public List<String> parseDataStr() {
        List<String> data = new ArrayList<>();

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();

        return data;
    }

    /**
     * Gets the data from the file with the specified day number, and splits it into individual lines as ints
     * @return List containing each line of data
     */
    public List<Integer> parseDataInt() {
        List<Integer> data = new ArrayList<>();

        while (sc.hasNextInt()) {
            data.add(sc.nextInt());
        }
        sc.close();

        return data;
    }

    /**
     * Gets the data from the file with the specified day number, and splits it into individual lines as ints
     * @return List containing each line of data
     */
    public List<Double> parseDataDouble() {
        List<Double> data = new ArrayList<>();

        while (sc.hasNextDouble()) {
            data.add(sc.nextDouble());
        }
        sc.close();

        return data;
    }
}
