package apharm.co.ke.fskcb.UserManagement.controllers;

import apharm.co.ke.fskcb.AuthModule.services.IAuthService;
import apharm.co.ke.fskcb.AuthModule.services.datasource.entities.LoginLogs;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.AccountType;
import apharm.co.ke.fskcb.UserManagement.payload.AddAdminUserRequest;
import apharm.co.ke.fskcb.UserManagement.services.IUsersService;
import apharm.co.ke.fskcb.models.LoginRequest;
import apharm.co.ke.fskcb.models.req.ChangePINRequest;
import apharm.co.ke.fskcb.models.req.CreatePINRequest;
import apharm.co.ke.fskcb.models.req.LoginResponse;
import apharm.co.ke.fskcb.repositories.LoginLogsRepository;
import apharm.co.ke.fskcb.responses.BaseAppResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.Date;

@Api(value = "Upload User  Rest Api")
@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1")
public class UsersVC {
    @Autowired
    ObjectMapper mObjectMapper;

    @Autowired
    IUsersService usersService;
    @Autowired
    private IAuthService authService;

    @Autowired
    DateFormat dateFormat;
    @Autowired
    LoginLogsRepository loginLogsRepository;

    @PostMapping("/users-create-user")
    public ResponseEntity<?> createAdminUser(@RequestBody AddAdminUserRequest request ) {
        //TODO; INSIDE SERVICE
        boolean success = usersService.attemptCreateUser(request, AccountType.ADMIN, false);
        if(success){
            //Object
            ObjectNode node = mObjectMapper.createObjectNode();

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/signin")
    @ApiOperation(value = "Login Api")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        LoginResponse resp = authService.attemptLogin(request);
        if(resp != null) {
            if(resp.isSuccess()){
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("token",resp.getToken());
                node.put("type",resp.getType());
                node.put("name",resp.getName());
                node.put("email",resp.getEmail());
                node.put("sales_code",resp.getSalesCode());
                node.put("team_name",resp.getTeamName());
                node.put("team_code",resp.getTeamCode());
                node.put("isRm",resp.isRm()? 1:0);
                node.put("issued",dateFormat.format(resp.getIssued()));
                node.put("expires_in",resp.getExpiresInMinutes());
                node.putPOJO("profiles",resp.getProfiles());
                loginTrail(resp.getName(),true,"portal");
                return ResponseEntity.ok(new BaseAppResponse(1,node,"User login successful"));
            }else if(resp.getErrorMessage() != null){

                return ResponseEntity.ok(new BaseAppResponse(1,mObjectMapper.createObjectNode(),resp.getErrorMessage()));
            }
        }
        return ResponseEntity.ok(new BaseAppResponse(1,mObjectMapper.createObjectNode(),"User login failed"));
    }
    @PostMapping("/create-pin")
    @ApiOperation(value = "Create PIN Api")
    public ResponseEntity<?> createPIN(@RequestBody CreatePINRequest request){

        boolean success = authService.attemptCreatePIN(request);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){

            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"User PIN created successful"));
        }
        return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }
    @PostMapping("/change-pin")
    @ApiOperation(value = "Change PIN Api")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePIN(@RequestBody ChangePINRequest request){

        boolean success = authService.attemptChangePIN(request);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //
            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"User PIN changed successful"));
        }
        return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }
    public  void loginTrail(String username, boolean loginStatus, String channel) {
        LoginLogs loginTrailEntity = new LoginLogs();
        loginTrailEntity.setFullName(username);
//        loginTrailEntity.setIpAddress(ipAddress);
        loginTrailEntity.setSuccessful(loginStatus);
        loginTrailEntity.setChannel(channel);
//        loginTrailEntity.setProfileCode(profiles);
//        loginTrailEntity.setLoginMessage(loginMessage);
        loginTrailEntity.setLoginDate(new Date());
        loginLogsRepository.save(loginTrailEntity);
    }
    }
