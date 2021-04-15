import logic.LogicalAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Program started.");
        LogicalAgent logicalAgent = new LogicalAgent();
        logicalAgent.run();
    }
}
