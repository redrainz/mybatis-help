package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class DuplicatePrimaryKeyException extends Exception {

    public DuplicatePrimaryKeyException() {
        super("duplicate primary key");
    }

    public DuplicatePrimaryKeyException(String message) {
        super(message);
    }
}
