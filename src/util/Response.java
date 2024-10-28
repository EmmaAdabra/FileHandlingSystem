package util;

public class Response {
    public Boolean status;
    public String message;
    public Object obj;

    public Response(Boolean status, String message, Object obj) {
        this.status = status;
        this.message = message;
        this.obj = obj;
    }
}
