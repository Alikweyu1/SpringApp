package apharm.co.ke.fskcb.controllers;

import apharm.co.ke.fskcb.responses.BaseAppResponse;
import apharm.co.ke.fskcb.UserManagement.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class LoginController {
    @Autowired
    AuthService authService;
    @PostMapping(value = "/get-all-login-logs")
    public ResponseEntity<?> getAllLoginLogs() {
        List<?> loginRequest = authService.getLoginLogs();
        boolean success = loginRequest != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)loginRequest);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
