import java.io.FileNotFoundException;
import java.util.*;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    int[][] heightMap;

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(9);

        List<String> data = parser.parseDataStr();

        heightMap = new int[data.size()][data.get(0).length()];

        for (int i = 0; i < data.size(); i++) {
            char[] line = data.get(i).toCharArray();
            for (int j = 0; j < line.length; j++) {
                heightMap[i][j] = Integer.parseInt(""+line[j]);
            }
        }

        int depthAcc = 0;

        List<Integer> basin = new ArrayList<>();

        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap[0].length; j++) {
                int value = heightMap[i][j];

                int top = Integer.MAX_VALUE;
                int bottom = Integer.MAX_VALUE;
                int left = Integer.MAX_VALUE;
                int right = Integer.MAX_VALUE;

                if (i != 0) {
                    top = heightMap[i - 1][j];
                }
                if (i != heightMap.length - 1) {
                    bottom = heightMap[i + 1][j];
                }

                if (j != 0) {
                    left = heightMap[i][j-1];
                }
                if (j != heightMap[0].length - 1) {
                    right = heightMap[i][j+1];
                }

//                System.out.println("value: " + value);
//                System.out.println("left: " + left);
//                System.out.println("right: " + right);
//                System.out.println("top: " + top);
//                System.out.println("bottom: " + bottom);

                if (value < top && value < bottom && value < left && value < right) {
//                    System.out.println(value);
//                    System.out.println("i: " + i + " j: " + j);
                    depthAcc += (1 + value);
                    int basinVal = checkSurrounding(i, j, new HashSet<>());
//                    System.out.println(basinVal);
                    basin.add(basinVal);
                }


            }
        }

        System.out.println(depthAcc);

        Collections.sort(basin);
        System.out.println(basin.get(basin.size() - 1) * basin.get(basin.size() - 2) * basin.get(basin.size() - 3));
    }

    private int checkSurrounding(int i, int j, Set<Coordinate> basins) {

        basins.add(new Coordinate(i, j));
        int value = heightMap[i][j];

        int top = 9;
        int bottom = 9;
        int left = 9;
        int right = 9;

        if (i != 0) {
            top = heightMap[i - 1][j];
        }
        if (i != heightMap.length - 1) {
            bottom = heightMap[i + 1][j];
        }

        if (j != 0) {
            left = heightMap[i][j-1];
        }
        if (j != heightMap[0].length - 1) {
            right = heightMap[i][j+1];
        }

        if (top > value && top != 9) {
            checkSurrounding(i - 1, j, basins);
        }
        if (bottom > value && bottom != 9) {
            checkSurrounding(i + 1, j, basins);
        }
        if (left > value && left != 9) {
            checkSurrounding(i, j - 1, basins);
        }
        if (right > value && right != 9) {
            checkSurrounding(i, j + 1, basins);
        }

        return basins.size();
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
