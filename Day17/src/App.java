import java.io.FileNotFoundException;
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

        Parser parser = new Parser(17);

        List<String> data = parser.parseDataStr();

        int targetXMax = 0, targetXMin = 0, targetYMax = 0, targetYMin = 0;

        for (String string : data) {
            String[] split = string.split(" ");
            String[] xSplit = split[2].substring(2, split[2].length() - 1).split("\\.\\.");
            targetXMin = Integer.parseInt(xSplit[0]);
            targetXMax = Integer.parseInt(xSplit[1]);
            String[] ySplit = split[3].substring(2).split("\\.\\.");
            targetYMin = Integer.parseInt(ySplit[0]);
            targetYMax = Integer.parseInt(ySplit[1]);
        }

        System.out.println(targetXMax);
        System.out.println(targetXMin);
        System.out.println(targetYMax);
        System.out.println(targetYMin);

        int minVelocityX = 0;

        int velX;
        int velY;
        int posX;
        int posY;

        while (true) {
            int sum = minVelocityX * (minVelocityX+1) / 2;
            if (sum <= targetXMax && sum >= targetXMin) {
                break; // Absolute minimum velocity to enter
            }
            minVelocityX++;
        }

        int overallMax = 0;
        int maxVelocityY = 0;

        for (int i = 0; i < 30000; i++) {
            int currentMax = i * (i + 1) / 2;
            posY = currentMax;
            velY = 0;

            while (!(posY <= targetYMax && posY >= targetYMin)) {

                posY += velY;
                velY -= 1;

                if (posY <= targetYMax && posY >= targetYMin) {
                    overallMax = Math.max(overallMax, currentMax);
                    maxVelocityY = Math.max(maxVelocityY, i);
                }

                if (posY < targetYMin) {
                    break;
                }
            }
        }

        System.out.println(minVelocityX);
        System.out.println(maxVelocityY);
        System.out.println(overallMax); // Part 1

        // Max x velocity is targetXMax, min y velocity is targetYMin

        int acc = 0;

        for (int x = minVelocityX; x <= targetXMax; x++) {
            for (int y = targetYMin; y <= maxVelocityY; y++) {

                velX = x;
                velY = y;
                posX = 0;
                posY = 0;

                while (!(posX <= targetXMax && posX >= targetXMin && posY <= targetYMax && posY >= targetYMin)) {

                    posX += velX;
                    posY += velY;
                    velX = (velX > 0) ? velX - 1 : velX;
                    velY -= 1;

                    if (posX <= targetXMax && posX >= targetXMin && posY <= targetYMax && posY >= targetYMin) {
                        acc++;
//                        System.out.println(x+","+y);
                    }

                    if (posY < targetYMin || posX > targetXMax) {
                        break;
                    }
                }

            }
        }

        System.out.println(acc);


    }
}
