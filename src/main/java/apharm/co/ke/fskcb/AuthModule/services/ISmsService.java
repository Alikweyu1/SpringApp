package apharm.co.ke.fskcb.AuthModule.services;

import apharm.co.ke.fskcb.AuthModule.services.datasource.entities.AuthCodeType;

public interface ISmsService {
    String sendSecurityCode(String staffNo, AuthCodeType type);

    boolean sendPasswordEmail(String receiverEmail,String fullName,String password,String staffNumber);

    boolean sendPasswordSMS(String phoneNo,String fullName,  String password,String staffNo);
    boolean sendDSRCreatedSMS(String phoneNo,String fullName);


    boolean sendDsrCreatedEmail(String receiverEmail,String fullName);

}
