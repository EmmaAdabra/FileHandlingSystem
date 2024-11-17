package util;

import java.util.Collections;

public class Response <T>{
    public Boolean status;
    public String message;
    public T obj;

    public Response(Boolean status, String message, T obj) {
        this.status = status;
        this.message = message;
        this.obj = obj;
    }
}
