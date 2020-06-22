package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class DeleteConditionNoExsitException extends Exception {

    public DeleteConditionNoExsitException() {
        super("delete condition is null");
    }

    public DeleteConditionNoExsitException(String message) {
        super(message);
    }
}
