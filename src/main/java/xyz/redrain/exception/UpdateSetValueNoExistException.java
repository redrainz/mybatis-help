package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class UpdateSetValueNoExistException extends Exception {

    public UpdateSetValueNoExistException() {
        super("update set value is null");
    }

    public UpdateSetValueNoExistException(String message) {
        super(message);
    }
}
