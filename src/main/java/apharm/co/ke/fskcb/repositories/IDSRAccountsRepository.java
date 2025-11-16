package apharm.co.ke.fskcb.repositories;

import apharm.co.ke.fskcb.entity.DSRAccountEntity;
import apharm.co.ke.fskcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDSRAccountsRepository extends JpaRepository<DSRAccountEntity, Long> {


    List<DSRAccountEntity> findByStatus(Status status);
    Optional<DSRAccountEntity> findById(long id);
    List<DSRAccountEntity> findAllByTeamId(long teamId);

    Optional<DSRAccountEntity> findByStaffNo(String staffNo);
    Optional<DSRAccountEntity> findByStaffNoAndPhoneNo(String staffNo,String phoneNo);

    Optional<DSRAccountEntity> findByEmail(String email);

    List<DSRAccountEntity> findByProfileCode(String profileCode);

    List<DSRAccountEntity> findByTargetId(Long targetId);

    List<DSRAccountEntity> findByAgencyTargetId(Long targetId);

    List<DSRAccountEntity> findByCbTargetId(Long targetId);

    List<DSRAccountEntity> findByPsTargetId(Long targetId);

    List<DSRAccountEntity> findByTreasuryTargetId(Long targetId);

    List<DSRAccountEntity> findByVoomaTargetId(Long targetId);

    Optional<Object> findBySalesCode(String salesCode);

    Optional<Object>findBystaffNo(String staffNo);


//    DSRAccountEntity findByDsrId(Long dsrId);
}
