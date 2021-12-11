import java.io.FileNotFoundException;
import java.util.*;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {


        Parser parser = new Parser(11);

        List<String> data = parser.parseDataStr();

        int[][] octopi = new int[data.size()][data.get(0).length()];

        for (int i = 0; i < data.size(); i++) {
            char[] numberList = data.get(i).toCharArray();
            for (int j = 0; j < numberList.length; j++) {
                octopi[i][j] = Integer.parseInt(""+numberList[j]);
            }
        }

        int height = octopi.length;
        int width = octopi[0].length;

        int acc = 0;
        for (int n = 1; n <= 500; n++) {
            Set<Coordinate> toFlash = new HashSet<>();
            Queue<Coordinate> toFlashQueue = new LinkedList<>();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    octopi[i][j]++;


                    if (octopi[i][j] >= 10) {
                        Coordinate coordinate = new Coordinate(i, j);

                        toFlash.add(coordinate);
                        toFlashQueue.add(coordinate);
                    }
                }
            }

            while (toFlashQueue.size() != 0) {
                Coordinate coord = toFlashQueue.remove();

                if (coord.x != 0) {
                    if (++octopi[coord.x - 1][coord.y] >= 10) {
                        Coordinate coordinate = new Coordinate(coord.x-1, coord.y);
                        if (!toFlash.contains(coordinate)) {
                            toFlash.add(coordinate);
                            toFlashQueue.add(coordinate);
                        }
                    }
                }
                if (coord.x != height - 1) {
                    if (++octopi[coord.x + 1][coord.y] >= 10) {
                        Coordinate coordinate = new Coordinate(coord.x+1, coord.y);
                        if (!toFlash.contains(coordinate)) {
                            toFlash.add(coordinate);
                            toFlashQueue.add(coordinate);
                        }
                    }
                }

                if (coord.y != 0) {
                    if (++octopi[coord.x][coord.y-1] >= 10) {
                        Coordinate coordinate = new Coordinate(coord.x, coord.y-1);
                        if (!toFlash.contains(coordinate)) {
                            toFlash.add(coordinate);
                            toFlashQueue.add(coordinate);
                        }
                    }
                }
                if (coord.y != width - 1) {
                    if (++octopi[coord.x][coord.y+1] >= 10) {
                        Coordinate coordinate = new Coordinate(coord.x, coord.y+1);
                        if (!toFlash.contains(coordinate)) {
                            toFlash.add(coordinate);
                            toFlashQueue.add(coordinate);
                        }
                    }
                }

                if (coord.x != 0 && coord.y != 0) {
                    if (++octopi[coord.x-1][coord.y-1] >= 10) {
                        Coordinate coordinate = new Coordinate(coord.x-1, coord.y-1);
                        if (!toFlash.contains(coordinate)) {
                            toFlash.add(coordinate);
                            toFlashQueue.add(coordinate);
                        }
                    }
                }
                if (coord.x != height - 1 && coord.y != 0) {
                    if (++octopi[coord.x+1][coord.y-1] >= 10) {
                        Coordinate coordinate = new Coordinate(coord.x+1, coord.y-1);
                        if (!toFlash.contains(coordinate)) {
                            toFlash.add(coordinate);
                            toFlashQueue.add(coordinate);
                        }
                    }
                }
                if (coord.x != 0 && coord.y != width - 1) {
                    if (++octopi[coord.x-1][coord.y+1] >= 10) {
                        Coordinate coordinate = new Coordinate(coord.x-1, coord.y+1);
                        if (!toFlash.contains(coordinate)) {
                            toFlash.add(coordinate);
                            toFlashQueue.add(coordinate);
                        }
                    }
                }
                if (coord.x != height - 1 && coord.y != width - 1) {
                    if (++octopi[coord.x+1][coord.y+1] >= 10) {
                        Coordinate coordinate = new Coordinate(coord.x+1, coord.y+1);
                        if (!toFlash.contains(coordinate)) {
                            toFlash.add(coordinate);
                            toFlashQueue.add(coordinate);
                        }
                    }
                }
            }

            if (toFlash.size() == height * width) {
                System.out.println("FLASH: " + n);
                break;
            }

            for (Coordinate coordinate : toFlash) {
                octopi[coordinate.x][coordinate.y] = 0;
                acc++;
            }
        }

        System.out.println(acc);
    }

    public class Coordinate {
        int x;
        int y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int[] getCoordinate() {
            return new int[]{x, y};
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinate that = (Coordinate) o;

            if (x != that.x) return false;
            return y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
