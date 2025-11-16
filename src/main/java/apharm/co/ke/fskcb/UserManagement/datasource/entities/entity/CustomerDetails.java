package apharm.co.ke.fskcb.UserManagement.datasource.entities.entity;


import apharm.co.ke.fskcb.UserManagement.datasource.entities.PersonalAccountType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "customer_details_tbl")
@SQLDelete(sql = "UPDATE customer_details_tbl SET status = 'D' WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class CustomerDetails {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    @Column(name="surname")
    private String surname;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private Date dob;
    @Column(name = "gender")
    private String gender;
    @Column(name = "company_youworkfor")
    private String companyYouWorkFor;
    @ManyToOne
    private PersonalAccountType accountType;
    @ManyToOne
    private EmploymentType employmentType;
    @Column(name = "income")
    private double income;
    @Lob
    @Column(name = "account_opening_purpose",length = 512)
    private String accountOpeningPurpose;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "created_on")
    private Date createdOn;
    @Column(name = "updated_on")
    private Date updatedOn;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @OneToMany(mappedBy = "customerDetails")
    @ToString.Exclude
    List<CustomerKYC> customerKYCList ;

}

