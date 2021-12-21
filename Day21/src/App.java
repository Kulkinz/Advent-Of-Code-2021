import java.io.FileNotFoundException;
import java.util.*;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    Map<Set, Possibilities> tree = new HashMap<>();

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(21);

        List<String> data = parser.parseDataStr();

        for (String string : data) {
            System.out.println(string);
        }
        
        int dice = 0;
        int pos1 = Integer.parseInt(data.get(0).split("Player 1 starting position: ")[1]);
        int score1 = 0;
        int pos2 = Integer.parseInt(data.get(1).split("Player 2 starting position: ")[1]);
        int score2 = 0;
        int diceRolls = 0;

        while (score1 < 1000 && score2 < 1000) {
            pos1 += (dice++ % 100 + 1) + (dice++ % 100 + 1) + (dice++ % 100 + 1);
            score1 += (pos1 % 10 == 0) ? 10 : pos1 % 10;
            diceRolls += 3;

            if (score1 >= 1000) {
                continue;
            }

            pos2 += (dice++ % 100 + 1) + (dice++ % 100 + 1) + (dice++ % 100 + 1);
            score2 += (pos2 % 10 == 0) ? 10 : pos2 % 10;
            diceRolls += 3;

//            System.out.println(score1 + "," + score2);
        }

        System.out.println("Part 1: " + diceRolls * Math.min(score1, score2));


        pos1 = Integer.parseInt(data.get(0).split("Player 1 starting position: ")[1]);
        pos2 = Integer.parseInt(data.get(1).split("Player 2 starting position: ")[1]);

        Possibilities possibility = new Possibilities(new Set(pos1, 0, pos2, 0));
        tree.put(new Set(pos1, 0, pos2, 0), possibility);


        generatePossibilities(possibility);

        long total = totalSumOfPossibilities(new Set(pos1, 0, pos2, 0));
        long p1Wins = sumPlayer1Wins(new Set(pos1, 0, pos2, 0));
        long p2Wins = total - p1Wins;
        System.out.println("Total: " + total);
        System.out.println("P1 Wins: " + p1Wins);
        System.out.println("P2 Wins: " + p2Wins);
    }

    private void generatePossibilities(Possibilities possibility) {

        for (Combination set : possibility.possibleSets) {
            if (set.set.xScore < 21 && set.set.yScore < 21 && !tree.containsKey(set.set)) {
                Possibilities newPossibility = new Possibilities(set.set);
                tree.put(set.set, newPossibility);
                generatePossibilities(newPossibility);
            }
        }
    }


    Map<Set, Long> valuesTree = new HashMap<>();
    private long totalSumOfPossibilities(Set set) {
        if (!tree.containsKey(set)) {
            return 1L;
        }
        long acc = 0L;
        for (Combination possibility : tree.get(set).possibleSets) {
            if (valuesTree.containsKey(possibility.set)) {
                acc += possibility.weight * valuesTree.get(possibility.set);
            } else {
                acc += possibility.weight * totalSumOfPossibilities(possibility.set);
            }
        }
        valuesTree.put(set, acc);
        return acc;
    }


    Map<Set, Long> valuePlayer1Tree = new HashMap<>();
    private long sumPlayer1Wins(Set set) {
        if (set.xScore >= 21) {
            valuePlayer1Tree.put(set, 1L);
            return 1L;
        }
        if (set.yScore >= 21) {
            valuePlayer1Tree.put(set, 0L);
            return 0L;
        }
        long acc = 0L;
        for (Combination possibility : tree.get(set).possibleSets) {
            if (valuePlayer1Tree.containsKey(possibility.set)) {
                acc += possibility.weight * valuePlayer1Tree.get(possibility.set);
            } else {
                acc += possibility.weight * sumPlayer1Wins(possibility.set);
            }
        }
        valuePlayer1Tree.put(set, acc);
        return acc;
    }

    List<int[]> pairs = new ArrayList<>(Arrays.asList(new int[]{3, 1}, new int[]{4, 3}, new int[]{5, 6}, new int[]{6, 7}, new int[]{7, 6}, new int[]{8, 3}, new int[]{9, 1}));


    private class Possibilities {
        List<Combination> possibleSets;

        public Possibilities(Set set) {
            possibleSets = new ArrayList<>();
            // 3:1 4:3 5:6 6:7 7:6 8:3 9:1
            for (int[] pair : pairs) {
                Set setCopy = new Set(set);
                setCopy.modifyX(pair[0]);
                if (setCopy.xScore >= 21) {
                    possibleSets.add(new Combination(pair[1], setCopy));
                } else {
                    for (int[] pairY : pairs) {
                        Set setCopyCopy = new Set(setCopy);
                        setCopyCopy.modifyY(pairY[0]);
                        possibleSets.add(new Combination(pair[1] * pairY[1], setCopyCopy));
                    }
                }
            }
        }

        @Override
        public String toString() {
            return possibleSets.toString();
        }


    }

    public class Combination {
        int weight;
        Set set;

        public Combination(int weight, Set set) {
            this.weight = weight;
            this.set = set;
        }

        @Override
        public String toString() {
            return set + " w:" + weight;
        }
    }

    private class Set {
        int xPos;
        int xScore;
        int yPos;
        int yScore;

        public Set(int xPos, int xScore, int yPos, int yScore) {
            this.xPos = xPos;
            this.xScore = xScore;
            this.yPos = yPos;
            this.yScore = yScore;
        }

        public Set(Set set) {
            xPos = set.xPos;
            xScore = set.xScore;
            yPos = set.yPos;
            yScore = set.yScore;
        }

        public void modifyX(int diceRoll) {
            xPos += diceRoll;
            xScore += xPos % 10 == 0 ? 10 : xPos % 10;
        }

        public void modifyY(int diceRoll) {
            yPos += diceRoll;
            yScore += yPos % 10 == 0 ? 10 : yPos % 10;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Set set = (Set) o;

            if (xPos != set.xPos) return false;
            if (xScore != set.xScore) return false;
            if (yPos != set.yPos) return false;
            return yScore == set.yScore;
        }

        @Override
        public int hashCode() {
            int result = xPos;
            result = 31 * result + xScore;
            result = 31 * result + yPos;
            result = 31 * result + yScore;
            return result;
        }

        @Override
        public String toString() {
            return xPos+":"+xScore + " , " + yPos+":"+yScore;
        }
    }
}
