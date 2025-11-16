package apharm.co.ke.fskcb.UserManagement.datasource.entities;


import apharm.co.ke.fskcb.utils.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
@Setter
@Getter
@Entity
@Table(name = "dbo_roles")
@SQLDelete(sql = "UPDATE dbo_roles SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 60)
    private String name;
    private String info;
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private RoleType type;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    private Date createdOn;
    private Boolean deleted = Boolean.FALSE;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "dbo_role_profiles",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "profile_id", referencedColumnName = "id"))
    private Set<UserProfileEntity> userProfiles;
}
