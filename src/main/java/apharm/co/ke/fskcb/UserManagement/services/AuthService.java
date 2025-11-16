package apharm.co.ke.fskcb.UserManagement.services;

import apharm.co.ke.fskcb.AuthModule.services.IAuthService;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.ProfileAndUserEntity;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.SystemRoles;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.UserProfileEntity;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.repositories.IUserAccountsRepository;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.repositories.UserProfilesRepository;
import apharm.co.ke.fskcb.UserManagement.security.JwtTokenProvider;
import apharm.co.ke.fskcb.models.LoginRequest;
import apharm.co.ke.fskcb.models.LookupRequest;
import apharm.co.ke.fskcb.models.req.*;
import apharm.co.ke.fskcb.repositories.ProfilesAndUsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthService implements IAuthService {
    @Value("${app.jwt-expiration-milliseconds}")
    private int JWT_EXPIRY_IN_MILLISECONDS = 30 * 60 * 1000;
    public static int MAX_PIN_LOGIN_ATTEMPTS = 4;
    @Autowired
    ObjectMapper mObjectMapper;
    @Autowired
    private IUserAccountsRepository userAccountRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    ProfilesAndUsersRepository profilesAndUsersRepository;
    @Autowired
    private UserProfilesRepository userProfilesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private java.util.logging.Logger mLogger = Logger.getLogger(getClass().getName());
    @Override
    public LoginResponse attemptLogin(LoginRequest model) {
        try {
            LoginResponse response = new LoginResponse();
            UserAccountEntity account = userAccountRepository.findByStaffNo(model.getStaffNo()).orElse(null);
            if(account == null){
                response.setSuccess(false);
                response.setErrorMessage("Account NOT Found in the system.");
                return response;
            }else if(account.isBlocked() || account.getRemLoginAttempts() <=0){
                response.setSuccess(false);
                response.setRemAttempts(account.getRemLoginAttempts());
                response.setErrorMessage("Account is Blocked.");
                return response;
            }else{
                if(!account.getRoles().stream().allMatch( h ->
                        h.getName().equalsIgnoreCase(SystemRoles.ADMIN) ||
                                h.getName().equalsIgnoreCase(SystemRoles.SYS_ADMIN))) {
                    //
                    response.setSuccess(false);
                    response.setRemAttempts(account.getRemLoginAttempts());
                    response.setErrorMessage("Account is not authorised for this channel");
                    return response;
                }
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(model.getStaffNo(),
                    model.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //get token from token provider
            String token= tokenProvider.generateToken(authentication);

            //get user details from authentication
            UserDetails userDetails=(UserDetails)authentication.getPrincipal();
            String username=userDetails.getUsername();
            //Update Login info ..
            account.setLastLogin(Calendar.getInstance().getTime());
            userAccountRepository.save(account);


            ArrayNode profiles = mObjectMapper.createArrayNode();
            for (ProfileAndUserEntity userAndProfile:
                    profilesAndUsersRepository.findAllByUserId(account.getId())) {
                UserProfileEntity userProfile = userProfilesRepository.findById(userAndProfile.getProfileId()).orElse(null);
                //
                if(userAndProfile != null) {
                    ObjectNode node = mObjectMapper.createObjectNode();
                    node.put("name",userProfile.getName());
                    node.put("code",userProfile.getCode());
                    profiles.add(node);
                }
            }
            //Response
            response.setSuccess(true);
            response.setToken(token);
            response.setIssued(Calendar.getInstance().getTime());
            response.setExpiresInMinutes(JWT_EXPIRY_IN_MILLISECONDS/(1000 * 60));
            response.setProfiles(profiles);
            response.setRm(account.getRoles().stream().allMatch( h ->
                    h.getName().equalsIgnoreCase(SystemRoles.RM)));
            response.setType("Bearer");
            //
//            response.setShouldSetSecQns(securityQuestionAnswersRepo.findAllByUserIdAndStatus(account.getId(),Status.ACTIVE).isEmpty());
            response.setShouldChangePin(account.isShouldChangePIN());
            response.setName(account.getFullName());
            response.setEmail(account.getEmail());
            //
            return response;
        }catch (AuthenticationException ex){
            Optional<UserAccountEntity> optionalUserAccount = userAccountRepository.findByStaffNo(model.getStaffNo());
            if(optionalUserAccount.isPresent()){
                UserAccountEntity userAccount = optionalUserAccount.get();
                userAccount.setRemLoginAttempts(userAccount.getRemLoginAttempts() - 1);
                //
                if(userAccount.getRemLoginAttempts() <= 0){
                    userAccount.setBlocked(true);
                    userAccount.setDateBlocked(Calendar.getInstance().getTime());
                }
                //
                userAccountRepository.save(userAccount);
                //
                LoginResponse response = new LoginResponse();
                response.setSuccess(false);
                response.setRemAttempts(userAccount.getRemLoginAttempts());
                //
                return response;
            }
        }catch (Exception ex){
            //
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    @Secured(SystemRoles.USER)
    public boolean attemptChangePIN(ChangePINRequest model) {

        try {
            //User should be authenticated,,
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            mLogger.log(Level.SEVERE,"CNAME.", username);
//            Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
//            if (principal instanceof UserDetails) {
//                username = ((UserDetails)principal). getUsername();
//            } else {
//                username = principal. toString();
//            }
            //
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,model.getCurrentPIN()));

            //Update Login info ..
            UserAccountEntity account = userAccountRepository.findByStaffNo(username).get();

            account.setPassword(passwordEncoder.encode(model.getNewPIN()));
            userAccountRepository.save(account);//save user to db

            return true;
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,"Change pin/password attempt failed.", ex);
        }
        return false;
    }


    @Override
    public AccountLookupState accountExists(LookupRequest model) {
        return null;
    }

    @Override
    public List<ObjectNode> getLoginLogs() {
        return List.of();
    }

    @Override
    public boolean attemptCreatePIN(CreatePINRequest model) {

        try{
            Optional<UserAccountEntity> optionalUserAccount = userAccountRepository.findByStaffNoAndPhoneNumber(model.getStaffNo(),model.getPhoneNo());

            if(optionalUserAccount.isPresent()){

                UserAccountEntity account = optionalUserAccount.get();
                //if(account.getShouldSetPIN()){
                //
                account.setPassword(passwordEncoder.encode(model.getNewPIN()));
                account.setShouldSetPIN(false);
                account.setRemLoginAttempts(MAX_PIN_LOGIN_ATTEMPTS);
                account.setLastModified(Calendar.getInstance().getTime());
                userAccountRepository.save(account);//save user to db
                return true;
                // }
            }else{
                //User not found ..
            }
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }
        return false;
    }

    @Override
    public boolean attemptChangePassword(ChangePasswordRequest model) {

        try {
            //User should be authenticated,,
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
//            Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
//            if (principal instanceof UserDetails) {
//                username = ((UserDetails)principal). getUsername();
//            } else {
//                username = principal. toString();
//            }
            //
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,model.getCurrentPassword()));

            //Update Login info ..
            UserAccountEntity account = userAccountRepository.findByStaffNo(username).get();

            account.setPassword(passwordEncoder.encode(model.getNewPassword()));
            userAccountRepository.save(account);//save user to db

            return true;
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,"Change pin/password attempt failed.", ex);
        }
        return false;
    }


}
