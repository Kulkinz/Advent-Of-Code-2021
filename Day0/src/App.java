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

        Parser parser = new Parser(0);

        List<String> data = parser.parseDataStr();

        for (String string : data) {
            System.out.println(string);
        }
    }
}
