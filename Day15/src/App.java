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

        Parser parser = new Parser(15);

        List<String> data = parser.parseDataStr();

        List<Path> paths = new ArrayList<>();
        Path lowestCompletePath = new Path();
        lowestCompletePath.risk = Integer.MAX_VALUE;

        int height = data.size();
        int width = data.get(0).length();

        Coordinate end = new Coordinate(width*5 - 1, height*5 - 1);

        int[][] map = new int[height * 5][width * 5];


        int[][] lowestValueForCoordinate = new int[height * 5][width * 5];

        for (int n = 0; n < 5; n++) {
            for (int i = 0; i < height; i++) {
                String string = data.get(i);
                for (int m = 0; m < 5; m++) {
                    for (int j = 0; j < width; j++) {
                        int wrapped = (Integer.parseInt("" + string.charAt(j)) + n + m) % 9;
                        map[i + height * n][j + width * m] = wrapped == 0 ? 9 : wrapped;
                        lowestValueForCoordinate[i + height * n][j + width * m] =  Integer.MAX_VALUE;
                    }
                }
            }
        }

        lowestValueForCoordinate[0][0] = 0;

        Path start = new Path();

        paths.add(start);


        // Invariant: lowest value is at the start
        while (paths.get(0).risk <= lowestCompletePath.risk) {

            Path current = paths.remove(0);

//            System.out.println(current.risk);

            if (current.current.equals(end)) {
                if (current.risk < lowestCompletePath.risk) {
                    lowestCompletePath = current;
                }
                if (paths.size() == 0) {
                    break;
                }
                continue;
            }

            Coordinate spot = current.current;

            for (Coordinate coordinate : spot.adjacent(width*5 - 1, height*5 - 1)) {
                if (!current.visited.contains(coordinate)) {
                    Path clone = new Path(current);
                    clone.current = coordinate;
                    clone.visited.add(coordinate);
                    clone.risk += map[coordinate.y][coordinate.x];

                    if (lowestValueForCoordinate[coordinate.y][coordinate.x] > clone.risk) {
                        lowestValueForCoordinate[coordinate.y][coordinate.x] = clone.risk;
                        paths.add(clone);
                    }
                }
            }


            Collections.sort(paths);


        }

        System.out.println(lowestCompletePath.risk);


    }

    private class Path implements Comparable<Path> {
        ArrayList<Coordinate> visited;
        Coordinate current;
        int risk;

        public Path() {
            current = new Coordinate(0, 0);
            visited = new ArrayList<>();
            visited.add(current);
            risk = 0;
        }

        public Path(Path copy) {
            visited = (ArrayList<Coordinate>) copy.visited.clone();
            current = copy.current;
            risk = copy.risk;
        }

        @Override
        public int compareTo(Path o) {
            return Integer.compare(this.risk, o.risk);
        }
    }

    private class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public List<Coordinate> adjacent(int maxX, int maxY) {
            List<Coordinate> adjacents = new ArrayList<>();

            if (x - 1 >= 0) {
                adjacents.add(new Coordinate(x - 1, y));
            }

            if (y - 1 >= 0) {
                adjacents.add(new Coordinate(x, y-1));
            }

            if (x + 1 <= maxX) {
                adjacents.add(new Coordinate(x + 1, y));
            }

            if (y + 1 <= maxY) {
                adjacents.add(new Coordinate(x, y+1));
            }

            return  adjacents;

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
    }
}
