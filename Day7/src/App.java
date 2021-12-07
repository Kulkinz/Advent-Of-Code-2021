import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

public class App {

    List<Integer> data;

    public App() throws FileNotFoundException {
        coreProcess();
    }

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(7);

        data = parser.parseLineInteger();

        int max = Collections.max(data);
        int min = Collections.min(data);

        int accMin = 0, accMax = 0;

        for (Integer value : data) {

            int change = Math.abs(value - min);

            accMin += (int) ((0.5f * (double) (change * (change+1))));
        }

        for (Integer value : data) {

            int change = Math.abs(value - max);

            accMax += (int) ((0.5f * (double) (change * (change+1))));
        }

        System.out.println(getLowest(new int[]{min, accMin}, new int[]{max, accMax})); // part 1


    }

    private int getLowest(int[] lowest, int[] highest) {

        int between = (lowest[0] + highest[0]) / 2;

        System.out.println(lowest[0] + " " + lowest[1]);
        System.out.println(highest[0] + " " + highest[1]);

        int acc = 0;

        for (Integer value : data) {
//            acc += Math.abs(value - between);

            int change = Math.abs(value - between);

            acc += (int) ((0.5f * (double) (change * (change+1))));
        }

        int[] middle = {between, acc};

        if ((middle[0] == lowest[0]) || (middle[0] == highest[0])) {
            return Math.min(Math.min(lowest[1], middle[1]),Math.min(middle[1], highest[1]));
        }



        if (lowest[1] < highest[1]) {
            return getLowest(lowest, middle);
        } else {
            return getLowest(middle, highest);
        }
    }
}
