import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        Parser parser = new Parser(6);

        List<Integer> data = parser.parseLineInteger();

        HashMap<Integer, Integer> fishList = new HashMap<>();

        Map<Object, Long> counts = data.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        for (int i = 0; i <= 8; i++) {
            if (!counts.containsKey(i)) {
                counts.put(i, 0L);
            }
        }

        for (int i = 1; i <= 256; i++) {

            Long fish = counts.get(0);
            counts.put(0, counts.get(1));
            counts.put(1, counts.get(2));
            counts.put(2, counts.get(3));
            counts.put(3, counts.get(4));
            counts.put(4, counts.get(5));
            counts.put(5, counts.get(6));
            counts.put(6, counts.get(7) + fish);
            counts.put(7, counts.get(8));
            counts.put(8, fish);
        }

        Long acc = 0L;

        for (Long amount : counts.values()) {
            acc += amount;
        }

        System.out.println(acc);

//        for (int value : parser.parseLineInteger()) { // OLD PART 1 solution
//            data.add(new Fish(value));
//        }
//
////        System.out.println(data);
//
//        for (int i = 1; i <= 80; i++) {
//            ListIterator<Fish> li = data.listIterator();
//
//            while (li.hasNext()) {
//                Fish value = li.next();
//                if (value.state == 0) {
//                    value.state = 6;
//                    li.add(new Fish());
//                } else {
//                    value.state--;
//                }
//            }
////            System.out.println(i + " " + data);
//        }

//        System.out.println(data.size());
    }

//    private class Fish {
//        int state;
//
//        public Fish() {
//            state = 8;
//        }
//
//        public Fish(int starting) {
//            state = starting;
//        }
//
//        @Override
//        public String toString() {
//            return ""+state;
//        }
//    }
}
