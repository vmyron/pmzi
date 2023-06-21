package one.nmu.pmzi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationJournal implements Journal {
    private static final Logger logger = LogManager.getLogger(RegistrationJournal.class);

    public void logIn() {
        logger.info(getMessage(SystemEvent.LOGIN));
    }

    public void logOut() {
        logger.info(getMessage(SystemEvent.LOGOUT));
    }
}
