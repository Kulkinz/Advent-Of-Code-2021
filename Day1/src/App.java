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

        List<Integer> data = parseData(1);

        System.out.println("Part 1: " + countRawChange(data));
        System.out.println("Part 2: " + countThreeChange(data));
    }

    private int countRawChange(List<Integer> data) {

        int i = 0;

        int prev = data.get(0);

        for (int value : data) {

            if (value > prev) {
                i++;
            }

            prev = value;
        }

        return i;
    }

    private int countThreeChange(List<Integer> data) {

        int i = 0;

        int a = data.get(0);
        int b = data.get(1);
        int c = data.get(2);


        int value = a + b + c;

        for (int j = 3; j < data.size(); j++) {

            a = b;
            b = c;
            c = data.get(j);

            if (a + b + c > value) {
                i++;
            }

            value = a + b + c;
        }

        return i;
    }

    /**
     * Gets the data from the file with the specified day number, and splits it into individual lines
     * @param dayNumber the day of the challenge
     * @return List containing each line of data
     * @throws FileNotFoundException if the data file cannot be found
     */
    private List<Integer> parseData(int dayNumber) throws FileNotFoundException {
        List<Integer> data = new ArrayList<>();

        File file = new File("Data/data" + dayNumber + ".txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextInt()) {
            data.add(sc.nextInt());
        }
        sc.close();

        return data;
    }
}
