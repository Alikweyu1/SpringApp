package apharm.co.ke.fskcb.UserManagement.datasource.entities;


import apharm.co.ke.fskcb.utils.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "dbo_profiles")
@SQLDelete(sql = "UPDATE dbo_profiles SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 120)
    private String name;
    @Column(length = 60)
    private String code;
    private String info;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    private Boolean deleted = Boolean.FALSE;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
}
