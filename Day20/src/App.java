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

        Parser parser = new Parser(20);

        List<String> data = parser.parseDataStr();

        String algorithm = data.remove(0);
        data.remove(0);

        Map<Coordinate, Integer> photo = new HashMap<>();

        int minX = 0;
        int maxX = data.get(0).length() - 1;
        int minY = 0;
        int maxY = data.size() - 1;
        for (int y = 0; y <= maxX; y++) {
            for (int x = 0; x <= maxY; x++) {
                int value = data.get(y).charAt(x) == '#' ? 1 : 0;
                photo.put(new Coordinate(x,y),value);
            }
        }

        for (int i = 0; i < 50; i++) {
            Map<Coordinate, Integer> photoReplaced = new HashMap<>();
            minX-= 1;
            maxX+= 1;
            minY-= 1;
            maxY+= 1;
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {

                    // we want x-1;y-1, x;y-1 x+1;y-1, x-1;y, x;y, x+1;y, x-1;y+1, x;y+1, x+1;y+1

                    ArrayList<Coordinate> coordinates = new ArrayList<>(Arrays.asList(new Coordinate(x-1, y-1),
                            new Coordinate(x, y-1),
                            new Coordinate(x+1, y-1),
                            new Coordinate(x-1, y),
                            new Coordinate(x, y),
                            new Coordinate(x+1, y),
                            new Coordinate(x-1, y+1),
                            new Coordinate(x, y+1),
                            new Coordinate(x+1, y+1)));
                    String enhanceValue = "";

                    for (Coordinate coordinate : coordinates) {
//                        if ((coordinate.x < minX) || (coordinate.x > maxX) || (coordinate.y < minY) || (coordinate.y > maxY)) {
//                            enhanceValue += i % 2;
//                        } else {
                            enhanceValue += photo.containsKey(coordinate) ? photo.get(coordinate) : i % 2;
//                        }
                    }

                    photoReplaced.put(new Coordinate(x, y), algorithm.charAt(Integer.parseInt(enhanceValue, 2)) == '#' ? 1 : 0);

                }
            }
            photo = photoReplaced;
        }


        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                System.out.print(photo.get(new Coordinate(x,y)) == 1 ? '#' : '.');
            }
            System.out.println();
        }

        int acc = 0;
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                acc+= photo.get(new Coordinate(x,y));
            }
        }
        System.out.println(acc);
    }

    private class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
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
            return x + "," + y;
        }
    }
}
