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

        State[] aRoom = new State[2];
        State[] bRoom = new State[2];
        State[] cRoom = new State[2];
        State[] dRoom = new State[2];

        aRoom[0] = getStateFromChar(data.get(2).charAt(3));
        bRoom[0] = getStateFromChar(data.get(2).charAt(5));
        cRoom[0] = getStateFromChar(data.get(2).charAt(7));
        dRoom[0] = getStateFromChar(data.get(2).charAt(9));

        aRoom[1] = getStateFromChar(data.get(3).charAt(3));
        bRoom[1] = getStateFromChar(data.get(3).charAt(5));
        cRoom[1] = getStateFromChar(data.get(3).charAt(7));
        dRoom[1] = getStateFromChar(data.get(3).charAt(9));

        Map startingMap = new Map(aRoom, bRoom, cRoom, dRoom);

        System.out.println(System.currentTimeMillis());
        System.out.println(getLeastAmountOfMoves(startingMap, Integer.MAX_VALUE));
        System.out.println(System.currentTimeMillis());
    }

    private int getLeastAmountOfMoves(Map map, int compare) {
        for (Map newMap : map.generateNextMaps()) {
            if (newMap.energySoFar > compare) {
                continue;
            }
            if (newMap.isComplete()) {
                compare = Math.min(compare, newMap.energySoFar);
            } else {
                compare = Math.min(compare, getLeastAmountOfMoves(newMap, compare));
            }
        }
        return compare;
    }

    private State getStateFromChar(char character) {
        switch (character) {
            case 'A':
                return State.A;
            case 'B':
                return State.B;
            case 'C':
                return State.C;
            case 'D':
                return State.D;
            default:
                return State.Empty;
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

        public Map(Map map) {
            hallway = map.hallway.clone();
            aRoom = map.aRoom.clone();
            bRoom = map.bRoom.clone();
            cRoom = map.cRoom.clone();
            dRoom = map.dRoom.clone();
            energySoFar = map.energySoFar;
        }

        public void increaseEnergy(int change) {
            energySoFar += change;
        }

        public List<Map> generateNextMaps() {
            List<Map> maps = new ArrayList<>();

            Map helper;
            int travelDistance;
            // checking hallway for anything in there
            for (int i = 0; i < 11; i++) {
                if (hallway[i] != State.Empty) {
                    switch (hallway[i]) {
                        case A:
                            if (canGetToSpot(i, 2)) {
                                if (aRoom[0] == State.Empty) {
                                    if (aRoom[1] == State.Empty) {
                                        helper = new Map(this);
                                        // get distance from here to A;
                                        travelDistance = Math.abs(i - 2);
                                        travelDistance += 2;
                                        helper.hallway[i] = State.Empty;
                                        helper.aRoom[1] = State.A;
                                        helper.increaseEnergy(travelDistance * 1);
                                        maps.add(helper);
                                    } else {
                                        if (aRoom[1] == State.A) {
                                            helper = new Map(this);
                                            // get distance from here to A;
                                            travelDistance = Math.abs(i - 2);
                                            travelDistance += 1;
                                            helper.hallway[i] = State.Empty;
                                            helper.aRoom[0] = State.A;
                                            helper.increaseEnergy(travelDistance * 1);
                                            maps.add(helper);
                                        }
                                    }
                                }
                            }
                            break;
                        case B:
                            if (canGetToSpot(i, 4)) {
                                if (bRoom[0] == State.Empty) {
                                    if (bRoom[1] == State.Empty) {
                                        helper = new Map(this);
                                        // get distance from here to B;
                                        travelDistance = Math.abs(i - 4);
                                        travelDistance += 2;
                                        helper.hallway[i] = State.Empty;
                                        helper.bRoom[1] = State.B;
                                        helper.increaseEnergy(travelDistance * 10);
                                        maps.add(helper);
                                    } else {
                                        if (bRoom[1] == State.B) {
                                            helper = new Map(this);
                                            // get distance from here to B;
                                            travelDistance = Math.abs(i - 4);
                                            travelDistance += 1;
                                            helper.hallway[i] = State.Empty;
                                            helper.bRoom[0] = State.B;
                                            helper.increaseEnergy(travelDistance * 10);
                                            maps.add(helper);
                                        }
                                    }
                                }
                            }
                            break;
                        case C:
                            if (canGetToSpot(i, 6)) {
                                if (cRoom[0] == State.Empty) {
                                    if (cRoom[1] == State.Empty) {
                                        helper = new Map(this);
                                        // get distance from here to C;
                                        travelDistance = Math.abs(i - 6);
                                        travelDistance += 2;
                                        helper.hallway[i] = State.Empty;
                                        helper.cRoom[1] = State.C;
                                        helper.increaseEnergy(travelDistance * 100);
                                        maps.add(helper);
                                    } else {
                                        if (cRoom[1] == State.C) {
                                            helper = new Map(this);
                                            // get distance from here to C;
                                            travelDistance = Math.abs(i - 6);
                                            travelDistance += 1;
                                            helper.hallway[i] = State.Empty;
                                            helper.cRoom[0] = State.C;
                                            helper.increaseEnergy(travelDistance * 100);
                                            maps.add(helper);
                                        }
                                    }
                                }
                            }
                            break;
                        case D:
                            if (canGetToSpot(i, 8)) {
                                if (dRoom[0] == State.Empty) {
                                    if (dRoom[1] == State.Empty) {
                                        helper = new Map(this);
                                        // get distance from here to D;
                                        travelDistance = Math.abs(i - 8);
                                        travelDistance += 2;
                                        helper.hallway[i] = State.Empty;
                                        helper.dRoom[1] = State.D;
                                        helper.increaseEnergy(travelDistance * 1000);
                                        maps.add(helper);
                                    } else {
                                        if (dRoom[1] == State.D) {
                                            helper = new Map(this);
                                            // get distance from here to D;
                                            travelDistance = Math.abs(i - 8);
                                            travelDistance += 1;
                                            helper.hallway[i] = State.Empty;
                                            helper.dRoom[0] = State.D;
                                            helper.increaseEnergy(travelDistance * 1000);
                                            maps.add(helper);
                                        }
                                    }
                                }
                            }
                            break;
                    }
                }
            }

            // handle each room
            // handling a, which is available
            List<Integer> availableSpots = generateAvailableSpots(2);
            if (aRoom[0] != State.Empty) { // there something there
                if (aRoom[0] == State.A && aRoom[1] != State.A) { // A is above, something else below
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 1;
                        travelDistance += Math.abs(spot - 2); // get distance
                        helper.aRoom[0] = State.Empty;
                        helper.hallway[spot] = State.A;
                        helper.increaseEnergy(travelDistance * 1);
                        maps.add(helper);
                    }
                } else if (aRoom[0] != State.A) {
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 1;
                        travelDistance += Math.abs(spot - 2); // get distance
                        helper.aRoom[0] = State.Empty;
                        helper.hallway[spot] = aRoom[0];
                        helper.increaseEnergy(travelDistance * getEnergyCost(aRoom[0]));
                        maps.add(helper);
                    }
                }
            } else { // theres nothing there
                if (aRoom[1] != State.A && aRoom[1] != State.Empty) {
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 2;
                        travelDistance += Math.abs(spot - 2); // get distance
                        helper.aRoom[1] = State.Empty;
                        helper.hallway[spot] = aRoom[1];
                        helper.increaseEnergy(travelDistance * getEnergyCost(aRoom[1]));
                        maps.add(helper);
                    }
                }
            }

            availableSpots = generateAvailableSpots(4);
            if (bRoom[0] != State.Empty) { // there something there
                if (bRoom[0] == State.B && bRoom[1] != State.B) { // B is above, something else below
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 1;
                        travelDistance += Math.abs(spot - 4); // get distance
                        helper.bRoom[0] = State.Empty;
                        helper.hallway[spot] = State.B;
                        helper.increaseEnergy(travelDistance * 10);
                        maps.add(helper);
                    }
                } else if (bRoom[0] != State.B) {
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 1;
                        travelDistance += Math.abs(spot - 4); // get distance
                        helper.bRoom[0] = State.Empty;
                        helper.hallway[spot] = bRoom[0];
                        helper.increaseEnergy(travelDistance * getEnergyCost(bRoom[0]));
                        maps.add(helper);
                    }
                }
            } else { // theres nothing there
                if (bRoom[1] != State.B && bRoom[1] != State.Empty) {
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 2;
                        travelDistance += Math.abs(spot - 4); // get distance
                        helper.bRoom[1] = State.Empty;
                        helper.hallway[spot] = bRoom[1];
                        helper.increaseEnergy(travelDistance * getEnergyCost(bRoom[1]));
                        maps.add(helper);
                    }
                }
            }

            availableSpots = generateAvailableSpots(6);
            if (cRoom[0] != State.Empty) { // there something there
                if (cRoom[0] == State.C && cRoom[1] != State.C) { // C is above, something else below
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 1;
                        travelDistance += Math.abs(spot - 6); // get distance
                        helper.cRoom[0] = State.Empty;
                        helper.hallway[spot] = State.C;
                        helper.increaseEnergy(travelDistance * 100);
                        maps.add(helper);
                    }
                } else if (cRoom[0] != State.C) {
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 1;
                        travelDistance += Math.abs(spot - 6); // get distance
                        helper.cRoom[0] = State.Empty;
                        helper.hallway[spot] = cRoom[0];
                        helper.increaseEnergy(travelDistance * getEnergyCost(cRoom[0]));
                        maps.add(helper);
                    }
                }
            } else { // theres nothing there
                if (cRoom[1] != State.C && cRoom[1] != State.Empty) {
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 2;
                        travelDistance += Math.abs(spot - 6); // get distance
                        helper.cRoom[1] = State.Empty;
                        helper.hallway[spot] = cRoom[1];
                        helper.increaseEnergy(travelDistance * getEnergyCost(cRoom[1]));
                        maps.add(helper);
                    }
                }
            }

            availableSpots = generateAvailableSpots(8);
            if (dRoom[0] != State.Empty) { // there something there
                if (dRoom[0] == State.D && dRoom[1] != State.D) { // D is above, something else below
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 1;
                        travelDistance += Math.abs(spot - 8); // get distance
                        helper.dRoom[0] = State.Empty;
                        helper.hallway[spot] = State.D;
                        helper.increaseEnergy(travelDistance * 1000);
                        maps.add(helper);
                    }
                } else if (dRoom[0] != State.D) {
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 1;
                        travelDistance += Math.abs(spot - 8); // get distance
                        helper.dRoom[0] = State.Empty;
                        helper.hallway[spot] = dRoom[0];
                        helper.increaseEnergy(travelDistance * getEnergyCost(dRoom[0]));
                        maps.add(helper);
                    }
                }
            } else { // theres nothing there
                if (dRoom[1] != State.D && dRoom[1] != State.Empty) {
                    for (int spot : availableSpots) {
                        helper = new Map(this);

                        travelDistance = 2;
                        travelDistance += Math.abs(spot - 8); // get distance
                        helper.dRoom[1] = State.Empty;
                        helper.hallway[spot] = dRoom[1];
                        helper.increaseEnergy(travelDistance * getEnergyCost(dRoom[1]));
                        maps.add(helper);
                    }
                }
            }

            return maps;
        }

        private boolean isComplete() {
            return (aRoom[0] == State.A && aRoom[1] == State.A &&
                    bRoom[0] == State.B && bRoom[1] == State.B &&
                    cRoom[0] == State.C && cRoom[1] == State.C &&
                    dRoom[0] == State.D && dRoom[1] == State.D);
        }

        private boolean canGetToSpot(int start, int goal) {
            if (goal < start) {
                for (int i = start-1; i >= goal; i--) {
                    if (hallway[i] != State.Empty) {
                        return false;
                    }
                }
            } else {
                for (int i = start+1; i <= goal; i++) {
                    if (hallway[i] != State.Empty) {
                        return false;
                    }
                }
            }
            return true;

        }

        private List<Integer> generateAvailableSpots(int spot) {

            List<Integer> availableSpots = new ArrayList<>();

            for (int i = spot - 1; i >= 0; i--) {
                if (i != 2 && i != 4 && i != 6 && i != 8) {
                    if (hallway[i] == State.Empty) {
                        availableSpots.add(i);
                    } else {
                        break;
                    }
                }
            }

            for (int i = spot + 1; i < 11; i++) {
                if (i != 2 && i != 4 && i != 6 && i != 8) {
                    if (hallway[i] == State.Empty) {
                        availableSpots.add(i);
                    } else {
                        break;
                    }
                }
            }
            return  availableSpots;
        }

        private int getEnergyCost(State state) {
            switch (state) {
                case A: return 1;
                case B: return 10;
                case C: return 100;
                case D: return 1000;
                case Empty: return 0;
            }
            return 0;
        }

        @Override
        public String toString() {
            String hallwayString = "";
            for (State state : hallway) {
                if (state == State.Empty) {
                    hallwayString += ".";
                } else {
                    hallwayString += state.name();
                }
            }
            return "#############\n#"
                    + hallwayString + "#\n###"
                    + ((aRoom[0] == State.Empty) ? "." : aRoom[0].name()) + "#"
                    + ((bRoom[0] == State.Empty) ? "." : bRoom[0].name()) + "#"
                    + ((cRoom[0] == State.Empty) ? "." : cRoom[0].name()) + "#"
                    + ((dRoom[0] == State.Empty) ? "." : dRoom[0].name()) + "###\n  #"
                    + ((aRoom[1] == State.Empty) ? "." : aRoom[1].name()) + "#"
                    + ((bRoom[1] == State.Empty) ? "." : bRoom[1].name()) + "#"
                    + ((cRoom[1] == State.Empty) ? "." : cRoom[1].name()) + "#"
                    + ((dRoom[1] == State.Empty) ? "." : dRoom[1].name()) + "#  \n  #########";
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
