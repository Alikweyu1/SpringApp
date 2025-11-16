package apharm.co.ke.fskcb.UserManagement.datasource.entities.repositories;


import apharm.co.ke.fskcb.UserManagement.datasource.entities.AccountType;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import apharm.co.ke.fskcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IUserAccountsRepository extends JpaRepository<UserAccountEntity, Long> {
    //
    Optional<UserAccountEntity> findByIdAndStatus(Long id, Status status);
    List<UserAccountEntity> findAllByAccountTypeAndStatus(AccountType type, Status status);
    Optional<UserAccountEntity> findByStaffNoAndPhoneNumber(String staffNo, String phoneNo);
    //retrieve user by email
    Optional<UserAccountEntity> findByEmail(String email);
    Optional<UserAccountEntity> findByStaffNo(String staffNo);
    Boolean existsByStaffNo(String staffNo);
    Boolean existsByEmail(String email);
    UserAccountEntity findByResetPasswordToken(String token);
//    @Query("SELECT c FROM UserAccount c WHERE c.email = ?1")
//    UserAccount findByEmail(String email);
    //Optional<UserAccount> findByStaffNo(String staffNo);



}
