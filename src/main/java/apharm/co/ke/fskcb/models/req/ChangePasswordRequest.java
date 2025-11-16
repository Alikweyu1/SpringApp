package apharm.co.ke.fskcb.models.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
}
