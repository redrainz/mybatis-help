package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class ParamClassIsNullException extends Exception {

    public ParamClassIsNullException() {
        super("param class is null");
    }

    public ParamClassIsNullException(String message) {
        super(message);
    }
}
