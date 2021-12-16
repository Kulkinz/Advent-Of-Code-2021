import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class App {

    public App() throws FileNotFoundException {
        coreProcess();
    }

    int versionNumbers;
    /**
     * Runs the core process of solving the problem
     * @throws FileNotFoundException if the data file cannot be found
     */
    private void coreProcess() throws FileNotFoundException {
        versionNumbers = 0;

        Parser parser = new Parser(16);

        List<String> data = parser.parseDataStr();

        for (String string : data) {
            System.out.println(string);
        }

        HashMap<Character, String> hashMap = new HashMap<>();
        hashMap.put('0', "0000");
        hashMap.put('1', "0001");
        hashMap.put('2', "0010");
        hashMap.put('3', "0011");
        hashMap.put('4', "0100");
        hashMap.put('5', "0101");
        hashMap.put('6', "0110");
        hashMap.put('7', "0111");
        hashMap.put('8', "1000");
        hashMap.put('9', "1001");
        hashMap.put('A', "1010");
        hashMap.put('B', "1011");
        hashMap.put('C', "1100");
        hashMap.put('D', "1101");
        hashMap.put('E', "1110");
        hashMap.put('F', "1111");

        String binary = "";
        String hex = data.get(0);

        for (int i = 0; i < hex.length(); i++) {
            char ch = hex.charAt(i);
            binary += hashMap.get(ch);
        }

        System.out.println(binary);

        System.out.println("Part 2: " + handleStart(binary).values[0]);

        System.out.println("Part 1: " + versionNumbers);
    }

    private Grouping handleStart(String binary) {
        String version = binary.substring(0, 3);
        int versionNum = Integer.parseInt(version, 2);
        versionNumbers += versionNum;
        String ID = binary.substring(3,6);
        int IDNum = Integer.parseInt(ID, 2);
        System.out.println("Version: " + versionNum + " ID: " + IDNum);
        if (IDNum == 4) {
            return handleLiteralType(binary.substring(6));
        } else {
            Grouping grouping = handleOperator(binary.substring(6));
            String newBinary = grouping.binary;
            BigInteger value = BigInteger.ZERO;
            switch (IDNum) {
                case 0: // Sum
                    for (BigInteger num : grouping.values) {
                        value = value.add(num);
                    }
                    return new Grouping(newBinary, new BigInteger[]{value});
                case 1: // Product
                    value = BigInteger.ONE;
                    for (BigInteger num : grouping.values) {
                        value = value.multiply(num);
                    }
                    return new Grouping(newBinary, new BigInteger[]{value});
                case 2: // Minimum
                    value = grouping.values[0];
                    for (BigInteger num : grouping.values) {
                        value = value.min(num);
                    }
                    return new Grouping(newBinary, new BigInteger[]{value});
                case 3: // Maximum
                    for (BigInteger num : grouping.values) {
                        value = value.max(num);
                    }
                    return new Grouping(newBinary, new BigInteger[]{value});
                case 5: // Greater than
                    value = grouping.values[0].compareTo(grouping.values[1]) > 0 ? BigInteger.ONE : BigInteger.ZERO;
                    return new Grouping(newBinary, new BigInteger[]{value});
                case 6: // Less than
                    value = grouping.values[0].compareTo(grouping.values[1]) < 0 ? BigInteger.ONE : BigInteger.ZERO;
                    return new Grouping(newBinary, new BigInteger[]{value});
                case 7: // Equals
                    value = grouping.values[0].compareTo(grouping.values[1]) == 0 ? BigInteger.ONE : BigInteger.ZERO;
                    return new Grouping(newBinary, new BigInteger[]{value});
                default:
                    throw new NoSuchElementException();
            }
        }
    }

    private Grouping handleLiteralType(String binary) {
        System.out.println("Literal");
        String acc = "";
        int pointer = 0;
        while (true) {
            int sequence = Integer.parseInt("" + binary.charAt(pointer));

            acc += binary.substring(pointer + 1, pointer + 5);
            pointer += 5;
            if (sequence == 0) {
                break;
            }
        }

        System.out.println("Value: " + Long.parseLong(acc, 2) + "\n");

        return new Grouping(binary.substring(pointer), new BigInteger[]{BigInteger.valueOf(Long.parseLong(acc, 2))});
    }

    private Grouping handleOperator(String binary) {
        System.out.println("Operator");
        int lengthTypeID = Integer.parseInt("" + binary.charAt(0));
        System.out.println("Length Type: " + lengthTypeID);
        if (lengthTypeID == 0) {
            int subPacketLength = Integer.parseInt(binary.substring(1, 16), 2);
            System.out.println("Sub Packet Length: " + subPacketLength + "\n");
            String binarySubPacket = binary.substring(16, 16 + subPacketLength);
            List<BigInteger> values = new ArrayList<>();
            while (!isTrailing(binarySubPacket)) {
                Grouping grouping = handleStart(binarySubPacket);
                binarySubPacket = grouping.binary;
                values.add(grouping.values[0]);
            }

            return new Grouping(binary.substring(16 + subPacketLength), values.toArray(new BigInteger[0]));
        } else {
            int numSubPackets = Integer.parseInt(binary.substring(1,12), 2);
            System.out.println("Number of Sub Packets: " + numSubPackets + "\n");
            String binarySubPacket = binary.substring(12);
            List<BigInteger> values = new ArrayList<>();
            for (int i = 0; i < numSubPackets; i++) {
                Grouping grouping = handleStart(binarySubPacket);
                binarySubPacket = grouping.binary;
                values.add(grouping.values[0]);
            }
            return new Grouping(binarySubPacket, values.toArray(new BigInteger[0]));
        }
    }

    private boolean isTrailing(String binary) {

        return !binary.contains("1");
    }

    private class Grouping {

        String binary;
        BigInteger[] values;

        public Grouping(String binary, BigInteger[] values) {
            this.binary = binary;
            this.values = values;
        }
    }
}
