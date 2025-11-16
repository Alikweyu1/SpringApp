package apharm.co.ke.fskcb.UserManagement.datasource.entities;


import apharm.co.ke.fskcb.utils.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
@Data
@Entity
@Table(name = "dbo_users",uniqueConstraints = {@UniqueConstraint(columnNames = {"staff_no"}),
        @UniqueConstraint(columnNames = {"email"})})
@SQLDelete(sql = "UPDATE dbo_users SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class UserAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    @Column(name="staff_no", nullable = false)
    private String staffNo;
    @Column(nullable=false)
    private String email;
    @Column(nullable=false)
    private String fullName;
    @Column(nullable=false)
    private String phoneNumber;
    @Column(name="acc_type", nullable = false)
    //@Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    private Date lastLogin;
    private String lastCoords;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "dbo_user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRoleEntity> roles;
    private  boolean isVerified = Boolean.FALSE;
    private  boolean shouldSetPIN = Boolean.TRUE;
    private  boolean shouldChangePIN = false;
    private  boolean blocked = Boolean.FALSE;
    private boolean deleted = Boolean.FALSE;

    @Column(name="status", nullable = false)
    //@Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    @Column(name="login_attempts")
    private int remLoginAttempts = 4;
    @Column(name="date_blocked")
    private Date dateBlocked;
    @Column(name="last_modified")
    private Date lastModified;
}
