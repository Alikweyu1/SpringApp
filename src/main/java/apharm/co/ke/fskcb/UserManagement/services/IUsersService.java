package apharm.co.ke.fskcb.UserManagement.services;

import apharm.co.ke.fskcb.UserManagement.datasource.entities.AccountType;
import apharm.co.ke.fskcb.UserManagement.payload.AddAdminUserRequest;

public interface IUsersService {



    boolean attemptCreateUser(AddAdminUserRequest model, AccountType type, boolean verified);


}
