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

        Parser parser = new Parser(13);

        List<String> data = parser.parseDataStr();

        List<Coordinate> coordinates = new ArrayList<>();

        Iterator<String> it = data.iterator();

        while (it.hasNext()) {
            String string = it.next();
            if (string.equals("")) {
                break;
            }
            String[] xy = string.split(",");
            Coordinate coordinate = new Coordinate(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            coordinates.add(coordinate);
        }

        while (it.hasNext()) {
            String string = it.next();
            String[] instruction = string.split(" ");
            String[] split = instruction[2].split("=");

            ListIterator<Coordinate> itList = coordinates.listIterator();
            int value = Integer.parseInt(split[1]);

            if (split[0].equals("x")) {
                while (itList.hasNext()) {
                    Coordinate current = itList.next();
                    if (current.x > value) {
                        itList.remove();
                        current.x = 2 * value - current.x;
                        if (!coordinates.contains(current)) {
                            itList.add(current);
                        }
                    }
                }
            } else {
                while (itList.hasNext()) {
                    Coordinate current = itList.next();
                    if (current.y > value) {
                        itList.remove();
                        current.y = 2 * value - current.y;
                        if (!coordinates.contains(current)) {
                            itList.add(current);
                        }
                    }
                }
            }
        }

        int maxX = 0;
        int maxY = 0;
        for (Coordinate coordinate : coordinates) {
            maxX = Math.max(coordinate.x, maxX);
            maxY = Math.max(coordinate.y, maxY);
        }

        char[][] answer = new char[maxY + 1][maxX + 1];

        for (int i = 0; i < maxY + 1; i++) {
            for (int j = 0; j < maxX + 1; j++) {
                if (coordinates.contains(new Coordinate(j, i))) {
                    answer[i][j] = '#';
                } else {
                    answer[i][j] = '.';
                }
            }
        }

        for (int i = 0; i < maxY + 1; i++) {
            String line = "";
            for (int j = 0; j < maxX + 1; j++) {
                line += answer[i][j];
            }
            System.out.println(line);
        }

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
    }
}
