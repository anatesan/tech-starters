package TechSandbox.util;

import org.slf4j.Logger;

/**
 * Created by ashok.natesan on 12/23/16.
 */
public class RepositoryHelper {
    public static void dumpList(Iterable list, Logger logger, String listName) {
        logger.info("Start of dump for list: " + listName);
        for (Object t:list
             ) {
            logger.info(">>> " + t.toString());
        }
        logger.info("End of dump for list: " + listName);

    }
}
