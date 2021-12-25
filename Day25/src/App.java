import java.io.FileNotFoundException;
import java.util.Arrays;
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

        Parser parser = new Parser(25);

        List<String> data = parser.parseDataStr();

        int width = data.get(0).length();
        int height = data.size();

        SeaCucumber[][] map = new SeaCucumber[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char spot = data.get(i).charAt(j);
                map[i][j] = spot == 'v' ? SeaCucumber.SOUTH : spot == '>' ? SeaCucumber.EAST : SeaCucumber.NONE;
            }
        }

        SeaCucumber[][] previous = new SeaCucumber[height][width];
        int i = 0;
        while (!Arrays.deepEquals(map, previous)) {
            i++;
            previous = Arrays.stream(map).map(SeaCucumber[]::clone).toArray(SeaCucumber[][]::new);
            SeaCucumber[][] helper = Arrays.stream(map).map(SeaCucumber[]::clone).toArray(SeaCucumber[][]::new);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (map[y][x] == SeaCucumber.EAST) {
                        int nextX = (x + 1 >= width) ? 0 : x + 1;
                        if (map[y][nextX] == SeaCucumber.NONE) {
                            helper[y][x] = SeaCucumber.NONE;
                            helper[y][nextX] = SeaCucumber.EAST;
                        }
                    }
                }
            }

            map = helper;
            helper = Arrays.stream(map).map(SeaCucumber[]::clone).toArray(SeaCucumber[][]::new);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (map[y][x] == SeaCucumber.SOUTH) {
                        int nextY = (y + 1 >= height) ? 0 : y + 1;
                        if (map[nextY][x] == SeaCucumber.NONE) {
                            helper[y][x] = SeaCucumber.NONE;
                            helper[nextY][x] = SeaCucumber.SOUTH;
                        }
                    }
                }
            }

            map = helper;
        }

        System.out.println(i);
    }

    private enum SeaCucumber {
        EAST,
        SOUTH,
        NONE
    }
}
