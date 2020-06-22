package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class SelectConditionNoExistException extends Exception {

    public SelectConditionNoExistException() {
        super("select condition is null");
    }

    public SelectConditionNoExistException(String message) {
        super(message);
    }
}
