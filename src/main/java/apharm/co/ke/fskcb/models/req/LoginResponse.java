package apharm.co.ke.fskcb.models.req;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class LoginResponse {

    private boolean shouldSetSecQns = true;
    private boolean shouldChangePin = false;
    private boolean success = false;
    private boolean isRm = false;
    private int remAttempts = 3;
    private String token;
    private String type;
    private Date issued;
    private int expiresInMinutes = 30;
    private List<String> roles = new ArrayList<>();

    private String errorMessage;
    private boolean expired = false;
    private ArrayNode profiles;
    private String name;
    private String email;
    private String salesCode;
    private Long dsrId;
    private String teamName;
    private String teamCode;
}
