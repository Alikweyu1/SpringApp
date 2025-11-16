package apharm.co.ke.fskcb.UserManagement.datasource.entities;


import apharm.co.ke.fskcb.utils.Status;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dbo_roles_profiles")
public class ProfileAndRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="role_id")
    private Long roleId;
    @Column(name="profile_id")
    private Long profileId;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
}
