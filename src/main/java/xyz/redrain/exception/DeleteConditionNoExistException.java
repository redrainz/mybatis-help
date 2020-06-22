package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class DeleteConditionNoExistException extends Exception {

    public DeleteConditionNoExistException() {
        super("delete condition is null");
    }

    public DeleteConditionNoExistException(String message) {
        super(message);
    }
}
