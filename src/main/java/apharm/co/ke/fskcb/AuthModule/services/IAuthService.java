package apharm.co.ke.fskcb.AuthModule.services;

import apharm.co.ke.fskcb.models.LoginRequest;
import apharm.co.ke.fskcb.models.LookupRequest;
import apharm.co.ke.fskcb.models.req.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IAuthService {

    LoginResponse attemptLogin(LoginRequest model);
    boolean attemptChangePIN(ChangePINRequest model);


    AccountLookupState accountExists(LookupRequest model);



    List<ObjectNode> getLoginLogs();
    boolean attemptCreatePIN(CreatePINRequest model);
    boolean attemptChangePassword(ChangePasswordRequest model);



}
