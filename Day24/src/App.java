import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    long w = 0;
    long x = 0;
    long y = 0;
    long z = 1;

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(24);

        List<String> data = parser.parseDataStr();

        List<InstructionSet> instructions = new ArrayList<>();

        for (String string : data) {
            String[] split = string.split(" ");
            Instruction instruction;
            switch (split[0]) {
                case "inp":
                    instruction = Instruction.inp;
                    break;
                case "add":
                    instruction = Instruction.add;
                    break;
                case "mul":
                    instruction = Instruction.mul;
                    break;
                case "div":
                    instruction = Instruction.div;
                    break;
                case "mod":
                    instruction = Instruction.mod;
                    break;
                case "eql":
                    instruction = Instruction.eql;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + split[0]);
            }

            instructions.add(new InstructionSet(instruction, string.substring(4).split(" ")));
        }


//        Collections.reverse(instructions);

        ArrayList<Long> integers = new ArrayList<>();

        // 99999999999939L 7627591
        // 99999999999899L 7627583
        // 99999999999839L 293368
        // 99999999989839L 11283
        // 99999969989839L 11273
        // 99999569989839L 433
        long input = 99999569989839L;

        w = 0;
        x = 0;
        y = 0;
        z = 0;

        int[] digits = Long.toString(input).chars().map(c -> c-'0').toArray();
        int i = 0;

        for (InstructionSet instructionSet : instructions) {
            switch (instructionSet.instruction) {

                case inp:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            w = digits[i];
                            break;
                        case "x":
                            x = digits[i];
                            break;
                        case "y":
                            y = digits[i];
                            break;
                        case "z":
                            z = digits[i];
                            break;
                    }
                    i++;
                    break;
                case add:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            w += getValue(instructionSet.arguments[1]);
                            break;
                        case "x":
                            x += getValue(instructionSet.arguments[1]);
                            break;
                        case "y":
                            y += getValue(instructionSet.arguments[1]);
                            break;
                        case "z":
                            z += getValue(instructionSet.arguments[1]);
                            break;
                    }
                    break;
                case mul:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            w *= getValue(instructionSet.arguments[1]);
                            break;
                        case "x":
                            x *= getValue(instructionSet.arguments[1]);
                            break;
                        case "y":
                            y *= getValue(instructionSet.arguments[1]);
                            break;
                        case "z":
                            z *= getValue(instructionSet.arguments[1]);
                            break;
                    }
                    break;
                case div:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            w /= getValue(instructionSet.arguments[1]);
                            break;
                        case "x":
                            x /= getValue(instructionSet.arguments[1]);
                            break;
                        case "y":
                            y /= getValue(instructionSet.arguments[1]);
                            break;
                        case "z":
                            z /= getValue(instructionSet.arguments[1]);
                            break;
                    }
                    break;
                case mod:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            w %= getValue(instructionSet.arguments[1]);
                            break;
                        case "x":
                            x %= getValue(instructionSet.arguments[1]);
                            break;
                        case "y":
                            y %= getValue(instructionSet.arguments[1]);
                            break;
                        case "z":
                            z %= getValue(instructionSet.arguments[1]);
                            break;
                    }
                    break;
                case eql:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            w = (w == getValue(instructionSet.arguments[1])) ? 1 : 0;
                            break;
                        case "x":
                            x = (x == getValue(instructionSet.arguments[1])) ? 1 : 0;
                            break;
                        case "y":
                            y = (y == getValue(instructionSet.arguments[1])) ? 1 : 0;
                            break;
                        case "z":
                            z = (z == getValue(instructionSet.arguments[1])) ? 1 : 0;
                            break;
                    }
                    break;
            }
        }

//        for (InstructionSet instructionSet : instructions) {
//            switch (instructionSet.instruction) {
//
//                case inp:
//                    switch (instructionSet.arguments[0]) {
//                        case "w":
//                            integers.add(w);
//                            break;
//                        case "x":
//                            integers.add(x);
//                            break;
//                        case "y":
//                            integers.add(y);
//                            break;
//                        case "z":
//                            integers.add(z);
//                            break;
//                    }
//                    break;
//                case add:
//                    switch (instructionSet.arguments[0]) {
//                        case "w":
//                            w -= getValue(instructionSet.arguments[1]);
//                            break;
//                        case "x":
//                            x -= getValue(instructionSet.arguments[1]);
//                            break;
//                        case "y":
//                            y -= getValue(instructionSet.arguments[1]);
//                            break;
//                        case "z":
//                            z -= getValue(instructionSet.arguments[1]);
//                            break;
//                    }
//                    break;
//                case mul:
//                    switch (instructionSet.arguments[0]) {
//                        case "w":
//                            w /= getValue(instructionSet.arguments[1]);
//                            break;
//                        case "x":
//                            x /= getValue(instructionSet.arguments[1]);
//                            break;
//                        case "y":
//                            y /= getValue(instructionSet.arguments[1]);
//                            break;
//                        case "z":
//                            z /= getValue(instructionSet.arguments[1]);
//                            break;
//                    }
//                    break;
//                case div:
//                    break;
//                case mod:
//                    break;
//                case eql:
//                    break;
//            }
//        }
//
//        Collections.reverse(integers);

        System.out.println(w);
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
    }

    private long getValue(String string) {
        switch (string) {
            case "w":
                return w;
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                return Long.parseLong(string);
        }
    }

    private class InstructionSet {
        Instruction instruction;
        String[] arguments;

        public InstructionSet(Instruction instruction, String[] arguments) {
            this.instruction = instruction;
            this.arguments = arguments;
        }
    }

    private enum Instruction {
        inp,
        add,
        mul,
        div,
        mod,
        eql
    }
}
