import java.io.FileNotFoundException;
import java.util.ArrayList;
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

        Parser parser = new Parser(22);

        List<String> data = parser.parseDataStr();

        List<Cube> cubes = new ArrayList<>();

        for (String string : data) {
            String[] split = string.split(" ");
            String[] coordSplit = split[1].split(",");
            coordSplit[0] = coordSplit[0].substring(2);
            coordSplit[1] = coordSplit[1].substring(2);
            coordSplit[2] = coordSplit[2].substring(2);
            String[] splitX = coordSplit[0].split("\\.\\.");
            String[] splitY = coordSplit[1].split("\\.\\.");
            String[] splitZ = coordSplit[2].split("\\.\\.");

            Coordinate lesserPoint = new Coordinate(Integer.parseInt(splitX[0]), Integer.parseInt(splitY[0]), Integer.parseInt(splitZ[0]));
            Coordinate greaterPoint = new Coordinate(Integer.parseInt(splitX[1]), Integer.parseInt(splitY[1]), Integer.parseInt(splitZ[1]));

            Cube cube = new Cube(lesserPoint, greaterPoint);
            boolean state = split[0].equals("on");

            // add
            if (state) {
                ArrayList<Cube> queue = new ArrayList<>();
                queue.add(cube);
                for (Cube listCube : cubes) {
                    ArrayList<Cube> result = new ArrayList<>();
                    for (Cube piecesOfCube : queue) {
                        result.addAll(piecesOfCube.subtract(listCube));
                    }
                    queue = result;
                }
                cubes.addAll(queue);
            } else { // subtract

                ArrayList<Cube> resultingCubes = new ArrayList<>();
                for (Cube listCube : cubes) {
                    resultingCubes.addAll(listCube.subtract(cube));
                }
                cubes = resultingCubes;
            }

        }

        long acc = 0L;
        for (Cube cube : cubes) {
            acc += cube.area();
        }
        System.out.println(acc);
    }

    private class Cube {
        Coordinate lesserPoint;
        Coordinate greaterPoint;

        public Cube(Coordinate lesserPoint, Coordinate greaterPoint) {
            this.lesserPoint = new Coordinate(lesserPoint);
            this.greaterPoint = new Coordinate(greaterPoint);
        }

        public long area() {
            return ((long) (greaterPoint.x + 1 - lesserPoint.x)) * ((long) (greaterPoint.y + 1 - lesserPoint.y)) * ((long) (greaterPoint.z + 1 - lesserPoint.z));
        }

        public List<Cube> subtract(Cube opposing) {
            List<Cube> newCubes = new ArrayList<>();
            Cube cubeHelper;
            Coordinate coordinateHelper;

            if ((lesserPoint.x < opposing.lesserPoint.x && greaterPoint.x < opposing.lesserPoint.x) ||
                    (lesserPoint.x > opposing.greaterPoint.x && greaterPoint.x > opposing.greaterPoint.x) ||
                    (lesserPoint.y < opposing.lesserPoint.y && greaterPoint.y < opposing.lesserPoint.y) ||
                    (lesserPoint.y > opposing.greaterPoint.y && greaterPoint.y > opposing.greaterPoint.y) ||
                    (lesserPoint.z < opposing.lesserPoint.z && greaterPoint.z < opposing.lesserPoint.z) ||
                    (lesserPoint.z > opposing.greaterPoint.z && greaterPoint.z > opposing.greaterPoint.z)) {
                newCubes.add(this);
                return newCubes; // they do not intersect, no subtraction needed
            }

            if (lesserPoint.x < opposing.lesserPoint.x) {
                    // |x-----|o--x-----o
                    coordinateHelper = new Coordinate(greaterPoint);
                    coordinateHelper.x = opposing.lesserPoint.x - 1;
                    cubeHelper = new Cube(lesserPoint, coordinateHelper);
                    newCubes.add(cubeHelper);
                    coordinateHelper.x += 1;
                    lesserPoint.x = coordinateHelper.x; // now its shaped further

                    // |x------|o-----o|-----x|
                    if (greaterPoint.x > opposing.greaterPoint.x) {
                        coordinateHelper = new Coordinate(lesserPoint);
                        coordinateHelper.x = opposing.greaterPoint.x + 1;
                        cubeHelper = new Cube(coordinateHelper, greaterPoint);
                        newCubes.add(cubeHelper);
                        coordinateHelper.x -= 1;
                        greaterPoint.x = coordinateHelper.x; // shaped further
                    }
            } else {
                // o-----x----o|----x|
                if (greaterPoint.x > opposing.greaterPoint.x) {
                    coordinateHelper = new Coordinate(lesserPoint);
                    coordinateHelper.x = opposing.greaterPoint.x + 1;
                    cubeHelper = new Cube(coordinateHelper, greaterPoint);
                    newCubes.add(cubeHelper);
                    coordinateHelper.x -= 1;
                    greaterPoint.x = coordinateHelper.x; // shaped further
                } else {
                    // o----x-----x----o
                }
            }

            if (lesserPoint.y < opposing.lesserPoint.y) {
                // |y-----|o--y-----o
                coordinateHelper = new Coordinate(greaterPoint);
                coordinateHelper.y = opposing.lesserPoint.y - 1;
                cubeHelper = new Cube(lesserPoint, coordinateHelper);
                newCubes.add(cubeHelper);
                coordinateHelper.y += 1;
                lesserPoint.y = coordinateHelper.y; // now its shaped further

                // |y------|o-----o|-----y|
                if (greaterPoint.y > opposing.greaterPoint.y) {
                    coordinateHelper = new Coordinate(lesserPoint);
                    coordinateHelper.y = opposing.greaterPoint.y + 1;
                    cubeHelper = new Cube(coordinateHelper, greaterPoint);
                    newCubes.add(cubeHelper);
                    coordinateHelper.y -= 1;
                    greaterPoint.y = coordinateHelper.y; // shaped further
                }
            } else {
                // o-----y----y|----o|
                if (greaterPoint.y > opposing.greaterPoint.y) {
                    coordinateHelper = new Coordinate(lesserPoint);
                    coordinateHelper.y = opposing.greaterPoint.y + 1;
                    cubeHelper = new Cube(coordinateHelper, greaterPoint);
                    newCubes.add(cubeHelper);
                    coordinateHelper.y -= 1;
                    greaterPoint.y = coordinateHelper.y; // shaped further
                } else {
                    // o----y-----y----o
                }
            }

            // check z
            if (lesserPoint.z < opposing.lesserPoint.z) {
                // |z-----|o--z-----o
                coordinateHelper = new Coordinate(greaterPoint);
                coordinateHelper.z = opposing.lesserPoint.z - 1;
                cubeHelper = new Cube(lesserPoint, coordinateHelper);
                newCubes.add(cubeHelper);
                coordinateHelper.z += 1;
                lesserPoint.z = coordinateHelper.z; // now its shaped further

                // |z------|o-----o|-----z|
                if (greaterPoint.z > opposing.greaterPoint.z) {
                    coordinateHelper = new Coordinate(lesserPoint);
                    coordinateHelper.z = opposing.greaterPoint.z + 1;
                    cubeHelper = new Cube(coordinateHelper, greaterPoint);
                    newCubes.add(cubeHelper);
                    coordinateHelper.z -= 1;
                    greaterPoint.z = coordinateHelper.z; // shaped further
                }
            } else {
                // o-----z----z|----o|
                if (greaterPoint.z > opposing.greaterPoint.z) {
                    coordinateHelper = new Coordinate(lesserPoint);
                    coordinateHelper.z = opposing.greaterPoint.z + 1;
                    cubeHelper = new Cube(coordinateHelper, greaterPoint);
                    newCubes.add(cubeHelper);
                    coordinateHelper.z -= 1;
                    greaterPoint.z = coordinateHelper.z; // shaped further
                } else {
                    // o----x-----x----o
                }
            }

            return newCubes;
        }

        @Override
        public String toString() {
            return "Cube{" +
                    "lesserPoint=" + lesserPoint +
                    ", greaterPoint=" + greaterPoint +
                    '}';
        }
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

        public Coordinate(Coordinate coordinate) {
            x = coordinate.x;
            y = coordinate.y;
            z = coordinate.z;
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

        @Override
        public String toString() {
            return x + "," + y + "," + z;
        }
    }
}
