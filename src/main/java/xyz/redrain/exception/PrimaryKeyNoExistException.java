package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class PrimaryKeyNoExistException extends Exception {

    public PrimaryKeyNoExistException() {
        super("primary key is null");
    }

    public PrimaryKeyNoExistException(String message) {
        super(message);
    }
}
