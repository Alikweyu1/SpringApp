package apharm.co.ke.fskcb.UserManagement.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Validated
@ApiModel(description = "Sign up requirements")
public class AddAdminUserRequest {
    @NotNull
    @ApiModelProperty(value = "User's full name", required = true)
    private String fullName;

    @NotNull
    @ApiModelProperty(value = "User's staff No", required = true)
    private String staffNo;

    @ApiModelProperty(value = "Phone Number")
    @NotNull
    private String phoneNo;

    @ApiModelProperty(value = "Email")
    @Email
    private String email;
    private Boolean isRm;
//
//    @ApiModelProperty(value = "Password")
//    @NotNull
//    private String password;

}

