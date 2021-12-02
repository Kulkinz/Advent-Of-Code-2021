import java.io.FileNotFoundException;
import java.util.List;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(1);

        List<Integer> data = parser.parseDataInt();

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
}
