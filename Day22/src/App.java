import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(22);

        List<String> data = parser.parseDataStr();

        Map<Coordinate, Boolean> reactor = new HashMap<>();

        for (String string : data) {
            String[] split = string.split(" ");
            String[] coordSplit = split[1].split(",");
            coordSplit[0] = coordSplit[0].substring(2);
            coordSplit[1] = coordSplit[1].substring(2);
            coordSplit[2] = coordSplit[2].substring(2);
            String[] splitX = coordSplit[0].split("\\.\\.");
            String[] splitY = coordSplit[1].split("\\.\\.");
            String[] splitZ = coordSplit[2].split("\\.\\.");

            boolean state = split[0].equals("on");
            for (int x = (Math.max(Integer.parseInt(splitX[0]), -50)); x <= (Math.min(Integer.parseInt(splitX[1]), 50)); x++) {
                for (int y = (Math.max(Integer.parseInt(splitY[0]), -50)); y <= (Math.min(Integer.parseInt(splitY[1]), 50)); y++) {
                    for (int z = (Math.max(Integer.parseInt(splitZ[0]), -50)); z <= (Math.min(Integer.parseInt(splitZ[1]), 50)); z++) {
                        Coordinate coordinate = new Coordinate(x, y, z);
                        reactor.put(coordinate, state);
                    }
                }
            }
        }

        long acc = 0;
        for (boolean state : reactor.values()) {
            if (state) {
                acc++;
            }
        }
        System.out.println(acc);
    }

    private class Coordinate {
        int x;
        int y;
        int z;

        public Coordinate(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinate that = (Coordinate) o;

            if (x != that.x) return false;
            if (y != that.y) return false;
            return z == that.z;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + z;
            return result;
        }
    }
}
