package apharm.co.ke.fskcb.repositories;

import apharm.co.ke.fskcb.AuthModule.services.datasource.entities.LoginLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogsRepository extends JpaRepository<LoginLogs,Long> {

}
