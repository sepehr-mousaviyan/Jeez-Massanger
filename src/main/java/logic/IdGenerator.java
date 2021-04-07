package logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IdGenerator {

    private static final Logger logger = LogManager.getLogger(IdGenerator.class);

    public static int generate() {
        String path = "src/main/resources/ID";
        File file = new File(path);
        int cur = 0;
        try {
            Scanner scanner = new Scanner(file);
            cur = scanner.nextInt();
            scanner.close();
        }
        catch (Exception e) {
            logger.fatal("Problem with opening the file", e);
        }
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(file, false));
            printStream.print(cur + 1);
            printStream.flush();
            printStream.close();
            return cur + 1;
        }
        catch (Exception e) {
            logger.fatal("Problem with writing in the file.", e);
        }
        return cur + 1;
    }

}
