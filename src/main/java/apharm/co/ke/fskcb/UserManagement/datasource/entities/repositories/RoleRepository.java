package apharm.co.ke.fskcb.UserManagement.datasource.entities.repositories;

import apharm.co.ke.fskcb.UserManagement.datasource.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRoleEntity, Long> {
    @Query(
            value =  "SELECT FROM dbo_roles WHERE id NOT IN (SELECT role_id FROM dbo_user_roles WHERE user_id = ?1)",
            nativeQuery = true
    )
    List<UserRoleEntity> getUserNotRoles(Long userId);
    Optional<UserRoleEntity> findByName(String name);


}