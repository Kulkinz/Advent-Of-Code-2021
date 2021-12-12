import java.io.FileNotFoundException;
import java.util.*;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    List<String> cavesTraversed;
    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(12);

        List<String> data = parser.parseDataStr();
        cavesTraversed = new ArrayList<>();

        Map<String, Cave> caveMap = new HashMap<>();
        HashMap<String, Integer> navigateMap = new HashMap<>();


        for (String string : data) {
            String[] toFrom = string.split("-");
            Cave cave1;
            Cave cave2;
            if (caveMap.containsKey(toFrom[0])) {
                cave1 = caveMap.get(toFrom[0]);
            } else {
                cave1 = new Cave(toFrom[0], toFrom[0].equals(toFrom[0].toUpperCase()));
                caveMap.put(toFrom[0], cave1);
            }

            if (caveMap.containsKey(toFrom[1])) {
                cave2 = caveMap.get(toFrom[1]);
            } else {
                cave2 = new Cave(toFrom[1], toFrom[1].equals(toFrom[1].toUpperCase()));
                caveMap.put(toFrom[1], cave2);
            }

            cave1.addConnection(cave2);
            cave2.addConnection(cave1);

            if (!navigateMap.containsKey(cave1.name)) {
                navigateMap.put(cave1.name, 0);
            }

            if (!navigateMap.containsKey(cave2.name)) {
                navigateMap.put(cave2.name, 0);
            }
        }

        LinkedList<Cave> navigateStart = new LinkedList<>();
        navigateStart.add(caveMap.get("start"));

        System.out.println(navigateCaves(navigateStart, new Bundle(navigateMap)));
    }

    private int navigateCaves(LinkedList<Cave> caves, Bundle bundle) {
        int acc = 0;
        for (Cave cave : caves.getLast().connection) {
            LinkedList<Cave> copyList = (LinkedList<Cave>) caves.clone();
            HashMap<String, Integer> copyMap = (HashMap<String, java.lang.Integer>) bundle.navigateMap.clone();
            Bundle copyBundle = new Bundle(copyMap, bundle.hasOccurredTwice);
            if (cave.name.equals("start")) {
                continue;
            }
            if (cave.name.equals("end")) {
                String s = "";
                for (Cave caveF : caves) {
                    s += caveF.name + ",";
                }
                s += "end";
                cavesTraversed.add(s);
                acc++;
            } else if (cave.isBigCave || (bundle.hasOccurredTwice ? (bundle.navigateMap.get(cave.name) < 1) : (bundle.navigateMap.get(cave.name) != 2))) {
                copyMap.put(cave.name, copyMap.get(cave.name)+1);
                if (!cave.isBigCave && copyMap.get(cave.name) == 2) {
                    copyBundle.hasOccurredTwice = true;
                }
                copyList.add(cave);
                acc += navigateCaves(copyList, copyBundle);
            }
        }
        return acc;
    }

    private class Cave {

        String name;
        boolean isBigCave;
        List<Cave> connection;

        public Cave(String name, boolean isBigCave) {
            this.name = name;
            this.isBigCave = isBigCave;
            connection = new ArrayList<>();
        }

        public void addConnection(Cave cave) {
            if (!connection.contains(cave)) {
                connection.add(cave);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Cave cave = (Cave) o;

            if (isBigCave != cave.isBigCave) return false;
            return name.equals(cave.name);
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + (isBigCave ? 1 : 0);
            return result;
        }
    }

    private class Bundle {
        boolean hasOccurredTwice;
        HashMap<String, Integer> navigateMap;

        public Bundle(HashMap<String, Integer> navigateMap) {
            this.navigateMap = navigateMap;
            hasOccurredTwice = false;
        }

        public Bundle(HashMap<String, Integer> navigateMap, Boolean hasOccurredTwice) {
            this.navigateMap = navigateMap;
            this.hasOccurredTwice = hasOccurredTwice;
        }
    }
}
