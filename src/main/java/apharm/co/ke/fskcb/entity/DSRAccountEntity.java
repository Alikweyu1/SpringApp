package apharm.co.ke.fskcb.entity;

import apharm.co.ke.fskcb.utils.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "dbo_dsr_accounts",uniqueConstraints = {@UniqueConstraint(columnNames = {"staff_no"}),
        @UniqueConstraint(columnNames = {"email"})})
public class DSRAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="phone_no",nullable = false)
    private String phoneNo;

    @Column(name="phone_verified")
    private Boolean phoneNoVerified = false;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="cat")
    private String staffCategory;
    private Boolean isRm;

    @Column(name="staff_no",nullable = false)
    private String staffNo;

    @Column(name="sales_code")
    private String salesCode;

    @Column(name="status",nullable = false)
    //@Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name="full_name",nullable = false)
    private String fullName;
    private String profileCode;

    @Column(name="location")
    private String location;

    @Column(name="gender")
    private String gender;

    @Column(name="national_id")
    private String idNumber;

//    @Column(name="system_user_id")
//    private Long systemUserId;

    //    @ManyToOne
//    private DSRTeamEntity dsrTeam;
    @Column(name="team_id",nullable = false)
    private Long teamId;

    @Column(name="branch_id",nullable = true)
    private Long branchId;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_on",nullable = false)
    private Date createdOn = Calendar.getInstance().getTime();

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name="updated_by")
    private String updatedBy;

    @Column(name="expires_on")
    private Date expiryDate;

    @Column(name="locked")
    private Boolean locked;

    @Column(name="date_unlocked")
    private Date dateLocked;
    @Column(name="date_locked")
    private Date dateUnlocked;
    private String targetValue;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "dfs_vooma_target_dsr",
//            joinColumns = @JoinColumn(name = "dsrAccountId"),
//            inverseJoinColumns = @JoinColumn(name = "dfsVoomaTargetId"))
//    private Set<DFSVoomaTargetEntity> dfsVoomaTargetEntities;

    private String campaignTargetValue;
    private String leadsTargetValue;
    private Long targetId;
    private String visitsTargetValue;
    private String volumeTargetValue;
    private String onboardTargetValue;
    private String MFITargetValue;
    private String crossSalesTargetValue;
    private String productTargetValue;
    private String assetLiabilityTargetValue;
    private String financialServiceTargetValue;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "acquiring_target_dsr",
//            joinColumns = @JoinColumn(name = "dsrAccountId"),
//            inverseJoinColumns = @JoinColumn(name = "acquiringTargetId"))
//    private Set<AcquiringTargetEntity> acquiringTargetEntities;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "agency_target_dsr",
//            joinColumns = @JoinColumn(name = "dsrAccountId"),
//            inverseJoinColumns = @JoinColumn(name = "agencyTargetId"))
//    private Set<AgencyBankingTargetEntity> agencyBankingTargetEntities;
    private Long agencyTargetId;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "ps_banking_target_dsr",
//            joinColumns = @JoinColumn(name = "dsrAccountId"),
//            inverseJoinColumns = @JoinColumn(name = "psBankingTargetId"))
//    private Set<PSBankingTargetEntity> psBankingTargetEntities;
    private Long psTargetId;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "premium_banking_target_dsr",
//            joinColumns = @JoinColumn(name = "dsrAccountId"),
//            inverseJoinColumns = @JoinColumn(name = "premiumBankingTargetId"))
//    private Set<PSTargetEntity> premiumTargetEntities;
//    private Long premiumTargetId;
//
//    //TreasuryTargetEntity
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "treasury_target_dsr",
//            joinColumns = @JoinColumn(name = "dsrAccountId"),
//            inverseJoinColumns = @JoinColumn(name = "treasuryTargetId"))
//    private Set<TreasuryTargetEntity> treasuryTargetEntities;
    private Long treasuryTargetId;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "cb_target_dsr",
//            joinColumns = @JoinColumn(name = "dsrAccountId"),
//            inverseJoinColumns = @JoinColumn(name = "cbTargetId"))
//    private Set<CBTargetEntity> cbTargetEntities;
    private Long cbTargetId;
    private Long voomaTargetId;
//
//    @JsonIgnore
//    @ManyToMany(mappedBy = "assignedMembers")
//    private Set<MeetingDetailsEntity> appointmentSet = new HashSet<>();




}
