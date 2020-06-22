package xyz.redrain.exception;

/**
 * Created by RedRain on 2020/6/21.
 *
 * @author RedRain
 * @version 1.0
 */

public class PageParamIsNullException extends Exception {

    public PageParamIsNullException() {
        super("page param is null");
    }

    public PageParamIsNullException(String message) {
        super(message);
    }
}
