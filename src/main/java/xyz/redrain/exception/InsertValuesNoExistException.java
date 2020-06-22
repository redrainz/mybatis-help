package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class InsertValuesNoExistException extends Exception {

    public InsertValuesNoExistException() {
        super("insert values value is null");
    }

    public InsertValuesNoExistException(String message) {
        super(message);
    }
}
