package apharm.co.ke.fskcb.UserManagement.services;

import apharm.co.ke.fskcb.AuthModule.services.ISmsService;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.AccountType;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.SystemRoles;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.UserRoleEntity;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.repositories.IUserAccountsRepository;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.repositories.RoleRepository;
import apharm.co.ke.fskcb.UserManagement.payload.AddAdminUserRequest;
import apharm.co.ke.fskcb.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Slf4j
@Transactional
public class UsersService implements IUsersService {
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);
    @Autowired
    private IUserAccountsRepository userAccountsRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ISmsService smsService;
    @Override
    public boolean attemptCreateUser(AddAdminUserRequest model, AccountType type, boolean verified) {

        try {

            if (!userAccountsRepository.findByStaffNo(model.getStaffNo()).isPresent()) {

                //
                String password = Utility.generatePIN();
                //
                UserAccountEntity account = new UserAccountEntity();
                account.setPhoneNumber(model.getPhoneNo());
                account.setAccountType(type);
                account.setFullName(model.getFullName());
                account.setEmail(model.getEmail());
                account.setStaffNo(model.getStaffNo());
                account.setIsVerified(verified);
                //
//                2783
                logger.info("GENERATED PASSWORD: {}", password);
                account.setPassword(passwordEncoder.encode(password));
                //userRepository.save(account);//save user to db
                //

                if (type == AccountType.ADMIN) {
                    //CAN ACCESS PORTAL
//                    UserRoleEntity userRole = roleRepository.findByName(SystemRoles.ADMIN).get();//get role from db
//                    account.setRoles(Collections.singleton(userRole));//set role to user
                    userAccountsRepository.save(account);//save user to db

                    //
                    if (smsService.sendPasswordEmail(account.getEmail(), account.getFullName(), password, account.getStaffNo())) {
                        //
                        log.info("OTP send via EMAIL " + account.getEmail());
                        //return true;
                    } else if (smsService.sendPasswordSMS(account.getPhoneNumber(), account.getFullName(), password, account.getStaffNo())) {
                        //
                        log.info("OTP send via PhoneNo " + account.getPhoneNumber());
                    } else {

                    }
                } else {
                    //DSR Account..
                    UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db
                    account.setRoles(Collections.singleton(userRole));//set role to user
//                    userAccountsRepository.save(account);//save user to db
                    if (model.getIsRm()) {
                        userRole = roleRepository.findByName(SystemRoles.RM).get();//get role from db
                        account.setRoles(Collections.singleton(userRole));//set role to user
                        //save user to db

                    }
                    userAccountsRepository.save(account);

                }
                //
                return true;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return false;
    }
}
