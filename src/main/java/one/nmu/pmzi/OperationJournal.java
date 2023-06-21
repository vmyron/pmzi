package one.nmu.pmzi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OperationJournal implements Journal {

    private static final Logger logger = LogManager.getLogger(OperationJournal.class);

    public void about() {
        logger.info(getMessage(SystemEvent.ABOUT));
    }

    public void addUser() {
        logger.info(getMessage(SystemEvent.ADD_USER));
    }

    public void editUser() {
        logger.info(getMessage(SystemEvent.EDIT_USER));
    }

    public void listUsers() {
        logger.info(getMessage(SystemEvent.LIST_USERS));
    }

    public void changePassword() {
        logger.info(getMessage(SystemEvent.PASSWORD_CHANGE));
    }

    public void setPassword() {
        logger.info(getMessage(SystemEvent.PASSWORD_SET));
    }

    public void wrongPassword() {
        logger.info(getMessage(SystemEvent.INCORRECT_PASSWORD));
    }
}
