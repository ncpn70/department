package by.task.service.exception;

import java.util.Objects;

/**
 * Created by simpson on 17.2.17.
 */
public class BadParamException extends RuntimeException {

    protected Object object;

    public BadParamException(String message, Object object){
        super(message);
        this.object = object;
    }

    public Object getObject(){
        return object;
    }
}
