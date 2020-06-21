package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class ParamIsNullException extends Exception {

    public ParamIsNullException() {
        super("param is null");
    }

    public ParamIsNullException(String message) {
        super(message);
    }
}
