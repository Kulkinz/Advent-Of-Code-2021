import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(5);

        List<String> data = parser.parseDataStr();

        List<Coordinate> coordinates = new ArrayList<>();


        for (String string : data) {

            String[] setOfCoords = string.split(" -> ");

            String[] cord1 = setOfCoords[0].split(",");
            String[] cord2 = setOfCoords[1].split(",");
            Coordinate one = new Coordinate(Integer.parseInt(cord1[0]), Integer.parseInt(cord1[1]));
            System.out.println("-" + one + "-");

            for (Coordinate coordinate : one.pointsBetween(new Coordinate(Integer.parseInt(cord2[0]), Integer.parseInt(cord2[1])))) {
                System.out.println(coordinate);
                coordinates.add(coordinate);
            }

        }

        Map<Object, Long> counts = coordinates.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        int acc = 0;
        for (Long value : counts.values()) {
            if (value >= 2) {
                acc++;
            }
        }

        System.out.println(acc);

    }


    private class Coordinate {
        public int x;
        public int y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public List<Coordinate> pointsBetween(Coordinate other) {
            List<Coordinate> coordinates = new ArrayList<>();
            if (other.x == x && other.y == y) {
                coordinates.add(this);
            } else if (other.x == x) {
                coordinates.add(this);
                for (int i = (Math.min(other.y, y)) + 1; i < (Math.max(other.y, y)); i++) {
                    coordinates.add(new Coordinate(x, i));
                }
                coordinates.add(other);
            } else if (other.y == y) {
                coordinates.add(this);
                for (int i = (Math.min(other.x, x)) + 1; i < (Math.max(other.x, x)); i++) {
                    coordinates.add(new Coordinate(i, y));
                }
                coordinates.add(other);
            } else if ((other.y > y && other.x > x) || (other.y < y && other.x < x)) {
                coordinates.add(this);
                int startX = Math.min(other.x, x);
                int startY = Math.min(other.y, y);
                int i = 1;
                while (startX + i < (Math.max(other.x, x))) {
                    coordinates.add(new Coordinate(startX + i, startY + i));
                    i++;
                }

                coordinates.add(other);
            } else {
                coordinates.add(this);
                int startX = Math.max(other.x, x);
                int startY = Math.min(other.y, y);
                int i = 1;
                while (startX - i > (Math.min(other.x, x))) {
                    coordinates.add(new Coordinate(startX - i, startY + i));
                    i++;
                }

                coordinates.add(other);
            }

            return coordinates;
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
