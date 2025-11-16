package apharm.co.ke.fskcb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {
    @ApiModelProperty(value = "Login staff no")
    @NotNull
    private String staffNo;
    @ApiModelProperty(value = "Login Password")
    @NotNull
    private String password;
}
