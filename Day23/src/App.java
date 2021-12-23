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

        State[] aRoom = new State[4];
        State[] bRoom = new State[4];
        State[] cRoom = new State[4];
        State[] dRoom = new State[4];

        aRoom[0] = getStateFromChar(data.get(2).charAt(3));
        bRoom[0] = getStateFromChar(data.get(2).charAt(5));
        cRoom[0] = getStateFromChar(data.get(2).charAt(7));
        dRoom[0] = getStateFromChar(data.get(2).charAt(9));

        aRoom[1] = getStateFromChar(data.get(3).charAt(3));
        bRoom[1] = getStateFromChar(data.get(3).charAt(5));
        cRoom[1] = getStateFromChar(data.get(3).charAt(7));
        dRoom[1] = getStateFromChar(data.get(3).charAt(9));

        aRoom[2] = getStateFromChar(data.get(4).charAt(3));
        bRoom[2] = getStateFromChar(data.get(4).charAt(5));
        cRoom[2] = getStateFromChar(data.get(4).charAt(7));
        dRoom[2] = getStateFromChar(data.get(4).charAt(9));

        aRoom[3] = getStateFromChar(data.get(5).charAt(3));
        bRoom[3] = getStateFromChar(data.get(5).charAt(5));
        cRoom[3] = getStateFromChar(data.get(5).charAt(7));
        dRoom[3] = getStateFromChar(data.get(5).charAt(9));

        Map startingMap = new Map(aRoom, bRoom, cRoom, dRoom);

        System.out.println(getLeastAmountOfMoves(startingMap, Integer.MAX_VALUE));
    }

    private int getLeastAmountOfMoves(Map map, int compare) {
        for (Map newMap : map.generateNextMaps()) {
            if (newMap.energySoFar > compare) {
                continue;
            }
            if (newMap.isComplete()) {
                compare = newMap.energySoFar;
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

        private int getSlotRemove(State state) {
            switch (state) {
                default: return -1;
                case A:
                    if (aRoom[0] == State.Empty) {
                        if (aRoom[1] == State.Empty) {
                            if (aRoom[2] == State.Empty) {
                                if (aRoom[3] == State.Empty) {
                                    return -1;
                                } else if (aRoom[3] == State.A) {
                                    return -1;
                                } else {
                                    return 3;
                                }
                            } else {
                                if (!allStateBelow(state, 2)) {
                                    return 2;
                                } else {
                                    return -1;
                                }
                            }
                        } else {
                            if (!allStateBelow(state, 1)) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    } else {
                        if (!allStateBelow(state, 0)) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                case B:
                    if (bRoom[0] == State.Empty) {
                        if (bRoom[1] == State.Empty) {
                            if (bRoom[2] == State.Empty) {
                                if (bRoom[3] == State.Empty) {
                                    return -1;
                                } else if (!allStateBelow(state, 3)) {
                                    return 3;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (!allStateBelow(state, 2)) {
                                    return 2;
                                } else {
                                    return -1;
                                }
                            }
                        } else {
                            if (!allStateBelow(state, 1)) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    } else {
                        if (!allStateBelow(state, 0)) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                case C:
                    if (cRoom[0] == State.Empty) {
                        if (cRoom[1] == State.Empty) {
                            if (cRoom[2] == State.Empty) {
                                if (cRoom[3] == State.Empty) {
                                    return -1;
                                } else if (!allStateBelow(state, 3)) {
                                    return 3;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (!allStateBelow(state, 2)) {
                                    return 2;
                                } else {
                                    return -1;
                                }
                            }
                        } else {
                            if (!allStateBelow(state, 1)) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    } else {
                        if (!allStateBelow(state, 0)) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                case D:
                    if (dRoom[0] == State.Empty) {
                        if (dRoom[1] == State.Empty) {
                            if (dRoom[2] == State.Empty) {
                                if (dRoom[3] == State.Empty) {
                                    return -1;
                                } else if (!allStateBelow(state, 3)) {
                                    return 3;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (!allStateBelow(state, 2)) {
                                    return 2;
                                } else {
                                    return -1;
                                }
                            }
                        } else {
                            if (!allStateBelow(state, 1)) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    } else {
                        if (!allStateBelow(state, 0)) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
            }
        }

        private int getSlotAvailable(State state) {

            switch (state) {
                default: return -1;
                case A:
                    if (aRoom[0] != State.Empty) {
                        return -1;
                    } else {
                        if (allStateBelow(state, 1)) {
                            return 0;
                        } else {
                            if (aRoom[1] != State.Empty) {
                                return -1;
                            } else {
                                if (allStateBelow(state, 2)) {
                                    return 1;
                                } else {
                                    if (aRoom[2] != State.Empty) {
                                        return -1;
                                    } else {
                                        if (allStateBelow(state, 3)) {
                                            return 2;
                                        } else {
                                            if (aRoom[3] != State.Empty) {
                                                return -1;
                                            } else {
                                                return 3;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                case B:
                    if (bRoom[0] != State.Empty) {
                        return -1;
                    } else {
                        if (allStateBelow(state, 1)) {
                            return 0;
                        } else {
                            if (bRoom[1] != State.Empty) {
                                return -1;
                            } else {
                                if (allStateBelow(state, 2)) {
                                    return 1;
                                } else {
                                    if (bRoom[2] != State.Empty) {
                                        return -1;
                                    } else {
                                        if (allStateBelow(state, 3)) {
                                            return 2;
                                        } else {
                                            if (bRoom[3] != State.Empty) {
                                                return -1;
                                            } else {
                                                return 3;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                case C:
                    if (cRoom[0] != State.Empty) {
                        return -1;
                    } else {
                        if (allStateBelow(state, 1)) {
                            return 0;
                        } else {
                            if (cRoom[1] != State.Empty) {
                                return -1;
                            } else {
                                if (allStateBelow(state, 2)) {
                                    return 1;
                                } else {
                                    if (cRoom[2] != State.Empty) {
                                        return -1;
                                    } else {
                                        if (allStateBelow(state, 3)) {
                                            return 2;
                                        } else {
                                            if (cRoom[3] != State.Empty) {
                                                return -1;
                                            } else {
                                                return 3;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                case D:
                    if (dRoom[0] != State.Empty) {
                        return -1;
                    } else {
                        if (allStateBelow(state, 1)) {
                            return 0;
                        } else {
                            if (dRoom[1] != State.Empty) {
                                return -1;
                            } else {
                                if (allStateBelow(state, 2)) {
                                    return 1;
                                } else {
                                    if (dRoom[2] != State.Empty) {
                                        return -1;
                                    } else {
                                        if (allStateBelow(state, 3)) {
                                            return 2;
                                        } else {
                                            if (dRoom[3] != State.Empty) {
                                                return -1;
                                            } else {
                                                return 3;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }

        private boolean allStateBelow(State state, int index) {
            switch (state) {
                default: return false;
                case A:
                    for (int i = index; i < 4; i++) {
                        if (aRoom[i] != State.A) {
                            return false;
                        }
                    }
                    return true;
                case B:
                    for (int i = index; i < 4; i++) {
                        if (bRoom[i] != State.B) {
                            return false;
                        }
                    }
                    return true;
                case C:
                    for (int i = index; i < 4; i++) {
                        if (cRoom[i] != State.C) {
                            return false;
                        }
                    }
                    return true;
                case D:
                    for (int i = index; i < 4; i++) {
                        if (dRoom[i] != State.D) {
                            return false;
                        }
                    }
                    return true;
            }
        }

        public List<Map> generateNextMaps() {
            List<Map> maps = new ArrayList<>();

            Map helper;
            int travelDistance;
            int slot;
            // checking hallway for anything in there
            for (int i = 10; i >= 0; i--) {
                if (hallway[i] != State.Empty) {
                    State state = hallway[i];
                    switch (state) {
                        case A:
                            if (canGetToSpot(i, 2)) {
                                slot = getSlotAvailable(state);
                                if (slot != -1) {
                                    helper = new Map(this);
                                    travelDistance = Math.abs(i - 2);
                                    travelDistance += slot + 1;
                                    helper.hallway[i] = State.Empty;
                                    helper.aRoom[slot] = state;
                                    helper.increaseEnergy(travelDistance * getEnergyCost(state));
                                    maps.add(helper);
                                }
                            }
                            break;
                        case B:
                            if (canGetToSpot(i, 4)) {
                                slot = getSlotAvailable(state);
                                if (slot != -1) {
                                    helper = new Map(this);
                                    travelDistance = Math.abs(i - 4);
                                    travelDistance += slot + 1;
                                    helper.hallway[i] = State.Empty;
                                    helper.bRoom[slot] = state;
                                    helper.increaseEnergy(travelDistance * getEnergyCost(state));
                                    maps.add(helper);
                                }
                            }
                            break;
                        case C:
                            if (canGetToSpot(i, 6)) {
                                slot = getSlotAvailable(state);
                                if (slot != -1) {
                                    helper = new Map(this);
                                    travelDistance = Math.abs(i - 6);
                                    travelDistance += slot + 1;
                                    helper.hallway[i] = State.Empty;
                                    helper.cRoom[slot] = state;
                                    helper.increaseEnergy(travelDistance * getEnergyCost(state));
                                    maps.add(helper);
                                }
                            }
                            break;
                        case D:
                            if (canGetToSpot(i, 8)) {
                                slot = getSlotAvailable(state);
                                if (slot != -1) {
                                    helper = new Map(this);
                                    travelDistance = Math.abs(i - 8);
                                    travelDistance += slot + 1;
                                    helper.hallway[i] = State.Empty;
                                    helper.dRoom[slot] = state;
                                    helper.increaseEnergy(travelDistance * getEnergyCost(state));
                                    maps.add(helper);
                                }
                            }
                            break;
                    }
                }
            }

            // handle each room
            // handling a, which is available

            List<Integer> availableSpots = generateAvailableSpots(8);
            slot = getSlotRemove(State.D);
            if (slot != -1) {
                for (int spot: availableSpots) {
                    helper = new Map(this);

                    travelDistance = slot + 1;
                    travelDistance += Math.abs(spot - 8);
                    helper.dRoom[slot] = State.Empty;
                    helper.hallway[spot] = dRoom[slot];
                    helper.increaseEnergy(travelDistance * getEnergyCost(dRoom[slot]));
                    maps.add(helper);
                }
            }

            availableSpots = generateAvailableSpots(6);
            slot = getSlotRemove(State.C);
            if (slot != -1) {
                for (int spot: availableSpots) {
                    helper = new Map(this);

                    travelDistance = slot + 1;
                    travelDistance += Math.abs(spot - 6);
                    helper.cRoom[slot] = State.Empty;
                    helper.hallway[spot] = cRoom[slot];
                    helper.increaseEnergy(travelDistance * getEnergyCost(cRoom[slot]));
                    maps.add(helper);
                }
            }

            availableSpots = generateAvailableSpots(4);
            slot = getSlotRemove(State.B);
            if (slot != -1) {
                for (int spot: availableSpots) {
                    helper = new Map(this);

                    travelDistance = slot + 1;
                    travelDistance += Math.abs(spot - 4);
                    helper.bRoom[slot] = State.Empty;
                    helper.hallway[spot] = bRoom[slot];
                    helper.increaseEnergy(travelDistance * getEnergyCost(bRoom[slot]));
                    maps.add(helper);
                }
            }

            availableSpots = generateAvailableSpots(2);
            slot = getSlotRemove(State.A);
            if (slot != -1) {
                for (int spot: availableSpots) {
                    helper = new Map(this);

                    travelDistance = slot + 1;
                    travelDistance += Math.abs(spot - 2);
                    helper.aRoom[slot] = State.Empty;
                    helper.hallway[spot] = aRoom[slot];
                    helper.increaseEnergy(travelDistance * getEnergyCost(aRoom[slot]));
                    maps.add(helper);
                }
            }

            return maps;
        }

        private boolean isComplete() {
            return (aRoom[0] == State.A && aRoom[1] == State.A &&
                    bRoom[0] == State.B && bRoom[1] == State.B &&
                    cRoom[0] == State.C && cRoom[1] == State.C &&
                    dRoom[0] == State.D && dRoom[1] == State.D &&
                    aRoom[2] == State.A && aRoom[3] == State.A &&
                    bRoom[2] == State.B && bRoom[3] == State.B &&
                    cRoom[2] == State.C && cRoom[3] == State.C &&
                    dRoom[2] == State.D && dRoom[3] == State.D);
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
                    + ((dRoom[1] == State.Empty) ? "." : dRoom[1].name()) + "#\n  #"
                    + ((aRoom[2] == State.Empty) ? "." : aRoom[2].name()) + "#"
                    + ((bRoom[2] == State.Empty) ? "." : bRoom[2].name()) + "#"
                    + ((cRoom[2] == State.Empty) ? "." : cRoom[2].name()) + "#"
                    + ((dRoom[2] == State.Empty) ? "." : dRoom[2].name()) + "#\n  #"
                    + ((aRoom[3] == State.Empty) ? "." : aRoom[3].name()) + "#"
                    + ((bRoom[3] == State.Empty) ? "." : bRoom[3].name()) + "#"
                    + ((cRoom[3] == State.Empty) ? "." : cRoom[3].name()) + "#"
                    + ((dRoom[3] == State.Empty) ? "." : dRoom[3].name()) + "#  \n  #########";
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
