package apharm.co.ke.fskcb.UserManagement.services;

import apharm.co.ke.fskcb.AuthModule.services.ISmsService;
import apharm.co.ke.fskcb.AuthModule.services.datasource.entities.AuthCodeType;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.repositories.IUserAccountsRepository;
import apharm.co.ke.fskcb.repositories.IDSRAccountsRepository;
import apharm.co.ke.fskcb.repositories.ISecurityAuthCodesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@Slf4j
public class SMSService implements ISmsService {

    @Value("CMCOM")
    private String SMS_SENDER_ID;
    //
    @Value("ECLECTICS")
    private String SMS_SENDER_ID1;
    @Value("5094")
    private String client_id;
    @Value("https://testgateway.ekenya.co.ke:8443/ServiceLayer/pgsms/send")
    private String SMS_GATEWAY_URL;
    @Value("janty")
    private String SMS_USER_NAME;
    @Value("b0c95e2144bdd4c86b94501a814f9bbd9d025651d8497df04b7b7f318fe5172088c491906756a67727f6ea964e9caf1c034bf9bb267b821e6b43cb3dcc569d0f")
    private String SMS_PASSWORD;
    @Value("30")
    private String otpExpiresIn;

    @Autowired
    ISecurityAuthCodesRepository securityAuthCodesRepository;
    @Autowired
    private IUserAccountsRepository IUserAccountsRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    IDSRAccountsRepository dsrAccountsRepository;
    private final static Logger logger = Logger.getLogger(SMSService.class.getName());
    @Override
    public String sendSecurityCode(String staffNo, AuthCodeType type) {
        return "";
    }

    @Override
    public boolean sendPasswordEmail(String receiverEmail, String fullName, String password, String staffNumber) {
        return false;
    }

    @Override
    public boolean sendPasswordSMS(String phoneNo, String fullName, String password, String staffNo) {
        return false;
    }

    @Override
    public boolean sendDSRCreatedSMS(String phoneNo, String fullName) {
        return false;
    }

    @Override
    public boolean sendDsrCreatedEmail(String receiverEmail, String fullName) {
        return false;
    }
}
