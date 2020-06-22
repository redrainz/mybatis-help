package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class InsertValuesNoExsitException extends Exception {

    public InsertValuesNoExsitException() {
        super("insert values value is null");
    }

    public InsertValuesNoExsitException(String message) {
        super(message);
    }
}
