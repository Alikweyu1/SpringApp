package apharm.co.ke.fskcb.UserManagement.datasource.entities.repositories;

import apharm.co.ke.fskcb.UserManagement.datasource.entities.UserProfileEntity;
import apharm.co.ke.fskcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserProfilesRepository extends JpaRepository<UserProfileEntity,Long> {
    @Query(
            value =  "SELECT FROM dbo_user_profiles WHERE id NOT IN (SELECT privilege_id FROM role_privileges WHERE role_id = ?1)",
            nativeQuery = true
    )
    List<UserProfileEntity> getRoleNotPrivilege(Long roleId);
    Optional<UserProfileEntity> findByName(String name);
    List<UserProfileEntity> findAllByStatus(Status status);

    Optional<UserProfileEntity> findByCode(String code);

}
