package apharm.co.ke.fskcb.UserManagement.datasource.entities.entity;

import apharm.co.ke.fskcb.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employmenttype_tb")
@SQLDelete(sql = "UPDATE employmenttype_tb SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class EmploymentType {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String employmentTypeName;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    private Boolean deleted = Boolean.FALSE;
}
