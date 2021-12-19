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

        Parser parser = new Parser(19);

        List<String> data = parser.parseDataStr();
        data.add("");

        List<Scanner> scanners = new ArrayList<>();

        Scanner current = null;
        for (String string : data) {
            if (string.contains("scanner")) {
                current = new Scanner();
            } else if (string.isEmpty()) {
                scanners.add(current);
                continue;
            } else {
                String[] split = string.split(",");
                current.addPoint(new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            }
        }

        List<Orientation> orderOfOperations = new ArrayList<>();
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZXY);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZXY);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZXY);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.YZ);
        orderOfOperations.add(Orientation.XZ);
        orderOfOperations.add(Orientation.XY);
        orderOfOperations.add(Orientation.XY);
        orderOfOperations.add(Orientation.XY);
        orderOfOperations.add(Orientation.XY);
        orderOfOperations.add(Orientation.XZ);
        orderOfOperations.add(Orientation.XZ);
        orderOfOperations.add(Orientation.XY);
        orderOfOperations.add(Orientation.XY);
        orderOfOperations.add(Orientation.XY);

        Scanner central = null;

        while (scanners.size() > 1) {

            Iterator<Scanner> it = scanners.iterator();
            central = it.next();

            while (it.hasNext()) {
                current = it.next();

                int startingSize = central.coordinates.size();

                for (Orientation orientation : orderOfOperations) {
                    central.changeReferencePoint(central.self);
                    for (Coordinate coordinate1 : central.coordinates) {
                        current.changeReferencePoint(current.self);
                        for (Coordinate coordinate2 : current.coordinates) {
                            central.changeReferencePoint(coordinate1);
                            current.changeReferencePoint(coordinate2);

                            List<Coordinate> coordinatesToCompare = new ArrayList<>();
                            coordinatesToCompare = new ArrayList<>();
                            coordinatesToCompare.addAll(current.coordinates);
                            coordinatesToCompare.retainAll(central.coordinates);

                            if (coordinatesToCompare.size() >= 12) {

                                // We found our match
                                for (Coordinate newCoordinate : current.coordinates) {
                                    central.addPoint(newCoordinate);
                                }

                                central.changeReferencePoint(central.self);

                                System.out.println("Hit!");
                                it.remove();
                                break;
                            }
                        }

                        if (startingSize != central.coordinates.size()) {
                            break;
                        }

                    }
                    if (startingSize != central.coordinates.size()) {
                        break;
                    }
                    current.rotatePoints(orientation);
                }
            }
        }

        System.out.println(central.coordinates.size());

    }

    private class Scanner extends Observable {

        List<Coordinate> coordinates;
        Coordinate self;

        public Scanner() {
            coordinates = new LinkedList<>();
            self = new Coordinate(0,0, 0);
        }

        public void addPoint(Coordinate coordinate) {
            if (!coordinates.contains(coordinate)) {
                coordinates.add(coordinate);
                addObserver(coordinate);
            }
        }

        public void changeReferencePoint(Coordinate newReferencePoint) {
            Coordinate copy = new Coordinate(newReferencePoint.x, newReferencePoint.y, newReferencePoint.z);
            self.x -= copy.x;
            self.y -= copy.y;
            self.z -= copy.z;
            setChanged();
            notifyObservers(copy);
        }

        public void rotatePoints(Orientation orientation) {
            setChanged();
            notifyObservers(orientation);
        }
    }

    private class Coordinate implements Observer {
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

        @Override
        public void update(Observable o, Object arg) {
            if (arg.getClass() == Coordinate.class) {
                Coordinate referencePoint = (Coordinate) arg;
                x -= referencePoint.x;
                y -= referencePoint.y;
                z -= referencePoint.z;
            } else if (arg.getClass() == Orientation.class) {
                switch ((Orientation) arg) {
                    case XY: {
                        int temp = x;
                        x = y;
                        y = -temp;
                        break;
                    }
                    case YZ: {
                        int temp = z;
                        z = y;
                        y = -temp;
                        break;
                    }
                    case XZ: {
                        int temp = x;
                        x = z;
                        z = -temp;
                        break;
                    }
                    case YZXY: {
                        int temp = z;
                        z = y;
                        y = -temp;
                        temp = x;
                        x = y;
                        y = -temp;
                        break;
                    }
                };
            }
        }

        @Override
        public String toString() {
            return x + "," + y + "," + z;
        }
    }

    private enum Orientation {
        XY,
        YZ,
        XZ,
        YZXY;
    }
}
