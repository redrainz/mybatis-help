package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class UpdateSetValueNoExsitException extends Exception {

    public UpdateSetValueNoExsitException() {
        super("update set value is null");
    }

    public UpdateSetValueNoExsitException(String message) {
        super(message);
    }
}
