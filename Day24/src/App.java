import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {

        Parser parser = new Parser(24);

        List<String> data = parser.parseDataStr();
        List<List<InstructionSet>> totalInstructions = new ArrayList<>();
        List<InstructionSet> instructions = new ArrayList<>();

        for (String string : data) {
            String[] split = string.split(" ");
            Instruction instruction;
            switch (split[0]) {
                case "inp":
                    if (instructions.size() != 0) {
                        totalInstructions.add(instructions);
                        instructions = new ArrayList<>();
                    }
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
        totalInstructions.add(instructions);


        Map<Values, Long> correspondingMax = new HashMap<>();
        correspondingMax.put(new Values(0,0,0,0), 0L);

        for (List<InstructionSet> instructionList : totalInstructions) {
            Map<Values, Long> nextCorrespondingMax = new HashMap<>();
            for (Values values : correspondingMax.keySet()) {
//                boolean quickCalculate = false;
//                Values base = null;
                for (int i = 1; i < 10; i++) {
                    Long currentInputTotal = correspondingMax.get(values) * 10 + i;
                    Values resultingValue;
//                    if (quickCalculate) {
//                        if (base == null) {
//                            base = getResultInput(i, values, instructionList);
//                            base.y -= i;
//                            base.z -= i;
//                        }
//                        resultingValue = new Values(base);
//                        resultingValue.y += i;
//                        resultingValue.z += i;
//                    } else {
                        resultingValue = getResultInput(i, values, instructionList);
//                        if (resultingValue.x == 0) {
//                            quickCalculate = true;
//                        }
//                    }
                    if (!nextCorrespondingMax.containsKey(resultingValue)) {
                        nextCorrespondingMax.put(resultingValue, currentInputTotal);
                    } else {
                        if (currentInputTotal > nextCorrespondingMax.get(resultingValue)) {
                            nextCorrespondingMax.put(resultingValue, currentInputTotal);
                        }
                    }
                }
            }
            correspondingMax = nextCorrespondingMax;
        }

        long max = 0;
//        long min = Long.MAX_VALUE;
        for (Map.Entry<Values, Long> pair : correspondingMax.entrySet()) {
            if (pair.getKey().z == 0) {
                max = Math.max(max, pair.getValue());
            }
        }
        System.out.println(max);
    }

    private Values getResultInput(long input, Values values, List<InstructionSet> instructions) {
        Values clone = new Values(values);

        int[] digits = Long.toString(input).chars().map(c -> c-'0').toArray();
        int i = 0;

        for (InstructionSet instructionSet : instructions) {
            switch (instructionSet.instruction) {

                case inp:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            clone.w = digits[i];
                            break;
                        case "x":
                            clone.x = digits[i];
                            break;
                        case "y":
                            clone.y = digits[i];
                            break;
                        case "z":
                            clone.z = digits[i];
                            break;
                    }
                    i++;
                    break;
                case add:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            clone.w += getValue(instructionSet.arguments[1], clone);
                            break;
                        case "x":
                            clone.x += getValue(instructionSet.arguments[1], clone);
                            break;
                        case "y":
                            clone.y += getValue(instructionSet.arguments[1], clone);
                            break;
                        case "z":
                            clone.z += getValue(instructionSet.arguments[1], clone);
                            break;
                    }
                    break;
                case mul:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            clone.w *= getValue(instructionSet.arguments[1], clone);
                            break;
                        case "x":
                            clone.x *= getValue(instructionSet.arguments[1], clone);
                            break;
                        case "y":
                            clone.y *= getValue(instructionSet.arguments[1], clone);
                            break;
                        case "z":
                            clone.z *= getValue(instructionSet.arguments[1], clone);
                            break;
                    }
                    break;
                case div:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            clone.w /= getValue(instructionSet.arguments[1], clone);
                            break;
                        case "x":
                            clone.x /= getValue(instructionSet.arguments[1], clone);
                            break;
                        case "y":
                            clone.y /= getValue(instructionSet.arguments[1], clone);
                            break;
                        case "z":
                            clone.z /= getValue(instructionSet.arguments[1], clone);
                            break;
                    }
                    break;
                case mod:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            clone.w %= getValue(instructionSet.arguments[1], clone);
                            break;
                        case "x":
                            clone.x %= getValue(instructionSet.arguments[1], clone);
                            break;
                        case "y":
                            clone.y %= getValue(instructionSet.arguments[1], clone);
                            break;
                        case "z":
                            clone.z %= getValue(instructionSet.arguments[1], clone);
                            break;
                    }
                    break;
                case eql:
                    switch (instructionSet.arguments[0]) {
                        case "w":
                            clone.w = (clone.w == getValue(instructionSet.arguments[1], clone)) ? 1 : 0;
                            break;
                        case "x":
                            clone.x = (clone.x == getValue(instructionSet.arguments[1], clone)) ? 1 : 0;
                            break;
                        case "y":
                            clone.y = (clone.y == getValue(instructionSet.arguments[1], clone)) ? 1 : 0;
                            break;
                        case "z":
                            clone.z = (clone.z == getValue(instructionSet.arguments[1], clone)) ? 1 : 0;
                            break;
                    }
                    break;
            }
        }

        return clone;
    }

    private long getValue(String string, Values values) {
        switch (string) {
            case "w":
                return values.w;
            case "x":
                return values.x;
            case "y":
                return values.y;
            case "z":
                return values.z;
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

    private class Values {
        int w;
        int x;
        int y;
        int z;

        public Values (int w, int x, int y, int z) {
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Values (Values values) {
            w = values.w;
            x = values.x;
            y = values.y;
            z = values.z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Values values = (Values) o;

            if (x != values.x) return false;
            if (y != values.y) return false;
            return z == values.z;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + z;
            return result;
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
