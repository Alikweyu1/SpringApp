package apharm.co.ke.fskcb.entity;

import apharm.co.ke.fskcb.AuthModule.services.datasource.entities.AuthCodeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_auth_codes")
public class SecurityAuthCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id",nullable=false)
    private Long userId;
    @Column(nullable=false)
    private String code;
    @Column(name="date_issued",nullable=false)
    private Date dateIssued = Calendar.getInstance().getTime();
    @Column(name="expires_in_minutes",nullable=false)
    private Integer expiresInMinutes = 10;
    @Column(nullable=false)
    private AuthCodeType type;
    @Column(nullable=false)
    private Boolean expired = false;
}
