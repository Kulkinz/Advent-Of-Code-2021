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

        Parser parser = new Parser(23);

        List<String> data = parser.parseDataStr();

        for (String string : data) {
            System.out.println(string);
        }
    }

    private class Map {
        int energySoFar = 0;
        State[] hallway;
        State[] aRoom;
        State[] bRoom;
        State[] cRoom;
        State[] dRoom;

        public Map(State[] aRoom, State[] bRoom, State[] cRoom, State[] dRoom) {
            hallway = new State[11];
            for (int i = 0; i < 11; i++) {
                hallway[i] = State.Empty;
            }
            this.aRoom = aRoom;
            this.bRoom = bRoom;
            this.cRoom = cRoom;
            this.dRoom = dRoom;
        }

        public void setEnergySoFar(int energySoFar) {
            this.energySoFar = energySoFar;
        }

        public List<Map> generateNextMaps() {
            List<Map> maps = new ArrayList<>();



            return maps;
        }
    }

    private enum State {
        Empty,
        A,
        B,
        C,
        D
    }
}
