package apharm.co.ke.fskcb.UserManagement.datasource.entities;


import apharm.co.ke.fskcb.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personal_account_type_tb")
@SQLDelete(sql = "UPDATE personal_account_type_tb SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class PersonalAccountType {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name="personal_account_type_name")
    private String personalAccountTypeName;

    //    @Column(name = "status", columnDefinition = "varchar(1) default '' comment 'A = Active, I = Inactive, D = Deleted'")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name ="created_on")
    private Date createdOn;

    @Column(name ="updated_on")
    private Date updatedOn;

    @Column(name ="created_by")
    private Long createdBy;

    @Column(name ="updated_by")
    private Long updatedBy;

    private Boolean deleted = Boolean.FALSE;

}

