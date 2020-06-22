package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class SelectConditionNoExsitException extends Exception {

    public SelectConditionNoExsitException() {
        super("select condition is null");
    }

    public SelectConditionNoExsitException(String message) {
        super(message);
    }
}
