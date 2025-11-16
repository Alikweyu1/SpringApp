package apharm.co.ke.fskcb.responses;

import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;

public class BaseAppResponse {
    private int status = 0;
    private String message;
    private Object data;

    public BaseAppResponse(){}

    public BaseAppResponse(int status, Object data, String message){
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static ResponseEntity<?> build(LinkedHashMap<String, Object> responseObject) {
        return ResponseEntity.ok(new BaseAppResponse((int) responseObject.get("status"), responseObject.get("data"), (String) responseObject.get("message")));
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
