package apharm.co.ke.fskcb.repositories;

import apharm.co.ke.fskcb.entity.SecurityAuthCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISecurityAuthCodesRepository extends JpaRepository<SecurityAuthCodeEntity, Long> {

    List<SecurityAuthCodeEntity> findAllByUserId(Long id);

    Optional<SecurityAuthCodeEntity> findAllByCode(String code);
    List<SecurityAuthCodeEntity> findAllByExpiredFalse();
}
