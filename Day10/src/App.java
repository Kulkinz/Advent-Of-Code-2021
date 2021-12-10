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

        Parser parser = new Parser(10);

        List<String> data = parser.parseDataStr();

        List<Long> lineValues = new ArrayList<>();
        int acc = 0;
        for (String string : data) {
            LinkedList<Character> order = new LinkedList<>();
            long value = 0L;
            for (char bracket : string.toCharArray()) {
                switch (bracket) {
                    case '(':
                    case '[':
                    case '{':
                    case '<':
                        order.addLast(bracket);
                        break;
                    case ')':
                        try {
                            char closing = order.removeLast();
                            if (closing != '(') {
                                value = 3L;
                            }
                        } catch (NoSuchElementException e) {
//                            value = value * 5 + 1;
                        }
                        break;
                    case ']':
                        try {
                            char closing = order.removeLast();
                            if (closing != '[') {
                                value = 57L;
                            }
                        } catch (NoSuchElementException e) {
//                            value = value * 5 + 2;
                        }
                        break;
                    case '}':
                        try {
                            char closing = order.removeLast();
                            if (closing != '{') {
                                value = 1197L;
                            }
                        } catch (NoSuchElementException e) {
//                            value = value * 5 + 3;
                        }
                        break;
                    case '>':
                        try {
                            char closing = order.removeLast();
                            if (closing != '<') {
                                value = 25137L;
                            }
                        } catch (NoSuchElementException e) {
//                            value = value * 5 + 4;
                        }
                        break;
                    default:
                        break;
                }

                if (value != 0) {
                    break;
                }
            }
            acc += value;
            if (value == 0) {
                while (order.size() != 0) {
                    char closing = order.removeLast();
                    switch (closing) {
                        case '(' -> value = value * 5 + 1;
                        case '[' -> value = value * 5 + 2;
                        case '{' -> value = value * 5 + 3;
                        case '<' -> value = value * 5 + 4;
                        default -> {
                        }
                    }
                }
                lineValues.add(value);
            }
        }


        Collections.sort(lineValues);
        System.out.println(lineValues.size() - 1);
        System.out.println((lineValues.size() - 1) / 2);
        System.out.println(lineValues.get((lineValues.size() - 1) / 2));
        System.out.println(acc);
    }
}
