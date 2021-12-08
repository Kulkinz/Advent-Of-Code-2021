import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(8);

        List<String> data = parser.parseDataStr();

        Map<NumDisplay, String[]> inout = new HashMap<>();

        for (String string : data) {
            String[] split = string.split("(\\s\\|\\s)");
            List<String> input = new ArrayList<>(Arrays.asList(split[0].split(" ")));
            input.sort((Comparator.comparingInt(String::length)));
            List<List<Character>> charInputs = new ArrayList<>();
            for (String together : input) {
                charInputs.add(together.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
            }
            // this results in: 2 3 4 5 5 5 6 6 6 7
            String[] output = split[1].split(" ");

            /*
                goes:  0
                      6 1
                       2
                      5 3
                       4
             */
            List<Character> p0;
            List<Character> p1;
            List<Character> p2;
            List<Character> p3;
            List<Character> p4;
            List<Character> p5;
            List<Character> p6;

            p1 = charInputs.get(0);
            p3 = charInputs.get(0);

            p0 = charInputs.get(1);
            p0.removeAll(p1); // Only certain solution

            p6 = charInputs.get(2);
            p2 = charInputs.get(2);
            p6.removeAll(p1);
            p2.removeAll(p1);

            p5 = charInputs.get(9);
            p4 = charInputs.get(9);
            p5.removeAll(p1);
            p5.removeAll(p0);
            p5.removeAll(p6);
            p4.removeAll(p1);
            p4.removeAll(p0);
            p4.removeAll(p6);


            for (int i = 6; i <= 8 ; i++) {
                List<Character> temp = charInputs.get(i);

                // Contains right two and top left and middle. looking for 9
                if (temp.containsAll(p1) &&
                        temp.containsAll(p3) && // redundancy for later modifications
                        temp.containsAll(p6) &&
                        temp.containsAll(p2)) {
                    temp.removeAll(p1);
                    temp.removeAll(p3);
                    temp.removeAll(p6);
                    temp.removeAll(p2);
                    temp.removeAll(p0);

                    p4 = temp;
                    p5.removeAll(temp);
                } else if (temp.containsAll(p1) &&  // Contains right two and bottom left and bottom. looking for 0
                        temp.containsAll(p3) &&
                        temp.containsAll(p5) &&
                        temp.containsAll(p4)) {
                    temp.removeAll(p1);
                    temp.removeAll(p3);
                    temp.removeAll(p5);
                    temp.removeAll(p4);
                    temp.removeAll(p0);

                    p6 = temp;
                    p2.removeAll(temp);
                } else { // has to be 6 by elimination
                    temp.removeAll(p6);
                    temp.removeAll(p2);
                    temp.removeAll(p5);
                    temp.removeAll(p4);
                    temp.removeAll(p0);

                    p3 = temp;
                    p1.removeAll(temp);
                }
            }

            inout.put(new NumDisplay(p0.get(0), p1.get(0), p2.get(0), p3.get(0), p4.get(0), p5.get(0), p6.get(0)), output);

        }

        Long acc = 0L;
//        for (String[] outputs : inout.values()) {
//            for (String digit : outputs) {
//                if (digit.length() == 2 || digit.length() == 4 || digit.length() == 3 || digit.length() == 7) {
//                    System.out.println(digit);
//                    acc++;
//                }
//            }
//        }

        for (Map.Entry<NumDisplay, String[]> pair : inout.entrySet()) {
//            System.out.println(pair.getValue()[0]);
//            System.out.println(pair.getValue()[1]);
//            System.out.println(pair.getValue()[2]);
//            System.out.println(pair.getValue()[3]);
            NumDisplay display = pair.getKey();
            int value = 1000 * display.value(pair.getValue()[0].chars().mapToObj(c -> (char) c).collect(Collectors.toList())) +
                    100 * display.value(pair.getValue()[1].chars().mapToObj(c -> (char) c).collect(Collectors.toList())) +
                    10 * display.value(pair.getValue()[2].chars().mapToObj(c -> (char) c).collect(Collectors.toList())) +
                    display.value(pair.getValue()[3].chars().mapToObj(c -> (char) c).collect(Collectors.toList()));

//            System.out.println(value);
            acc += value;
        }

        System.out.println(acc);


    }

    private class NumDisplay {

        List<Character> zero, one, two, three, four, five, six, seven, eight, nine;

        public NumDisplay(char p0, char p1, char p2, char p3, char p4, char p5, char p6) {
            zero = Arrays.asList(p0, p1, p3, p4, p5, p6);
            one = Arrays.asList(p1, p3);
            two = Arrays.asList(p0, p1, p2, p5, p4);
            three = Arrays.asList(p0, p1, p2, p3, p4);
            four = Arrays.asList(p6, p1, p2, p3);
            five = Arrays.asList(p0, p6, p2, p3, p4);
            six = Arrays.asList(p0, p6, p5, p4, p3, p2);
            seven = Arrays.asList(p0, p1, p3);
            eight = Arrays.asList(p0, p1, p2, p3, p4, p5, p6);
            nine = Arrays.asList(p0, p1, p2, p3, p4, p6);
        }

        public int value(List<Character> input) {
            if (zero.containsAll(input) && input.containsAll(zero)) {
                return 0;
            } else if (one.containsAll(input) && input.containsAll(one)) {
                return 1;
            } else if (two.containsAll(input) && input.containsAll(two)) {
                return 2;
            } else if (three.containsAll(input) && input.containsAll(three)) {
                return 3;
            } else if (four.containsAll(input) && input.containsAll(four)) {
                return 4;
            } else if (five.containsAll(input) && input.containsAll(five)) {
                return 5;
            } else if (six.containsAll(input) && input.containsAll(six)) {
                return 6;
            } else if (seven.containsAll(input) && input.containsAll(seven)) {
                return 7;
            } else if (eight.containsAll(input) && input.containsAll(eight)) {
                return 8;
            } else if (nine.containsAll(input) && input.containsAll(nine)) {
                return 9;
            } else {
                return -1;
            }
        }
    }
}
