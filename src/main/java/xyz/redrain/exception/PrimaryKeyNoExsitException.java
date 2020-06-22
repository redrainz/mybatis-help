package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class PrimaryKeyNoExsitException extends Exception {

    public PrimaryKeyNoExsitException() {
        super("primary key is null");
    }

    public PrimaryKeyNoExsitException(String message) {
        super(message);
    }
}
