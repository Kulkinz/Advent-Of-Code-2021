import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(18);

        List<String> data = parser.parseDataStr();
        Pattern MY_PATTERN = Pattern.compile("\\[[a-z0-9],[a-z0-9]\\]");


        List<Number> numberList = new ArrayList<>();

        for (String string : data) {

            Map<Character, Number> currentDirectory = new HashMap<>();
            char reference = 'a';

            while (string.length() > 1) {

                Matcher m = MY_PATTERN.matcher(string);
                StringBuffer sb = new StringBuffer();
                while (m.find()) {
                    String s = m.group(0);
                    m.appendReplacement(sb, "" + reference);
                    String[] split = s.split(",");
                    Number number = new Number();
                    if (currentDirectory.containsKey(split[0].charAt(1))) {
                        number.setLeft(currentDirectory.get(split[0].charAt(1)));
                    } else {
                        number.setRawLeft(Integer.parseInt("" + split[0].charAt(1)));
                    }

                    if (currentDirectory.containsKey(split[1].charAt(0))) {
                        number.setRight(currentDirectory.get(split[1].charAt(0)));
                    } else {
                        number.setRawRight(Integer.parseInt("" + split[1].charAt(0)));
                    }
                    currentDirectory.put(reference, number);
                    reference++;
//                    System.out.println(number);
                }
                m.appendTail(sb);
//                System.out.println(sb.toString());
                string = sb.toString();
            }

            numberList.add(currentDirectory.get(string.charAt(0)));
        }

        Number prevAnswer = null;

        for (Number number : numberList) {
            if (prevAnswer == null) {
                prevAnswer = number.clone();
                continue;
            }
            number = new Number(prevAnswer, number.clone());
            prevAnswer = operator(number);
        }

        System.out.println(prevAnswer);
        System.out.println(magnitude(prevAnswer));

        int acc = 0;
        for (int i = 0; i < numberList.size(); i++) {
            for (int j = i+1; j < numberList.size(); j++) {
                acc = Math.max(acc, magnitude(operator(new Number(numberList.get(i).clone(), numberList.get(j).clone()))));
            }
        }

        for (int i = numberList.size()-1; i >= 0; i--) {
            for (int j = i-1; j >= 0; j--) {
                acc = Math.max(acc, magnitude(operator(new Number(numberList.get(i).clone(), numberList.get(j).clone()))));
            }
        }

        System.out.println(acc);

    }

    public Number operator(Number number) {

        boolean explode = true;
        boolean split = true;
        while (explode || split) {
            explode = explode(number, 0);
            if (!explode) {
                split = split(number);
            }
        }

        return number;
    }

    public int magnitude(Number number) {
        int left;
        int right;
        if (number.left != null) {
            left = magnitude(number.left);
        } else {
            left = number.rawLeft;
        }

        if (number.right != null) {
            right = magnitude(number.right);
        } else {
            right = number.rawRight;
        }

        return 3*left + 2*right;
    }

    public boolean explode(Number number, int depth) {
        if (depth == 4) {
            increaseLeftDown(number, number.rawLeft);
            increaseRightDown(number, number.rawRight);

            if (number.nested.left == number) {
                number.nested.setRawLeft(0);
            } else {
                number.nested.setRawRight(0);
            }

            return true;
        } else {
            return ((number.left != null && explode(number.left, depth + 1)) || (number.right != null && explode(number.right, depth + 1)));
        }
    }

    public void increaseLeftDown(Number number, int amount) {
        Number nested = number.nested;
        if (nested == null) { // gone left down and there is no more numbers to the left
            return;
        }

        // we've gone down the stack
        if (nested.left == null) { // is a number
            nested.setRawLeft(nested.rawLeft + amount);
        } else { // is a pair
            if (nested.left == number) { // we are to the left still, so keep going down
                increaseLeftDown(nested, amount);
            } else { // theres a value now to the left that is a pair, keep going up that value to the right
                increaseRightUp(nested.left, amount);
            }
        }
    }

    public void increaseRightUp(Number number, int amount) {
        if (number.right != null) {
            increaseRightUp(number.right, amount);
        } else {
            number.setRawRight(number.rawRight + amount);
        }
    }

    public void increaseRightDown(Number number, int amount) {
        Number nested = number.nested;
        if (nested == null) { // gone right down and there is no more numbers to the right
            return;
        }

        // we've gone down the stack
        if (nested.right == null) { // is a number
            nested.setRawRight(nested.rawRight + amount);
        } else { // is a pair
            if (nested.right == number) { // we are to the right still, so keep going down
                increaseRightDown(nested, amount);
            } else { // theres a value now to the right that is a pair, keep going up that value to the left
                increaseLeftUp(nested.right, amount);
            }
        }
    }

    public void increaseLeftUp(Number number, int amount) {
        if (number.left != null) {
            increaseLeftUp(number.left, amount);
        } else {
            number.setRawLeft(number.rawLeft + amount);
        }
    }

    public boolean split(Number number) {
        if (number == null) {
            return false;
        }

        if (number.rawLeft >= 10) {
            number.setLeft(new Number((int) Math.floor((double) number.rawLeft / 2), (int) Math.ceil((double) number.rawLeft / 2)));
            return true;
        } else {
            if (split(number.left)) {
                return true;
            } else {
                if (number.rawRight >= 10) {
                    number.setRight(new Number((int) Math.floor((double) number.rawRight / 2), (int) Math.ceil((double) number.rawRight / 2)));
                    return true;
                } else {
                    return split(number.right);
                }
            }
        }
    }

    private class Number {
        Number left = null;
        int rawLeft = -1;
        Number right = null;
        int rawRight = -1;
        Number nested = null;

        public Number(Number left, Number right) {
            setLeft(left);
            setRight(right);
        }

        public Number(int left, Number right) {
            setRawLeft(left);
            setRight(right);
        }

        public Number(Number left, int right) {
            setLeft(left);
            setRawRight(right);
        }

        public Number(int left, int right) {
            setRawLeft(left);
            setRawRight(right);
        }

        public Number() {
        }

        public void setLeft(Number left) {
            this.left = left;
            left.nested = this;
            rawLeft = -1;
        }

        public void setRight(Number right) {
            this.right = right;
            right.nested = this;
            rawRight = -1;
        }

        public void setRawLeft(int rawLeft) {
            this.rawLeft = rawLeft;
            left = null;
        }

        public void setRawRight(int rawRight) {
            this.rawRight = rawRight;
            right = null;
        }


        public Number clone() {
            if (left != null && right != null) {
                return new Number(left.clone(), right.clone());
            } else if (left != null) {
                return new Number(left.clone(), rawRight);
            } else if (right != null) {
                return new Number(rawLeft, right.clone());
            } else {
                return new Number(rawLeft, rawRight);
            }
        }

        @Override
        public String toString() {

            String s = "[";
            if (left != null) {
                s += left.toString();
            } else {
                s += rawLeft;
            }
            s += ",";
            if (right != null) {
                s += right.toString();
            } else {
                s += rawRight;
            }
            s += "]";
            return s;
        }
    }
}
