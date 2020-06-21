package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class IdNoExsitException extends Exception {

    public IdNoExsitException() {
        super("id is null");
    }

    public IdNoExsitException(String message) {
        super(message);
    }
}
