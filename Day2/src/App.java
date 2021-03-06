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

        Parser parser = new Parser(2);

        List<String> data = parser.parseDataStr();

        System.out.println("Part 1: " + directDepth(data));
        System.out.println("Part 2: " + indirectDepth(data));
    }

    private int directDepth(List<String> data) {
        int depth = 0;
        int distance = 0;

        for (String string : data) {
            String[] split = string.split(" ");

            if (split[0].equalsIgnoreCase("forward")) {
                distance += Integer.parseInt(split[1]);
            } else if (split[0].equalsIgnoreCase("down")) {

                depth += Integer.parseInt(split[1]);
            } else {

                depth -= Integer.parseInt(split[1]);
            }
        }

        return depth * distance;
    }

    private int indirectDepth(List<String> data) {
        int depth = 0;
        int distance = 0;
        int aim = 0;

        for (String string : data) {
            String[] split = string.split(" ");

            if (split[0].equalsIgnoreCase("forward")) {
                distance += Integer.parseInt(split[1]);
                depth += aim * Integer.parseInt(split[1]);
            } else if (split[0].equalsIgnoreCase("down")) {

                aim += Integer.parseInt(split[1]);
            } else {

                aim -= Integer.parseInt(split[1]);
            }
        }

        return depth * distance;
    }



}
