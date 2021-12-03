import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
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

        Parser parser = new Parser(3);

        List<String> data = parser.parseDataStr();

        List<boolean[]> values = new ArrayList<>();

        int binaryLength = data.get(0).toCharArray().length;

        for (String string : data) {
            boolean[] booleans = new boolean[binaryLength];
            char[] chars = string.toCharArray();
            for (int i = 0; i < binaryLength; i++) {
                if (chars[i] == '1') {
                    booleans[i] = true;
                }
            }
            values.add(booleans);
        }

        int total = data.size();
        int[] ints = new int[binaryLength];

        for (boolean[] booleans : values) {
            for (int i = 0; i < binaryLength; i++) {
                if (booleans[i]) {
                    ints[i]++;
                }
            }
        }

        String finalVal = "";

        for (int i = 0; i < binaryLength; i++) {
            if (ints[i] > total/2) {
                finalVal += 1;
            } else {
                finalVal += 0;
            }
        }

        System.out.println(finalVal);

        int gamma = Integer.parseInt(finalVal,2);
        int epsilon = (int) Math.pow(2, binaryLength) - 1 - gamma;

        System.out.println(gamma);
        System.out.println(epsilon);
        System.out.println(gamma * epsilon);


        List<boolean[]> valuesOxy = new ArrayList<>();
        List<boolean[]> valuesCO2 = new ArrayList<>();

        for (boolean[] copy : values) {
            valuesOxy.add(copy.clone());
            valuesCO2.add(copy.clone());
        }

        Iterator<boolean[]> oxyIterator;
        Iterator<boolean[]> cO2Iterator;

        for (int i = 0; i < binaryLength; i++) {
            int count = 0;
            oxyIterator = valuesOxy.iterator();
            while (oxyIterator.hasNext()) {
                boolean[] next = oxyIterator.next();
                if (next[i]) {
                    count++;
                }
            }
            boolean lookingFor = ((double) count >= ((double) valuesOxy.size())/2);
            oxyIterator = valuesOxy.iterator();
            while (oxyIterator.hasNext()) {
                boolean[] next = oxyIterator.next();
                if (next[i] != lookingFor) {
                    oxyIterator.remove();
                }
            }
            if (valuesOxy.size() == 1) {
                break;
            }
        }

        String finalValOxy = "";

        System.out.println(valuesOxy.toString());

        for (int i = 0; i < binaryLength; i++) {
            if (valuesOxy.get(0)[i]) {
                finalValOxy += 1;
            } else {
                finalValOxy += 0;
            }
        }

        System.out.println(finalValOxy);

        int oxy = Integer.parseInt(finalValOxy,2);

        for (int i = 0; i < binaryLength; i++) {
            int count = 0;
            cO2Iterator = valuesCO2.iterator();
            while (cO2Iterator.hasNext()) {
                boolean[] next = cO2Iterator.next();
                if (next[i]) {
                    count++;
                }
            }
            boolean lookingFor = ((double) count < ((double) valuesCO2.size())/2);
            cO2Iterator = valuesCO2.iterator();
            while (cO2Iterator.hasNext()) {
                boolean[] next = cO2Iterator.next();
                if (next[i] != lookingFor) {
                    cO2Iterator.remove();
                }
            }
            if (valuesCO2.size() == 1) {
                break;
            }
        }

        String finalValCO2 = "";

        for (int i = 0; i < binaryLength; i++) {
            if (valuesCO2.get(0)[i]) {
                finalValCO2 += 1;
            } else {
                finalValCO2 += 0;
            }
        }

        System.out.println(finalValCO2);

        int co2 = Integer.parseInt(finalValCO2,2);

        System.out.println(oxy);
        System.out.println(co2);
        System.out.println(oxy * co2);

     }
}
