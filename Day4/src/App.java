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

        Parser parser = new Parser(4);

        List<String> data = parser.parseDataStr();
        data.add("");

        List<Integer> bingoNumbers = new ArrayList<>();

        Map<Integer, List<BingoTile>> tiles = new HashMap<>();
        List<BingoCard> bingoCards = new ArrayList<>();

        for (String value : data.get(0).split(",")) {
            bingoNumbers.add(Integer.parseInt(value));
        }

        data.remove(0);
        data.remove(0);

        BingoCard currentBingoCard = new BingoCard();
        for (String line : data) {
            if (line.isEmpty()) {
                bingoCards.add(currentBingoCard);
                currentBingoCard = new BingoCard();
                continue;
            }
            line = line.trim();

            String[] cardNumbers = line.split("\\s+");
            for (String cardNumber : cardNumbers) {
                int tileInt = Integer.parseInt(cardNumber);
                BingoTile tile = new BingoTile(tileInt);
                currentBingoCard.addTile(tile);

                if (tiles.containsKey(tileInt)) {
                    tiles.get(tileInt).add(tile);
                } else {
                    ArrayList<BingoTile> tilesList = new ArrayList<>();
                    tilesList.add(tile);
                    tiles.put(tileInt, tilesList);
                }
            }
        }

        BingoCard winner = null;
        int winningNumber = 0;
        for (int bingoNum : bingoNumbers) {
            if (tiles.containsKey(bingoNum)) {
                for (BingoTile bingoTile : tiles.get(bingoNum)) {
                    bingoTile.setChecked(true);
                }
            }
//            for (BingoCard bingoCard : bingoCards) { part 1
//                if (bingoCard.isBingo()) {
//                    System.out.println(bingoCard);
//                    winner = bingoCard;
//                    winningNumber = bingoNum;
//                    break;
//                }
//            }
            Iterator bingoCardit = bingoCards.iterator(); // part 2
            while (bingoCardit.hasNext()) {
                BingoCard nextBingoCard = (BingoCard) bingoCardit.next();
                if (nextBingoCard.isBingo()) {
                    if (bingoCards.size() > 1) {
                        bingoCardit.remove();
                    } else {
                        System.out.println(nextBingoCard);
                        winner = nextBingoCard;
                        winningNumber = bingoNum;
                        break;
                    }
                }
            }
            if (winner != null) {
                break;
            }
        }

        System.out.println(winningNumber);
        System.out.println(winner.sumUnchecked());
        System.out.println(winningNumber * winner.sumUnchecked());
    }

    public class BingoCard {

        private BingoTile[][] tiles;

        public BingoCard() {
            tiles = new BingoTile[5][5];
        }

        private int row = 0;
        private int column = 0;

        public void addTile(BingoTile tile) {
            tiles[row][column] = tile;
            column++;
            if (column > 4) {
                column = 0;
                row++;
            }
        }

        public boolean isBingo() {
            return (isRowBingo(0) || isRowBingo(1) || isRowBingo(2) || isRowBingo(3) || isRowBingo(4) ||
                    isColumnBingo(0) || isColumnBingo(1) || isColumnBingo(2) || isColumnBingo(3) || isColumnBingo(4));
        }

        private boolean isRowBingo(int row) {
            return (tiles[row][0].isChecked() &&
                    tiles[row][1].isChecked() &&
                    tiles[row][2].isChecked() &&
                    tiles[row][3].isChecked() &&
                    tiles[row][4].isChecked());
        }

        private boolean isColumnBingo(int column) {
            return (tiles[0][column].isChecked() &&
                    tiles[1][column].isChecked() &&
                    tiles[2][column].isChecked() &&
                    tiles[3][column].isChecked() &&
                    tiles[4][column].isChecked());
        }

        public int sumUnchecked() {
            int acc = 0;
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (!tiles[i][j].isChecked()) {
                        acc += tiles[i][j].getNumber();
                    }
                }
            }
            return acc;
        }

        @Override
        public String toString() {
            return "BingoCard{" +
                    "tiles=" + "\n" +
                    tiles[0][0] + " " + tiles[0][1] + " " + tiles[0][2] + " " + tiles[0][3] + " " + tiles[0][4] + "\n" +
                    tiles[1][0] + " " + tiles[1][1] + " " + tiles[1][2] + " " + tiles[1][3] + " " + tiles[1][4] + "\n" +
                    tiles[2][0] + " " + tiles[2][1] + " " + tiles[2][2] + " " + tiles[2][3] + " " + tiles[2][4] + "\n" +
                    tiles[3][0] + " " + tiles[3][1] + " " + tiles[3][2] + " " + tiles[3][3] + " " + tiles[3][4] + "\n" +
                    tiles[4][0] + " " + tiles[4][1] + " " + tiles[4][2] + " " + tiles[4][3] + " " + tiles[4][4] + "\n" +
                    '}';
        }
    }

    public class BingoTile {

        private int number;
        private boolean checked;

        public BingoTile(int number) {
            this.number = number;
            checked = false;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public int getNumber() {
            return number;
        }

        @Override
        public String toString() {
            return number + " " + checked;
        }
    }
}
