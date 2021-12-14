import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
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

        Parser parser = new Parser(14);

        List<String> data = parser.parseDataStr();

        String sequence = data.remove(0);
        data.remove(0);

        Map<String, Character> instructions = new HashMap<>();
        Map<Character, Long> characterCount = new HashMap<>();

        for (String string : data) {
            String[] split = string.split(" -> ");
            instructions.put(split[0], split[1].charAt(0));
        }

        Molecule pointer = null;
        Molecule start = null;
        for (char letter : sequence.toCharArray()) {
            if (characterCount.containsKey(letter)) {
                characterCount.put(letter, characterCount.get(letter) + 1);
            } else {
                characterCount.put(letter, 1L);
            }

            Molecule molecule = new Molecule(letter);
            if (start == null) {
                start = molecule;
            }
            if (pointer != null) {
                pointer.next = molecule;
            }
            pointer = molecule;
        }

        Map<Molecule, Character> queue = new HashMap<>();

        for (int i = 1; i <= 10; i++) {
            queue.clear();

            for (Molecule mole : start) {
                String instruction = mole.getSequence();
                if (instructions.containsKey(instruction)) {
                    char c = instructions.get(instruction);

                    if (characterCount.containsKey(c)) {
                        characterCount.put(c, characterCount.get(c) + 1);
                    } else {
                        characterCount.put(c, 1L);
                    }
                    queue.put(mole, c);
                }
            }

            for (Map.Entry<Molecule, Character> pair : queue.entrySet()) {
                pair.getKey().setNext(new Molecule(pair.getValue()));
            }
        }

        long max = 0L;
        long min = Long.MAX_VALUE;
        for (long value : characterCount.values()) {
            max = Math.max(max, value);
            min = Math.min(min, value);
        }

        System.out.println("Part 1: " + (max - min)); // Part 1

        HashMap<String, Long> combinations = new HashMap<>();

        characterCount.clear();

        char prev = ' ';
        for (char letter : sequence.toCharArray()) {

            if (characterCount.containsKey(letter)) {
                characterCount.put(letter, characterCount.get(letter) + 1);
            } else {
                characterCount.put(letter, 1L);
            }

            if (prev != ' ') {
                String combination = "" + prev + letter;
                if (combinations.containsKey(combination)) {
                    combinations.put(combination, combinations.get(combination) + 1);
                } else {
                    combinations.put(combination, 1L);
                }
            }

            prev = letter;
        }

        for (int i = 1; i <= 40 ; i++) {

            HashMap<String, Long> copy = new HashMap<>();
            for (String combination : combinations.keySet()) {
                if (instructions.containsKey(combination)) {
                    char c = instructions.get(combination);

                    String result1 = "" + combination.charAt(0) + c;
                    String result2 = "" + c + combination.charAt(1);

                    if (characterCount.containsKey(c)) {
                        characterCount.put(c, characterCount.get(c) + combinations.get(combination));
                    } else {
                        characterCount.put(c, combinations.get(combination));
                    }

                    if (copy.containsKey(result1)) {
                        copy.put(result1, copy.get(result1) + combinations.get(combination));
                    } else {
                        copy.put(result1, combinations.get(combination));
                    }

                    if (copy.containsKey(result2)) {
                        copy.put(result2, copy.get(result2) + combinations.get(combination));
                    } else {
                        copy.put(result2, combinations.get(combination));
                    }
                }
            }

            combinations = copy;
        }

        max = 0L;
        min = Long.MAX_VALUE;
        for (long value : characterCount.values()) {
            max = Math.max(max, value);
            min = Math.min(min, value);
        }

        System.out.println("Part 2: " + (max - min));


    }

    private class Molecule implements Iterable<Molecule> {
        char element;
        Molecule next;
        Molecule prev;

        public Molecule(char name) {
            element = name;
            next = null;
            prev = null;
        }

        public String getSequence() {
            if (next == null) {
                return "";
            } else {
                return "" + element + next.element;
            }
        }

        public void setNext(Molecule next) {
            if (this.next != next) {
                Molecule temp = this.next;
                this.next = next;
                if (temp != null) {
                    temp.setPrev(next);
                }
                next.setPrev(this);
            }
        }

        public void setPrev(Molecule prev) {
            if (this.prev != prev) {
                Molecule temp = this.prev;
                this.prev = prev;
                if (temp != null) {
                    temp.setNext(prev);
                }
                prev.setNext(this);
            }
        }

        @Override
        public Iterator<Molecule> iterator() {
            return new MoleculeIterator(this);
        }

        public class MoleculeIterator implements Iterator<Molecule> {

            Molecule pointer;

            public MoleculeIterator(Molecule start) {
                pointer = start;
            }

            @Override
            public boolean hasNext() {
                return pointer != null;
            }

            @Override
            public Molecule next() {
                Molecule returnMolecule = pointer;
                pointer = pointer.next;
                return returnMolecule;
            }
        }
    }
}
